package mnp200002;

public class RMQBlock implements RMQStructure {
    
    public int blockSize;
    public int numBlocks;
    public int[] minimaBlockArray;

    @Override
    public void preProcess(int[] arr) {
        int n = arr.length;
        numBlocks = (int)Math.ceil(n/blockSize);
        minimaBlockArray = new int[numBlocks];
        
        for(int i=0; i<numBlocks; i++) {
            int min = Integer.MAX_VALUE;
            for(int j=0; j<blockSize; j++) {
                int index = i*blockSize+j;
                if(index >= n) break;

                if(arr[index] < min) min = arr[index];
            }
            minimaBlockArray[i] = min;
        }
    }

    @Override
    public int query(int[] arr, int i, int j) {
        int block1 = i/blockSize;
        int block2 = j/blockSize;
        int n = arr.length;
        int min = Integer.MAX_VALUE;

        if(block1 == block2) {
            for(int index=i; index<=j; index++) {
                if(arr[index] < min) {
                    min = arr[index];
                }
            }
            return min;
        }
        else {
            // Min for Block 1
            for(int index=i; index<=block1*blockSize+blockSize; index++) {
                if(index >= n) break;
                if(arr[index] < min) min = arr[index];
            }
            // Min for Block2
            for(int index=block2*blockSize; index<=j; index++) {
                if(arr[index] < min) min = arr[index];
            }
            // Min for blocks in between
            for(int index=block1+1; index < block2; index++) {
                if(minimaBlockArray[index] < min) min = minimaBlockArray[index];
            }
        }
        return min;
    }

    public static void main(String[] args) {
        RMQBlock rmq = new RMQBlock();
        int[] arr = new int[] { 4, 6, -1, 1, 11, 7, -40, 9, 5, 7, 3, 4, 5, -1, 3, 4, 77, 1, 8, 9, 0, 1, 9 };
        rmq.blockSize = 3;
        rmq.preProcess(arr);
        System.out.println(rmq.query(arr, 8, 22));
        // System.out.println(rho.query(arr, 0, 5));
        // System.out.println(rho.query(arr, 0, 10));
    }
}
