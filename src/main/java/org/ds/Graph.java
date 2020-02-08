package org.ds;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
    private String dotFormat;
    private boolean isDirected;
    private boolean hasWeights;

    private ArrayList<Edge> edgeList;
    private int vertexCount;
    private Vertex[] vertexArr;

    private int[][] incMat;
    private int incRowSize;
    private int incColSize;

    private int[][] adjMat;
    private int adjRowSize;
    private int adjColSize;

    private LinkedList<Vertex>[] adjList;

    public Graph(int vertexCount, boolean isDirected){
        this.vertexCount = vertexCount;
        this.isDirected = isDirected;
        this.hasWeights = false;

        this.edgeList = new ArrayList<Edge>();
        this.vertexArr = new Vertex[vertexCount+1];
        for(int i = 1; i <= vertexCount; i++)
            vertexArr[i] = new Vertex(i, null);
    }

    public void createDataStructs(){
        createIncMatrix();
        createAdjMatrix();
        createAdjList();
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

    public int getVertexCount(){ return this.vertexCount; }

    public boolean isDirected(){ return this.isDirected; }

    public boolean hasWeights(){ return this.hasWeights; }

    public void setHasWeights(boolean hasWeights){ this.hasWeights = hasWeights; }

    // Finds an edge with two given vertices for an undirected graph and returns it when found
    public Edge findEdge(Vertex from, Vertex to){
        for (Edge e: this.edgeList) {
            if(e.getFromV().equals(from) && e.getToV().equals(to) || e.getFromV().equals(to) && e.getToV().equals(from)){
                return e;
            }
        }
        return null;
    }

    public Vertex[] getVertexArr(){ return this.vertexArr; }

    public String convertDOT(boolean isDirected){
        System.out.println("\nPrinting DOT format...");
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

    public void printEdgeList(boolean directed) {
        System.out.println("\nPrinting Edge List...");
        String arrow = "";
        if(directed) arrow = "->";
                else arrow = "-";
        for (int i = 0; i < edgeList.size(); i++) {
            Edge e = edgeList.get(i);
            System.out.println("Edge " + e.getId() + "\t"
                    + "v" + e.getFromV().getId() + " -" + e.getWeight() + arrow + "v" + e.getToV().getId());
        }
    }

    /* Graph G = (V,E)
     * |V| x |E| Matrix with entry a(i,j) = 1 if Edge j incident to Vertex i, else 0
     *
     * Example:
     *    e1 e2 e3 ...
     * v1  0  1  1
     * v2  1  1  0
     * v3  0  0  1
     * ...
     * - Each column has 1 two times, rest is 0
     * - Sum of 1 in each row is deg(G)
     * - Parallel edges create identical columns
     * - G is isomorphic to G', if their incidence matrices are identical
     *       through permutation of rows & columns
     */
    private void createIncMatrix(){
        incRowSize = vertexCount+1; // +1 bc we index v,e from id 1
        incColSize = edgeList.size()+1;
        incMat = new int[incRowSize][incColSize];

        for(int i = 0; i < incRowSize; i++){
            for(int j = 0; j < incColSize; j++){
                incMat[i][j] = 0; // init
            }
        }

        for(int i = 0; i < edgeList.size(); i++){
            Edge e = edgeList.get(i);
            Vertex fromV = edgeList.get(i).getFromV();
            Vertex toV = edgeList.get(i).getToV();

            incMat[fromV.getId()][e.getId()] = 1;
            incMat[toV.getId()][e.getId()] = 1;
        }
    }

    public int[][] exposeIncMatrix(){
        return incMat;
    }

    public void printIncMatrix(){
        System.out.println("\nPrinting Incidence Matrix...");
        String colIndexes = "";
        for(int i = 1; i < incColSize; i++)
            colIndexes = colIndexes + "e" + i + "\t";
        System.out.println("\t" + colIndexes);
        for(int i = 1; i < incRowSize; i++){
            String colValues = "";
            for(int j = 1; j < incColSize; j++)
                colValues = colValues + incMat[i][j] + "\t";
            System.out.println("v" + i + "\t" + colValues);
        }
    }

    /* Graph G = (V,E)
     * |V| x |V| Matrix with entry a(i,j) = 1 if Vertex i adjacent to Vertex j, else 0
     *
     * Example:
     *    v1 v2 v3 ...
     * v1  0  1  1
     * v2  1  1  0
     * v3  1  0  0
     * ...
     * - Entry in diagonal correspond to self edges
     * - Multiplying adjacent matrix k times with itself,
     *      resulting entry a(i,j) becomes the nr. of paths of length k from i to j.
     */
    private void createAdjMatrix(){
        adjRowSize = adjColSize = vertexCount+1; // +1 bc we index v,e from 1
        adjMat = new int[adjRowSize][adjColSize];
        // init with 0s
        for(int i = 0; i < adjRowSize; i++){
            for(int j = 0; j < adjColSize; j++){
                adjMat[i][j] = 0;
            }
        }
        for(int i = 0; i < edgeList.size(); i++){
            Vertex fromV = edgeList.get(i).getFromV();
            Vertex toV = edgeList.get(i).getToV();

            adjMat[fromV.getId()][toV.getId()] = 1;
            adjMat[toV.getId()][fromV.getId()] = 1;
        }
    }

    public int[][] exposeAdjMatrix(){
        return adjMat;
    }

    public void printAdjMatrix(){
        System.out.println("\nPrinting Adjacency Matrix...");
        String colIndexes = "";
        for(int i = 1; i < adjColSize; i++)
            colIndexes = colIndexes + "v" + i + "\t";
        System.out.println("\t" + colIndexes);
        for(int i = 1; i < adjRowSize; i++){
            String colValues = "";
            for(int j = 1; j < adjColSize; j++)
                colValues = colValues + adjMat[i][j] + "\t";
            System.out.println("v" + i + "\t" + colValues);
        }
    }

    /* Graph G = (V,E)
     * Array Adj of |V| Lists
     * Adj[u] contains a list with all Vertices v, if there is an edge (u,v) e E.
     * Array index is equal to vertex id, list contains all vertices adjacent to array vertex.
     */
    private void createAdjList(){
        adjList = new LinkedList[vertexCount+1]; // +1 again since we skip index 0....

        for (Edge e: edgeList) {
            Vertex from = e.getFromV(); // create copies
            Vertex to = e.getToV();
            if(adjList[from.getId()] == null){
                adjList[from.getId()] = new LinkedList<Vertex>();
                adjList[from.getId()].add(from); // add it as first element because we need the vertex object later
            }
            if(adjList[to.getId()] == null) {
                adjList[to.getId()] = new LinkedList<Vertex>();
                adjList[to.getId()].add(to); // add it as first element because we need the vertex object later
            }
            if(!e.isDirected()){ // Edge undirected, add adjacent vertices for both
                adjList[from.getId()].add(to);
                adjList[to.getId()].add(from);
            } else { // Edge directed, so only create for one direction
                adjList[from.getId()].add(to);
            }
        }
    }

    public void printAdjList(){
        for(int i = 1; i < adjList.length; i++){
            System.out.print("\n[V" + i + "]: ");
            if(adjList[i] != null) {
                for (Vertex v : adjList[i]) {
                    System.out.print("V" + v.getId() + "->");
                }
            }
        }
    }

    public LinkedList<Vertex>[] exposeAdjList(){
        return adjList;
    }
}
