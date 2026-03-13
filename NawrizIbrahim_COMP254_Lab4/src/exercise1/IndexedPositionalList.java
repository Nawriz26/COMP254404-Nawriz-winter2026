/*

 ** File:            IndexedPositionalList.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #4 – Exercise 1 – Using ADT Stacks, Queues, and Lists
 ** Date:            March 12, 2026
 ** Description:     Extends the PositionalList ADT by adding the indexOf(p) method that returns
 **                  the current index of a given position.
 */

package exercise1;

import lists.PositionalList;
import lists.Position;

// This is an Extended positional list interface that adds an indexOf method
public interface IndexedPositionalList<E> extends PositionalList<E> {
    // Returns the current zero-based index of the given position
    int indexOf(Position<E> p);
}
