package mnp200002;

public class RMQIndexTable implements RMQStructure {
    
    int[][] indexTable;

    @Override
    public void preProcess(int[] arr) {
        int size = arr.length;
        indexTable = new int[size][];

        for(int i=0; i<size; i++) {
            indexTable[i] = new int[i+1];
            indexTable[i][i] = i;
        }

        for(int i=1; i<size; i++) {
            for(int col=0; col<size-i; col++) {
                int row = col+i;
                if(arr[indexTable[row-1][col]] < arr[indexTable[row][col+1]]) {
                    indexTable[row][col] = indexTable[row-1][col];
                }
                else {
                    indexTable[row][col] = indexTable[row][col+1];
                }
            }
        }
    }


    @Override
    public int query(int[] arr, int i, int j) {
        return arr[indexTable[j][i]];
    }


}
