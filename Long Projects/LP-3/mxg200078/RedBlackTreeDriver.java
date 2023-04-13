

/** * CS 6301.003 LP3
 * LP Group 2:
Team Members:
Manav Prajapati (mnp200002)
Rahul Bosamia (rnb200003)
Mayank Goyani (mxg200078)
Kalyan Kumar (axs200019)
 */
package mxg200078;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

//Driver program for red black tree implementation.

public class RedBlackTreeDriver {
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
		RedBlackTree<Long> redBlackTree = new RedBlackTree<>();
		// Initialize the timer
		Timer timer = new Timer();

		while (!((operation = sc.next()).equals("End"))) {
			switch (operation) {
				case "Add": {
					operand = sc.nextLong();
					if (redBlackTree.add(operand)) {
						result = (result + 1) % modValue;
					}
					break;
				}
				case "Remove": {
					operand = sc.nextLong();
					if (redBlackTree.remove(operand) != null) {
						result = (result + 1) % modValue;
					}
					break;
				}
				case "Contains": {
					operand = sc.nextLong();
					if (redBlackTree.contains(operand)) {
						result = (result + 1) % modValue;
					}
					break;
				}
			}
		}
		// redBlackTree.printRBT();
		// redBlackTree.parentPrint();
		// End Time
		timer.end();
		System.out.println(result);
		// redBlackTree.printRBT();
		System.out.println("Is valid RBT? " + redBlackTree.verifyRBT());

		System.out.println(timer);
	}

	public static class Timer {
		long startTime, endTime, elapsedTime, memAvailable, memUsed;

		public Timer() {
			startTime = System.currentTimeMillis();
		}

		public void start() {
			startTime = System.currentTimeMillis();
		}

		public Timer end() {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			memAvailable = Runtime.getRuntime().totalMemory();
			memUsed = memAvailable - Runtime.getRuntime().freeMemory();
			return this;
		}

		public String toString() {
			return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed / 1048576) + " MB / "
					+ (memAvailable / 1048576) + " MB.";
		}
	}

}

// Add 39 Add 35 Add 20 End