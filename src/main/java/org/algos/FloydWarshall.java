package org.algos;

public class FloydWarshall {


    // public int[][] returnTransposed(int[][] weightAdjMat)
    // need a adjacency matrix with weights as entries
    // n = weightAdjMat.getRowSize();
    // int[][] D⁰ = weightAdjMat; // init matrix here with input mat
    // for(int k = 1, k <= n; k++)
        // create new matrix here D  with same dimensions
        // iterate through rows
            // iterate through cols
                // write into new matrix what our relaxation/min function returns
                // min(d_k-1[i[{j], d_k-1[i][k] + d_k-1[k][j])
                // Im Endeffekt schauen ob man über benachbarte Kante kürzer hinkommt
    // min = relax methode (matrix übergeben und index von i, j, k als Integer Objekte z.B.
    // Matrix muss dann am Ende überschrieben werden
}
