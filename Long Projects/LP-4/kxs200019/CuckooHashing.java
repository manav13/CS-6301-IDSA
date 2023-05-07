package kxs200019;
import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CuckooHashing<T> {
    HashSet<T> extrahash = new HashSet<>();
    int maxSize;
    int curr_size;
    int threshold;
    Entry<T>[][] hashTable;
    double lf;

    static CuckooHashing<Long> ch = new CuckooHashing<>(10, 0.9D);
    static Set<Long> hs = new HashSet<>();

    class Entry<T>{
        T element;

        public Entry(T element){
            this.element = element;
        }
    }
    public int tableSize() {return this.curr_size;}
    public CuckooHashing(){
        maxSize = 60;
        curr_size = 0;
        threshold = (int)Math.log(maxSize);
        lf = 0.9D;
        hashTable = (Entry<T>[][])new Entry[maxSize][2];
    }

    public CuckooHashing(int size, double lf){
        maxSize = size;
        curr_size = 0;
        this.threshold = (int)Math.log(maxSize);
        this.lf = lf;
        hashTable = (Entry<T>[][])new Entry[maxSize][2];
    }

    public int hash1(T key){
        int hash = key.hashCode();
        hash ^= (hash >>> 21) ^ (hash >>> 13);
        return ((hash^ (hash >>> 1) ^ (hash >>> 5)) % maxSize);
    }

    public int hash2(T key){
        int hash = key.hashCode();
        hash ^= (hash >>> 20) ^ (hash >>> 12);
        return ((hash^(hash >>> 7) ^ (hash >>> 4)) % maxSize);
    }

    public int returnHashVal(T key, int col){
        return (col == 0)? hash1(key) : hash2(key);
    }

    public boolean contains(T n){
        int i = 0;
        while(i < 2){
            int hash_val = returnHashVal(n, i);
            if(this.hashTable[hash_val][i] != null && n.equals((this.hashTable[hash_val][i]).element)){
                return true;
            }
            i++;
        }

        return extrahash.contains(n);
    }

    public boolean add(T x){
        if(contains(x) || extrahash.contains(x)){
            return false;
        }
        this.curr_size++;
        if(0.9D <= this.curr_size/this.maxSize){
            table_resize();
        }
        int eleCount = 0;
        int colCount = 0;

        while(eleCount < this.threshold){
            int hash_no = returnHashVal(x, colCount);
            if(this.hashTable[hash_no][colCount]!=null){
                T tmp = (T)(this.hashTable[hash_no][colCount]).element;
                (this.hashTable[hash_no][colCount]).element = x;
                colCount = (colCount + 1) % 2;
                x = tmp;
                eleCount++;
                continue;
            }
            this.hashTable[hash_no][colCount] = new Entry(x);
        }

        if(eleCount >= this.threshold){
            this.extrahash.add(x);
        }

        return true;
    }

    public boolean remove(T x){

        if(this.curr_size == 0){
            return false;
        }

        int colCount = 0;
        while(colCount < 2){
            int hash_no = returnHashVal(x, colCount);
            if(this.hashTable[hash_no][colCount] !=null && hashTable[hash_no][colCount].element.equals(x)){
                this.hashTable[hash_no][colCount] = null;
                this.curr_size--;
                return true;                
            }
            colCount++;
        } 

        if(extrahash.contains(x)){ 
            return true;
        }

        return false;

    }

    public void table_resize(){
        Entry<T>[][] new_table = this.hashTable;
        this.maxSize *= 2;
        this.threshold = (int)Math.log(maxSize);
        this.hashTable = (Entry<T>[][])new Entry[this.maxSize][2];
        int j = 0;

        while(j < new_table.length){
            int colCount = 0;
            while(colCount < 2){
                if(new_table[j][colCount]!=null){
                    add((T)(new_table[j][colCount]).element);
                }
                colCount++;
            }
            j++;
        }
    }

    //just for reference
    public void printHashTable() {
		System.out.println("\n Hash Table: \n");
		System.out.format("%-15s%-15s%-15s%-2s", new Object[] { "| Location", "| First Cell", "| Second Cell", " |\n" });
		System.out.format("%45s", new Object[] { "|---------------------------------------------|\n" });
		for (int idx = 0; idx < this.hashTable.length; idx++) {
			Entry<T> cell1 = this.hashTable[idx][0];
			Entry<T> cell2 = this.hashTable[idx][1];
			String emptyCell = "-";
			if (cell1 != null) {
				if (cell2 != null) {
					System.out.format("%-15s%-15s%-15s%-2s", new Object[] { "| " + idx, "| " + cell1.element, "| " + cell2.element, " |\n" });
				} else {
					System.out.format("%-15s%-15s%-15s%-2s", new Object[] { "| " + idx, "| " + cell1.element, "| " + emptyCell, " |\n" });
				} 
			} else if (cell2 != null) {
				System.out.format("%-15s%-15s%-15s%-2s", new Object[] { "| " + idx, "| " + emptyCell, "| " + cell2.element, " |\n" });
			} else {
				System.out.format("%-15s%-15s%-15s%-2s", new Object[] { "| " + idx, "| " + emptyCell, "| " + emptyCell, " |\n" });
			} 
		} 
		System.out.format("%45s", new Object[] { "|---------------------------------------------|\n" });
		System.out.format("%-12s%-12s%-2s", new Object[] { "Size = " + this.curr_size, "", " Maximum size = " + this.maxSize });
	}

    public static void main(String[] args){
        CuckooHashing<Integer> ch = new CuckooHashing<>();
		int[] in = { 
				12, 8979, 87, 45, 90, 2, 8, 990, 19, 65, 
				38, 72, 9111 };
		for (int num : in)
			ch.add(Integer.valueOf(num)); 
        ch.printHashTable();
		for (int num : in)
			System.out.println(ch.contains(Integer.valueOf(num))); 
		for (int num : in)
			ch.remove(Integer.valueOf(num));
        ch.printHashTable();
    }  
}
