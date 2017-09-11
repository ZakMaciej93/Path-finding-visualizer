package graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonModel;
import javax.swing.JButton;

public class Edge extends JButton implements Comparable<Edge>{
    boolean marked;
    boolean labelEnabled;
    boolean weightEnabled;
    private final Vertex tail;
    private final Vertex head;
    private double weight;
    private int offsetX;
    private int offsetY;
    private String label;
    
    private final List<GraphElementChangeListener> graphElementChangeListeners;

    
    public void addGraphElementChangeListener(GraphElementChangeListener l){
        this.graphElementChangeListeners.add(l);
    }
    
    public void removeGraphElementChangeListener(GraphElementChangeListener l){
        this.graphElementChangeListeners.remove(l);
    }
    
    protected void graphElementChanged(){
        GraphElementChangeEvent evt=new GraphElementChangeEvent(this);
        for(GraphElementChangeListener l:graphElementChangeListeners){
            l.graphElementChanged(evt);
        }
    }

    public Edge(Vertex tail, Vertex head, String label) {
        super();
        this.marked=false;
        this.labelEnabled=true;
        this.weightEnabled=false;
        this.graphElementChangeListeners=new ArrayList<>();
        this.tail = tail;
        this.head = head;
        this.label = label;
        this.offsetX=0;
        this.offsetY=0;
        setWeight(getDefaultWeight());
        setLocation(getDefaultLocation());
        setContentAreaFilled(false);
	setBorder(null);
	setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
	setForeground(Color.BLACK);
	setFocusable(false);
        setText(getDefaultText());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Edge){
            Edge e=(Edge)obj;
            return this.tail==e.tail&&this.head==e.head;
        }else{
            return false;
        }
    }
    
    public void updateLocation(){
        Point def=getDefaultLocation();
        super.setLocation(def.x+offsetX, def.y+offsetY);
    }

    @Override
    public void setLocation(int x, int y) {
        Point def=getDefaultLocation();
        if(Math.abs(def.x-x)<80){
            super.setLocation(x, getY());
            offsetX=x-def.x;
        }
        if(Math.abs(def.y-y)<80){
            super.setLocation(getX(), y);
            offsetY=y-def.y;
        }
    }

    @Override
    public final void setText(String text) {
        super.setText(text);
        setSize(getPreferredSize());
    }
    
    public final String getDefaultText(){
        return (labelEnabled?(weightEnabled?(label+":"):label):"")+(weightEnabled?getWeight():"");
    }
    
    public final Point getDefaultLocation(){
        if(tail==null)return head.getLocation();
        if(head==null)return tail.getLocation();
        int x=(tail.getX()+head.getX())/2;
        int y=(tail.getY()+head.getY())/2;
        return new Point(x,y);
    }
    
    public final double getDefaultWeight(){
        if(tail==null||head==null)return 0d;
        return Math.floor((head.getCords().distance(tail.getCords()))* 100) / 100;
    }

    public Vertex getTail() {
        return tail;
    }

    public Vertex getHead() {
        return head;
    }

    public double getWeight() {
        return weight;
    }

    public final void setWeight(double weight) {
        double w=Math.floor(weight * 100) / 100;
        if(this.weight!=w){
            this.weight = w;
            setText(getDefaultText());
            graphElementChanged();
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        ButtonModel model=getModel();
        if(model.isPressed()){
            setForeground(Color.GRAY);
        }else if(model.isRollover()){
            setForeground(Color.DARK_GRAY);
        }else{
            setForeground(Color.black);
        }
	super.paintComponent(grphcs);
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isLabelEnabled() {
        return labelEnabled;
    }

    public void setLabelEnabled(boolean labelEnabled) {
        this.labelEnabled = labelEnabled;
        setVisible(labelEnabled||weightEnabled);
        if(isVisible())setText(getDefaultText());
    }

    public boolean isWeightEnabled() {
        return weightEnabled;
    }

    public void setWeightEnabled(boolean weightEnabled) {
        this.weightEnabled = weightEnabled;
        setVisible(labelEnabled||weightEnabled);
        if(isVisible())setText(getDefaultText());
    }

    @Override
    public final void setLabel(String label) {
        if(!this.label.equals(label)){
            this.label=label;
            setText(getDefaultText());
            graphElementChanged();
        }
        
    }

    @Override
    public final String getLabel() {
        return this.label;
    }

    @Override
    public int compareTo(Edge o) {
        return Double.compare(weight, o.getWeight());
    }
    
    

}
