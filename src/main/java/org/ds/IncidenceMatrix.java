package org.ds;


public class IncidenceMatrix {

    /* Graph G = (V,E)
     * |V| x |E| Matrix with entry a(i,j) = 1 if Edge j incident to Vertex i, else 0
     *
     * Example:
     *    e1 e2 e3 ...
     * v1  0  1  1
     * v2  1  1  0
     * v3  0  0  1
     * ...
     * - Each column has 1 two times, rest is 0
     * - Sum of 1 in each row is deg(G)
     * - Parallel edges create identical columns
     * - G is isomorphic to G', if their incidence matrices are identical
     *       through permutation of rows & columns
     */

    private int[][] incMat;
    private int rowSize;
    private int colSize;

    public IncidenceMatrix(EdgeList el){
        incMat = new int[el.getVertexCount()+1][el.returnEdgeList().size()+1]; // +1 bc we index v,e from 1
        // for traversal
        rowSize = el.getVertexCount()+1;
        colSize = el.returnEdgeList().size()+1;
        // init with 0s
        for(int i = 0; i < rowSize; i++){
            for(int j = 0; j < colSize; j++){
                incMat[i][j] = 0;
            }
        }
        createMat(el);
    }

    private void createMat(EdgeList el){
        for(int i = 0; i < el.returnEdgeList().size(); i++){
            Edge e = el.returnEdgeList().get(i);
            Vertex fromV = el.returnEdgeList().get(i).getFromV();
            Vertex toV = el.returnEdgeList().get(i).getToV();

            incMat[fromV.getId()][e.getId()] = 1;
            incMat[toV.getId()][e.getId()] = 1;
        }
    }

    public int[][] exposeMat(){
        return incMat;
    }

    public int getRowSize(){
        return rowSize;
    }

    public int getColSize(){
        return colSize;
    }

}
