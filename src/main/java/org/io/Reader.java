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
            Integer nrArr[] = new Integer[3];
            int id = 0;
            while ((currentLine = reader.readLine()) != null) {
                if (reader.getLineNumber() == 1) {  // first line is always number of vertices in G(V,E)
                    String vStr = "";
                    for(int i = 0; i < currentLine.length(); i++){
                        if(Character.isDigit(currentLine.charAt(i))){ // if is digit
                            vStr += currentLine.charAt(i); // append character
                        }
                    }
                    vertexCount = Integer.valueOf(vStr);
                    edgeList = new EdgeList(vertexCount);
                    logger.info("vertex count is: " + vertexCount);
                } else {
                    id++;
                    String nrStr = "";
                    int nrCount = 0;
                    for(int i = 0; i < currentLine.length(); i++){
                        if(Character.isDigit(currentLine.charAt(i))){ // if is digit
                            nrStr += currentLine.charAt(i); // append character
                        }
                        if(currentLine.charAt(i) == ' '){
                            nrArr[nrCount] = Integer.valueOf(nrStr);
                            nrStr = "";
                            nrCount++;
                        }
                    }
                    nrArr[nrCount] = Integer.valueOf(nrStr);
                    if(nrArr.length == 3) edgeList.addEdge(nrArr[0], nrArr[2], id, isDirected, nrArr[1]);
                    else edgeList.addEdge(nrArr[0], nrArr[1], id, isDirected, null);
                }
            }
        } catch (IOException e) {
            logger.error("Catched IO exception...");
        }
        return edgeList;
        }
}
