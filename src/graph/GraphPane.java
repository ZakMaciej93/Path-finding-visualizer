package graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JLayeredPane;

public class GraphPane extends JLayeredPane {
    private Polygon arrowHead;
    private List<Vertex> graph;
    private final List<GraphChangeListener> graphChangeListeners;
    boolean labelEnabled;
    boolean weightEnabled;
    boolean edgeEnabled;
    boolean vertexEnabled;
    boolean cordsEnabled;

    public GraphPane() {
        this.graph = new ArrayList<>();
        arrowHead = new Polygon();
        arrowHead.addPoint(0, 5);
        arrowHead.addPoint(-5, -5);
        arrowHead.addPoint(5, -5);
        this.graphChangeListeners = new ArrayList<>();
    }

    public void addGraphChangeListener(GraphChangeListener l) {
        this.graphChangeListeners.add(l);
    }

    public void removeGraphChangeListener(GraphChangeListener l) {
        this.graphChangeListeners.remove(l);
    }

    protected void graphChanged() {
        GraphChangeEvent evt = new GraphChangeEvent(this);
        for (GraphChangeListener l : graphChangeListeners) {
            l.graphChanged(evt);
        }
    }

    public void clearGraph() {
        Iterator vertexIterator = graph.iterator();
        while (vertexIterator.hasNext()) {
            Vertex v = (Vertex) vertexIterator.next();
            Iterator edgeIterator = v.getNeighbourhood().iterator();
            while (edgeIterator.hasNext()) {
                Edge e = (Edge) edgeIterator.next();
                this.remove(e);
                edgeIterator.remove();
            }
        }
        vertexIterator = graph.iterator();
        while (vertexIterator.hasNext()) {
            Vertex v = (Vertex) vertexIterator.next();
            this.remove(v);
            vertexIterator.remove();
        }
        graph.clear();
        repaint();
        graphChanged();
    }

    public boolean addVertex(Vertex v) {
        if (graph.contains(v)) {
            return false;
        } else {
            v.setVisible(vertexEnabled);
            this.graph.add(v);
            this.add(v, -1);
            v.addGraphElementChangeListener(new GraphElementChangeListener() {
                @Override
                public void graphElementChanged(GraphElementChangeEvent evt) {
                    graphChanged();
                }
            });
            graphChanged();
            return true;
        }
    }

    public boolean addEdge(Edge e) {
        if (e.getTail().addEdge(e)) {
            e.setLabelEnabled(labelEnabled);
            e.setWeightEnabled(weightEnabled);
            this.add(e, -1);
            e.addGraphElementChangeListener(new GraphElementChangeListener() {
                @Override
                public void graphElementChanged(GraphElementChangeEvent evt) {
                    graphChanged();
                }
            });
            graphChanged();
            repaint();
            return true;
        } else {
            return false;
        }
    }

    public void removeEdge(Edge e) {
        this.remove(e);
        for (Vertex v : graph) {
            if (v.getNeighbourhood().contains(e)) {
                v.getNeighbourhood().remove(e);
                break;
            }
        }
        graphChanged();
        repaint();
    }

    public void removeVertex(Vertex v) {
        for (Vertex t : graph) {
            Iterator it = t.getNeighbourhood().iterator();
            while (it.hasNext()) {
                Edge e = (Edge) it.next();
                if (e.getTail().equals(v) || e.getHead().equals(v)) {
                    this.remove(e);
                    it.remove();
                }
            }
        }
        this.graph.remove(v);
        this.remove(v);
        graphChanged();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g.create();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Vertex v : graph) {
            if (edgeEnabled) {
                for (Edge e : v.getNeighbourhood()) {
                    double x1 = e.getTail().getX() + Vertex.DEF_RADIUS;
                    double y1 = e.getTail().getY() + Vertex.DEF_RADIUS;
                    double x2 = e.getX() + e.getWidth() / 2;
                    double y2 = e.getY() + e.getHeight() / 2;
                    Point p = getCircleLineIntersectionPoint(e.getLocation(), e.getHead().getLocation(), Vertex.DEF_RADIUS + 2);
                    QuadCurve2D q = new QuadCurve2D.Double(x1, y1, x2, y2, p.x + Vertex.DEF_RADIUS, p.y + Vertex.DEF_RADIUS);
                    g2D.setColor(e.isMarked() ? Color.GRAY : Color.BLACK);
                    g2D.draw(q);
                    double angle = Math.atan2(p.y - e.getY(), p.x - e.getX());
                    AffineTransform t = g2D.getTransform();
                    g2D.translate(p.x + Vertex.DEF_RADIUS, p.y + Vertex.DEF_RADIUS);
                    g2D.rotate((angle - Math.PI / 2d));
                    g2D.fill(arrowHead);
                    g2D.setTransform(t);
                }
            }
            if (cordsEnabled) {
                g2D.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
                g2D.setColor(Color.BLACK);
                g2D.drawString("[" + v.getCords().getX() + "," + v.getCords().getY() + "]", v.getX() + Vertex.DEF_RADIUS + 1, v.getY() + 1);
                g2D.setColor(Color.WHITE);
                g2D.drawString("[" + v.getCords().getX() + "," + v.getCords().getY() + "]", v.getX() + Vertex.DEF_RADIUS, v.getY());
            }
        }
        g2D.dispose();
        super.paintComponent(g);
    }

    public void updateEdges(Vertex v) {
        for (Vertex t : graph) {
            for (Edge e : t.getNeighbourhood()) {
                if (v.equals(e.getHead()) || v.equals(e.getTail())) {
                    e.updateLocation();
                }
            }
        }
        repaint();
    }

    public Point getCircleLineIntersectionPoint(Point pointA, Point pointB, double radius) {
        double distanceX = pointB.x - pointA.x;
        double distanceY = pointB.y - pointA.y;

        double a = Math.pow(distanceX, 2) + Math.pow(distanceY, 2);
        double c = a - Math.pow(radius, 2);

        double disc = 1 - c / a;
        double tmpSqrt = Math.sqrt(disc);
        double scalingFactor1 = tmpSqrt - 1;
        return new Point((int) (pointA.x - distanceX * scalingFactor1), (int) (pointA.y - distanceY * scalingFactor1));
    }

    public boolean isLabelEnabled() {
        return labelEnabled;
    }

    public void setLabelEnabled(boolean labelEnabled) {
        this.labelEnabled = labelEnabled;
        for (Vertex v : graph) {
            for (Edge e : v.getNeighbourhood()) {
                e.setLabelEnabled(labelEnabled);
            }
        }
        repaint();
    }

    public boolean isWeightEnabled() {
        return weightEnabled;
    }

    public void setWeightEnabled(boolean weightEnabled) {
        this.weightEnabled = weightEnabled;
        for (Vertex v : graph) {
            for (Edge e : v.getNeighbourhood()) {
                e.setWeightEnabled(weightEnabled);
            }
        }
        repaint();
    }

    public boolean isEdgeEnabled() {
        return edgeEnabled;
    }

    public void setEdgeEnabled(boolean edgeEnabled) {
        this.edgeEnabled = edgeEnabled;
        repaint();
    }

    public boolean isVertexEnabled() {
        return vertexEnabled;
    }

    public void setVertexEnabled(boolean vertexEnabled) {
        this.vertexEnabled = vertexEnabled;
        for (Vertex v : graph) {
            v.setVisible(vertexEnabled);
        }
    }

    public boolean isCordsEnabled() {
        return cordsEnabled;
    }

    public void setCordsEnabled(boolean cordsEnabled) {
        this.cordsEnabled = cordsEnabled;
        repaint();
    }

    public List<Vertex> getGraph() {
        return graph;
    }

}
