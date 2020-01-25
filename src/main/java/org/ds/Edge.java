package org.ds;


import java.util.Comparator;

public class Edge {
    private Vertex fromVertex;
    private Vertex toVertex;
    private Integer weight;
    private Integer id;
    boolean isDirected;

    public Edge(Vertex fromVertex, Vertex toVertex, boolean isDirected, Integer id, Integer weight) {
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
        this.isDirected = isDirected;
        this.id = id;
        this.weight = weight;
    }

    public static final Comparator<Edge> getComparator(){
        Comparator comp = new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.weight.compareTo(e2.weight);
            }
        };
        return comp;
    }

    public void setDirected(boolean isDirected){
        this.isDirected = isDirected;
    }

    public boolean isDirected(){
        return this.isDirected;
    }

    public Integer getWeight(){ return this.weight; }

    public void setFromV(Vertex fromVertex) {
        this.fromVertex = fromVertex;
    }

    public void setToV(Vertex toVertex) {
        this.toVertex = toVertex;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Vertex getFromV() {
        return this.fromVertex;
    }

    public Vertex getToV() {
        return this.toVertex;
    }

    public Integer getId(){
        return this.id;
    }
}