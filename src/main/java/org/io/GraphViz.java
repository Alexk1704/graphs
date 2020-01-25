package org.io;


import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;

import guru.nidi.graphviz.model.MutableNode;
import org.ds.Edge;
import org.ds.EdgeList;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static guru.nidi.graphviz.model.Factory.*;
import static java.lang.String.valueOf;


public class GraphViz {

    /* Resources:
     * graphviz.gitlab.io/_pages/doc/info/lang.html
     * github.com/nidi3/graphviz-java#user-content-how-it-works
     * Examples: graphs.grevian.org/example
     */
    public void printGraph(String name, EdgeList el, boolean directed) throws IOException {
        try {
            MutableGraph g = mutGraph(name).setDirected(directed);
            // Maybe first create vertex then add links later...?
            for (int i = 1; i < el.getVertexCount() + 1; i++) {
                g.add(mutNode(valueOf(i)));
            }
            for (int i = 0; i < el.returnEdgeList().size(); i++) {
                Edge e = el.returnEdgeList().get(i);
                int vFromId = e.getFromV().getId();
                int vToId = e.getToV().getId();
                MutableNode a = mutNode(valueOf(vFromId));
                MutableNode b = mutNode(valueOf(vToId));
                //System.out.println(a.toString() + b.toString());
                g.add(a.addLink(b));
            }
            Graphviz.fromGraph(g).render(Format.PNG).toFile(new File("example/" + name + ".png"));
        } catch(IOException e) {
            System.err.println("Something went wrong converting the graph to a .png file...");
        }
    }
}
