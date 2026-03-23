/*
 ** File:            Exercise2BinaryTree.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      ab Assignment #5 – Using Trees and Priority Queues
 ** Date:            March 23, 2026
 ** Description:     Binary tree implementation for Exercise 2 using postorder to print subtree heights
 */

package exercise2;

public class Exercise2BinaryTree<E> {
    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            parent = p;
            left = l;
            right = r;
        }

        @Override
        public E getElement() throws IllegalStateException {
            return element;
        }

        public Node<E> getLeft() {
            return left;
        }
        public Node<E> getRight() {
            return right;
        }
        public void setLeft(Node<E> l) {
            this.left = l;
        }
        public void setRight(Node<E> r) {
            this.right = r;
        }
    }

    private Node<E> root = null;
    private int size = 0;

    protected Node<E> validate(Position<E> p) {
        if (p == null) throw new IllegalArgumentException("Position is null");
        if(!(p instanceof Node)) throw new IllegalArgumentException("Invalid position type");
        return (Node<E>) p;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Position<E> addRoot(E e) {
        if(!isEmpty()) throw new IllegalStateException("Tree already has a root");
        root = new Node<>(e, null, null, null);
        size = 1;
        return root;
    }

    public Position<E> addLeft(Position<E> p, E e) {
        Node<E> parent = validate(p);
        if(parent.getLeft() != null) throw new IllegalStateException("Left child already exists");
        Node<E> child = new Node<>(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }
    public Position<E> addRight(Position<E> p, E e) {
        Node<E> parent = validate(p);
        if(parent.getRight() != null) throw new IllegalStateException("Right child already exists");
        Node<E> child = new Node<>(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    //Prints each node in postorder along with the height of its subtree
    // Worst-case running time: O(n), because each node is visited exactly once
    public void printElementsWithSubtreeHeights() {
        if (isEmpty()) {
            System.out.println("The tree is empty.");
        } else {
            System.out.println("Postorder output of each element followed by subtree height:");
            printSubtreeHeights(root);
        }
    }

    private int printSubtreeHeights(Node<E> node) {
        int leftHeight = -1;
        int rightHeight = -1;

        if (node.getLeft() != null) {
            leftHeight = printSubtreeHeights(node.getLeft());
        }
        if (node.getRight() != null) {
            rightHeight = printSubtreeHeights(node.getRight());
        }

        int height = Math.max(leftHeight, rightHeight) + 1;
        System.out.println(node.getElement() + " -> height " + height);
        return height;
    }
}
