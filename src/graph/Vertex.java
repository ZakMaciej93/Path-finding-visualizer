package graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;

public class Vertex extends JButton {
    private boolean highlighted;
    private Point2D cords;
    private byte mark;
    private final List<Edge> neighbourhood;
    private final Ellipse2D shape;

    public static final byte DEF_RADIUS = 16;
    private static final Color DEF_FOREGROUND_COLOR = Color.BLACK;
    private static final Color DEF_START_COLOR = Color.LIGHT_GRAY;
    private static final Color DEF_END_COLOR = Color.GRAY;
    private static final Color ROLL_START_COLOR = Color.WHITE;
    private static final Color ROLL_END_COLOR = Color.LIGHT_GRAY;
    private static final Color PRESS_START_COLOR = Color.GRAY;
    private static final Color PRESS_END_COLOR = Color.DARK_GRAY;
    private static final Color BORDER_START_COLOR = Color.BLACK;
    private static final Color BORDER_END_COLOR = Color.DARK_GRAY;

    public static final byte NOT_MARKED = 0;
    public static final byte MARKED_ACTIVE = 1;
    public static final byte MARKED_INACTIVE = 2;
    public static final byte MARKED_START = 3;
    public static final byte MARKED_END = 4;
    public static final byte MARKED_ADDED = 5;

    private final List<GraphElementChangeListener> graphElementChangeListeners;

    public void addGraphElementChangeListener(GraphElementChangeListener l) {
        this.graphElementChangeListeners.add(l);
    }

    public void removeGraphElementChangeListener(GraphElementChangeListener l) {
        this.graphElementChangeListeners.remove(l);
    }

    protected void graphElementChanged() {
        GraphElementChangeEvent evt = new GraphElementChangeEvent(this);
        for (GraphElementChangeListener l : graphElementChangeListeners) {
            l.graphElementChanged(evt);
        }
    }

    public Vertex(String label, int x, int y) {
        super();
        this.graphElementChangeListeners = new LinkedList<>();
        this.shape = new Ellipse2D.Double(0, 0, DEF_RADIUS*2, DEF_RADIUS*2);
        this.neighbourhood = new LinkedList<>();
        this.cords=new Point2D.Double(x, y);
        setContentAreaFilled(false);
        setBorder(null);
        setForeground(DEF_FOREGROUND_COLOR);
        setFocusable(false);
        setSize(DEF_RADIUS*2, DEF_RADIUS*2);
        setLocation(x, y);
        setText(label);
    }

    @Override
    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2D = (Graphics2D) grphcs.create();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient;
        int h = getHeight();
        int w = getWidth();
        if (model.isPressed()) {
            gradient = new GradientPaint(0, 0, PRESS_START_COLOR, w, h, PRESS_END_COLOR, true);
        } else if (model.isRollover()) {
            gradient = new GradientPaint(0, 0, ROLL_START_COLOR, w, h, ROLL_END_COLOR, true);
        } else {
            gradient = new GradientPaint(0, 0, DEF_START_COLOR, w, h, DEF_END_COLOR, true);
        }
        setForeground(DEF_FOREGROUND_COLOR);
        switch (mark) {
            case MARKED_ACTIVE:
                gradient = new GradientPaint(0, 0, Color.GREEN, w, h, Color.YELLOW, true);
                break;
            case MARKED_INACTIVE:
                gradient = new GradientPaint(0, 0, Color.DARK_GRAY, w, h, Color.DARK_GRAY, true);
                setForeground(Color.WHITE);
                break;
            case MARKED_ADDED:
                gradient = new GradientPaint(0, 0, Color.WHITE, w, h, Color.LIGHT_GRAY, true);
                break;
            case MARKED_START:
                gradient = new GradientPaint(0, 0, Color.YELLOW, w, h, Color.ORANGE, true);
                break;
            case MARKED_END:
                gradient = new GradientPaint(0, 0, Color.BLUE, w, h, Color.CYAN, true);
                break;
        }
        g2D.setPaint(gradient);
        g2D.fillOval(0, 0, w - 1, h - 1);
        g2D.setStroke(new BasicStroke(2));
        if (highlighted) {
            gradient = new GradientPaint(0, 0, Color.GREEN, w, h, Color.YELLOW, true);
            g2D.setStroke(new BasicStroke(2));
        } else {
            gradient = new GradientPaint(0, 0, BORDER_START_COLOR, w, h, BORDER_END_COLOR, true);
            g2D.setStroke(new BasicStroke(1));
        }
        g2D.setPaint(gradient);
        g2D.drawOval(0, 0, w - 1, h - 1);
        g2D.dispose();
        super.paintComponent(grphcs);
    }

    public void setMark(byte mark) {
        if (isCorrectMark(mark)) {
            this.mark = mark;
        }
    }

    public boolean isCorrectMark(byte mark) {
        return (mark >= 0) && (mark <= 4);
    }

    @Override
    public String toString() {
        return getText();
    }

    public List<Edge> getNeighbourhood() {
        return neighbourhood;
    }

    public boolean addEdge(Edge e) {
        if (!e.getTail().equals(this)||e.getHead().equals(this)||neighbourhood.contains(e)) {
            return false;
        } else {
            neighbourhood.add(e);
            graphElementChanged();
            return true;
        }
    }

    @Override
    final public void setText(String text) {
        if (!getText().equals(text)) {
            if (text.length() != 0) {
                int size = getWidth() / (int) (text.length());
                if (size < 1) {
                    size = 1;
                }
                setFont(new Font(Font.SANS_SERIF, Font.BOLD, size));
            }
            super.setText(text);
            graphElementChanged();
        }
    }

    public Point2D getCords() {
        return cords;
    }

    public void setCords(Point2D cords) {
        if(Double.compare(this.cords.getX(), cords.getX())!=0||Double.compare(this.cords.getY(), cords.getY())!=0){
            graphElementChanged();
        }
        this.cords = cords;
    }
    
    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

}
