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
        // Set up simple config that logs on console
        logger.trace("Starting application..");

        reader = new Reader();
        EdgeList edgeListDir = reader.readFile(args, true);
        EdgeList edgeListUnDir = reader.readFile(args, false);

        // print .png of both graph versions
        GraphViz gViz = new GraphViz();
        gViz.printGraph("graph_dir", edgeListDir, true);
        gViz.printGraph("graph_undir", edgeListUnDir, false);

        // EdgeList as ArrayList of edge objects
        ArrayList<Edge> elDir = edgeListDir.returnEdgeList();

        System.out.println("\nPrinting Edge List...");
        for(int i = 0; i < elDir.size(); i++){
            Edge e = elDir.get(i);
            System.out.println("Edge " + e.getId() + "\t"
                    + "v" + e.getFromV().getId() + " -" + e.getWeight() + "-> " + "v" + e.getToV().getId());
        }

        System.out.println("\nPrinting DOT format...");
        // Print DOT formats of EdgeList (graph & digraph)
        System.out.println(edgeListDir.convertDOT(true));
        System.out.println(edgeListUnDir.convertDOT(false));

        // From this form begin to convert into other representations
        // INCIDENCE MATRIX
        IncidenceMatrix imat = new IncidenceMatrix(edgeListUnDir);
        int[][] iMat = imat.exposeMat();

        System.out.println("\nPrinting Incidence Matrix...");

        String colIndexes = "";
        for(int i = 1; i < imat.getColSize(); i++){
            colIndexes = colIndexes + "e" + i + "\t";
        }
        System.out.println("\t" + colIndexes);

        for(int i = 1; i < imat.getRowSize(); i++){
            String colValues = "";
            for(int j = 1; j < imat.getColSize(); j++){
                colValues = colValues + iMat[i][j] + "\t";
            }
            System.out.println("v" + i + "\t" + colValues);
        }

        // ADJACENCY MATRIX
        AdjacencyMatrix amat = new AdjacencyMatrix(edgeListUnDir);
        int[][] aMat = amat.exposeMat();

        System.out.println("\nPrinting Adjacency Matrix...");
        colIndexes = "";
        for(int i = 1; i < amat.getColSize(); i++){
            colIndexes = colIndexes + "v" + i + "\t";
        }
        System.out.println("\t" + colIndexes);

        for(int i = 1; i < amat.getRowSize(); i++){
            String colValues = "";
            for(int j = 1; j < amat.getColSize(); j++){
                colValues = colValues + aMat[i][j] + "\t";
            }
            System.out.println("v" + i + "\t" + colValues);
        }

        // ADJACENCY LIST
        AdjacencyList adjListUnDir = new AdjacencyList(edgeListUnDir);
        AdjacencyList adjListDir = new AdjacencyList(edgeListDir);

        System.out.println("\nPrinting Adjacency List (undirected)...");
        for(int i = 1; i < adjListUnDir.getDimension(); i++){
            System.out.print("\n[V" + i + "]: ");
            for(Vertex v: adjListUnDir.exposeAdjList()[i]) {
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

        /* ALGO SECTION */
        BreadthFirstSearch bfsUndir = new BreadthFirstSearch(adjListUnDir.exposeAdjList());
        BFSTreeNode<Vertex> rootNode = bfsUndir.initBfsTree(1);
        bfsUndir.processList();
        //bfsUndir.dumpTree(rootNode);
        //bfsUndir.shortestPath(1, 5);

        logger.trace("Quiting application...");

    }
}



