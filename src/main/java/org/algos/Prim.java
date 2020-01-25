package org.algos;


import java.util.*;

import org.ds.AdjacencyList;
import org.ds.Edge;
import org.ds.EdgeList;
import org.ds.Vertex;


/*
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
     public Vertex MSTPrim(EdgeList edgeList, AdjacencyList adjList){
         ArrayList el = edgeList.returnEdgeList();
         LinkedList<Vertex>[] al = adjList.exposeAdjList();
         // randomly chose a root for set A (min spanning tree) and set its key to 0
         Random rand = new Random();
         Vertex root = (Vertex) edgeList.getVertexArr()[rand.nextInt(el.size())];
         root.setKey(0);
         // create p-queue with weight (key) comparator including all vertices not added to MST yet
         //PriorityQueue<Edge> pq = new PriorityQueue<>(el.size(), Edge.getComperator());
         //pq.addAll(el); // add all edges to q
         PriorityQueue<Vertex> pq = new PriorityQueue<>(edgeList.getVertexCount(), Vertex.getComparator());
         for(int i = 0; i < edgeList.getVertexArr().length; i++) {
             pq.add(edgeList.getVertexArr()[i]);
         }
         while(pq.size() != 0){ // go through all edges
             //Edge current = pq.poll();
             //Vertex currentFrom = current.getFromV();
             Vertex current = pq.poll();
             // for each adjacent vertex of from vertex from current edge, check if those vertices are still in Q and if
             // key of current from V is bigger than edge we are processing right now, if so, change the key of current from Vertex.
             for(int i = 0; i < al[current.getId()].size(); i++){
                 Vertex adjVertex = al[current.getId()].get(i); // a vertex (v) connected to current (u)
                 // if v element of Q && w(u,v) < v.key
                 Edge w = edgeList.findEdge(current, adjVertex);
                 if(pq.contains(adjVertex) && w.getWeight() < current.getKey()){
                     adjVertex.setParent(current); // v.pred = u
                     current.addChild(adjVertex);
                     adjVertex.setKey(w.getWeight()); // v.key = w(u,v)
                 }
             }
         }
         return root;
     }

     public void printPrim(Vertex root){
         System.out.println("\nPrinting PRIM MST top-down:\n");
         Vertex curr = root;
         System.out.println("[V" + curr.getId() + "] Key: " + curr.getKey());
         ArrayList<Vertex> children = curr.getChildren();
         for (Vertex v: children) {
             printPrim(v);
         }
     }
}
