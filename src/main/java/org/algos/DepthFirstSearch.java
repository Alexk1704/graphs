package org.algos;


/* Depth First Search
 * Start with a arbitrary node and search for all reachable nodes depth wise - calculate a DFS forest.
 * Usage of DFS in many other algorithms!
 * Let G be a graph with (V,E) represented by a adjacency list
 * Each Node u element of V has three attributes:
 *      - u.color: flag (e.g. white, black) for visited or not visited
 *      - u.pred: element of V (predecessor in DFS forest) / reference to node it comes from
 *      - u.d: element of {1,2,3,..., 2|V|}, time stamp when node was discovered first (entered),
 *                                           nr. of steps in algorithm when a node was reached
 *      - u.f: element of {1,2,3,,...,2|V|}, time stamp when adjacency list exhausted (finished),
 *                                           last exit step
 * DFS algorithm works for undirected and directed graphs
 * Run time O(V+E) for adjacency list representation
 *
 * Define a DFS forest G' = (V, E') with E' = {(v.pred, v) | v element of V and v.pred != null} -> Tree edges
 *
 * Edges in G can be classified:
 *      - Tree edges are edges in G' (traversed by algorithm)
 *      - Back edges are edges (u, v) from u to a predecessor v in G' (also self edges) - predecessors
 *        INFO: a self edge COULD be both (forward/backward) but we define it as a back edges
 *      - Forward edges are edges (u,v) from u to a successor v in G' - successors (skips order from tree)
 *      - Cross edges are all other edges
 *
 * Facts:
 *      - Directed graph G is acyclic if DFS returns NO back edges! This is useful for Makefiles, Order of compilation,
 *        e.g. for build tools, calculates order that fits the graph
 * */

import org.ds.Vertex;

import java.util.LinkedList;
import java.util.Stack;

public class DepthFirstSearch {

    Integer time;

    public DepthFirstSearch(){ }

    /* All nodes white (0), predecessor: NULL ptr, time var (global): 0 */
    public void depthSearch(LinkedList<Vertex>[] adjList){
        // nodes already white, and predecessor is NULL by default
        time = 0;
        for(int i = 1; i < adjList.length; i++){
            if(adjList[i].getFirst().getFlag() == Vertex.Flag.WHITE){ // if WHITE visit
                visit(adjList, adjList[i], null, null, false);
            }
        }
    }

    /*
     * First node, discovery time 1, then for each adjacent node:
     * If not visited (flag 0)
     *  set the predecessor of this node (current node)
     *  then recursively call visit for this node
     * After return of control, colour current node black
     * increment time and save finish time
     * Idea: do not program it recursively instead we could use a call stack, because sometimes it is more
     * efficient than the runtime environment call stack of java.
     * The algorithm builds the tree from bottom up, we need to turn it to get our DFS forest
     */
    // FIXME: rewrite with local heap stack instead of recursion
    public void visit(LinkedList<Vertex>[] adjList, LinkedList<Vertex> adj, Stack s, LinkedList<Vertex> topSort, boolean scc) {
        Vertex u = adj.getFirst();
        if(!scc) {
            time = time + 1;
            System.out.println("[V" + u.getId() + "]\tDISCOVERED ON STEP " + time);
            u.setDiscoveryTime(time); // set discovery time for calling vertex
        }
        u.setFlag(Vertex.Flag.GRAY); // set colour to GRAY (discovered)
        if(scc)
            System.out.print("[V" + u.getId() + "]\t");
        for(int i = 0; i < adj.size(); i++){ // explore edge (u, v), skip first since its u node
            // skip first since its calling node
            if(adj.get(i).getFlag() == Vertex.Flag.WHITE){ // adjacent vertex white?
                if(!scc)
                    adj.get(i).setParent(u); // set its parent
                visit(adjList, adjList[adj.get(i).getId()], s, topSort, scc); // recursive call
            }
        }
        u.setFlag(Vertex.Flag.BLACK); // set to black after return of control
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
    public LinkedList<Vertex> topSort(LinkedList<Vertex>[] adjList){
        LinkedList<Vertex> topSort;
        topSort = new LinkedList<>();
        time = 0;
        for(int i = 1; i < adjList.length; i++){
            if(adjList[i].getFirst().getFlag() == Vertex.Flag.WHITE){ // if WHITE visit
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
     * Kosaraju's algorithm
     * Flip edges (gives us same amount of strongly connected components)
     * Start with node with highest finishing time gives us same amount of trees/SCCs
     * Method flips the Tree, call DFS for transposed Tree
     * Depth search: What can be reached from my point?
     * We cannot see connected components if we go forward
     * For a Graph G = (V,E) we define G' = (V,E') with E' =
     *  {(v,u) | (u,v) element of E}
     *
     * 1. call DFS(G) and calculate finishing times v.f for each node v
     * 2. calculate G' (transposed)
     * 3. call DFS(G'), work through neighbouring nodes in descending order of v.f
     * 4. return every tree of DFS forest (from 3.) as SCC
     *
     * DFS of a graph with only one SCC always produces a tree
     * So depending on source vertex DFS can produce either a tree or a forest
     * To find and print all SCCs, if we do a DFS of graph and store vertices according to their finish times,
     * we make sure that the finish time of a vertex hat connects to other SCCs (other than its own SCC), will
     * always be greater than finish time of vertices in other SCC.
     * DFS traversal of complete graph and push every finished vertex to a stack.
     *
     * Next step: we reverse the graph. In G' edges that connect two components are reversed.
     * So SCC {0, 1, 2} becomes sink and SCC {4} becomes source. As discussed above, in S, we always have
     * 0 before 3 and 4. so if we do DFS on reversed graph using sequence of vertices in stack, we process from sink to source (in G').
     *
     */
    public void SCC(LinkedList<Vertex>[] adjList){
        System.out.println("STRONGLY CONNECTED COMPONENTS (DIGRAPH):");
        Stack s = new Stack(); // Create empty stack S
        time = 0;
        System.out.println("DFS TRAVERSAL FOR G:");
        for(int i = 1; i < adjList.length; i++){ // DFS traversal of graph G
            if(adjList[i].getFirst().getFlag() == Vertex.Flag.WHITE){ // if WHITE visit
                visit(adjList, adjList[i], s, null, false);
            }
        }
        System.out.println("\nTRANSPOSING ADJACENCY LIST (CREATING G')");
        LinkedList<Vertex>[] transposedAdj = transpose(adjList); // create transposed G

        System.out.println("PRINTING STRONGLY CONNECTED COMPONENTS FOR G'");
        while(!s.empty()) {
            Vertex v = (Vertex) s.pop();
            if(v.getFlag() == Vertex.Flag.WHITE) {
                visit(transposedAdj, transposedAdj[v.getId()], null, null, true);
                System.out.println();
            }
        }
        // Reverse directions of all arcs to obtain transpose graph
        // One by one pop vertex from S while S not empty
        // Let popped vertex v, take v as source and do DFS.
        // DFS starting from v prints SCC of v, process vertices in order 0, 3, 4, 2, 1
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
                    v.setFlag(Vertex.Flag.WHITE);
                    transposedAdjList[adjList[i].get(j).getId()].add(v);
                }
                Vertex v = adjList[i].getFirst();
                v.setFlag(Vertex.Flag.WHITE);
                transposedAdjList[adjList[i].get(j).getId()].add(v);
            }
        }
        return transposedAdjList;
    }

    /* INFO about RECURSION/EXPLICIT STACKS
     * The Church-Turing thesis essentially states that every model of computation is equivalent in power.
     * Hence, everything that you can do using recursion (i.e. the lambda calculus) can also be done using iteration (i.e. a Turing machine).
     *
     * In fact, most computers in the world are based on the Turing machine. Hence, every computer uses iteration only.
     * Nevertheless, your computer can still execute recursive programs.
     * This is because a compiler translates your recursive program into iterative machine code.
     * The lambda calculus (the basis of recursion) and Turing machines (the basis of iteration)
     * are the most popular models of computation. Another popular model of computation is μ-recursion.

     * What is a model of computation?
     * Mathematicians wanted to know which problems can be computed (i.e. which problems have a solution)
     * and which problems cannot be computed (i.e. which problems have no solution).
     * They also wanted to know the nature of the computation (e.g. how much time does it take to compute the solution relative to its input size, etc.).
     * Problem “computation” is a very abstract term.
     *
     * How do you reason about something that's not concrete? Invent a model of computation to reason about to capture "essence of computation"
     * That means that if there's a problem which can be computed then there must be an algorithm for computing it in every model of computation.
     *
     * Why is recursion sometimes inefficient and often cause stack overflows, since it just uses stacks in an automatic way invisible to the user?
     * Reason why using too much recursion causes a stack overflow:
     *      This is because of the way operating systems handle processes.
     *      Most operating systems impose a maximum limit on the size of a stack.
     * On the other hand, the size of the heap can be dynamically increased while the process is executing (as long as free space is available).
     * Hence, you don't have to worry about running out of memory when using iteration (even when using an explicit stack).
     *
     * Furthermore, recursion is generally slower than iteration because calling a function requires a context switch
     * while in iteration you only need to modify the instruction pointer (i.e. jump, possibly conditional).
     *
     * However, this doesn't mean that iteration is always better than recursion.
            * Recursive programs are usually smaller and easier to understand than iterative programs.
            * In addition, in certain cases the compiler can eliminate context switches altogether via tail call optimization (TCO).
            * This not only makes recursion as fast as iteration but also ensures that the stack size doesn't increase.
     */
}
