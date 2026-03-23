/*
 ** File:            Exercise3Test.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      ab Assignment #5 – Using Trees and Priority Queues
 ** Date:            March 23, 2026
 ** Description:     Tests Exercise 3: recursive unheap in HeapPriorityQueue
 */

package exercise3;

public class Exercise3Test {
    public static void main(String[] args) {
        RecursiveHeapPriorityQueue<Integer, String> pq = new RecursiveHeapPriorityQueue<>();

        System.out.println("================ EXERCISE 3 ================");
        System.out.println("Testing recursive upheap in HeapPriorityQueue");
        System.out.println();

        pq.insert(45, "A");
        pq.insert(20, "B");
        pq.insert(14, "C");
        pq.insert(12, "D");
        pq.insert(31, "E");
        pq.insert(7, "F");
        pq.insert(11, "G");

        System.out.println("Heap entries stored in array order:");
        for(int i = 0; i < pq.size(); i++) {
            System.out.println("(" + pq.heap.get(i).getKey() + ", " + pq.heap.get(i).getValue() + ")");
        }

        System.out.println();
        System.out.println("Minimum entry: (" + pq.min().getKey() + ", " + pq.min().getValue() + ")");
        System.out.println("Worst-case running time: O(log n)");
    }
}
