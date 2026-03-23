/*
 ** File:            Exercise1BinaryTree.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      ab Assignment #5 – Using Trees and Priority Queues
 ** Date:            March 22, 2026
 ** Description:     Binary tree implementation for Exercise 1 using inorderNext(p)
 */

package exercise1;

public class Exercise1BinaryTree<E> {
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

        public Node<E> getParent() {return parent;}
        public Node<E> getLeft() {return left;}
        public Node<E> getRight() {return right;}
        public void setLeft(Node<E> l) {this.left = l;}
        public void setRight(Node<E> r) {this.right = r;}
    }

    private Node<E> root = null;
    private int size = 0;

    protected Node<E> validate(Position<E> p) {
        if (p == null) throw new IllegalArgumentException("Position is null");
        if (!(p instanceof Node)) throw new IllegalArgumentException("Invalid position type");
        return (Node<E>) p;
    }

    public int  size() {
        return this.size;
    }
    public boolean isEmpty() {
        return this.size == 0;
    }
    public Position<E> root(){
        return this.root;
    }

    public Position<E> addRoot(E e){
        if(!isEmpty()) throw new IllegalStateException("Tree already has a root");
        root = new Node<>(e,null,null,null);
        size = 1;
        return root;
    }

    public Position<E> addLeft(Position<E> p, E e){
        Node<E> parent = validate(p);
        if(parent.getLeft() != null) throw new IllegalArgumentException("Left child already exists");
        Node<E> child = new Node<>(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    public Position<E> addRight(Position<E> p, E e){
        Node<E> parent = validate(p);
        if(parent.getRight() != null) throw new IllegalArgumentException("Right child already exists");
        Node<E> child = new Node<>(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    // Returns the node visited immediately after p in an inorder traversal
    // Worst-case running time: O(h), where h is the tree height
    public Position<E> inorderNext(Position<E> p){
        Node<E> node = validate(p);

        if (node.getRight() != null){
            Node<E> walk = node.getRight();
            while (walk.getLeft() != null){
                walk = walk.getLeft();
            }
            return walk;
        }

        Node<E> walk = node;
        Node<E> parent = walk.getParent();
        while (parent != null && walk == parent.getRight()){
            walk = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    public void printInorder(){
        System.out.print("Inorder Traversal: ");
        printInorder(root);
        System.out.println();
    }

    public void printInorder(Node<E> node){
        if (node == null) return;
        printInorder(node.getLeft());
        System.out.print(node.getElement() + " ");
        printInorder(node.getRight());
    }
}
