package org.ds;


import java.util.ArrayList;
import java.util.LinkedList;


public class AdjacencyList {

    /* Graph G = (V,E)
     * Array Adj of |V| Lists
     * Adj[u] contains a list with all Vertices v, if there is an edge (u,v) e E.
     * Array index is equal to vertex id, list contains all vertices adjacent to array vertex.
     */

    private LinkedList<Vertex>[] adjList;
    int vertexCount;

    public AdjacencyList(EdgeList el){
        vertexCount = el.getVertexCount();
        adjList = new LinkedList[vertexCount+1];
        createAdjList(el.returnEdgeList());
    }

    private void createAdjList(ArrayList<Edge> el){
        for (Edge e: el) {
            Vertex from = e.getFromV();
            Vertex to = e.getToV();
            if(adjList[from.getId()] == null){
                adjList[from.getId()] = new LinkedList<Vertex>();
            }
            if(adjList[to.getId()] == null) {
                adjList[to.getId()] = new LinkedList<Vertex>();
            }
            if(!e.isDirected()){ // Edge undirected, add adjacent vertices for bot
                adjList[from.getId()].add(to);
                adjList[to.getId()].add(from);
            } else { // Edge directed, so only create for one direction
                adjList[from.getId()].add(to);
            }
        }
    }

    public LinkedList<Vertex>[] exposeAdjList(){
        return adjList;
    }

    public int getDimension(){ return this.vertexCount+1; }
}