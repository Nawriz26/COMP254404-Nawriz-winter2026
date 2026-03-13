
/*

 ** File:            ExtendedLinkedPositionalList.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #4 – Exercise 1 – Using ADT Stacks, Queues, and Lists
 ** Date:            March 12, 2026
 ** Description:     Extends LinkedPositionalList and implements the indexOf(p) method using
                     only PositionalList ADT methods.
 */
package exercise1;

import lists.Position;
import lists.LinkedPositionalList;


public class ExtendedLinkedPositionalList<E> extends LinkedPositionalList<E> implements IndexedPositionalList<E> {
    @Override
    public int indexOf(Position<E> p) {
        // If input is null then reject it
        if (p == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        // Start traversal at the first position
        Position<E> walk = first();

        // The first element has index ZERO
        int index = 0;

        // Traverse through the list one position at a time
        while (walk != null) {
            // If the current position is the same as p, then return the index
            if (walk == p){
                return index;
            }

            // Move to the next position and increase the counter
            walk = after(walk);
            index++;
        }

        // If p was not found, then return -1
        return -1;
    }
}
