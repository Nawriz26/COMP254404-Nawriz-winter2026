/*

 ** File:            Exercise1Test.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #4 – Exercise 2 – Using ADT Stacks, Queues, and Lists
 ** Date:            March 12, 2026
 ** Description:     Declares the stack ADT used in Exercise 2
 */

package exercise2;

// Generic Stack ADT interface
public interface Stack<E> {
    int size();
    boolean isEmpty();
    void push(E e);
    E top();
    E pop();
}
