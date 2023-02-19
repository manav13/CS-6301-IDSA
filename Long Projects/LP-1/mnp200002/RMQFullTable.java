package mnp200002;

public class RMQFullTable implements RMQStructure {

    int[][] preProcessTable;

    @Override
    public void preProcess(int[] arr) {
        int size = arr.length;
        preProcessTable = new int[size][];

        for(int i=0; i<size; i++) {
            preProcessTable[i] = new int[i+1];
            preProcessTable[i][i] = arr[i];
        }

        for(int i=1; i<size; i++) {
            for(int col=0; col<size-i; col++) {
                int row = col+i;
                preProcessTable[row][col] = Math.min(preProcessTable[row-1][col], preProcessTable[row][col+1]);
            }
        }
    }


    @Override
    public int query(int[] arr, int i, int j) {
        return preProcessTable[j][i];
    }

    public static void main(String[] args) {
        RMQFullTable rmq = new RMQFullTable();
        int[] arr = new int[] {24, 32, 58, 6};

        rmq.preProcess(arr);

        System.out.println(rmq.query(arr, 0, 2));
        System.out.println(rmq.query(arr, 1, 1));
        System.out.println(rmq.query(arr, 1, 3));
        System.out.println(rmq.query(arr, 0, 3));
    }
}
