
/*
 ** File:            Exercise3Test.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #4 – Exercise 3 – Using ADT Stacks, Queues, and Lists
 ** Date:            March 12, 2026
 ** Description:     Tests the concatenate(LinkedQueue<E> Q2) method
 */

package exercise3;

public class Exercise3Test {
    public static void main(String[] args) {
        LinkedQueue<String> Q1 = new LinkedQueue<>();
        LinkedQueue<String> Q2 = new LinkedQueue<>();

        // Add sample elements to Q1
        Q1.enqueue("A");
        Q1.enqueue("B");
        Q1.enqueue("C");

        // Add sample elements to Q2
        Q2.enqueue("D");
        Q2.enqueue("E");
        Q2.enqueue("F");

        System.out.println("===== Exercise 3: concatenate(Q2) =====");
        System.out.println("Before concatenate:");
        System.out.println("Q1 = " + Q1);
        System.out.println("Q2 = " + Q2);

        // Append all elements of Q2 to Q1
        Q1.concatenate(Q2);

        System.out.println("\nAfter concatenate:");
        System.out.println("Q1 = " + Q1);
        System.out.println("Q2 = " + Q2);
    }
}
