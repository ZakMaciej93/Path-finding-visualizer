package algorithms;

import graph.Edge;
import graph.Vertex;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra extends AbstractSearchAlgorithm {

    protected PriorityQueue<Edge> queue;

    public Dijkstra() {
        super();
        Comparator c = new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                DijkstraVertexData vD1 = (DijkstraVertexData) vertexData.get(o1.getHead());
                DijkstraVertexData vD2 = (DijkstraVertexData) vertexData.get(o2.getHead());
                return Double.compare(vD1.getCost(), vD2.getCost());
            }
        };
        queue = new PriorityQueue<>(c);
        super.sEmptyCollection = "Kolejka jest pusta - algorytm kończy działanie";
    }

    @Override
    protected boolean isNotEmpty() {
        return !queue.isEmpty();
    }

    @Override
    protected void addEdge(Edge e) {
        queue.add(e);
    }

    @Override
    protected Edge nextEdge() {
        return queue.remove();
    }

    @Override
    protected void clear() {
        queue.clear();
    }

    @Override
    protected String examineEdge(Edge e) {
        String s = "";
        Vertex head = e.getHead();
        DijkstraVertexData headData = (DijkstraVertexData) vertexData.get(head);
        if (!headData.getVisited()) {
            s = head + ": ";
            Vertex tail = e.getTail();
            DijkstraVertexData tailData = (DijkstraVertexData) vertexData.get(tail);
            double newCost = tailData.getCost() + e.getWeight();
            s += tailData.getCost() + " + " + e.getWeight() + " < "
                    + headData.getCost() + " ? ";
            if (newCost < headData.getCost()) {
                s += "\nTAK - zmiana kosztu " + head.toString() + " na " + newCost
                        + " i poprzednika na " + tail.toString();
                s += "\n" + head.toString() + " zostaje dopisany do kolejki";
                vertexData.put(head, new DijkstraVertexData(tail, false, newCost));
                addEdge(e);
            } else {
                s += "\nNIE - bez zmian";
            }
        }
        return s;
    }

    @Override
    public String getDescription() {
        return "-Przeszukuje graf korzystając z mechanizmu kolejki priorytetowej,"
                + " gdzie priorytetem są koszty dotarcia do wierzchołka z "
                + "wierzchołka startowego.\n-W przypadku wystąpienia ujemnych"
                + " wag może zwrócić niewłaściwy wynik\n-Jeżeli zostanie "
                + "podany wierzchołek docelowy, to algorytm przerwie działanie "
                + "po dotraciu do niego";
    }

    @Override
    public String[] getColumns() {
        return new String[]{"Wierzcholek", "Poprzednik", "Odwiedzony", "Koszt"};
    }

    @Override
    public String[][] getRows() {
        int size = graph.size();
        String[][] rows = new String[size][4];
        for (int y = 0; y < size; y++) {
            Vertex v = graph.get(y);
            DijkstraVertexData vD = (DijkstraVertexData) vertexData.get(v);
            rows[y][0] = v.toString();
            rows[y][1] = vD.getPredecessor() == null ? "Brak" : vD.getPredecessor().toString();
            rows[y][2] = "" + vD.getVisited();
            rows[y][3] = "" + vD.getCost();
        }
        return rows;
    }

    @Override
    protected void initializeVertexData() {
        for (Vertex v : graph) {
            vertexData.put(v, new DijkstraVertexData(null, false, Double.POSITIVE_INFINITY));
        }
        if (initialNode != null) {
            DijkstraVertexData vD = (DijkstraVertexData) vertexData.get(initialNode);
            vD.setCost(0d);
        }
    }
}

class DijkstraVertexData extends SearchVertexData {

    private double cost;

    public DijkstraVertexData(Vertex predecessor, Boolean visited, double cost) {
        super(predecessor, visited);
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}
