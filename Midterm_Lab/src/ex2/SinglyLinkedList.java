package ex2;

/*
 ** File              : SinglyLinkedList.java
 ** Student           : Nawriz Ibrahim
 ** Student number    : 301161181
 ** Course            : COMP254 - Data Structures & Algorithms
 ** Lab               : Lab A - Exercise 2
 ** Date              : February 24, 2026
 ** Description       : A basic singly linked list with an in-place reverse() method
 **                     that runs in O(n) time using O(1) extra space.
 **
 ** Note              : This class is based on the textbook implementation by
 **                     Goodrich, Tamassia, Goldwasser (DSA in Java).
 */

package ex2;

/**
 * A basic singly linked list implementation.
 */
public class SinglyLinkedList<E> implements Cloneable {

    // ---------------- nested Node class ----------------

    /**
     * Node of a singly linked list.
     */
    private static class Node<E> {
        private E element;        // element stored at this node
        private Node<E> next;     // reference to the next node

        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        public E getElement() { return element; }
        public Node<E> getNext() { return next; }
        public void setNext(Node<E> n) { next = n; }
    }

    // ---------------- instance variables ----------------

    private Node<E> head = null;  // first node (null if empty)
    private Node<E> tail = null;  // last node  (null if empty)
    private int size = 0;         // number of nodes

    /** Constructs an initially empty list. */
    public SinglyLinkedList() { }

    // ---------------- access methods ----------------

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public E first() {
        if (isEmpty()) return null;
        return head.getElement();
    }

    public E last() {
        if (isEmpty()) return null;
        return tail.getElement();
    }

    // ---------------- update methods ----------------

    public void addFirst(E e) {
        head = new Node<>(e, head);
        if (size == 0) {
            tail = head;
        }
        size++;
    }

    public void addLast(E e) {
        Node<E> newest = new Node<>(e, null);
        if (isEmpty()) {
            head = newest;
        } else {
            tail.setNext(newest);
        }
        tail = newest;
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) return null;
        E answer = head.getElement();
        head = head.getNext();
        size--;
        if (size == 0) {
            tail = null;
        }
        return answer;
    }

    /**
     * Reverses the list in-place using only O(1) additional space.
     *
     * Idea (single pass):
     * - Maintain three pointers: prev, curr, next.
     * - For each node, redirect curr.next to prev.
     * - Advance prev=curr, curr=next.
     *
     * After the loop:
     * - prev points to the new head.
     * - The old head becomes the new tail.
     *
     * Running time: O(n) because each node is visited once.
     * Extra space: O(1).
     */
    public void reverse() {
        // Empty list or single-node list is already reversed.
        if (head == null || head.getNext() == null) {
            return;
        }

        Node<E> prev = null;
        Node<E> curr = head;
        Node<E> oldHead = head; // will become the new tail after reversal

        while (curr != null) {
            Node<E> next = curr.getNext(); // temporarily save next
            curr.setNext(prev);            // reverse the link
            prev = curr;                   // move prev forward
            curr = next;                   // move curr forward
        }

        // After the loop, prev is the new head.
        head = prev;
        tail = oldHead;
    }

    // ---------------- utilities ----------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        Node<E> walk = head;
        while (walk != null) {
            sb.append(walk.getElement());
            if (walk != tail) sb.append(", ");
            walk = walk.getNext();
        }
        sb.append(")");
        return sb.toString();
    }
}
