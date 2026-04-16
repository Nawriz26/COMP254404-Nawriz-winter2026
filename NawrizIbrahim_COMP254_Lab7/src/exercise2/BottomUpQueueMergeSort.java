package exercise2;

/*
 ** File:            BottomUpQueueMergeSort.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #7 – Exercise 2 – Search Trees and Sorting
 ** Date:            April 15, 2026
 ** Description:     This program implements a bottom-up merge sort for a collection of
 **                  items stored in queues. Each item is first placed into its own queue,
 **                  then pairs of queues are repeatedly merged until only one sorted queue
 **                  remains. A queue of queues is used to manage the merging process.
 */

import common.LinkedQueue;
import common.Queue;

import java.util.Comparator;

public class BottomUpQueueMergeSort {

    /**
     * Merges two sorted queues into a third queue in sorted order.
     */
    public static <E> void merge(Queue<E> q1, Queue<E> q2, Queue<E> result,
                                 Comparator<E> comp) {
        // Compare the front elements of both queues and move the smaller one.
        while (!q1.isEmpty() && !q2.isEmpty()) {
            if (comp.compare(q1.first(), q2.first()) <= 0) {
                result.enqueue(q1.dequeue());
            } else {
                result.enqueue(q2.dequeue());
            }
        }

        // Move any leftover elements from q1.
        while (!q1.isEmpty()) {
            result.enqueue(q1.dequeue());
        }

        // Move any leftover elements from q2.
        while (!q2.isEmpty()) {
            result.enqueue(q2.dequeue());
        }
    }

    /**
     * Sorts a queue using bottom-up merge sort and returns the sorted queue.
     */
    public static <E> Queue<E> bottomUpMergeSort(Queue<E> data, Comparator<E> comp) {
        // This queue stores smaller queues that will be merged step by step.
        Queue<Queue<E>> queueOfQueues = new LinkedQueue<>();

        // Step 1: place each item into its own single-element queue.
        while (!data.isEmpty()) {
            Queue<E> single = new LinkedQueue<>();
            single.enqueue(data.dequeue());
            queueOfQueues.enqueue(single);
        }

        // If the original queue was empty, return an empty queue.
        if (queueOfQueues.isEmpty()) {
            return new LinkedQueue<>();
        }

        // Step 2: repeatedly merge pairs of queues until one sorted queue remains.
        while (queueOfQueues.size() > 1) {
            Queue<E> first = queueOfQueues.dequeue();
            Queue<E> second = queueOfQueues.dequeue();
            Queue<E> merged = new LinkedQueue<>();

            merge(first, second, merged, comp);
            queueOfQueues.enqueue(merged);
        }

        // The last remaining queue is the fully sorted result.
        return queueOfQueues.dequeue();
    }
}
