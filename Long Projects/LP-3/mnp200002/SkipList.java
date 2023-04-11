/* Starter code for LP3 */

// Change this to netid of any member of team
package mnp200002;

import java.util.Iterator;
import java.util.Random;

// Skeleton for skip list implementation.

public class SkipList<T extends Comparable<? super T>> {
    static final int maxLevel = 32;
    Random r;
    
    static class Entry<E> {
        E element;
        Entry<E>[] next;
        Entry<E> prev;

        public Entry(E x, int lev) {
            element = x;
            next = new Entry[lev];

            for(int i=0; i<lev; i++) {
                next[i] = null;
            }
            // add more code if needed
        }

        public E getElement() {
            return element;
        }

        public int level() {
            return next.length;
        }
    }

    Entry<T> head, tail;
    int size;
    Entry<T>[] pred;

    // Constructor
    public SkipList() {
        r = new Random();
        head = new Entry<T>(null, maxLevel);
        tail = new Entry<T>(null, maxLevel);

        // Initialize the head's next pointers
        for(int i=0; i<head.level(); i++) {
            head.next[i] = tail;
            tail.next[i] = null;
        }

        // Initialize the pred array
        pred = new Entry[head.level()];
        size = 0;
    }

    public int chooseLevel() {
        int level = 1 + Integer.numberOfTrailingZeros(r.nextInt());
        return Math.min(level, maxLevel-1);
    }

    public void findPred(T x) {
        Entry<T> p = head;
        for(int level = head.level()-1; level >= 0; level--) {
            while(p.next[level] != tail && p.next[level].element.compareTo(x) < 0) {
                p = p.next[level];
            }
            pred[level] = p;
        }
    }

    // Add x to list. If x already exists, reject it. Returns true if new node is added to list
    public boolean add(T x) {
        // System.out.print("Add ");
        // Check if the element already exists or not
        if(contains(x)) return false;

        // Get a Random level
        int level = chooseLevel();
        Entry<T> entry = new Entry<>(x, level);

        for(int i=0; i<level; i++) {
            entry.next[i] = pred[i].next[i];
            pred[i].next[i] = entry;
        }
        size++;
	    return true;
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {

        Entry<T> p = head;
        for(int level = head.level()-1; level >= 0; level--) {
            while(p.next[level] != tail && p.next[level].element.compareTo(x) < 0) {
                p = p.next[level];
            }
            pred[level] = p;
        }
        // System.out.println(pred[0].next[0].getElement());
	    return pred[0].next[0].getElement();
    }

    // Does list contain x?
    public boolean contains(T x) {
        findPred(x);
        // if(pred[0].next[0].element == x) {
        //     System.out.print(x + ": Present");
        // }
        // else System.out.print(x + ": Absent");

        // System.out.println(", LastElement: " + pred[0].next[0].getElement());

        return pred[0].next[0].getElement() != null && pred[0].next[0].getElement().equals(x);
    }

    // Return first element of list
    public T first() {
        // System.out.println("First: " + head.next[0].getElement());
	    return head.next[0].getElement();
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
        Entry<T> p = head;
        for(int level = head.level()-1; level >= 0; level--) {
            while(p.next[level] != tail && p.next[level].next[level] != tail && p.next[level].next[level].element.compareTo(x) < 0) {
                p = p.next[level];
            }
            pred[level] = p;
        }
        // System.out.println(pred[0].next[0].getElement());
	    return pred[0].next[0].getElement();
    }

    // Return element at index n of list.  First element is at index 0.
    public T get(int n) {
        return getLinear(n);
    }

    // O(n) algorithm for get(n)
    public T getLinear(int n) {
        if(n > size-1) return null;

        Entry<T> p = head;
        for(int i=0; i<=n; i++) {
            p = p.next[0];
        }
        // System.out.println(p.getElement());
        return p.getElement();
    }

    // Optional operation: Eligible for EC.
    // O(log n) expected time for get(n).
    public T getLog(int n) {
        return null;
    }

    // Is the list empty?
    public boolean isEmpty() {
	    return size == 0;
    }

    // Iterate through the elements of list in sorted order
    public Iterator<T> iterator() {
	    return null;
    }

    // Return last element of list
    public T last() {
        Entry<T> p = head;
        while(p.next[0] != tail) {
            p = p.next[0];
        }
        // System.out.println("Last: " + p.getElement());
	    return p.getElement();
    }

 
    // Not a standard operation in skip lists. 
    public void rebuild() {
	
    }

    // Remove x from list.  Removed element is returned. Return null if x not in list
    public T remove(T x) {
        // System.out.print("Remove ");
        if(!contains(x)) return null;

        Entry<T> entry = pred[0].next[0];
        int level = entry.level();

        for(int i=0; i<level; i++) {
            pred[i].next[i] = entry.next[i];
        }
        size--;
	    return entry.getElement();
    }

    // Return the number of elements in the list
    public int size() {
	    return size;
    }

    // public void printTimesCalled() {
    //     System.out.println("Times Called:" + timesCalled);
    // }
}
