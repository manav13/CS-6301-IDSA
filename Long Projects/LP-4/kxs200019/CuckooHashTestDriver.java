package kxs200019;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Random;
import kxs200019.Timer;

public class CuckooHashTestDriver {
	public static int randomGenerator(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

    public static void main(String[] args) throws FileNotFoundException {
	Scanner sc;
	if (args.length > 0) {
	    File file = new File(args[0]);
	    sc = new Scanner(file);
	} else {
	    sc = new Scanner(System.in);
	}
	
	String operation = "";
	long operand = 0;
	int modValue = 999983;
	long result = 0;
	Long returnValue = null;
	CuckooHashing<Long> ht = new CuckooHashing<>();

	
	
	// Initialize the timer
	Timer timer = new Timer();

	while (!((operation = sc.next()).equals("End"))) {
	    switch (operation) {
			case "Add": {
				operand = sc.nextLong();
				if(ht.add(operand)) {
					result = (result + 1) % modValue;
				}
				break;
			}
			case "Remove": {
				operand = sc.nextLong();
				if (ht.remove(operand) ) {
					result = (result + 1) % modValue;
				}
				break;
			}
			case "Contains":{
				operand = sc.nextLong();
				if (ht.contains(operand)) {
					result = (result + 1) % modValue;
				}
				break;
			}
	    }
	}

	// End Time
	timer.end();
	System.out.println(result);
	System.out.println(timer);
    }
}