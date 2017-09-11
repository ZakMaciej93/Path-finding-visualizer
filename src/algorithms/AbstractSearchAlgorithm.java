package algorithms;

import graph.Edge;
import graph.Vertex;
import java.util.List;

public abstract class AbstractSearchAlgorithm extends AbstractAlgorithm {
    protected String sEmptyCollection;
    
    @Override
    public void reset(List<Vertex> graph, Vertex initialNode, Vertex destinationNode) {
        super.reset(graph, initialNode, destinationNode);
        clear();
        initializeVertexData();
        if (initialNode != null) {
            addEdge(new Edge(null, initialNode, ""));
        }
    }

    protected abstract boolean isNotEmpty();
    protected abstract void initializeVertexData();

    protected boolean isVisited(Vertex v) {
        SearchVertexData vD=(SearchVertexData)vertexData.get(v);
        return vD.getVisited();
    }

    protected void markVisited(Edge e) {
        SearchVertexData vD=(SearchVertexData)vertexData.get(e.getHead());
        vD.setPredecessor(e.getTail());
        vD.setVisited(true);
        e.getHead().setMark(Vertex.MARKED_INACTIVE);
        e.setMarked(true);
    }
    protected abstract void addEdge(Edge e);

    protected abstract Edge nextEdge();

    protected abstract void clear();

    protected abstract String examineEdge(Edge e);
    
    @Override
    public String nextStep() {
        String step = "";
        if (initialNode != null) {
            if (isNotEmpty()) {
                step = visit(nextEdge());
            } else {
                step = sEmptyCollection;
                finished = true;
            }
        } else {
            finished = true;
            super.showError("Nie wybrano wierzchołka startowego");
        }
        return step;
    }

    protected String visit(Edge e) {
        String s;
        Vertex v = e.getHead();
        point = v.getLocation();
        if (isVisited(v)) {
            s = nextStep();
        } else {
            s = "Odwiedzany jest wierzchołek " + v;
            markVisited(e);
            if (v.equals(destinationNode)) {
                s += " (wierzchołek docelowy).\nAlgorytm kończy działanie.";
                finished = true;
                markPath(v);
                return s;
            } else if (v.equals(initialNode)) {
                s += " (wierzchołek startowy)";
            }
            v.setMark(Vertex.MARKED_INACTIVE);
            List<Edge> n = v.getNeighbourhood();
            if (n.size() > 0) {
                s += "\nBadani są jego sąsiedzi:";
            }
            for (Edge t : n) {
                if (!isVisited(t.getHead())) {
                    s += "\n-" + examineEdge(t);
                }
            }
        }
        return s;
    }
}

class SearchVertexData extends VertexData{
    protected Boolean visited;

    public SearchVertexData(Vertex predecessor, Boolean visited) {
        super(predecessor);
        this.visited = visited;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

}
