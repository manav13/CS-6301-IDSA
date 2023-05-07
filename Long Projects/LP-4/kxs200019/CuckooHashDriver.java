/**
 * CuckooHashDriver implements hashing using cuckoo hashing and Java's Hash Set
 * using large random number inputs.
 * @author Andres Daniel Uriegas
 * @author Dhara Patel
 * */

package kxs200019;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import kxs200019.Timer;

public class CuckooHashDriver {
    static CuckooHashing<Long> ch = new CuckooHashing<>();
    static Set<Long> hs = new HashSet<>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = null;
        long[] randomNumbers = null;
        boolean random = false; //use random generator
        
        if (args.length > 0) {
            if (args[0].endsWith("txt")) {
                File file = new File(args[0]);
                sc = new Scanner(file);
            }
            //generate random numbers
            else{
                random = true;
                randomNumbers = new long[Integer.parseInt(args[0])];
                for(int i=0; i < Long.parseLong(args[0]); i++){
                    randomNumbers[i] = (long)(Math.random()*((100000-100)+1))+100;
                }
            }
        }
        else {
            sc = new Scanner(System.in);
        }

        String op;
        long operand;
        int type = 0;

        // check if user has indicated which method to use
        if (args.length > 1) {
            type = Integer.parseInt(args[1]);
        }

        // Initialize the timer
        Timer timer = new Timer();
        if (random){    //run on random numbers
            for (long i:randomNumbers) {
                if (type == 1) {
                    ch.add(i);
                }
                else {
                    hs.add(i);
                }
            }
            for (long i:randomNumbers) {
                if (type == 1) {
                    ch.contains(i);
                }
                else{
                    hs.contains(i);
                }
            }
            for (long i:randomNumbers) {
                if (type == 1) {
                    ch.remove(i);
                }
                else{
                    hs.remove(i);
                }
            }
            timer.end();
            if (type == 1){
                System.out.println("Cuckoo Hashing:");
            }
            else{
                System.out.println("Java Hash Set");
            }
            System.out.println(timer);
            return;
        }
        else {
            while (!((op = sc.next()).equals("End"))) {
                switch (op) {
                    case "Add": {
                        operand = sc.nextInt();

                        if (type == 1) {
                            ch.add(operand);
                        } else {
                            hs.add(operand);
                        }
                        break;
                    }
                    case "Remove": {
                        operand = sc.nextInt();

                        if (type == 1) {
                            ch.remove(operand);
                        } else {
                            hs.remove(operand);
                        }
                        break;
                    }
                    case "Contains": {
                        operand = sc.nextLong();
                        if (type == 1) {
                            ch.contains(operand);
                        } else {
                            hs.contains(operand);
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        }
        if (type == 1) {
            System.out.println("Cuckoo Hashing:");
            System.out.println("Size: " + ch.curr_size);

        } else {
            System.out.println("Java Hash Set");
            System.out.println("Size: " + hs.size());
        }
        System.out.println(timer.end());
    }
}

