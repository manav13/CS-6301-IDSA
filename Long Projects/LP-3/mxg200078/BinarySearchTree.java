/** @author 
 *  Binary search tree (starter code)
 **/

/** * CS 6301.003 LP3
 * LP Group 2: 
    Team Members:
    Manav Prajapati (mnp200002)
    Rahul Bosamia (rnb200003)
    Mayank Goyani (mxg200078)
    Kalyan Kumar (axs200019)
 */
package mxg200078;

import java.util.*;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }

    Entry<T> root;
    int size;
    int index = 0;
    Stack<Entry<T>> stack = new Stack<>();
    Map<Entry<T>, Entry<T>> parentMap;
    Entry<T> splicedChild;
    Entry<T> splEntry;

    public BinarySearchTree() {
        root = null;
        size = 0;
        parentMap = new HashMap<>();
    }

    public Entry<T> find(Entry<T> t, T x) {
        // Entry<T> t = this.root;
        if (t == null || t.element == x) {
            return t;
        }
        while (true) {
            if (x.compareTo(t.element) < 0) {
                if (t.left == null || t.left.element == null) {
                    break;
                }
                stack.push(t);
                parentMap.put(t.left, t);
                t = t.left;
            } else if (x.compareTo(t.element) == 0) {
                break;
            } else if (t.right == null || t.right.element == null) {
                break;
            } else {
                stack.push(t);
                parentMap.put(t.right, t);
                t = t.right;
            }
        }
        return t;
    }

    /**
     * TO DO: Is x contained in tree?
     */

    public boolean contains(T x) {
        Entry<T> t = find(this.root, x);
        if (t == null || t.element == null || t.element.compareTo(x) != 0) {
            return false;
        }
        return true;
    }

    /**
     * TO DO: Is there an element that is equal to x in the tree?
     * Element in tree that is equal to x is returned, null otherwise.
     */
    public T get(T x) {
        return null;
    }

    public boolean add(Entry<T> newEntry) {
        if (size == 0) {
            root = newEntry;
            size++;
            return true;
        } else {
            Entry<T> t = find(this.root, newEntry.element);
            if (newEntry.element.compareTo(t.element) == 0) {
                return false;
            } else if (newEntry.element.compareTo(t.element) < 0) {
                Entry<T> child = newEntry;
                parentMap.put(child, t);
                t.left = child;
            } else {
                Entry<T> child = newEntry;
                parentMap.put(child, t);
                t.right = child;
            }
            size++;
        }
        return true;
    }

    /**
     * TO DO: Add x to tree.
     * If tree contains a node with same key, replace element by x.
     * Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        return add(new Entry<T>(x, null, null));
    }

    /**
     * TO DO: Remove x from tree.
     * Return x if found, otherwise return null
     */
    public void splice(Entry<T> t) {
        Entry<T> parent = stack.peek();
        Entry<T> child = ((t.left == null || t.left.element == null) ? t.right : t.left);
        splEntry = t;
        splicedChild = child;
        if (parent == null || parent.element == null) {
            root = child;
        } else if (parent.left == t) {
            parent.left = child;
        } else {
            parent.right = child;
        }
        parentMap.remove(t);
        t = null;
    }

    public T remove(T x) {
        if (size == 0) {
            return null;
        }
        Entry<T> t = find(this.root, x);
        // print();
        if (t.element.compareTo(x) != 0) {
            return null;
        }
        if (t.left == null || t.right == null || t.left.element == null || t.right.element == null) {
            splice(t);
            size--;
        } else {
            stack.push(t);
            Entry<T> minRight = find(t.right, x);
            t.element = minRight.element;
            splice(minRight);
            size--;
        }
        return null;
    }

    public T min() {
        return null;
    }

    public T max() {
        return null;
    }

    public Comparable[] inorder(Entry<T> t, Comparable[] arr) {
        if (t == null) {
            return arr;
        }
        inorder(t.left, arr);
        arr[index++] = t.element;
        inorder(t.right, arr);
        return arr;
    }

    // TODO: Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[size];
        arr = inorder(this.root, arr);
        return arr;
    }

    // Start of Optional problem 2

    /**
     * Optional problem 2: Iterate elements in sorted order of keys
     * Solve this problem without creating an array using in-order traversal
     * (toArray()).
     */
    public Iterator<T> iterator() {
        return null;
    }

    // Optional problem 2. Find largest key that is no bigger than x. Return null if
    // there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem 2. Find smallest key that is no smaller than x. Return null
    // if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem 2. Find predecessor of x. If x is not in the tree, return
    // floor(x). Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem 2. Find successor of x. If x is not in the tree, return
    // ceiling(x). Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

    // End of Optional problem 2

    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the Input: ");
        while (in.hasNext()) {
            int x = in.nextInt();
            if (x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if (x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for (int i = 0; i < t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                t.print();
                return;
            }
        }
    }

    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

    public void print() {
        print(root, "", true);
    }

    private void print(Entry<T> node, String prefix, boolean isTail) {
        if (node == null || node.element == null) {
            if (node != null) {
                System.out.println(prefix + (isTail ? "└── " : "├── ") + node.element);

            }
            return;
        }
        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.element);
        print(node.left, prefix + (isTail ? "    " : "│   "), node.right == null);
        print(node.right, prefix + (isTail ? "    " : "│   "), true);
    }

}
/*
 * Sample input:
 * 11 3 5 7 9 2 4 6 8 10 13 15 17 0
 * 11 3 5 7 9 2 4 6 8 10 13 15 17 -3 -6 -3 0
 * 
 * Output:
 * Add 1 : [1] 1
 * Add 3 : [2] 1 3
 * Add 5 : [3] 1 3 5
 * Add 7 : [4] 1 3 5 7
 * Add 9 : [5] 1 3 5 7 9
 * Add 2 : [6] 1 2 3 5 7 9
 * Add 4 : [7] 1 2 3 4 5 7 9
 * Add 6 : [8] 1 2 3 4 5 6 7 9
 * Add 8 : [9] 1 2 3 4 5 6 7 8 9
 * Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
 * Remove -3 : [9] 1 2 4 5 6 7 8 9 10
 * Remove -6 : [8] 1 2 4 5 7 8 9 10
 * Remove -3 : [8] 1 2 4 5 7 8 9 10
 * Final: 1 2 4 5 7 8 9 10
 * 
 */

// Add 39 Add 35 Add 20