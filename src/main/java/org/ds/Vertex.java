package org.ds;


import java.util.ArrayList;
import java.util.Comparator;

public class Vertex {

    public enum Flag { // WHITE (UNVISITED), GRAY (DISCOVERED), BLACK (VISITED/FINISHED)
        WHITE, GRAY, BLACK
    };

    private Integer id;
    private Flag flag;
    private Integer distance; // distance from source node (BFS)
    private Integer discovered; // discovery time (DFS)
    private Integer finished; // finishing time (DFS)
    private Integer key; // key (Prim)
    private Edge edge; // edge reference

    private Vertex parent; // reference to parent node
    private ArrayList<Vertex> children;

    public Vertex(Vertex copy){
        this(copy.id, copy.parent);
    }

    public Vertex(final Integer id, Vertex parent){
        this.id = id;
        this.parent = parent;
        this.flag = Flag.WHITE;
        this.distance = null;
        this.discovered = null;
        this.finished = null;
        this.key = null;
        this.children = new ArrayList<Vertex>();
        this.edge = null;
    }

    public static final Comparator<Vertex> getComparator(){
        Comparator comp = new Comparator<Vertex>(){
            @Override
            public int compare(Vertex v1, Vertex v2) {
                return v1.key.compareTo(v2.key);
            }
        };
        return comp;
    }

    public static final Comparator<Vertex> getDistanceComp(){
        Comparator comp = new Comparator<Vertex>(){
            @Override
            public int compare(Vertex v1, Vertex v2) {
                return v1.distance.compareTo(v2.distance);
            }
        };
        return comp;
    }

    public void setParent(Vertex parent){
        this.parent = parent;
    }

    public Vertex getParent() { return this.parent;}

    public int getChildCount(){
        return this.children.size();
    }

    public ArrayList<Vertex> getChildren(){
        return this.children;
    }

    public void addChild(Vertex child){
        this.children.add(child);
    }

    public void wipeChildren() { this.children = new ArrayList<Vertex>(); }

    public void setId(final int id){
        this.id = id;
    }

    public boolean isRoot(){
        return (this.parent == null);
    }

    public Integer getId(){
        return this.id;
    }

    public Flag getFlag(){
        return this.flag;
    }

    public void setFlag(Flag flag){
        this.flag = flag;
    }

    public Integer getDistance(){
        return this.distance;
    }

    public void setDistance(Integer distance){
        this.distance = distance;
    }

    public Integer getDiscoveryTime(){
        return this.discovered;
    }

    public void setDiscoveryTime(Integer discovered){
        this.discovered = discovered;
    }

    public Integer getFinishTime(){
        return this.finished;
    }

    public void setFinishTime(Integer finished){
        this.finished = finished;
    }

    public Integer getKey(){
        return this.key;
    }

    public void setKey(Integer key){
        this.key = key;
    }

    public void setEdge(Edge edge) { this.edge = edge; }

    public Edge getEdge() { return this.edge; }

}
