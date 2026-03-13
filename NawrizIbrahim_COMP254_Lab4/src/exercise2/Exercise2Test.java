/*
 ** File:            LinkedStack.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #4 – Exercise 2 – Using ADT Stacks, Queues, and Lists
 ** Date:            March 12, 2026
 ** Description:     Tests the transfer(S, T) method for stacks
 */

package exercise2;

public class Exercise2Test {
    // Transfers all elements from stack S to stack T.
    //
    // The element originally at the top of S is the first inserted into T.
    // The element originally at the bottom of S ends up at the top of T.
    //
    // Running time: O(n)
    public static <E> void transfer(Stack<E> S, Stack<E> T) {
        // Keep moving elements until the source stack becomes empty.
        while (!S.isEmpty()) {
            T.push(S.pop());
        }
    }

    public static void main(String[] args) {
        LinkedStack<Integer> S = new LinkedStack<>();
        LinkedStack<Integer> T = new LinkedStack<>();

        // Push elements onto S
        S.push(10);
        S.push(20);
        S.push(30);
        S.push(40);

        System.out.println("===== Exercise 2: transfer(S, T) =====");
        System.out.println("Before transfer:");
        System.out.println("S = " + S);
        System.out.println("T = " + T);

        // Transfer all elements from S to T
        transfer(S, T);

        System.out.println("\nAfter transfer:");
        System.out.println("S = " + S);
        System.out.println("T = " + T);
    }
}
