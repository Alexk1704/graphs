package org.ds;


import java.util.ArrayList;


public class EdgeList {

    private ArrayList<Edge> edgeList;
    private int vertexCount;
    private String dotFormat;
    private Vertex[] vertexArr;

    public EdgeList(int dims) {
        this.vertexCount = dims;
        this.edgeList = new ArrayList<Edge>();
        this.vertexArr = new Vertex[dims+1];
        for(int i = 1; i <= dims; i++){
            vertexArr[i] = new Vertex(i, null);
        }
    }

    public ArrayList<Edge> returnEdgeList(){
        return this.edgeList;
    }

    public void addEdge(int first, int second, int id, boolean isDirected, Integer weight){
        edgeList.add(new Edge
                (       vertexArr[first],
                        vertexArr[second],
                        isDirected,
                        id,
                        weight
                )
        );
    }

    public void setVertexCount(int count){
        this.vertexCount = count;
    }

    public int getVertexCount(){
        return this.vertexCount;
    }

    public Edge findEdge(Vertex from, Vertex to){
        for (Edge e: this.edgeList) {
            if(e.getFromV().equals(from) && e.getToV().equals(to)){
                return e;
            }
        }
        return null;
    }

    public Vertex[] getVertexArr(){
        return this.vertexArr;
    }

    public String convertDOT(boolean isDirected){
        if(isDirected) {
            dotFormat = "digraph {\n";
            for(int i = 0; i < edgeList.size(); i++){
                dotFormat = dotFormat + "\t" + edgeList.get(i).getFromV().getId() + " -> "
                        + edgeList.get(i).getToV().getId()+ ";\n";
            }
        } else {
            dotFormat = "graph {\n";
            for(int i = 0; i < edgeList.size(); i++){
                dotFormat = dotFormat + "\t" + edgeList.get(i).getFromV().getId() + " -- "
                        + edgeList.get(i).getToV().getId()+ ";\n";
            }
        }
        dotFormat = dotFormat + "}";

        return dotFormat;
    }
}
