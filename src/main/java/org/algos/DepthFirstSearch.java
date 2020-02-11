package org.algos;


import org.ds.Graph;
import org.ds.Vertex;

import java.util.LinkedList;
import java.util.Stack;


/* Depth First Search
 * Start with a arbitrary node and search for all reachable nodes depth wise - calculate a DFS forest.
 * Let G be a graph with (V,E) represented by a adjacency list
 * Each Node u element of V has three attributes:
 *      - u.color: flag (e.g. white, black) for visited or not visited
 *      - u.pred: element of V (predecessor in DFS forest) / reference to node it comes from
 *      - u.d: element of {1,2,3,..., 2|V|}, time stamp when node was discovered first (entered),
 *                                           nr. of steps in algorithm when a node was reached
 *      - u.f: element of {1,2,3,,...,2|V|}, time stamp when adjacency list exhausted (finished),
 *                                           last exit step
 * DFS algorithm works for undirected and directed graphs, run time: O(V+E) for adjacency list representation
 * Define a DFS forest G' = (V, E') with E' = {(v.pred, v) | v element of V and v.pred != null} -> Tree edges
 */
public class DepthFirstSearch {
    Integer time;
    LinkedList<Vertex>[] adjList;
    Vertex.Flag[] flags;

    public DepthFirstSearch(Graph g){
        this.adjList = g.exposeAdjList();
        this.flags = new Vertex.Flag[adjList.length];
    }

    private void init(){
        for(int i = 1; i < adjList.length; i++) {
            for (Vertex v : adjList[i]) {
                v.setParent(null);
                v.setDiscoveryTime(null);
                v.setFinishTime(null);
            }
        }
        for(int i = 1; i < flags.length; i++) flags[i] = Vertex.Flag.WHITE;
    }

    /* All nodes white (0), predecessor: NULL ptr, time var (global): 0 */
    public void depthSearch(){
        init();
        time = 0;
        System.out.println("\nDEPTH SEARCH (DIGRAPH):\n");
        for(int i = 1; i < adjList.length; i++){
            if(flags[adjList[i].getFirst().getId()] == Vertex.Flag.WHITE){ // if WHITE visit
                visit(adjList, adjList[i], null, null, false);
            }
        }
    }

    /*
     * First node, discovery time 1, then for each adjacent node:
     * If not visited (flag 0)
     *      set the predecessor of this node (current node)
     *      then recursively call visit for this node
     * After return of control, colour current node black
     * increment time and save finish time
     * Idea: do not program it recursively instead we could use a call stack, because sometimes it is more
     * efficient than the runtime environment call stack of java.
     * The algorithm builds the tree from bottom up, we need to turn it to get our DFS forest
     * !!! COULD BE written with local heap stack instead of recursion !!!
     */
    public void visit(LinkedList<Vertex>[] adjList, LinkedList<Vertex> adj, Stack s, LinkedList<Vertex> topSort, boolean scc) {
        Vertex u = adj.getFirst();
        if(!scc) {
            time = time + 1;
            System.out.println("[V" + u.getId() + "]\tDISCOVERED ON STEP " + time);
            u.setDiscoveryTime(time); // set discovery time for calling vertex
        }
        flags[u.getId()] = Vertex.Flag.GRAY; // set colour to GRAY (discovered)
        if(scc)
            System.out.print("[V" + u.getId() + "]\t");
        for(int i = 0; i < adj.size(); i++){ // explore edge (u, v), skip first since its u node
            // skip first since its calling node
            if(flags[adj.get(i).getId()] == Vertex.Flag.WHITE){ // adjacent vertex white?
                if(!scc)
                    adj.get(i).setParent(u); // set its parent
                visit(adjList, adjList[adj.get(i).getId()], s, topSort, scc); // recursive call
            }
        }
        flags[u.getId()] = Vertex.Flag.BLACK; // set to black after return of control
        if(s != null) {
            // after calling recursive DFS for adjacent vertices of a vertex, push vertex to stack (sorted by f(u))
            s.push(u);
        }
        if(!scc){
            time = time + 1;
            u.setFinishTime(time); // set finishing time
            System.out.println("[V" + u.getId() + "]\tFINISHED ON STEP " + time);
            if(topSort != null)
                topSort.add(u);  // add to linked list if topological sort
        }
    }

    /* Topological sort of a DAG (directed, acyclic graph) is a sort of all nodes,
     * so that u comes before v, if there is an edge (u,v)
     *
     * Ordering of a graphs' vertices along a horizontal line so that all
     * directed edges go from left to right.
     *
     * All nodes are white, predecessor null, time = 0
     * For each white node call DFS visit(G,u)
     *
     * 1. call DFS(G) and calculate finishing times v.f for each node v
     * 2. When setting v.f add node to the front of a list
     * 3. return that list
     * A directed graph G is acyclic exactly then when DFS gives us no back edges
     */
    public LinkedList<Vertex> topSort(){
        LinkedList<Vertex> topSort;
        topSort = new LinkedList<>();
        init();
        time = 0;
        for(int i = 1; i < adjList.length; i++){
            if(flags[adjList[i].getFirst().getId()] == Vertex.Flag.WHITE){ // if WHITE visit
                visit(adjList, adjList[i], null, topSort, false);
            }
        }
        return topSort;
    }

    public void printTopSort(LinkedList<Vertex> topSortList){
        System.out.println("\nTOPOLOGICAL SORT (DIGRAPH):\n");
        for(Vertex v: topSortList){
            System.out.println("V[" + v.getId() + "]\tDISCOVERY: "
                    + v.getDiscoveryTime() + "\tFINISHED: "
                    + v.getFinishTime());
        }
        System.out.println("\nTOPOLOGICAL SORT (DIGRAPH) FINISHED...\n");
    }

    /* SCC(G): takes transposed G
     * Kosaraju's algorithm:
     *  1. call DFS(G) and calculate finishing times v.f for each node v
     *  2. calculate G' (transposed)
     *  3. call DFS(G'), work through neighbouring nodes in descending order of v.f
     *  4. return every tree of DFS forest (from 3.) as SCC
     */
    public void SCC(){
        System.out.println("STRONGLY CONNECTED COMPONENTS (DIGRAPH):");
        Stack s = new Stack(); // Create empty stack S
        init(); // reset adj list
        time = 0;
        System.out.println("DFS TRAVERSAL FOR G:");
        for(int i = 1; i < adjList.length; i++){ // DFS traversal of graph G
            if(flags[adjList[i].getFirst().getId()] == Vertex.Flag.WHITE){ // if WHITE visit
                visit(adjList, adjList[i], s, null, false);
            }
        }
        System.out.println("\nTRANSPOSING ADJACENCY LIST (CREATING G')");
        // Reverse directions of all arcs to obtain transpose graph
        LinkedList<Vertex>[] transposedAdj = transpose(adjList); // create transposed G

        System.out.println("PRINTING STRONGLY CONNECTED COMPONENTS FOR G'");
        while(!s.empty()) { // One by one pop vertex from S while S not empty
            Vertex v = (Vertex) s.pop();
            if(flags[v.getId()] == Vertex.Flag.WHITE) { // Let popped vertex be v, take v as source and do DFS
                visit(transposedAdj, transposedAdj[v.getId()], null, null, true);
                System.out.println();
            }
        }
    }

    /* create G' a transposed adjacency list with edges flipped (u,v) to (v,u) for each edge */
    public LinkedList<Vertex>[] transpose(LinkedList<Vertex>[] adjList){
        LinkedList<Vertex>[] transposedAdjList;
        transposedAdjList = new LinkedList[adjList.length];
        for(int i = 1; i < adjList.length; i++){
            for(int j = 1; j < adjList[i].size(); j++){
                if(transposedAdjList[adjList[i].get(j).getId()] == null){
                    transposedAdjList[adjList[i].get(j).getId()] = new LinkedList<Vertex>();

                    Vertex v = adjList[adjList[i].get(j).getId()].getFirst();
                    flags[v.getId()] = Vertex.Flag.WHITE;
                    transposedAdjList[adjList[i].get(j).getId()].add(v);
                }
                if(transposedAdjList[i] == null){
                    transposedAdjList[i] = new LinkedList<Vertex>();

                    Vertex v = adjList[i].getFirst();
                    flags[v.getId()] = Vertex.Flag.WHITE;
                    transposedAdjList[i].addFirst(v);
                }
                Vertex v = adjList[i].getFirst();
                flags[v.getId()] = Vertex.Flag.WHITE;
                transposedAdjList[adjList[i].get(j).getId()].add(v);
            }
        }
        return transposedAdjList;
    }
}
