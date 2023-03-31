/** @author 
 *  Binary search tree (starter code)
 **/

/*
    Team Members:
    1. Manav Prajapati - mnp200002
    2. Rahul Bosamia - rnb200003
 */

package mnp200002;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

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
    int counter;

    public BinarySearchTree() {
        root = null;
        size = 0;
    }


    /** TO DO: Is x contained in tree?
     */
    public boolean contains(T x) {
        if(get(x) != null) return true;
        return false;
    }

    public Entry<T> searchBST(Entry<T> node, T x) {
        while(node != null && node.element != x) {
            node = x.compareTo(node.element) < 0 ? node.left : node.right; 
        }
        return node;
    }

    /** TO DO: Is there an element that is equal to x in the tree?
     *  Element in tree that is equal to x is returned, null otherwise.
     */
    public T get(T x) {
        Entry<T> cur = root;
        Entry<T> node = searchBST(cur, x);
        if(node == null) return null;
	    return node.element;
    }

    /** TO DO: Add x to tree. 
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        if(root == null) {
            root = new Entry<T>(x, null, null);
            size++;
            return true;
        }

        Entry<T> cur = root;
        while(true) {
            if(cur.element == x) {
                return false;
            }
            else if(cur.element.compareTo(x) < 0) {
                if(cur.right != null) {
                    cur = cur.right;
                }
                else {
                    cur.right = new Entry<T>(x, null, null);
                    break;
                }
            }
            else {
                if(cur.left != null) {
                    cur = cur.left;
                }
                else {
                    cur.left = new Entry<T>(x, null, null);
                    break;
                }
            }
        }
        size++;
	    return true;
    }

    /** TO DO: Remove x from tree. 
     *  Return x if found, otherwise return null
     */
    public T remove(T x) {
        Entry<T> node = root;

        if(root == null) return null;
        if(root.element == x) {
            size--;
            root = helper(root);
            return x;
        }

        while(node != null) {
            if(x.compareTo(node.element) < 0) {
                if(node.left != null && node.left.element == x) {
                    node.left = helper(node.left);
                    size--;
                    return x;
                }
                else {
                    node = node.left;
                }
            }
            else {
                if(node.right != null && node.right.element == x) {
                    node.right = helper(node.right);
                    size--;
                    return x;
                }
                else {
                    node = node.right;
                }
            }
        }
	    return null;
    }

    public Entry<T> helper(Entry<T> node) {
        if(node.left == null) {
            return node.right;
        }
        else if (node.right == null) {
            return node.left;
        }
        Entry<T> rightChild = node.right;
        Entry<T> lastRight = findLastRight(node.left);
        lastRight.right = rightChild;
        return node.left;
    }

    public Entry<T> findLastRight(Entry<T> node) {
        if(node.right == null) {
            return node;
        }
        return findLastRight(node.right);
    }

    public T min() {

        Entry<T> node = root;

        while(node.left != null) {
            node = node.left;
        }

	    return node.element;
    }

    public T max() {
        Entry<T> node = root;

        while(node.right != null) {
            node = node.right;
        }

	    return node.element;
    }

    public void inorderTranversal(Entry<T> node, Comparable[] arr) {
        if(node == null)
            return;
        
        inorderTranversal(node.left, arr);
        arr[counter] = node.element;
        counter++;
        inorderTranversal(node.right, arr);
    }

    // TODO: Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[size];
        /* write code to place elements in array here */
        counter = 0;
        inorderTranversal(root, arr);
        counter = 0;
        return arr;
    }


// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
	Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
	return null;
    }

    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

// End of Optional problem 2

    public static void main(String[] args) {
	BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i=0; i<t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                // System.out.println(t.max());
                // System.out.println(t.min());
                // System.out.println(t.get(20));
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
        if(node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/
