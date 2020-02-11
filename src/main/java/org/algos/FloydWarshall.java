package org.algos;

import org.ds.Graph;

public class FloydWarshall {

    private Integer[][] weightAdjMatrix;
    int rowSize, colSize;

    public FloydWarshall(Graph g){
        weightAdjMatrix = g.exposeWeightAdjMatrix();
        rowSize = g.getAdjRowSize();
        colSize = g.getAdjColSize();
    }

    public Integer[][] floydwarshall(){
        int n = rowSize-1;
        Integer[][][] wMats = new Integer[n+1][rowSize][colSize];
        wMats[0] = weightAdjMatrix; // init matrix here with input weight matrix
        for(int k = 1; k <= n; k++){
            // Create new matrix with same dimensions
            wMats[k] = new Integer[rowSize][colSize];
            System.out.println("\nWEIGHT MATRIX - ITERATION: " + k);
            for(int i = 1; i <= n; i++){
                for(int j = 1; j <= n; j++){
                    // entry of current new matrix at index i,j (i is from, j is to) overwritten by min function
                    wMats[k][i][j] = min(wMats[k-1][i][j], wMats[k-1][i][k], wMats[k-1][k][j]);
                }
            }
            printMat(wMats[k]);
        }
        System.out.println("\nFLOYD-WARSHALL FINAL WEIGHT MATRIX RESULT");
        return wMats[n]; // return last version
    }

    /* min(ij, ik, kj), basically the same like relax(u, v, w)
     * ij: old entry
     * ik:
     * kj:
     * We are looking if we can get there through adjacent edges with a lower cost
     */
    private Integer min(Integer ij, Integer ik, Integer kj){
        System.out.println("\tMIN ON [ij: " + ij +"\tik: "+ ik +"\tkj: "+ kj + "]");
        if(ik == null || kj == null) return ij;
        else {
            if (ij == null) {
                return ik + kj;
            } else {
                if (ij > ik + kj) return ik + kj;
                else return ij;
            }
        }
    }

    public void printMat(Integer[][] mat){
        String colIndexes = "";
        for(int i = 1; i < colSize; i++)
            colIndexes = colIndexes + "v" + i + "\t";
        System.out.println("\t" + colIndexes);
        for(int i = 1; i < rowSize; i++){
            String colValues = "";
            for(int j = 1; j < colSize; j++)
                if(mat[i][j] == null) colValues = colValues + "X\t";
                else colValues = colValues + mat[i][j] + "\t";
            System.out.println("v" + i + "\t" + colValues);
        }
    }
}
