package algorithms;

import graph.Edge;
import graph.Vertex;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStar extends Dijkstra {

    public AStar() {
        super();
        Comparator c = new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                AStarVertexData vD1 = (AStarVertexData) vertexData.get(o1.getHead());
                AStarVertexData vD2 = (AStarVertexData) vertexData.get(o2.getHead());
                return Double.compare(vD1.getValue(), vD2.getValue());
            }
        };
        queue = new PriorityQueue<>(c);
    }

    @Override
    public String nextStep() {
        if (destinationNode != null) {
            return super.nextStep();
        } else {
            finished = true;
            super.showError("Nie wybrano wierzchołka docelowego");
            return "";
        }
    }

    @Override
    public String[][] getRows() {
        String[][] rows = new String[graph.size()][6];
        for (int y = 0; y < graph.size(); y++) {
            Vertex v = graph.get(y);
            AStarVertexData vD = (AStarVertexData) vertexData.get(v);
            rows[y][0] = v.toString();
            rows[y][1] = vD.getPredecessor() == null ? "Brak" : vD.getPredecessor().toString();
            rows[y][2] = "" + vD.getVisited();
            rows[y][3] = "" + vD.getCost();
            rows[y][4] = "" + vD.getDistance();
            rows[y][5] = "" + vD.getValue();
        }
        return rows;
    }

    @Override
    public String[] getColumns() {
        return new String[]{"Wierzcholek", "Poprzednik", "Odwiedzony", "Koszt", "Dystans", "Wartość"};
    }

    @Override
    public String getDescription() {
        return "-Odnajduje najkrótszą ścieżkę pomiędzy wierzchołkiem startowym, a "
                + "docelowym.\n-Wymaga podania wierzchołka docelowego\n-Przeszukuje"
                + " graf korzystając z mechanizmu kolejki priorytetowej,"
                + " gdzie priorytetem jest wartość funkcji celu f(x)=g(x)+h(x)\n"
                + "gdzie:\ng(x) - łączny koszt dotarcia do wierzchołka\nh(x) - "
                + "wartość funkcji heurystycznej (w tym przypadku odległość w linii "
                + "prostej między wierzchołkami w metryce euklidesowej)\n";
    }

    @Override
    protected String examineEdge(Edge e) {
        String s = "";
        Vertex head = e.getHead();
        AStarVertexData headData = (AStarVertexData) vertexData.get(head);
        if (!headData.getVisited()) {
            s = head + ": ";
            Vertex tail = e.getTail();
            AStarVertexData tailData = (AStarVertexData) vertexData.get(tail);
            double newCost = tailData.getCost() + e.getWeight();
            double newDistance = getDistance(head, destinationNode);
            double newValue = newCost + newDistance;
            s += newCost + " + " + newDistance + " < " + headData.getValue() + " ? ";
            if (newValue < headData.getValue()) {
                s += "\nTAK - zmiana wartości " + head.toString() + " na " + newValue + " i poprzednika na " + tail;
                s += "\n" + head.toString() + " zostaje dopisany do kolejki";
                vertexData.put(head, new AStarVertexData(tail, false, newCost, newValue, newDistance));
                addEdge(e);
            } else {
                s += "\nNIE - bez zmian";
            }
        }
        return s;
    }

    @Override
    protected void initializeVertexData() {
        for (Vertex v : graph) {
            vertexData.put(v, new AStarVertexData(null, false, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        }
        if (initialNode != null && destinationNode != null) {
            AStarVertexData vD = (AStarVertexData) vertexData.get(initialNode);
            vD.setCost(0d);
            vD.setDistance(getDistance(initialNode, destinationNode));
            vD.setValue(vD.getCost() + vD.getDistance());
        }
    }

    private double getDistance(Vertex a, Vertex b) {
        return a.getCords().distance(b.getCords());
    }
}

class AStarVertexData extends DijkstraVertexData {

    private double value;
    private double distance;

    public AStarVertexData(Vertex predecessor, Boolean visited, double cost, double value, double distance) {
        super(predecessor, visited, cost);
        this.value = value;
        this.distance = distance;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
