package org.io;


import org.App;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ds.EdgeList;

import java.io.*;


public class Reader {
    private String currentLine;
    private int vertexCount;
    private File input;
    private LineNumberReader reader;
    private EdgeList edgeList;
    private int[] vertexArr;
    private static final Logger logger = LogManager.getLogger(App.class);

    public Reader() {
        currentLine = null;
        input = null;
        reader = null;
        edgeList = null;
        vertexCount = 0;
    }

    public EdgeList readFile(final String args[], boolean isDirected) {
        if (args.length != 0 && args != null) {
            input = new File(args[0]);
        } else {
            logger.error("Can't open file from this path...");
            System.exit(-1);
        }
        try {
            reader = new LineNumberReader(new FileReader(input));

            int id = 0;
            while ((currentLine = reader.readLine()) != null) {
                if (reader.getLineNumber() == 1) {  // first line is always number of vertices in G(V,E)
                    vertexCount = Character.getNumericValue(currentLine.charAt(0));
                    edgeList = new EdgeList(vertexCount);
                    logger.info("vertex count is: " + vertexCount);
                } else {
                    id++;
                    Integer weight;
                    weight = null;
                    int second;
                    int first = Character.getNumericValue(currentLine.charAt(0));
                    if(currentLine.length() == 5){ // weights included
                        weight = Character.getNumericValue(currentLine.charAt(2));
                        second = Character.getNumericValue(currentLine.charAt(4));
                    } else {
                        second = Character.getNumericValue(currentLine.charAt(2));
                    }
                    edgeList.addEdge(first, second, id, isDirected, weight);
                }
            }
        } catch (IOException e) {
            logger.error("Catched IO exception...");
        }
        return edgeList;
        }
}
