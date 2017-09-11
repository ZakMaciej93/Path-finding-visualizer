package algorithms;

import graph.Edge;
import graph.Vertex;
import java.util.Stack;

public class DFS extends AbstractSearchAlgorithm {
    private final Stack<Edge> stack;
    
    public DFS() {
        super();
        stack=new Stack<>();
        super.sEmptyCollection="Stos jest pusty - algorytm kończy działanie";
    }
    
    @Override
    protected boolean isNotEmpty() {
        return !stack.isEmpty();
    }
    
    @Override
    protected void addEdge(Edge e) {
        stack.push(e);
    }
    
    @Override
    protected Edge nextEdge() {
        return stack.pop();
    }
    
    @Override
    public String getDescription() {
        return "-Przeszukuje graf korzystając z mechanizmu stosu.\n-Wagi krawędzi"
                + " są ignorowane.\n-Jeżeli zostanie podany wierzchołek docelowy,"
                + " to algorytm przerwie działanie po dotraciu do niego";
    }
    
    @Override
    protected void clear() {
        stack.clear();
    }
    
    @Override
    protected String examineEdge(Edge e) {
        String s = "";
        if (!isVisited(e.getHead())) {
            s += e.getHead().toString();
            addEdge(e);
        }
        return s;
    }

    @Override
    public String[] getColumns() {
        return new String[]{"Wierzcholek", "Poprzednik", "Odwiedzony"};
    }

    @Override
    public String[][] getRows() {
        int size = graph.size();
        String[][] rows = new String[graph.size()][3];
        for (int y = 0; y < size; y++) {
            Vertex v = graph.get(y);
            SearchVertexData vD = (SearchVertexData)vertexData.get(v);
            rows[y][0] = v.toString();
            rows[y][1] = vD.getPredecessor() == null ? "Brak" : vD.getPredecessor().toString();
            rows[y][2] = "" + vD.getVisited();
        }
        return rows;
    }

    @Override
    protected void initializeVertexData() {
        for (Vertex v : graph) {
            vertexData.put(v, new SearchVertexData(null, false));
        }
    }
    
}