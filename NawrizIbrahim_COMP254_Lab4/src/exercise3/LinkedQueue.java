/*
 ** File:            LinkedQueue.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #4 – Exercise 3 – Using ADT Stacks, Queues, and Lists
 ** Date:            March 12, 2026
 ** Description:     Implements a linked queue and provides an O(1) concatenate(LinkedQueue<E> Q2) method
 */

package exercise3;

import java.util.NoSuchElementException;

// Linked implementation of a FIFO queue
public class LinkedQueue<E> {
    // Node class used internally by the linked queue
    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    // Reference to the front node of the queue
    private Node<E> head = null;

    // Reference to the rear node of the queue
    private Node<E> tail = null;

    // Number of elements in the queue
    private int size = 0;

    //Returns the number of elements in the queue
    public int size() {
        return size;
    }

    // Returns true if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns, but does not remove, the first element
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }

        return head.element;
    }

    // Inserts an element at the rear of the queue
    public void enqueue(E e) {
        // Create the new node that will be placed at the rear
        Node<E> newest = new Node<>(e, null);

        if (isEmpty()) {
            // If the queue is empty, the new node is both head and tail
            head = newest;
        } else {
            // Otherwise, link the old tail to the new node
            tail.next = newest;
        }

        // Update tail and increase size
        tail = newest;
        size++;
    }

    // Removes and returns the first element

    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }

        // Save the front element.
        E answer = head.element;

        // Move head forward.
        head = head.next;
        size--;

        // If the queue is now empty, tail must also become null
        if (isEmpty()) {
            tail = null;
        }

        return answer;
    }

    // Appends all elements of Q2 to the end of the current queue in O(1) time
    // After the operation, Q2 becomes empty.

    public void concatenate(LinkedQueue<E> Q2) {
        if (Q2 == null) {
            throw new IllegalArgumentException("Q2 cannot be null.");
        }

        // If Q2 is empty, there is nothing to add
        if (Q2.isEmpty()) {
            return;
        }

        if (this.isEmpty()) {
            // If the current queue is empty, simply adopt Q2's chain
            this.head = Q2.head;
            this.tail = Q2.tail;
            this.size = Q2.size;
        } else {
            // Link this queue's tail directly to Q2's head
            this.tail.next = Q2.head;

            // Update tail to Q2's tail
            this.tail = Q2.tail;

            // Increase size by the size of Q2
            this.size += Q2.size;
        }

        // Empty Q2 as required by the exercise
        Q2.head = null;
        Q2.tail = null;
        Q2.size = 0;
    }

    // Returns a string representation of the queue from front to rear
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("front [");
        Node<E> walk = head;

        while (walk != null) {
            sb.append(walk.element);
            walk = walk.next;

            if (walk != null) {
                sb.append(", ");
            }
        }

        sb.append("] rear");
        return sb.toString();
    }

}