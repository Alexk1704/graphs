package org.algos;

import org.ds.Edge;
import org.ds.EdgeList;
import org.ds.Vertex;
import org.jgrapht.alg.util.UnionFind;
import java.util.PriorityQueue;

import java.util.*;

/* Kruskal MST
 * Visit edges in ascending order of weights, add only if two disconnected trees in forest get connected
 * Priority Queue: objects processed based on priority
 * Run-time depends on implementation of union find data structure:
 *      |V| make-set operations
 *      |E| find-set operations
 *      |E| union operations
 * O(E*logV) using ordinary binary heaps
 */

public class Kruskal{
    /* Pseudo code:
    *  init A (forest)
    *  for each v element of G vertex set
    *      make-set(v)
    *  sort edges in growing order by their weights w
    *  for each (u,v) element of G edge set (chosen by growing weight)
    *      if find-set(u) != find-set(v)
    *          A = A U {(u,v)}
    *          union(u,v)
    *  return A (forest)
    *
    * Sort edgeList lo-high based on weight (implemented via priority-queue)
    * take edge with lowest weight and add it to MST, if cycle gets created - reject it.
    * keep adding weights until we reached all vertices!
    */
    public ArrayList<Edge> MSTKruskal(EdgeList edgeList){
        // UnionFind is a disjoint-set DS, can find set a specific element is in, and merge two sets.
        ArrayList<Edge> mst = new ArrayList<Edge>();
        HashSet<Vertex> vertexSet = new HashSet<Vertex>();
        for (Vertex v: edgeList.getVertexArr()) {
             vertexSet.add(v);
        }
        // disjoint set implementation for find set and union operations
        UnionFind uf = new UnionFind(vertexSet);
        ArrayList el = edgeList.returnEdgeList();
        // create p-queue with weight comparator
        PriorityQueue<Edge> pq = new PriorityQueue<>(el.size(), Edge.getComparator());
        pq.addAll(el); // add all edges to q
        // Any MST algorithm checks if adding an edge creates a loop or not (connects two unconnected trees if not)
        while(pq.size() != 0){
            Edge current = pq.poll();
            if(!uf.inSameSet(current.getFromV(), current.getToV())){ // check if in same set
                uf.union(current.getFromV(), current.getToV());
                mst.add(current);
            }
        }
        return mst;
    }

    public void printKruskal(ArrayList<Edge> mst){
        System.out.println("\nPRINTING MIN SPANNING TREE VIA KRUSKAL'S ALGORITHM!\n");
        for (Edge e: mst) { System.out.println("[EDGE] " + e.getFromV().getId() + "---" + e.getToV().getId()); }
    }
}
