package org.ds;


import java.util.ArrayList;


public class EdgeList {

    private ArrayList<Edge> edgeList;
    private int vertexCount;
    private String dotFormat;

    public EdgeList() {
        edgeList = new ArrayList<Edge>();
    }

    public ArrayList<Edge> returnEdgeList(){
        return this.edgeList;
    }

    public void addEdge(int first, int second, int id, boolean isDirected, Integer weight){
        edgeList.add(new Edge(new Vertex(first), new Vertex(second), isDirected, id, weight));
    }

    public void addVertexCount(int count){
        this.vertexCount = count;
    }

    public int getVertexCount(){
        return this.vertexCount;
    }

    public String convertDOT(boolean isDirected){
        if(isDirected == true) {
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
