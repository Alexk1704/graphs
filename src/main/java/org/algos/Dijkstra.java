package org.algos;

import org.ds.Graph;
import org.ds.Vertex;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Dijkstra {
    private Graph g;
    private LinkedList<Vertex>[] adjList;
    private PriorityQueue<Vertex> pq;
    private HashSet<Integer> solved;
    private Integer source;

    /* Different to Bellmann-Ford because it CAN NOT detect negative cycles,
     * thus not capable of handling negative weights correctly!
     * */
    public Dijkstra(Graph g, int source){
        this.g = g;
        this.source = source;
        adjList = g.exposeAdjList();
        pq = new PriorityQueue<>(adjList.length-1, Vertex.getDistanceComp());
        solved = new HashSet<>();
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
        }
        Vertex s = adjList[source].getFirst();
        s.setDistance(0);
        s.setParent(null);
        pq.add(s);
        return source;
    }

    /* Dijkstra (G, w, s)
     * while Q not empty
     *      u = extract-min(Q)
     *      S = S U [u]
     *      for each v e G.Adj[u]
     *          Relax(u,v,w)
     */
    public void dijkstra(){
        System.out.println("\nDIJKSTRA SHORTEST PATH ALGORITHM (DIGRAPH)\n");
        while(pq.size() != 0){
            Vertex u = pq.poll(); // get min distance node
            solved.add(u.getId());
            for(int i = 1; i < adjList[u.getId()].size(); i++){ // get neighbours 'v'
                Vertex v = adjList[adjList[u.getId()].get(i).getId()].getFirst();
                int w = adjList[u.getId()].get(i).getEdge().getWeight(); // get weight of v
                System.out.println("RELAX ON EDGE [U" + u.getId() + "] --(" + w + ")-> [V" + v.getId() + "]");
                relax(u, v, w);
                if(!solved.contains(v.getId())) pq.add(v); // add to Q if not done with node already
            }
        }
    }

    private void relax(Vertex u, Vertex v, Integer weight){
        if(u.getDistance() != Integer.MAX_VALUE && v.getDistance() > u.getDistance() + weight){
            v.setDistance(u.getDistance() + weight);
            v.setParent(u);
            System.out.println("\t[V"+ v.getId() + "] NEW DISTANCE: (" + u.getDistance() + " + " + weight + ")");
        }
    }

    public void printShortestPath(){
        System.out.println("\nDIJKSTRA SHORTEST PATH FOR SOURCE VERTEX [" + source + "]");
        for(int i = 1; i < adjList.length; i++){
            System.out.println("V[" + adjList[i].getFirst().getId() + "]\tDISTANCE: " + adjList[i].getFirst().getDistance());
        }
    }
}
