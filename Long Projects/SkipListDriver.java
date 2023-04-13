
// package idsa;

import idsa.Timer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import mnp200002.SkipList;
//Driver program for skip list implementation.

public class SkipListDriver {
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
	SkipList<Long> skipList = new SkipList<>();
	// Initialize the timer
	Timer timer = new Timer();

	Set<Long> set = new HashSet<>();
	long setResult = 0;
	long addResult = 0, addResult2 = 0;
	long removeResult = 0, removeResult2 = 0;
	long containsResult = 0, containsResult2 = 0;

	while (!((operation = sc.next()).equals("End"))) {
	    switch (operation) {
			case "Add": {
				operand = sc.nextLong();
				if(skipList.add(operand)) {
					result = (result + 1) % modValue;
					addResult2 = (addResult2 + 1) % modValue;
				}
				if(!set.contains(operand)) {
					set.add(operand);
					setResult = (setResult + 1) % modValue;
					addResult = (addResult + 1) % modValue;
				}
				break;
			}
			case "Ceiling": {
				operand = sc.nextLong();
				returnValue = skipList.ceiling(operand);
				if (returnValue != null) {
					result = (result + returnValue) % modValue;
				}
				break;
			}
			case "First": {
				returnValue = skipList.first();
				if (returnValue != null) {
					result = (result + returnValue) % modValue;
				}
				break;
			}
			case "Get": {
				int intOperand = sc.nextInt();
				returnValue = skipList.get(intOperand);
				if (returnValue != null) {
					result = (result + returnValue) % modValue;
				}
				break;
			}
			case "Last": {
				returnValue = skipList.last();
				if (returnValue != null) {
					result = (result + returnValue) % modValue;
				}
				break;
			}
			case "Floor": {
				operand = sc.nextLong();
				returnValue = skipList.floor(operand);
				if (returnValue != null) {
					result = (result + returnValue) % modValue;
				}
				break;
			}
			case "Remove": {
				operand = sc.nextLong();
				if (skipList.remove(operand) != null) {
					result = (result + 1) % modValue;
					removeResult2 = (removeResult2 + 1) % modValue;
				}
				if(set.contains(operand)) {
					set.remove(operand);
					setResult = (setResult + 1) % modValue;
					removeResult = (removeResult + 1) % modValue;
				}
				break;
			}
			case "Contains":{
				operand = sc.nextLong();
				if (skipList.contains(operand)) {
					result = (result + 1) % modValue;
					containsResult2 = (containsResult2 + 1) % modValue;
				}
				if(set.contains(operand)) {
					setResult = (setResult + 1) % modValue;
					containsResult = (containsResult + 1) % modValue;
				}
				break;
			}
	    }
	}

	// End Time
	timer.end();
	System.out.println(result);
	System.out.println(setResult);
	System.out.println("Add: " + addResult + " Remove: " + removeResult + " Contains: " + containsResult);
	System.out.println("Add: " + addResult2 + " Remove: " + removeResult2 + " Contains: " + containsResult2);
	System.out.println(timer);
    }
}
