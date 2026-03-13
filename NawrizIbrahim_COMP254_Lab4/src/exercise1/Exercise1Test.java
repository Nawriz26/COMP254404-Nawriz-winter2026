/*

 ** File:            Exercise1Test.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #4 – Exercise 1 – Using ADT Stacks, Queues, and Lists
 ** Date:            March 12, 2026
 ** Description:     Tests the indexOf(p) method for the positional list
 */

package exercise1;
import lists.Position;

// This is a Test class for Exercise 1
public class Exercise1Test {
    public static void main(String[] args) {
        // Create an extended positional list of strings
        ExtendedLinkedPositionalList<String> names = new ExtendedLinkedPositionalList<>();

        // Store the returned positions so we can test indexOf(p)
        Position<String> p1 = names.addLast("Ali");
        Position<String> p2 = names.addLast("Nawriz");
        Position<String> p3 = names.addLast("Mohammed");
        Position<String> p4 = names.addLast("Hassan");

        System.out.println("===== Exercise 1: indexOf(p) =====");
        System.out.println("List: " + names);
        System.out.println("Index of Ali  = " + names.indexOf(p1));
        System.out.println("Index of Nawriz  = " + names.indexOf(p2));
        System.out.println("Index of Mohammed = " + names.indexOf(p3));
        System.out.println("Index of Hassan = " + names.indexOf(p4));

        // Remove one element to show that positions technically shift
        names.remove(p2);

        System.out.println("\nAfter removing Nawriz:");
        System.out.println("List: " + names);
        System.out.println("Index of Ali  = " + names.indexOf(p1));
        System.out.println("Index of Mohammed = " + names.indexOf(p3));
        System.out.println("Index of Hassan = " + names.indexOf(p4));
    }
}
