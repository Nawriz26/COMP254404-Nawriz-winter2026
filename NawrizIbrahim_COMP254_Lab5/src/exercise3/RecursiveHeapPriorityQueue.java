/*
 ** File:            RecursiveHeapPriorityQueue.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      ab Assignment #5 – Using Trees and Priority Queues
 ** Date:            March 23, 2026
 ** Description:     Alternative heap priority queue using a recursive unheap method
 */

package exercise3;

import java.util.Comparator;

public class RecursiveHeapPriorityQueue<K,V> extends HeapPriorityQueue<K,V> {
    public RecursiveHeapPriorityQueue(){
        super();
    }

    public RecursiveHeapPriorityQueue(Comparator<K> comp) {
        super(comp);
    }

    @Override
    protected void unheap(int j){
        if(j == 0){
            return;
        }
        int p = parent(j);
        if(compare(heap.get(j),heap.get(p)) < 0){
            swap(j,p);
            unheap(p);
        }
    }
}
