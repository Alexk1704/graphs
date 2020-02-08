package org.io;


import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import org.ds.Edge;
import org.ds.Graph;

import java.io.File;
import java.io.IOException;

import static guru.nidi.graphviz.model.Factory.*;
import static java.lang.String.valueOf;


public class GraphViz {

    /* Resources:
     * graphviz.gitlab.io/_pages/doc/info/lang.html
     * github.com/nidi3/graphviz-java#user-content-how-it-works
     * Examples: graphs.grevian.org/example
     */
    public void printGraph(String name, Graph graph, boolean directed) throws IOException {
        try {
            MutableGraph g = mutGraph(name).setDirected(directed);
            for (int i = 1; i < graph.getVertexCount() + 1; i++) {
                g.add(mutNode(valueOf(i)));
            }
            for (int i = 0; i < graph.returnEdgeList().size(); i++) {
                Edge e = graph.returnEdgeList().get(i);
                int vFromId = e.getFromV().getId();
                int vToId = e.getToV().getId();
                MutableNode a = mutNode(valueOf(vFromId));
                MutableNode b = mutNode(valueOf(vToId));
                g.add(a.addLink(b));
            }
            Graphviz.fromGraph(g).render(Format.PNG).toFile(new File("example/" + name + ".png"));
        } catch(IOException e) {
            System.err.println("Something went wrong converting the graph to a .png file...");
        }
    }
}
