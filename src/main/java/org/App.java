package org;


import org.algos.BreadthFirstSearch;
import org.algos.DepthFirstSearch;
import org.algos.Kruskal;
import org.algos.Prim;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ds.*;
import org.io.GraphViz;
import org.io.Reader;

import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.LinkedList;


public class App {

    // Define static logger var so that it references the Logger instance "App"
    private static final Logger logger = LogManager.getLogger(App.class);
    private static Reader reader;

    public static void main( String[] args ) throws IOException {
        logger.trace("Starting application.."); // Set up simple config that logs on console
        long t1 = System.nanoTime();

        /* DATA STRUCTURE SECTION */
        reader = new Reader();
        Graph gDirected = reader.readFile(args, true);
        Graph gUndirected = reader.readFile(args, false);

        /* print .png of graphs via gViz */
        GraphViz gViz = new GraphViz();
        gViz.printGraph("graph_dir", gDirected, true);
        gViz.printGraph("graph_undir", gDirected, false);

        /* Print DOT format of edge list (graph & digraph) */
        System.out.println(gDirected.convertDOT(true));
        System.out.println(gUndirected.convertDOT(false));

        gDirected.createDataStructs();
        gUndirected.createDataStructs();

        gDirected.printEdgeList(true);
        gUndirected.printEdgeList(false);

        gDirected.printIncMatrix();
        gUndirected.printIncMatrix();

        gDirected.printAdjMatrix();
        gUndirected.printAdjMatrix();

        System.out.println("\n\nPrinting Adjacency List (directed)...");
        gDirected.printAdjList();

        System.out.println("\nPrinting Adjacency List (undirected)...");
        gUndirected.printAdjList();

        System.out.println("\n\nDone printing data structures...\n");

        /* ALGORITHM SECTION */
        System.out.println("Starting algorithm section...");

        /* BREADTH FIRST SEARCH UNDIRECTED*/
        BreadthFirstSearch BFS = new BreadthFirstSearch(gUndirected); // UNDIRECTED
        Vertex bfsRoot = BFS.initTree(1); // vertex with id: 1 is source/root
        BFS.buildTree(); // builds BFS tree
        BFS.showPath(bfsRoot, BFS.searchTree(bfsRoot, 2)); // shows the path, looks for vertex in BFS tree we specified
        System.out.println("BUILDING BFS TREE (UNDIRECTED GRAPH) AND SHOWING PATH DONE...\n");

        /* BREADTH FIRST SEARCH DIRECTED */
        BreadthFirstSearch BFSDIR = new BreadthFirstSearch(gDirected); // DIRECTED
        Vertex bfsRootDirected = BFSDIR.initTree(1);
        BFSDIR.buildTree();
        BFSDIR.showPath(bfsRootDirected, BFSDIR.searchTree(bfsRootDirected, 2));
        System.out.println("BUILDING BFS TREE (DIRECTED GRAPH) AND SHOWING PATH DONE...\n");

        /* DEPTH FIRST SEARCH DIRECTED */
        DepthFirstSearch DFSDIR = new DepthFirstSearch(gDirected);

        /* NORMAL DEPTH SEARCH */
        // DFSDIR.depthSearch();

        /* TOPOLOGICAL SORT */
        DFSDIR.printTopSort(DFSDIR.topSort());

        /* SCC */
        DFSDIR.SCC(); // SCC visit of transposed graph

        /* KRUSKAL */
        Kruskal kruskal = new Kruskal();
        if(gUndirected.hasWeights()) kruskal.printKruskal((kruskal.MSTKruskal(gUndirected)));

        /* PRIM */
        Prim prim = new Prim();
        if(gUndirected.hasWeights()) prim.printPrim(prim.MSTPrim(gUndirected, 1));

        /* BELLMAN FORD */

        /* DJIKSTRA */

        /* FLOYD-WARSHALL */

        long elapsed = System.nanoTime() - t1;
        double seconds = elapsed / 1000000000;
        System.out.println("\nSeconds elapsed: " + seconds);
        logger.trace("Quiting application...");
    }
}



