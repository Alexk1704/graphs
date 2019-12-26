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
 * Define a DFS forest G' = (V, E') with E' = {(v.pred, v) | v element of V and v.pred != null}
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

import org.ds.AdjacencyList;

public class DepthFirstSearch {

    public DepthFirstSearch(){

    }

    // All nodes white (0), predecessor: NULL ptr, time var (global): 0
    public void init(){

    }

    /*
     * First node, discovery time 1, then for each adjacent node:
     * If not visited (flag 0)
     *  set the predecessor of this node (current node)
     *  then recursively call visit for this node
     * After return of control, colour current node black
     * increment time and save finish time
     * Idea: do not program it recursively instead we could use a call stack, because sometimes it is more
     * efficient then the runtime environment call stack of java.
     * The algorithm builds the tree from bottom up, we need to turn it to get our DFS forest
     */
    public void visit(AdjacencyList adjList, int index){

    }

    /* Topological sort of a DAG (directed, acyclic graph) is a sort of all nodes,
     * so that u comes before v, if there is an edge (u,v)
     * All nodes are white, predecessor null, time = 0
     * For each white node call DFS visit(G,u)
     *
     * 1. call DFS(G) and calculate finishing times v.f for each node v
     * 2. When setting v.f add node to the front of a list
     * 3. return that list
     * A directed graph G is acyclic exactly then when DFS gives us no back edges
     */
    public void topSort(){

    }

    /* SCC(G): takes transposed G
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
     * */
    public void scc(){

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
