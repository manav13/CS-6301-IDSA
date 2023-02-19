/** RMQ sparseTable.
 *  @author SA
 */

package mnp200002;

import java.util.*;

public class RMQSparseTable implements RMQStructure {

    int[][] sparseTable;

    public RMQSparseTable() {

    }

    @Override
    public void preProcess(int[] arr) {
        int len = arr.length;
        int m = len;
        int n = log2(len) + 1;
        // intialize the sparseTable
        sparseTable = new int[m][n];

        for (int i = 0; i < m; i++) {
            sparseTable[i][0] = arr[i];
        }
        // preprocessing of the sparseTable
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < m; i++) {
                if (i >= m - power2(j))
                    continue;
                sparseTable[i][j] = Math.min(sparseTable[i][j - 1], sparseTable[i + power2(j - 1)][j - 1]);
            }
        }
    }

    @Override
    public int query(int[] arr, int i, int j) {
        // now its time to query
        // total number of elements
        int n = j - i + 1;
        int k = log2(n);
        int remains = n - power2(k) - 1;
        int result = 0;
        if (remains < 0) {
            result = sparseTable[i][k];
        } else {
            result = Math.min(sparseTable[i][k], sparseTable[i + remains][k]);
        }
        return result;
    }

    public static int power2(int num) {
        int result = (int) Math.pow(2, num);
        return result;
    }

    public static int log2(int len) {
        int result = (int) Math.floor((Math.log(len) / Math.log(2)));
        return result;
    }

    // for testing purposese
    public static void main(String[] args) {
        RMQSparseTable rst = new RMQSparseTable();

        int[] arr = new int[] { 4, 6, -1, 1, 11, 7, 4, 9, 5, 7, 3 };
        rst.preProcess(arr);
        System.out.println(Arrays.deepToString(rst.sparseTable));
        System.out.println(rst.query(arr, 3, 3));
        System.out.println(rst.query(arr, 0, 5));
        System.out.println(rst.query(arr, 0, 10));
    }

}
