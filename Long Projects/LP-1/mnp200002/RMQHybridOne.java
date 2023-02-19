/** RMQ Hybrid One.
 *  @author SA
 */

package mnp200002;

import java.util.*;

public class RMQHybridOne implements RMQStructure {
    RMQSparseTable rst;
    int[] blocks;
    int blockSize;
    int len;

    @Override
    public void preProcess(int[] arr) {
        rst = new RMQSparseTable();
        len = arr.length;
        blockSize = log2(len);
        // intialize the blocks
        blocks = new int[(int) Math.ceil((float) len / blockSize)];
        // fill blocks with max values
        Arrays.fill(blocks, Integer.MAX_VALUE);
        // populate the blocks minima
        for (int i = 0; i < len; i += blockSize) {
            for (int j = i; j < i + blockSize && j < len; j++) {
                blocks[i / blockSize] = Math.min(blocks[i / blockSize], arr[j]);
            }
        }
        rst.preProcess(blocks);
    }

    @Override
    public int query(int[] arr, int i, int j) {
        // minimas
        int leftMin = Integer.MAX_VALUE;
        int rightMin = Integer.MAX_VALUE;
        int blockMinima = Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;
        // start index of the block
        int start = i / blockSize + 1;
        // end index of the block
        int end = j / blockSize - 1;
        // if it reside in the same block
        if (j - i < blockSize) {
            for (int k = i; k <= j; k++) {
                min = Math.min(min, arr[k]);
            }
            return min;
        }
        // if it reside in the nearby blocks
        if(start <= end){
            blockMinima = rst.query(blocks, start, end);
        }
        
        for(int left = i; left < start * blockSize && left < len; left++){
            leftMin = Math.min(leftMin, arr[left]);
        }
        for(int right = j; right > (end + 1) * blockSize && right >= 0; right--){
            rightMin = Math.min(rightMin, arr[right]);

        }
        min = Math.min(leftMin, Math.min(blockMinima, rightMin));
        return min;
        // return Math.min(leftMin, Math.min(blockMinima,rightMin));
    }

    public static int power2(int num) {
        int result = (int) Math.pow(2, num);
        return result;
    }

    public static int log2(int len) {
        int result = (int) Math.floor((Math.log(len) / Math.log(2)));
        return result;
    }

    public static void main(String[] args) {
        RMQHybridOne rho = new RMQHybridOne();
        int[] arr = new int[] { 4, 6, -1, 1, 11, 7, -40, 9, 5, 7, 3, 4, 5, -1, 3, 4, 77, 1, 8, 9, 0, 1, 9 };
        rho.preProcess(arr);
        System.out.println(rho.query(arr, 3, 7));
        // System.out.println(rho.query(arr, 0, 5));
        // System.out.println(rho.query(arr, 0, 10));
    }
}
