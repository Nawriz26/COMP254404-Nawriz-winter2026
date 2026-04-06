/*
 ** File:            ProbeHashMap.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #6 – Using Maps and Hash Tables – Exercise 1
 ** Date:            April 6, 2026
 ** Description:     Probe hash map implementation that uses linear probing, defunct markers,
 **                  and the configurable load factor support from AbstractHashMap.
 */

package exercise1;

import java.util.ArrayList;

public class ProbeHashMap<K,V> extends AbstractHashMap<K,V> {

    // Array used to store entries directly in the hash table.
    private MapEntry<K,V>[] table;

    // Special marker for a deleted entry.
    // This allows probing to continue correctly after removals.
    private final MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null);

    // Constructors that pass arguments to the superclass.
    public ProbeHashMap() { super(); }
    public ProbeHashMap(double maxLoadFactor) { super(maxLoadFactor); }
    public ProbeHashMap(int cap) { super(cap); }
    public ProbeHashMap(int cap, double maxLoadFactor) { super(cap, maxLoadFactor); }
    public ProbeHashMap(int cap, int p) { super(cap, p); }
    public ProbeHashMap(int cap, int p, double maxLoadFactor) { super(cap, p, maxLoadFactor); }

    @Override
    @SuppressWarnings({"unchecked"})
    protected void createTable() {
        // Create a new array of the current capacity.
        // Each position will hold either:
        // null, DEFUNCT, or a valid MapEntry.
        table = (MapEntry<K,V>[]) new MapEntry[capacity];
    }

    private boolean isAvailable(int j) {
        // A slot is considered available if it is empty (null)
        // or contains the DEFUNCT marker from a previous deletion.
        return table[j] == null || table[j] == DEFUNCT;
    }

    private int findSlot(int h, K k) {
        // Stores the first available slot seen while probing.
        // It remains -1 until an empty or DEFUNCT slot is found.
        int avail = -1;

        // Start probing at the original hash index.
        int j = h;

        do {
            // If this slot can be used for insertion...
            if (isAvailable(j)) {
                // Record the first available position.
                if (avail == -1)
                    avail = j;

                // If the slot is truly null, the search can stop,
                // because the key cannot appear later in the probe sequence.
                if (table[j] == null)
                    break;

                // If this slot is occupied and the key matches,
                // return its index immediately.
            } else if (table[j].getKey().equals(k)) {
                return j;
            }

            // Move to the next slot using linear probing.
            j = (j + 1) % capacity;

            // Stop if we have come full circle back to the start.
        } while (j != h);

        // Key was not found.
        // Return the first available slot encoded as a negative value.
        return -(avail + 1);
    }

    @Override
    protected V bucketGet(int h, K k) {
        // Find the slot containing key k, starting from hash index h.
        int j = findSlot(h, k);

        // If the returned value is negative, the key was not found.
        if (j < 0) return null;

        // Otherwise return the associated value.
        return table[j].getValue();
    }

    @Override
    protected V bucketPut(int h, K k, V v) {
        // Search for either the key or an available slot.
        int j = findSlot(h, k);

        // If j is nonnegative, the key already exists.
        // Replace the old value and return it.
        if (j >= 0)
            return table[j].setValue(v);

        // Otherwise decode the insertion index from the negative result
        // and store a new entry there.
        table[-(j + 1)] = new MapEntry<>(k, v);

        // Increase the total number of entries in the map.
        n++;

        // Since the key was new, return null.
        return null;
    }

    @Override
    protected V bucketRemove(int h, K k) {
        // Search for the key in its probe sequence.
        int j = findSlot(h, k);

        // If not found, return null.
        if (j < 0) return null;

        // Save the value before removal.
        V answer = table[j].getValue();

        // Replace the entry with the DEFUNCT marker instead of null.
        // This preserves the probing chain for later searches.
        table[j] = DEFUNCT;

        // Decrease the number of active entries.
        n--;

        // Return the removed value.
        return answer;
    }

    @Override
    public Iterable<Entry<K,V>> entrySet() {
        // Create a list to hold all active entries.
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();

        // Visit every slot in the table.
        for (int h = 0; h < capacity; h++)
            // Only add slots that contain actual entries.
            if (!isAvailable(h))
                buffer.add(table[h]);

        // Return all active entries in iterable form.
        return buffer;
    }
}