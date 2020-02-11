package org.algos;

import org.ds.Edge;
import org.ds.Graph;
import org.ds.Vertex;

import java.util.ArrayList;
import java.util.LinkedList;

public class BellmannFord {

    private Graph g;
    private LinkedList<Vertex>[] adjList;
    private Integer source;

    /* BELLMANN-FORD
     * Calculate cost of shortest paths from a source node to all other nodes
     * Iteratively correct from bad cost estimation until we found a good one
     */
    public BellmannFord(Graph g, int source){
        this.g = g;
        this.source = source;
        adjList = g.exposeAdjList();
        initSingleSource();
    }

    /* Init-Single-Source(G, s)
     * for each vertex
     *      v.d = infinity
     *      v.pred = nil
     * s.d = 0
     */
    private int initSingleSource(){
        for(int i = 1; i < adjList.length; i++){
            adjList[i].getFirst().setDistance(Integer.MAX_VALUE);
            adjList[i].getFirst().setParent(null);
            if(adjList[i].getFirst().getId() == source) adjList[i].getFirst().setDistance(0);
        }
        return source;
    }

    /* |V| steps + |E| steps then relax (from u to v with weight w)
     * for i = 1 to |G.V| - 1
     *       for each (u,v) e G.E
     *           Relax(u, v, w)
     *
     *  for each (u,v) e G.E
     *       if v.d > u.d + w(u,v)
     *           return FALSE
     *  return TRUE
     */
    public boolean bellmannFord(int source){
        System.out.println("\nBELLMAN-FORD SHORTEST PATH ALGORITHM (DIGRAPH)");
        for(int i = 2; i < adjList.length; i++){ // n-1 phases
            System.out.println("\nPHASE " + (i-1));
            for(int j = 1; j < adjList.length; j++){
                for(int k = 1; k < adjList[j].size(); k++){
                    Vertex u = adjList[j].getFirst();
                    Vertex v = adjList[adjList[j].get(k).getId()].getFirst();
                    int w = adjList[j].get(k).getEdge().getWeight();
                    System.out.println("RELAX ON EDGE [U" + u.getId() + "] --(" + w + ")-> [V" + v.getId() + "]");
                    relax(u, v, w);
                }
            }
        }
        for(int i = 1; i < adjList.length; i++){
            for(int j = 1; j < adjList.length; j++) {
                for (int k = 1; k < adjList[j].size(); k++) {
                    Vertex u = adjList[j].getFirst();
                    Vertex v = adjList[adjList[j].get(k).getId()].getFirst();
                    int w = adjList[j].get(k).getEdge().getWeight();
                    if (u.getDistance() != Integer.MAX_VALUE && v.getDistance() > u.getDistance() + w) {
                        System.out.println("\nNEGATIVE CYCLE DETECTED!!!!");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /* Relax(u, v, w)
     * u: source of edge
     * v: destination of edge
     * Basically look if step from u to v with weight w is smaller than current estimation of v.
     * This happens for every edge
     * if v.d > u.d + w(u,v)
     *      v.d = u.d + w(u,v)
     *      v.pred = u;
     */
    private void relax(Vertex u, Vertex v, Integer weight){
        if(u.getDistance() != Integer.MAX_VALUE && v.getDistance() > u.getDistance() + weight){
            v.setDistance(u.getDistance() + weight);
            v.setParent(u);
            System.out.println("\t[V"+ v.getId() + "] NEW DISTANCE: (" + u.getDistance() + " + " + weight + ")");
        }
    }

    public void printShortestPath(){
        System.out.println("\nBELLMANN-FORD SHORTEST PATH FOR SOURCE VERTEX [" + source + "]");
        for(int i = 1; i < adjList.length; i++){
           System.out.println("V[" + adjList[i].getFirst().getId() + "]\tDISTANCE: " + adjList[i].getFirst().getDistance());
        }
    }
}
