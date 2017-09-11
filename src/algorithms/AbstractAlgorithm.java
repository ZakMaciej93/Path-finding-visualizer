package algorithms;

import graph.Edge;
import graph.Vertex;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;

public abstract class AbstractAlgorithm {
    protected List<Vertex> graph;
    protected Vertex initialNode;
    protected Vertex destinationNode;
    protected Point point;
    protected boolean finished;
    protected HashMap<Vertex,VertexData> vertexData;

    public AbstractAlgorithm() {
        vertexData=new HashMap();
    }
    
    public void showError(String text){
        JOptionPane.showMessageDialog(null, text, "Błąd", JOptionPane.ERROR_MESSAGE);
    }
    
    protected void resetMarks(){
        for(Vertex v:graph){
            for(Edge e:v.getNeighbourhood()){
                e.setMarked(false);
            }
            if(v.equals(initialNode)){
                v.setMark(Vertex.MARKED_START);
            }else if(v.equals(destinationNode)){
                v.setMark(Vertex.MARKED_END);
            }else{
                v.setMark(Vertex.NOT_MARKED);
            }
        }
    }
    
    protected void markPath(Vertex endVertex) {
        do {
            endVertex.setMark(Vertex.MARKED_ACTIVE);
            VertexData vD=(VertexData)vertexData.get(endVertex);
            endVertex = vD.getPredecessor();
        } while (endVertex != null);
    }
    
    
    public abstract String nextStep();
    
    public void reset(List<Vertex> graph,Vertex initialNode,Vertex destinationNode){
        this.graph=graph;
        this.initialNode=initialNode;
        this.destinationNode=destinationNode;
        this.point=new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.finished=false;
        vertexData = new HashMap<>(graph.size());
        resetMarks();
    }
    public abstract String getDescription();
    public boolean isFinished(){
        return finished;
    }
    public Point getStepPoint(){
        return point;
    }
    public abstract String[] getColumns();
    public abstract String[][] getRows();
}

class VertexData {
    protected Vertex predecessor;

    public VertexData(Vertex predecessor) {
        this.predecessor = predecessor;
    }

    public Vertex getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertex predecessor) {
        this.predecessor = predecessor;
    }
}