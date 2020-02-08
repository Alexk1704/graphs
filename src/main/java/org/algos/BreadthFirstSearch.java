package org.algos;


/* Breadth First Search
 * Start with a source node and search for all reachable nodes (layer wise)
 * Calculate a BFS tree and distances (n nodes) of source
 * Useful for shortest path algorithms (navigation systems, routing algorithms, -> look for something already near
 * Let Graph G = (V,E) represented as an adjacency list
 * Each node u € V has three attributes:
 *      * u.color € {white,black} for not visited or visited
 *      * u.pred € V: predecessor in BFS tree
 *      * u.d € N: distance to source (n distance)
 *
 * FIFO queue Q: O(n) linear, constant depends on node count O(V)
 * Queue manages unprocessed nodes/edges
 * A work list algorithm is a management structure that manages when the algorithm is done.
 * Overall runtime is O(V+E) but E grows larger than V!
 */

import org.ds.Graph;
import org.ds.Vertex;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;


public class BreadthFirstSearch {

    Stack workingQueue;
    final LinkedList<Vertex>[] adjList;
    Integer distance;

    public BreadthFirstSearch(Graph g){
        this.adjList = g.exposeAdjList();
        this.distance = 0;
    }

    /*
     * Source s (node): color black, distance 0, pred: null ptr
     * all other nodes: color white, distance infinity, pred: null ptr
     * init working queue Q (Stack), that only contains s at start
     */
    public Vertex initTree(int s_id){
        workingQueue = new Stack();
        Vertex source = null;
        if(s_id < 1 || s_id > adjList.length+1){
            System.err.println("Check source node index!");
        } else {
            for(int i = 1; i < adjList.length; i++) {
                for (Vertex v : adjList[i]) {
                    if (v.getId() == s_id) {
                        source = v;
                        source.setFlag(Vertex.Flag.BLACK);
                        source.setDistance(distance);
                        source.setParent(null);
                        source.wipeChildren();
                    } else {
                        v.setFlag(Vertex.Flag.WHITE);
                        v.setDistance(null);
                        v.setParent(null);
                        v.wipeChildren();
                    }
                }
            }
        }
        workingQueue.push(source); // push to working queue
        return source;
    }

    /*
     * Start with next processing node and take it out of Q
     * for each neighbour of s
     *  visited y/no
     *      y: do nothing
     *      n: visit, colour it, distance +1, pred on node u (the one being process right now)
     *         & add to working queue Q
     * O(E) for each Edge exactly once O(E)
     */
    public void buildTree(){
        Vertex current;
        while(!workingQueue.empty()) {
            current = (Vertex) workingQueue.pop();
            for (Vertex v : adjList[current.getId()]) {
                if (v.getFlag() == Vertex.Flag.BLACK) {/* do nothing */}
                // also first vertex in linked list is vertex we are referencing by index, so skip it
                else {
                    v.setFlag(Vertex.Flag.BLACK); // visited neighbouring node
                    adjList[v.getId()].getFirst().setFlag(Vertex.Flag.BLACK); // hax
                    v.setDistance(current.getDistance()+1); // increment distance
                    v.setParent(current);
                    System.out.println("[V" + v.getId() + "]\t" + v +  "\tVISITED\n\t\tSETTING THE PARENT @[V"
                            + current.getId() + "]\t" + current);
                    current.addChild(v); // add v as child of current
                    workingQueue.push(v); // add v to working queue
                    System.out.println("[V" + v.getId() + "]\tADDED TO WORKING QUEUE");
                }
            }
        }
    }

    public Vertex searchTree(Vertex source, int index){
        if (source.getId() == index) { // index same as source, done
            return source;
        }
        else if(source.getChildCount() != 0) { // has children
            ArrayList<Vertex> childList = source.getChildren();
            Vertex result;
            for(int i = 0; i < childList.size(); i++){ // loop through source children
                result = searchTree(childList.get(i), index); // recursive call
                if(result != null)
                    return result; // if we found the node, return it!
            }
        }
        return null;
    }

    /* Shows a path from source node to vertex v
     * s: source node, v: vertex we want to reach
     * if v == s
     *  print s
     * elseif v.pred == NIL
     *  print "no path from s to v exists"
     * else printPath(s, v.pred)
     *  print v
     */
    public void showPath(Vertex s, Vertex v){
        if(v == s){
            System.out.println("\nPATH TO VERTEX: [" + s.getId() + "] DONE!");
        } else if(v.getParent() == null) { // no predecessor
            System.out.println("NO PATH BETWEEN s & v!");
        } else {
            showPath(s, v.getParent());
            System.out.println("\tV[" + v.getId() + "] (DISTANCE " + v.getDistance() + ")");
        }
    }
}
