/** Starter code for Red-Black Tree
 */

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

public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    static class Entry<T> extends BinarySearchTree.Entry<T> {
        boolean color;

        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            color = RED;
        }

        boolean isRed() {
            return color == RED;
        }

        boolean isBlack() {
            return color == BLACK;
        }
    }

    private Entry<T> NIL;

    public RedBlackTree() {
        super();
        NIL = new Entry(null, null, null);
        NIL.color = BLACK;
    }

    public boolean isLeftChild(Entry<T> node) {
        if (node == null) {
            return false;
        }
        if (!parentMap.containsKey(node)) {
            return false;
        }
        return node == par(node).left;
    }

    public boolean isRightChild(Entry<T> node) {
        if (node == null) {
            return false;
        }
        if (!parentMap.containsKey(node)) {
            return false;
        }
        return node == par(node).right;
    }

    public Entry<T> par(Entry<T> node) {
        if (!parentMap.containsKey(node)) {
            return null;
        }
        return (Entry<T>) parentMap.get(node);
    }

    public Entry<T> uncle(Entry<T> node) {
        Entry<T> parent = par(node);
        Entry<T> gParent = par(parent);
        if (gParent.left == parent) {
            return (Entry<T>) gParent.right;
        } else {
            return (Entry<T>) gParent.left;
        }
    }

    public Entry<T> sib(Entry<T> node) {
        if (node == root) {
            return null;
        }
        Entry<T> parent = par(node);
        if (parent.left == node) {
            return (Entry<T>) parent.right;
        } else {
            return (Entry<T>) parent.left;
        }
    }

    private void rotateRight(Entry<T> c) {
        Entry<T> leftChild = (Entry<T>) c.left;
        c.left = leftChild.right;
        if (leftChild.right != null) {
            parentMap.put(leftChild.right, c);
        }
        Entry<T> parent = par(c);
        if (parent == null) {
            parentMap.remove(leftChild);
        } else {
            parentMap.put(leftChild, parent);
        }
        if (parent == null) {
            root = leftChild;
        } else if (isLeftChild(c)) {
            parent.left = leftChild;
        } else {
            parent.right = leftChild;
        }
        leftChild.right = c;
        parentMap.put(c, leftChild);
    }

    private void rotateLeft(Entry<T> c) {
        Entry<T> rightChild = (Entry<T>) c.right;
        c.right = rightChild.left;
        if (rightChild.left != null) {
            parentMap.put(rightChild.left, c);
        }
        Entry<T> parent = par(c);
        if (parent == null) {
            parentMap.remove(rightChild);
        } else {
            parentMap.put(rightChild, parent);
        }
        if (parent == null) {
            root = rightChild;
        } else if (isLeftChild(c)) {
            parent.left = rightChild;
        } else {
            parent.right = rightChild;
        }
        rightChild.left = c;
        parentMap.put(c, rightChild);

    }

    public boolean add(T x) {
        Entry<T> curr = new Entry<T>(x, NIL, NIL);
        if (!super.add(curr)) {
            return false;
        }
        curr.color = RED;
        while (curr != root && par(curr).isRed()) {
            if (isLeftChild(par(curr))) {
                if (uncle(curr).isRed()) {
                    uncle(curr).color = BLACK;
                    par(curr).color = BLACK;
                    curr = par(par(curr));
                    curr.color = RED;
                } else {
                    if (isRightChild(curr)) {
                        curr = par(curr);
                        rotateLeft(curr);
                    }
                    par(curr).color = BLACK;
                    if (par(par(curr)) != null) {
                        par(par(curr)).color = RED;
                        rotateRight(par(par(curr)));
                    }
                }
            } else {

                if (uncle(curr).isRed()) {
                    uncle(curr).color = BLACK;
                    par(curr).color = BLACK;
                    curr = par(par(curr));
                    curr.color = RED;
                } else {
                    if (isLeftChild(curr)) {
                        curr = par(curr);
                        rotateRight(curr);
                    }
                    par(curr).color = BLACK;
                    if (par(par(curr)) != null) {
                        par(par(curr)).color = RED;
                        rotateLeft(par(par(curr)));
                    }
                }
            }
        }
        ((Entry<T>) root).color = BLACK;
        return true;
    }

    public T remove(T x) {
        Entry<T> temp = (Entry<T>) find(super.root, x);
        if (size == 0 || temp.element.compareTo(x) != 0) {
            return null;
        }
        super.remove(x);
        Entry<T> curr = (Entry<T>) splicedChild;
        if (((Entry<T>) splEntry).isBlack()) {
            fixUp(curr);
        }
        return x;
    }

    public void fixUp(Entry<T> curr) {
        Entry<T> sib = null, par = null, leftSib = null, rightSib = null;
        // System.out.println(curr.element);
        while (curr != root && curr.isBlack()) {
            if (isLeftChild(curr)) {
                sib = sib(curr);
                if (sib.isRed()) {
                    sib.color = BLACK;
                    par = par(curr);
                    par.color = RED;
                    rotateLeft(par);
                    sib = sib(curr);
                }
                if (sib == NIL) {
                    break;
                }
                leftSib = (Entry<T>) sib.left;
                rightSib = (Entry<T>) sib.right;
                if (leftSib.isBlack() && rightSib.isBlack()) {
                    sib.color = RED;
                    curr = par(curr);
                } else {
                    if (leftSib != NIL && rightSib.isBlack()) {
                        leftSib.color = BLACK;
                        sib.color = RED;
                        rotateRight(sib);
                        sib = sib(curr);
                    }
                    if (sib == NIL) {
                        break;
                    }
                    rightSib = (Entry<T>) sib.right;
                    rightSib.color = BLACK;
                    par = par(curr);
                    sib.color = par.color;
                    par.color = BLACK;
                    rotateLeft(par);
                    curr = (Entry<T>) root;

                }
            } else {
                sib = sib(curr);
                if (sib.isRed()) {
                    sib.color = BLACK;
                    par = par(curr);
                    par.color = RED;
                    rotateRight(par);
                    sib = sib(curr);
                }
                if (sib == NIL) {
                    break;
                }
                leftSib = (Entry<T>) sib.left;
                rightSib = (Entry<T>) sib.right;
                if (leftSib.isBlack() && rightSib.isBlack()) {
                    sib.color = RED;
                    curr = par(curr);
                } else {
                    if (rightSib != NIL && leftSib.isBlack()) {
                        rightSib.color = BLACK;
                        sib.color = RED;
                        rotateLeft(sib);
                        sib = sib(curr);
                    }
                    if (sib == NIL) {
                        break;
                    }
                    leftSib = (Entry<T>) sib.left;
                    leftSib.color = BLACK;
                    par = par(curr);
                    sib.color = par.color;
                    par.color = BLACK;
                    rotateLeft(par);
                    curr = (Entry<T>) root;
                }
            }
        }
    }

    public boolean verifyRBT() {
        boolean rootNode, redParentNode, numOfBlackNodes;
        HashSet<Entry<T>> allLeafNodes = new HashSet<>();
        rootNode = ((Entry<T>) (root)).isBlack();
        Queue<Entry<T>> queue = new LinkedList<>();
        redParentNode = true;
        queue.add((Entry<T>) root);
        while (!queue.isEmpty() && redParentNode) {
            Entry<T> rt = queue.remove();
            if (rt.isRed()) {
                redParentNode = ((Entry<T>) rt.left).isBlack();
                redParentNode = redParentNode && ((Entry<T>) rt.right).isBlack();
            }
            if (rt.left != null) {
                queue.add((Entry<T>) rt.left);
            }
            if (rt.right != null) {
                queue.add((Entry<T>) rt.right);
            }
            if (rt.left == null && rt.right == null) {
                allLeafNodes.add(rt);
            }
        }
        numOfBlackNodes = blackNodesHeight((Entry<T>) root) != -1;

        return rootNode && numOfBlackNodes;
    }

    private int blackNodesHeight(Entry<T> root) {
        if (root == null)
            return 1;

        int leftSideHeight = blackNodesHeight((Entry<T>) root.left);
        if (leftSideHeight == 0) {
            return leftSideHeight;
        }

        int rightSideHeight = blackNodesHeight((Entry<T>) root.right);
        if (rightSideHeight == 0) {
            return rightSideHeight;
        }

        if (leftSideHeight != rightSideHeight) {
            return 0;
        } else {
            return leftSideHeight + (root.isBlack() ? 1 : 0);
        }
        // return 0;
    }

    public void parentPrint() {
        for (BinarySearchTree.Entry<T> key : parentMap.keySet()) {
            System.out.println(key.element + "->" + (parentMap.get(key)).element);

        }
    }

    public void printRBT() {
        printRBT((Entry<T>) root, "", true);
    }

    private void printRBT(Entry<T> node, String prefix, boolean isTail) {
        if (node == null || node.element == null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + node.element + "(" + node.isBlack()
                    + ")");
            return;
        }
        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.element + "(" + node.isBlack() + ")");
        printRBT((Entry<T>) node.left, prefix + (isTail ? "    " : "│   "), node.right == null);
        printRBT((Entry<T>) node.right, prefix + (isTail ? "    " : "│   "), true);
    }
}
