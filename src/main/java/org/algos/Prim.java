package org.algos;

import java.util.*;

import org.ds.AdjacencyList;
import org.ds.Edge;
import org.ds.EdgeList;
import org.ds.Vertex;

/*
 * Start with source node r.
 * Invariant: Edges in set A always only form one tree!
 * Manage the unvisited neighbours from current tree, sorted ascending by edge weight.
 * Algorithm starts from arbitrary root vertex r and grows until tree spans all vertices in V.
 * Each step adds to tree A a light edge that connects A to an isolated vertex - one on which no edge of A is incident (safe edge)
 * Greedy approach: each step it adds to the tree an edge that contributes the min amount possible to tree's weight.
 *
 * In order to implement, we need a fast way to select new edge to add to tree.
 * Input: connected graph G and root r of MST to be grown are inputs
 * During execution, all vertices that are not in the tree reside in a min-priority Queue Q based on key attribute.
 * For each vertex v, attribute v.key is min weight of any edge connecting v to a vertex in the tree, v.key = infinity if there is no such edge.
 * attribute v.pred parent of v in the tree
 * Output: formed MST
 * DS: Priority Queue
 *
 * Idea: maintain two sets of vertices, first contains vertices already included in MST, other set contains vertices not included.
 * At every step, it considers all edges that connect the two sets, and picks min weight edge from these edges. After picking edge, moves the other endpoint of the edge to the set containing MST.
 * Group of edges that connects two set of vertices in a graph is called cut in graph theory.
 * At every step of Prim's, we find a cut (of two sets, one contains vertices already included in MST and other contains rest of vertices), pick
 * min weight edge from cut and include this vertex to MST SET (set with already included vertices).
 *
 * A spanning tree means all vertices must be connected. So the two disjoint subsets (discussed above) of vertices must be connected to make a spanning tree.
 * And they must be connected with the min weight edge to make it a MST.
 *
 * Algorithm:
 *  1) Create a mstSet that keeps track of vertices already included in MST
 *  2) Assign key value to all vertices in the input graph. Init all key values to INFINITE. Assign key value as 0 for first vertex so that it is
 *     picked first
 *  3) While mstSet doesnt include all vertices
 *      a) Pick vertex u which is not there in mstSet and has min key value
 *      b) include u to mstSet
 *      c) update key value of all adjacent vertices of u. to update key values, iterate through all adjacent vertices,
 *         for every adjacent vertex v, if weight of edge u-v is less than the previous key value of v, update key value as weight of u-v
 *
 * Algorithm implicitly maintains set A from Generic-MST as A = {(v,v.pred) : v e V - {r} - Q}.
 * When algorithm terminates, min-prio queue Q is empty, thus MST A for G is:
 *  A = {(v,v.pred) : v e V - {r}}.
 *
 * Run-time: Depends on implementation of priority queue.
 *           O(E * log V)
 *              - |E| times adjust priority: E log V
 *              - |V| times extract-min: V log V
 *
 *  O(E+VlogV) by using Fibonacci heaps, which improves over binary-heap implementation
 *  if |V| is much smaller than |E|.
 *
 * More on Heaps: http://www.jheaps.org/heaps/
 * Fibonacci:
 * asymptotically greater in an amortized sense (average running time for a lot of operations),
 * e.g. consider self balancing tree: 3 different costs for insert operation -> if we aggregate all costs we get the avg.
 * running time of an algorithm.
 *
 * Binomial Heap: Collections of binomial trees that are linked together where each tree is an ordered heap.
 * There are either one or zero binomial trees of order k, where k helps describe the nr. of elements a given tree can have: 2^k.
 * Similar to binary heaps but have a more specific structure and allow for efficient merging of heaps.
 *
 * Binary Heap: Heap, i.e. a tree which obeys the property that the root of any tree is greater than or equal to all its children (heap property).
 * Primary use of heaps is to implement a priority queue.
 *
 */
public class Prim {
    /* Pseudo-code:
     * A = {(v,v.pred) : v e V - {r} - Q}
     * Vertices already placed into MST are those in V-Q
     * For all vertices v e Q, if v.pred != NIL, then v.key < infinite and
     * v.key is weight of a light edge (v,v.pred) connecting v to some vertex already placed into MST.
     * Identify vertex u e Q incident on a light edge that crosses the cut (V-Q.Q) (with exception of first iteration)
     * Removing u from set Q adds it to the set V-Q of vertices in the tree, thus adding (u,u.pred) to A.
     * For loop updates key and pred attributes of every vertex v adjacent to u but not in the tree.
     *
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
