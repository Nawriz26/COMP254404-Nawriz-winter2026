/*
 ** File              : ReverseList.java
 ** Student           : Nawriz Ibrahim
 ** Student number    : 301161181
 ** Course            : Data Structures & Algorithms - COMP254 - Winter 2026
 ** Assignment        : Midterm_Lab - ex2
 ** Date              : February 24, 2026
 ** Description       : Test driver for reversing a singly linked list in-place
 */

package ex2;

import javax.swing.*;

public class ReverseList {
    private static void assertEquals(String expected, String actual, String testName) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(testName + ": expected " + expected + " but was " + actual);
        }
        System.out.println(testName + ": expected " + expected + " but was " + actual);
    }
    public static void main(String[] args) {
        // Test 1: empty list
        SinglyLinkedList<Integer> empty = new SinglyLinkedList<>();
        empty.reverse();
        assertEquals(empty.toString(), "[]", "Empty List");

        SinglyLinkedList<String> single = new SinglyLinkedList<>();
        single.addLast("A");
        single.reverse();
        assertEquals(single.toString(), "[A]", "Single List");

        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addLast("MSP");
        list.addLast("ATL");
        list.addLast("BOS");
        list.addFirst("LAX");

        assertEquals(list.toString(), "[MSP, ATL, BOS, LAX]", "Before reverse");

        list.reverse();
        assertEquals("[BOS, ATL, MSP, LAX]",  list.toString(), "After reverse");

        list.reverse();
        assertEquals("[LAX, MSP, ATL, BOS]", list.toString(), "Reverse twice");


    }
}
