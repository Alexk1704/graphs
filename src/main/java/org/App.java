package org;


import org.algos.BreadthFirstSearch;
import org.algos.DepthFirstSearch;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ds.*;
import org.io.GraphViz;
import org.io.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


public class App {

    // Define static logger var so that it references the Logger instance "App"
    private static final Logger logger = LogManager.getLogger(App.class);
    private static Reader reader;

    public static void main( String[] args ) throws IOException {
        logger.trace("Starting application.."); // Set up simple config that logs on console

        reader = new Reader();
        EdgeList edgeListDir = reader.readFile(args, true);
        EdgeList edgeList = reader.readFile(args, false);

        /* print .png of both graph versions via gViz */
        GraphViz gViz = new GraphViz();
        gViz.printGraph("graph_dir", edgeListDir, true);
        gViz.printGraph("graph_undir", edgeList, false);

        // edge list as ArrayList of edge objects
        ArrayList<Edge> elDir = edgeListDir.returnEdgeList();

        System.out.println("\nPrinting Edge List...");
        for(int i = 0; i < elDir.size(); i++){
            Edge e = elDir.get(i);
            System.out.println("Edge " + e.getId() + "\t"
                    + "v" + e.getFromV().getId() + " -" + e.getWeight() + "-> " + "v" + e.getToV().getId());
        }

        /* Print DOT format of edge list (graph & digraph) */
        System.out.println("\nPrinting DOT format...");
        System.out.println(edgeListDir.convertDOT(true));
        System.out.println(edgeList.convertDOT(false));

        /* Conversion to different representations */

        // INCIDENCE MATRIX
        IncidenceMatrix iMatrix = new IncidenceMatrix(edgeList);
        int[][] iMat = iMatrix.exposeMat();

        System.out.println("\nPrinting Incidence Matrix...");

        String colIndexes = "";
        for(int i = 1; i < iMatrix.getColSize(); i++){
            colIndexes = colIndexes + "e" + i + "\t";
        }
        System.out.println("\t" + colIndexes);

        for(int i = 1; i < iMatrix.getRowSize(); i++){
            String colValues = "";
            for(int j = 1; j < iMatrix.getColSize(); j++){
                colValues = colValues + iMat[i][j] + "\t";
            }
            System.out.println("v" + i + "\t" + colValues);
        }
        // FIXME: refactor print methods for data structures into classes
        // ADJACENCY MATRIX
        AdjacencyMatrix aMatrix = new AdjacencyMatrix(edgeList);
        int[][] aMat = aMatrix.exposeMat();

        System.out.println("\nPrinting Adjacency Matrix...");
        colIndexes = "";
        for(int i = 1; i < aMatrix.getColSize(); i++){
            colIndexes = colIndexes + "v" + i + "\t";
        }
        System.out.println("\t" + colIndexes);

        for(int i = 1; i < aMatrix.getRowSize(); i++){
            String colValues = "";
            for(int j = 1; j < aMatrix.getColSize(); j++){
                colValues = colValues + aMat[i][j] + "\t";
            }
            System.out.println("v" + i + "\t" + colValues);
        }

        // ADJACENCY LIST
        AdjacencyList adjList = new AdjacencyList(edgeList);
        AdjacencyList adjListDir = new AdjacencyList(edgeListDir);

        System.out.println("\nPrinting Adjacency List (undirected)...");
        adjList.printAdjList(adjList.exposeAdjList());

        System.out.println("\n\nPrinting Adjacency List (directed)...");
        adjListDir.printAdjList(adjListDir.exposeAdjList());

        System.out.println("\n\nDone printing data structures...\n");

        /* ALGORITHM SECTION */
        System.out.println("Starting algorithm section...");

        /* BREADTH-FIRST-SEARCH */
        BreadthFirstSearch bfs = new BreadthFirstSearch(adjList.exposeAdjList()); // UNDIRECTED
        Vertex bfsRoot = bfs.initTree(1); // vertex with id: 1 is source/root
        bfs.buildTree(); // builds BFS tree
        bfs.showPath(bfsRoot, bfs.searchTree(bfsRoot, 2)); // shows the path, looks for vertex in BFS tree we specified
        System.out.println("BUILDING BFS TREE (UNDIRECTED GRAPH) AND SHOWING PATH DONE...\n");

        BreadthFirstSearch bfsDirected = new BreadthFirstSearch(adjListDir.exposeAdjList()); // DIRECTED
        Vertex bfsRootDirected = bfsDirected.initTree(1);
        bfsDirected.buildTree();
        bfsDirected.showPath(bfsRootDirected, bfsDirected.searchTree(bfsRootDirected, 2));
        System.out.println("BUILDING BFS TREE (DIRECTED GRAPH) AND SHOWING PATH DONE...\n");

        /* DEPTH-FIRST-SEARCH */
        EdgeList edgeListDfsDirected = reader.readFile(args, false);
        AdjacencyList alDfs = new AdjacencyList(edgeListDfsDirected); // create a new adjacency list
        LinkedList<Vertex>[] alDfsL = alDfs.exposeAdjList();

        DepthFirstSearch DFS = new DepthFirstSearch();
        //dfsDirected.depthSearch();

        /* TOPOLOGICAL SORT */
        LinkedList<Vertex> topSort = DFS.topSort(alDfsL);
        DFS.printTopSort(topSort);

        /* SCC */
        EdgeList edgeListScc = reader.readFile(args, true);
        AdjacencyList alScc = new AdjacencyList(edgeListScc); // create a new adjacency list
        // LinkedList<Vertex>[] talSccL = dfsDirected.transpose(alScc.exposeAdjList());
        // alScc.printAdjList(talSccL);
        DFS.SCC(alScc.exposeAdjList()); // SCC visit of transposed graph

        logger.trace("Quiting application...");
    }
}



