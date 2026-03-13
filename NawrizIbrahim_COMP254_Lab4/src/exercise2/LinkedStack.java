/*
 ** File:            LinkedStack.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #4 – Exercise 2 – Using ADT Stacks, Queues, and Lists
 ** Date:            March 12, 2026
 ** Description:     Implements a stack using a singly linked structure
 */
package exercise2;

import java.util.NoSuchElementException;

// Linked implementation of the Stack ADT
public class LinkedStack<E> implements Stack<E> {
    // Node class used internally by the stack
    private static class Node<E> {
        public E element;
        public Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    // Reference to the top of the stack
    private Node<E> head = null;

    // Number of elements in the stack
    private int size = 0;

    // Returns the number of elements in the stack
    @Override
    public int  size() {
        return size;
    }

    // Returns true if empty, false otherwise
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Pushes an element onto the top of the stack
    @Override
    public void push(E e) {
        // Insert the new node at the front of the linked chain
        head = new Node<>(e, head);
        size++;
    }

    // Return the top element but without removing it
    @Override
    public E top() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return head.element;
    }

    // Removes and returns the top element
    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        // Save the top element before removing it
        E answer  = head.element;

        // Move head to the next node
        head = head.next;
        size--;

        return answer;
    }

    // Returns a string representation of the stack from top to bottom
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("top [");
        Node<E> walk = head;
        while (walk != null) {
            sb.append(walk.element);
            walk = walk.next;

            if (walk != null) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }
}
