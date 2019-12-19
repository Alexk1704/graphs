package org.ds;


public class AdjacencyMatrix {

    /* Graph G = (V,E)
     * |V| x |V| Matrix with entry a(i,j) = 1 if Vertex i adjacent to Vertex j, else 0
     *
     * Example:
     *    v1 v2 v3 ...
     * v1  0  1  1
     * v2  1  1  0
     * v3  1  0  0
     * ...
     * - Entry in diagonal correspond to self edges
     * - Multiplying adjacent matrix k times with itself,
     *      resulting entry a(i,j) becomes the nr. of paths of length k from i to j.
     */

    private int[][] adjMat;
    private int rowSize;
    private int colSize;

    public AdjacencyMatrix(EdgeList el){
        adjMat = new int[el.getVertexCount()+1][el.getVertexCount()+1]; // +1 bc we index v,e from 1
        // for traversal
        rowSize = el.getVertexCount()+1;
        colSize = el.getVertexCount()+1;
        // init with 0s
        for(int i = 0; i < rowSize; i++){
            for(int j = 0; j < colSize; j++){
                adjMat[i][j] = 0;
            }
        }
        createMat(el);
    }

    private void createMat(EdgeList el){
        for(int i = 0; i < el.returnEdgeList().size(); i++){
            Vertex fromV = el.returnEdgeList().get(i).getFromV();
            Vertex toV = el.returnEdgeList().get(i).getToV();

            adjMat[fromV.getId()][toV.getId()] = 1;
            adjMat[toV.getId()][fromV.getId()] = 1;
        }
    }

    public int[][] exposeMat(){
        return adjMat;
    }

    public int getRowSize(){
        return rowSize;
    }

    public int getColSize(){
        return colSize;
    }
}
