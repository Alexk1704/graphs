package org.algos;

import org.ds.Edge;
import org.ds.Graph;
import org.ds.Vertex;

import java.util.ArrayList;

public class BellmannFord {

    private Graph g;
    private Vertex[] vArr;
    private ArrayList<Edge> edgeList;


    /* BELLMANN FORD
     * Calculate cost of shortest paths from a source node to all other nodes
     * Iteratively correct from bad cost estimation until we found a good one
     */
    public BellmannFord(Graph g, int source){
        this.g = g;
        edgeList = g.returnEdgeList();
        vArr = g.getVertexArr();
        initSingleSource(source);
    }

    /* Init-Single-Source(G, s)
     * for each vertex
     *      v.d = infinity
     *      v.pred = nil
     * s.d = 0
     */
    private int initSingleSource(int source){
        for(int i = 1; i < vArr.length; i++){
            vArr[i].setDistance(Integer.MAX_VALUE);
            vArr[i].setParent(null);
            if(vArr[i].getId() == source) vArr[i].setDistance(0);
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
        for(int i = 2; i < vArr.length; i++){ // n-1 phases
            for(Edge e : edgeList){
                Vertex u = e.getFromV();
                Vertex v = e.getToV();
                int w = e.getWeight();
                System.out.println("u: " + u.getId() + " -> v: " + v.getId() + " (weight: " + w + ")");
                relax(u, v, w);
            }
        }
        for(int i = 1; i < vArr.length; i++){
            for(Edge e : edgeList){
                Vertex u = e.getFromV();
                Vertex v = e.getToV();
                int w = e.getWeight();
                if(u.getDistance() != Integer.MAX_VALUE && v.getDistance() > u.getDistance() + w)
                    return false;

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
            System.out.println("Setting new distance for v: " + u.getDistance() + " + " + weight);
        }
    }

    public void printShortestPath(int node){
        if(vArr[node].getParent() != null) {
            System.out.print("V[" + vArr[node].getParent().getId() + " (distance: "+ vArr[node].getDistance() + "] ->");
            printShortestPath(vArr[node].getParent().getId());
        }
        /*
                System.out.println("Vertex Distance from Source");
                 for (int i = 0; i < V; ++i)
                   System.out.println(i + "\t\t" + dist[i]);
    }
         */
    }
}
