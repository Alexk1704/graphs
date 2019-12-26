package org;


import org.algos.BreadthFirstSearch;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ds.*;
import org.io.GraphViz;
import org.io.Reader;

import java.io.IOException;
import java.util.ArrayList;


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
        for(int i = 1; i < adjList.getDimension(); i++){
            System.out.print("\n[V" + i + "]: ");
            for(Vertex v: adjList.exposeAdjList()[i]) {
                System.out.print("V" + v.getId() + "->");
            }
        }
        System.out.println("\n\nPrinting Adjacency List (directed)...");
        for(int i = 1; i < adjListDir.getDimension(); i++){
            System.out.print("\n[V" + i + "]: ");
            for(Vertex v: adjListDir.exposeAdjList()[i]) {
                System.out.print("V" + v.getId() + "->");
            }
        }
        System.out.println("\n\nDone printing data structures...");

        /* ALGORITHM SECTION */
        /* BREADTH-FIRST-SEARCH */
        BFSTreeNode<Vertex> searchedVertex; // for tree search method

        BreadthFirstSearch bfs = new BreadthFirstSearch(adjList.exposeAdjList()); // UNDIRECTED
        BFSTreeNode<Vertex> bfsRootNode = bfs.initBfsTree(1); // vertex with id: 1 is source/root
        bfs.processList();

        searchedVertex = bfs.search(bfsRootNode, 5); // search tree for selected vertex id
        bfs.shortestPath(bfsRootNode, searchedVertex); // look for BFS tree node vertex with id 8!

        BreadthFirstSearch bfsDir = new BreadthFirstSearch(adjListDir.exposeAdjList()); // DIRECTED
        BFSTreeNode<Vertex> bfsRootNodeDir = bfsDir.initBfsTree(1);
        bfsDir.processList();

        searchedVertex = bfsDir.search(bfsRootNodeDir, 5);
        bfs.shortestPath(bfsRootNodeDir, searchedVertex);

        /* DEPTH-FIRST-SEARCH */

        /* TOPOLOGICAL SORT */

        /* SCC */


        logger.trace("Quiting application...");
    }
}



