package org.algos;


import java.util.*;

import org.ds.AdjacencyList;
import org.ds.Edge;
import org.ds.EdgeList;
import org.ds.Vertex;


/* Prim MST
 * Input: connected graph G and root r (source node) of MST to be grown are inputs
 * During execution, all vertices that are not in the tree reside in a min-priority Queue Q based on key attribute
 * For each vertex v, attribute v.key is min weight of any edge connecting v to a vertex in the tree,
 * v.key = infinity if there is no such edge, attribute v.pred is the parent of v in the tree
 * DS: Priority Queue, Output: formed MST
 *
 * Algorithm:
 *  1) Create a mstSet that keeps track of vertices already included in MST
 *  2) Assign key value to all vertices in the input graph. Init all key values to INFINITE. Assign key value as 0 for first vertex so that it is
 *     picked first
 *  3) While mstSet does not include all vertices
 *      a) Pick vertex u which is not there in mstSet and has min key value
 *      b) include u to mstSet
 *      c) update key value of all adjacent vertices of u. to update key values, iterate through all adjacent vertices,
 *         for every adjacent vertex v, if weight of edge u->v is less than the previous key value of v, update key value as weight of u->v
 *
 * Run-time depends on implementation of priority queue:
 *      |E| times adjust priority: E log V
 *      |V| times extract-min: V log V
 *  O(E+V*log*V) by using Fibonacci heaps, which improves over binary-heap implementation if |V| is much smaller than |E|
 */
public class Prim {
    /* Pseudo-code:
     *  for each u element of G.V (G's vertex set)
     *      u.key = infinity
     *      u.pi = nil
     *  r.key = 0
     *  Q = G.V
     *  while Q not empty
     *      u = extract-min(Q)
     *      for each v element of G.Adj[u]
     *          if v element of Q and w(u,v) < v.key
     *              v.pi = u
     *              v.key = w(u,v)
     */
     public LinkedList<Vertex> MSTPrim(EdgeList edgeList, AdjacencyList adjList, int rootId){
         System.out.println("\nMST PRIM TRAVERSAL...");
         LinkedList<Vertex>[] al = adjList.exposeAdjList();
         LinkedList<Vertex> mstSet = new LinkedList<Vertex>();
         Vertex root = null;
         // create p-queue with (key) comparator including all vertices not added to MST yet
         PriorityQueue<Vertex> pq = new PriorityQueue<>(edgeList.getVertexCount(), Vertex.getComparator());
         for(int i = 1; i < al.length; i++){
             Vertex vertex = al[i].getFirst();
             if(vertex.getId() == rootId) {
                 vertex.setKey(0);
                 root = vertex;
             }
             else vertex.setKey(99999);
             pq.add(vertex);
         }
         while(pq.size() != 0){ // pick vertex with lowest key value not added to mstSet yet
             Vertex current = pq.poll();
             System.out.println("[V" + current.getId() + "] CURRENT Q VERTEX");
             mstSet.add(current);
             for(int i = 0; i < al[current.getId()].size(); i++) { // go through all adjacent vertices
                 Vertex adjVertex = al[current.getId()].get(i); // a vertex (v) connected to current (u)
                 // if v element of Q && w(u,v) < v.key
                 Edge w = edgeList.findEdge(current, adjVertex);
                 if(adjVertex != null && w != null){
                     if (pq.contains(adjVertex) && w.getWeight() < adjVertex.getKey()) {
                         System.out.println("\tUPDATED ADJACENT [V" + adjVertex.getId() + "] WITH KEY: " + w.getWeight());
                         adjVertex.setParent(current); // v.pred = u
                         if(pq.remove(adjVertex)) pq.add(adjVertex); // have to remove and re-add to ensure priority sorting
                         adjVertex.setKey(w.getWeight()); // v.key = w(u,v)
                     }
                 }
             }
         }
         return mstSet;
     }

     public void printPrim(LinkedList<Vertex> mstSet){
         int weightSum = 0;
         System.out.println("PRINTING MIN SPANNING TREE VIA PRIM'S ALGORITHM!");
         for(Vertex v : mstSet){
             weightSum += v.getKey();
             System.out.print("[V" + v.getId() + "] ");
         }
         System.out.println("OVERALL KEY SUM: " + weightSum);
     }
}
