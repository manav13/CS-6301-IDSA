package mnp200002;

import java.util.HashMap;
import java.util.Stack;
import java.util.Arrays;

public class RMQFischerHeun implements RMQStructure {
    RMQBlock rmqBlock;
    RMQSparseTable rmqSparse;
    public int blockSize;
    public int[] summaryArray;
    HashMap<String, RMQIndexTable> cartesianHash;

    public RMQFischerHeun() {
        rmqBlock = new RMQBlock();
        rmqSparse = new RMQSparseTable();
        cartesianHash = new HashMap<>();
    }

    public static int log4(int len) {
        int result = (int) Math.floor((Math.log(len) / Math.log(4)));
        return result;
    }

    public static String cartesianEncoding(int[] arr) {
        StringBuilder output = new StringBuilder();
        Stack<Integer> st = new Stack<>();

        for(int i=0; i<arr.length; i++) {
            if(st.empty() || st.peek() <= arr[i]) {
                st.push(arr[i]);
                output.append("1");
            }
            else {
                while(!st.empty() && st.peek() > arr[i]) {
                    st.pop();
                    output.append("0");
                }
                st.push(arr[i]);
                output.append("1");

            }
        }

        while(!st.empty()) {
            st.pop();
            output.append("0");

        }

        return output.toString();
    }

    @Override
    public void preProcess(int[] arr) {
        int n = arr.length;
        // rmqBlock.blockSize = log4(n)/2;
        rmqBlock.blockSize = (int)Math.ceil(Math.sqrt(arr.length));
        blockSize = rmqBlock.blockSize;

        rmqBlock.preProcess(arr);
        summaryArray = rmqBlock.minimaBlockArray;
        rmqSparse.preProcess(summaryArray);
        
        for(int i=0; i<rmqBlock.numBlocks; i++) {
            int startIndex = i * blockSize;
            int endIndex = Math.min((i+1) * blockSize, n);
            String cartesianNumber = cartesianEncoding(Arrays.copyOfRange(arr, startIndex, endIndex));

            if(!cartesianHash.containsKey(cartesianNumber)) {
                RMQIndexTable rmqIndex = new RMQIndexTable();
                rmqIndex.preProcess(Arrays.copyOfRange(arr, startIndex, endIndex));
                cartesianHash.put(cartesianNumber, rmqIndex);
            }
        }
    }

    @Override
    public int query(int[] arr, int i, int j) {
        int n = arr.length;
        int min = Integer.MAX_VALUE;

        int block1 = i/blockSize;
        int block2 = j/blockSize;

        String cartesianNumber;

        // If indexes are in same block
        if(block1 == block2) {
            for(int index=i; index<=j; index++) {
                if(arr[index] < min) {
                    min = arr[index];
                }
            }
            return min;
        }

        // Update min from block1
        int[] leftBlock = Arrays.copyOfRange(arr, block1*blockSize, (block1+1)*blockSize);
        cartesianNumber = cartesianEncoding(leftBlock);
        RMQIndexTable rmqIndex = cartesianHash.get(cartesianNumber);
        min = Math.min(min, rmqIndex.query(leftBlock, i-block1*blockSize, blockSize-1));

        // Update min from block2
        int[] rightBlock = Arrays.copyOfRange(arr, block2*blockSize, Math.min((block2+1)*blockSize, n));
        cartesianNumber = cartesianEncoding(rightBlock);
        rmqIndex = cartesianHash.get(cartesianNumber);
        min = Math.min(min, rmqIndex.query(rightBlock, 0, j-block2*blockSize));

        // Update min for blocks in between 1 and 2
        if(block2 - block1 > 1) {
            min = Math.min(min, rmqSparse.query(summaryArray, block1+1, block2-1));
        }

        return min;
    } 

    public static void main(String[] args) {
        RMQFischerHeun rmq = new RMQFischerHeun();
        int[] arr = new int[] {66, 13, 59, 5, 55, 39, 31, 17, 25, 84, 44, 22};

        rmq.preProcess(arr);

        // System.out.println(rmq.query(arr, 0, 2));
        System.out.println(rmq.query(arr, 1, 9));
        // System.out.println(rmq.query(arr, 1, 3));
        // System.out.println(rmq.query(arr, 0, 3));

        // System.out.println(rmq.cartesianEncoding(arr));
    }
}