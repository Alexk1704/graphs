package org.algos;


/* BFS
 * Start with a source node and search for all reachable nodes (layer wise)
 * Calculate a BFS tree and distances (n nodes) of source
 * Useful for shortest path algorithms (navigation systems, routing algos, look for something already near
 * Let Graph G = (V,E) represented as an adjacency list
 * Each node u € V has three attributes:
 *      * u.color € {white,black} for not visited or visited
 *      * u.pred € V: predecessor in BFS tree
 *      * u.d € N: distance to source (n distance)
 *
 * FIFO queue Q: O(n) linear, constant depends on node count O(V)
 * Queue manages unprocessed nodes/edges
 * Worklist algorithm is a management structure that manages when the algorithm is done.
 * Overall runtime is O(V+E) but E grows larger than V!
 * */

import org.ds.AdjacencyList;
import org.ds.Vertex;
import org.ds.BFSTreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;


public class BreadthFirstSearch {

    Stack workingQueue;
    final LinkedList<Vertex>[] adjList;
    BFSTreeNode<Vertex> root;
    BFSTreeNode<Vertex> curr;

    public BreadthFirstSearch(LinkedList<Vertex>[] adjList){
        this.adjList = adjList;
    }

    /*
     * Source s (node): color black, distance 0, pred null ptr
     * all other nodes: color white, distance infinity, pred null ptr
     * Init working queue Q (Stack), that only contains s at start
     */
    public BFSTreeNode<Vertex> initBfsTree(int s_id){
        Vertex source = null;
        if(s_id < 1 || s_id > adjList.length+1){
            System.err.println("Check source node index!");
        } else {
            source = new Vertex(s_id); // create a vertex with id as passed
        }
        root = new BFSTreeNode<>(source); // create root of tree
        workingQueue = new Stack();
        workingQueue.push(root); // push to working queue

        return root;
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
    public void processList(){
        while(!workingQueue.empty()) {
            curr = (BFSTreeNode<Vertex>) workingQueue.pop();
            int vertexId = curr.getData().getId();
            for (Vertex v : adjList[vertexId]) {
                if (v.getFlag() == 1) {/* do nothing */}
                else {
                    v.setFlag(1); // set white
                    v.setDistance(curr.getData().getDistance()+1); // increment distance
                    BFSTreeNode<Vertex> newNode = new BFSTreeNode<Vertex>(v, curr); // Create TreeNode with predecessor
                    curr.addChild(newNode); // add child to current node
                    workingQueue.push(newNode); // add it to working queue
                }
            }
        }
    }

    public BFSTreeNode<Vertex> search(BFSTreeNode<Vertex> s, int index){
        if (s.getData().getId() == index) { // index same as source, done
            return s;
        }
        else if(s.getChildCount() != 0) { // has children
            ArrayList<BFSTreeNode<Vertex>> childList = s.getChildren();
            BFSTreeNode<Vertex> result = null;
            for(int i = 0; i < childList.size(); i++){ // loop through source children
                result = search(childList.get(i), index);
                if(result != null)
                    return result;
            }
            return result;
        }
        return null;
    }

    /* s: source node, v: vertex we want to reach
     * if v == s
     *  print s
     * elseif v.pred == NIL
     *  print "no path from s to v exists"
     * else printPath(s, v.pred)
     *  print v
     */
    // FIXME: Shortest path is actually NOT using shortest path if node has multiple parents
    public void shortestPath(BFSTreeNode<Vertex> s, BFSTreeNode<Vertex> v){
        if(v == s){
            System.out.println("Done with path to vertex: " + s.getData().getId());
        } else if(v.getParent() == null) { // no predecessor
            System.out.println("There is no path between v & s.");
        } else {
            shortestPath(s, v.getParent());
            System.out.println("Node (distance " + v.getData().getDistance() + "): " + v.getData().getId());
        }
    }
}
