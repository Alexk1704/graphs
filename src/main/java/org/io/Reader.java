package org.io;


import org.App;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ds.Graph;

import java.io.*;


public class Reader {
    private static final Logger logger = LogManager.getLogger(App.class);

    public Graph readFile(final String args[], boolean isDirected) {
        File input = null;
        Graph g = null;

        if (args.length != 0 && args != null) {
            input = new File(args[0]);
        } else {
            logger.error("Can't open file from this path...");
            System.exit(-1);
        }
        try {
            LineNumberReader reader = new LineNumberReader(new FileReader(input));
            String currentLine;
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
                    g = new Graph(Integer.valueOf(vStr), isDirected);
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
                    if(nrArr[2] != null) {
                        g.addEdge(nrArr[0], nrArr[2], id, isDirected, nrArr[1]);
                        if(!g.hasWeights()) g.setHasWeights(true);
                    }
                    else g.addEdge(nrArr[0], nrArr[1], id, isDirected, null);
                }
            }
        } catch (IOException e) {
            logger.error("Catched IO exception...");
        }
        return g;
    }
}
