package algorithms;

import graph.Edge;
import graph.Vertex;
import java.awt.Point;
import java.util.List;

public class FloydWarshall extends AbstractAlgorithm {

    private int predecessor[][];
    private double cost[][];
    private int size;
    private int indexK;
    private int indexI;
    private int indexJ;

    @Override
    public String nextStep() {
        String step = "";
        if (indexK < size && indexI < size && indexJ < size) {
            step = visit(indexK, indexJ, indexI);
            incrIndexes();
        } else {
            finished = true;
            step = "Główna pętla algorytmu została wykonana\nCzy graf zawiera cykl ujemny?\n";
            if (hasNegativeCycle()) {
                step += "Tak - nie można zwrócić poprawnego wyniku";
            } else {
                step += "Nie - ścieżki i ich koszty mogą być odczytane z tabelki";
                if (initialNode != null && destinationNode != null) {
                    step += markPath();
                }
            }
        }
        return step;
    }

    protected void incrIndexes() {
        indexJ++;
        if (indexJ >= size) {
            resetMarks();
            indexI++;
            if (indexI >= size) {
                indexK++;
                indexI = 0;
            }
            indexJ = 0;
        }
    }

    protected boolean hasNegativeCycle() {
        boolean result = false;
        for (int i = 0; i < size; i++) {
            if (cost[i][i] < 0) {
                result = true;
            }
        }
        return result;
    }

    protected String markPath() {
        int init = graph.indexOf(initialNode);
        int index = graph.indexOf(destinationNode);
        if (predecessor[init][index] == -1) {
            return "\n\nNie można utworzyć ścieżki od wierzchołka początkowego do końcowego";
        } else {
            do {
                Vertex v = graph.get(index);
                v.setMark(Vertex.MARKED_ACTIVE);
                index = predecessor[init][index];
            } while (index != -1);
        }
        return "";
    }

    @Override
    public String getDescription() {
        return "-Oblicza łączne koszty ścieżek pomiędzy wszystkimi parami "
                + "wierzchołków\n-Dopuszcza ujemne wagi\n-Wykrywa cykle ujemne"
                + "\n-Jeżeli zostanie podany wierzchołek docelowy, to zostanie "
                + "odtworzona ścieżka pomiędzy nim a wierzchołkiem startowym o "
                + "ile taka istnieje";
    }

    @Override
    public String[] getColumns() {
        String[] columns = new String[size + 1];
        columns[0] = "Koszt|Poprzednik";
        for (int y = 0; y < size; y++) {
            columns[y + 1] = graph.get(y).toString();
        }
        return columns;
    }

    @Override
    public String[][] getRows() {
        String[][] rows = new String[size][size + 1];
        for (int y = 0; y < size; y++) {
            rows[y][0] = graph.get(y).toString();
            for (int x = 0; x < size; x++) {
                rows[y][x + 1] = cost[y][x] + " | " + (predecessor[y][x] == -1 ? "Brak" : graph.get(predecessor[y][x]).toString());
            }
        }
        return rows;
    }

    protected void initializeVertexData() {
        predecessor = new int[size][size];
        cost = new double[size][size];
        for (int i = 0; i < size; i++) {
            Vertex v = graph.get(i);
            for (int j = 0; j < size; j++) {
                cost[i][j] = (i == j ? 0d : Double.POSITIVE_INFINITY);
                predecessor[i][j] = -1;
            }
            for (Edge e : v.getNeighbourhood()) {
                int head = graph.indexOf(e.getHead());
                cost[i][head] = e.getWeight();
                predecessor[i][head] = i;
            }
        }
    }

    @Override
    public void reset(List<Vertex> graph, Vertex initialNode, Vertex destinationNode) {
        super.reset(graph, initialNode, destinationNode);
        size = graph.size();
        if (initialNode != null) {
            point = initialNode.getLocation();
        } else {
            point = new Point(20, 20);
        }
        initializeVertexData();
        indexK = indexJ = indexI = 0;
    }

    private String visit(int indexK, int indexJ, int indexI) {
        String s = "k=" + indexK + " i=" + indexI + " j=" + indexJ;
        Vertex v = graph.get(indexJ);
        v.setMark(Vertex.MARKED_INACTIVE);
        point = v.getLocation();
        double newCost = cost[indexI][indexK] + cost[indexK][indexJ];
        s += "\n" + cost[indexI][indexJ] + " > " + cost[indexI][indexK] + " + " + cost[indexK][indexJ] + " ?";
        if (cost[indexI][indexJ] > newCost) {
            cost[indexI][indexJ] = newCost;
            predecessor[indexI][indexJ] = predecessor[indexK][indexJ];
            Vertex vB = graph.get(indexI);
            s += "\nTak - zmiana kosztu [" + vB.toString() + "," + v.toString() + "] na " + newCost;
            s += "\ni poprzednika na" + graph.get(predecessor[indexK][indexJ]);
        } else {
            s += "\nNie - brak zmian";
        }
        return s;
    }

}
