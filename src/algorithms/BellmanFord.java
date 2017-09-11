package algorithms;

import graph.Edge;
import graph.Vertex;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BellmanFord extends AbstractAlgorithm {
    protected boolean changed;
    protected boolean canStop;
    protected List<Edge> edges;
    protected Iterator iterator;
    protected int index;
    protected int limit;

    public BellmanFord() {
        vertexData = new HashMap();
        edges = new LinkedList<>();
        iterator = edges.iterator();
    }

    @Override
    public void reset(List<Vertex> graph, Vertex initialNode, Vertex destinationNode) {
        super.reset(graph, initialNode, destinationNode);
        edges.clear();
        for (Vertex v : graph) {
            for (Edge e : v.getNeighbourhood()) {
                edges.add(e);
            }
        }
        initializeVertexData();
        iterator = edges.iterator();
        if(!iterator.hasNext()&&initialNode!=null)point=initialNode.getLocation();
        canStop=false;
        index = 0;
        limit = graph.size() - 1;
        changed = false;
    }

    @Override
    public String nextStep() {
        String step = "";
        if (initialNode != null) {
            Edge e = nextEdge();
            if (canStop || (index > limit)) {
                finished = true;
                if (changed) {
                    return "Graf zawiera cykl ujemny - nie można zwrócić poprawnego wyniku";
                } else {
                    step += "Algorytm kończy działanie - koszty ścieżek mogą być odczytane z tabelki";
                    if (destinationNode != null) {
                        if (vertexData.get(destinationNode).getPredecessor() == null) {
                            step += "\nNie można utworzyć ścieżki od wierzchołka początkowego do końcowego";
                        } else {
                            markPath(destinationNode);
                        }
                    }
                }
            } else {
                step += examineEdge(e);
            }
        } else {
            finished = true;
            super.showError("Nie wybrano wierzchołka startowego");
        }
        return step;
    }

    protected Edge nextEdge() {
        if (iterator.hasNext()) {
            return (Edge) iterator.next();
        } else {
            index++;
            if (changed && index <= limit) {
                iterator = edges.iterator();
                changed = false;
                resetMarks();
                return nextEdge();
            } else {
                canStop = true;
                return null;
            }
        }
    }

    private String examineEdge(Edge e) {
        String s = (index == limit ? "Sprawdzenie, czy występuje cykl ujemny" : "Obieg pętli " + (index + 1) + " z " + limit) + ":";
        Vertex head = e.getHead();
        Vertex tail = e.getTail();
        BFVertexData headData = (BFVertexData) vertexData.get(head);
        BFVertexData tailData = (BFVertexData) vertexData.get(tail);
        s += "\nOdwiedzana jest krawędź " + e.getLabel();
        point = e.getLocation();
        e.setMarked(true);
        double newCost = tailData.getCost() + e.getWeight();
        s += "\n" + headData.getCost() + " > " + tailData.getCost() + " + " + e.getWeight() + " ?";
        if (headData.getCost() > newCost) {
            s += "\nTAK - zmiana kosztu " + head + " na " + newCost + " i poprzednika na " + tail.toString();
            headData.setCost(newCost);
            headData.setPredecessor(tail);
            changed = true;
        } else {
            s += "\nNIE - brak zmian";
        }
        return s;
    }

    @Override
    public String getDescription() {
        return "-Oblicza łączne koszty ścieżek od wierzchołka startowego do wszystkich"
                + " pozostałych \n-Dopuszcza ujemne wagi\n-Wykrywa cykle ujemne\n"
                + "-Jeżeli zostanie podany wierzchołek docelowy, to zostanie "
                + "odtworzona ścieżka pomiędzy nim a wierzchołkiem startowym "
                + "o ile taka istnieje";
    }

    @Override
    public String[] getColumns() {
        return new String[]{"Wierzcholek", "Poprzednik", "Koszt"};
    }

    @Override
    public String[][] getRows() {
        String[][] rows = new String[graph.size()][3];
        for (int y = 0; y < graph.size(); y++) {
            Vertex v = graph.get(y);
            BFVertexData vD = (BFVertexData) vertexData.get(v);
            rows[y][0] = v.toString();
            rows[y][1] = vD.getPredecessor() == null ? "Brak" : vD.getPredecessor().toString();
            rows[y][2] = "" + vD.getCost();
        }
        return rows;
    }

    protected void initializeVertexData() {
        for (Vertex v : graph) {
            vertexData.put(v, new BFVertexData(null, Double.POSITIVE_INFINITY));
        }
        if (initialNode != null) {
            BFVertexData vD = (BFVertexData) vertexData.get(initialNode);
            vD.setCost(0d);
        }
    }

}

class BFVertexData extends VertexData {

    protected double cost;

    public BFVertexData(Vertex predecessor, double cost) {
        super(predecessor);
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}
