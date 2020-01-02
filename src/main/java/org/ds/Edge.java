package org.ds;


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