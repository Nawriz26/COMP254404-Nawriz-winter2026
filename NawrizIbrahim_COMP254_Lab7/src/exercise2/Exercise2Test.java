package exercise2;

/*
 ** File:            Exercise2Test.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #7 – Exercise 2 – Search Trees and Sorting
 ** Date:            April 15, 2026
 ** Description:     This program tests the bottom-up queue merge sort implementation.
 **                  It builds queues of integers and strings, prints the original contents,
 **                  sorts them using BottomUpQueueMergeSort, and prints the sorted results.
 */

import common.LinkedQueue;
import common.Queue;

import java.util.Comparator;

public class Exercise2Test {

    /**
     * Prints a queue without permanently changing its contents.
     */
    public static <E> void printQueue(Queue<E> queue) {
        Queue<E> temp = new LinkedQueue<>();

        // Dequeue each element, print it, and store it temporarily.
        while (!queue.isEmpty()) {
            E value = queue.dequeue();
            System.out.print(value + " ");
            temp.enqueue(value);
        }

        // Restore the original queue contents.
        while (!temp.isEmpty()) {
            queue.enqueue(temp.dequeue());
        }

        System.out.println();
    }

    public static void main(String[] args) {
        // Create and fill a queue of integers.
        Queue<Integer> numbers = new LinkedQueue<>();
        numbers.enqueue(85);
        numbers.enqueue(24);
        numbers.enqueue(63);
        numbers.enqueue(45);
        numbers.enqueue(17);
        numbers.enqueue(31);
        numbers.enqueue(96);
        numbers.enqueue(50);

        Comparator<Integer> intComp = Integer::compareTo;

        System.out.println("Original integer queue:");
        printQueue(numbers);

        // Sort the integer queue.
        Queue<Integer> sortedNumbers = BottomUpQueueMergeSort.bottomUpMergeSort(numbers, intComp);

        System.out.println("Sorted integer queue:");
        printQueue(sortedNumbers);

        // Create and fill a queue of strings.
        Queue<String> words = new LinkedQueue<>();
        words.enqueue("pear");
        words.enqueue("apple");
        words.enqueue("orange");
        words.enqueue("banana");
        words.enqueue("kiwi");

        Comparator<String> stringComp = String::compareTo;

        System.out.println();
        System.out.println("Original string queue:");
        printQueue(words);

        // Sort the string queue.
        Queue<String> sortedWords = BottomUpQueueMergeSort.bottomUpMergeSort(words, stringComp);

        System.out.println("Sorted string queue:");
        printQueue(sortedWords);
    }
}
