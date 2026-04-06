/*
 ** File:            ChainHashMap.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #6 – Using Maps and Hash Tables – Exercise 1
 ** Date:            April 6, 2026
 ** Description:     Implements a hash map using separate chaining, with each bucket stored as an UnsortedTableMap.
 */

package exercise1;

/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;

/*
 * Map implementation using hash table with separate chaining.
 *
 * In separate chaining, each index of the hash table stores a bucket.
 * If multiple keys hash to the same index, they are stored together
 * inside that bucket rather than probing for another slot.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class ChainHashMap<K,V> extends AbstractHashMap<K,V> {

    // Array of buckets.
    // Each bucket is an UnsortedTableMap that stores all entries
    // whose keys hash to the same table index.
    private UnsortedTableMap<K,V>[] table;   // initialized within createTable

    // Constructors that simply forward arguments to the superclass.
    public ChainHashMap() { super(); }
    public ChainHashMap(double maxLoadFactor) { super(maxLoadFactor); }
    public ChainHashMap(int cap) { super(cap); }
    public ChainHashMap(int cap, double maxLoadFactor) { super(cap, maxLoadFactor); }
    public ChainHashMap(int cap, int p) { super(cap, p); }
    public ChainHashMap(int cap, int p, double maxLoadFactor) { super(cap, p, maxLoadFactor); }

    /** Creates an empty table having length equal to current capacity. */
    @Override
    @SuppressWarnings({"unchecked"})
    protected void createTable() {
        // Create the array of buckets.
        // At first, every bucket position is null.
        table = (UnsortedTableMap<K,V>[]) new UnsortedTableMap[capacity];
    }

    /**
     * Returns value associated with key k in bucket with hash value h.
     * If no such entry exists, returns null.
     * @param h  the hash value of the relevant bucket
     * @param k  the key of interest
     * @return   associate value (or null, if no such entry)
     */
    @Override
    protected V bucketGet(int h, K k) {
        // Look at the bucket stored at index h.
        UnsortedTableMap<K,V> bucket = table[h];

        // If there is no bucket yet, then the key is not in the map.
        if (bucket == null) return null;

        // Otherwise, search for the key inside that bucket.
        return bucket.get(k);
    }

    /**
     * Associates key k with value v in bucket with hash value h, returning
     * the previously associated value, if any.
     * @param h  the hash value of the relevant bucket
     * @param k  the key of interest
     * @param v  the value to be associated
     * @return   previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketPut(int h, K k, V v) {
        // Get the bucket at index h.
        UnsortedTableMap<K,V> bucket = table[h];

        // If no bucket exists yet, create one first.
        if (bucket == null)
            bucket = table[h] = new UnsortedTableMap<>();

        // Save the old size so we can tell whether a new entry was added
        // or an existing entry was simply updated.
        int oldSize = bucket.size();

        // Insert the key-value pair into the bucket.
        V answer = bucket.put(k,v);

        // Increase the overall map size only if the bucket actually grew.
        // If the key already existed, the bucket size stays the same.
        n += (bucket.size() - oldSize);

        // Return the old value if one existed, otherwise null.
        return answer;
    }

    /**
     * Removes entry having key k from bucket with hash value h, returning
     * the previously associated value, if found.
     * @param h  the hash value of the relevant bucket
     * @param k  the key of interest
     * @return   previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketRemove(int h, K k) {
        // Get the bucket at index h.
        UnsortedTableMap<K,V> bucket = table[h];

        // If the bucket does not exist, the key cannot be present.
        if (bucket == null) return null;

        // Save the old bucket size to detect whether removal happened.
        int oldSize = bucket.size();

        // Attempt to remove the key from the bucket.
        V answer = bucket.remove(k);

        // Decrease the total map size only if the bucket size went down.
        n -= (oldSize - bucket.size());

        // Return the removed value, or null if the key was not found.
        return answer;
    }

    /**
     * Returns an iterable collection of all key-value entries of the map.
     *
     * @return iterable collection of the map's entries
     */
    @Override
    public Iterable<Entry<K,V>> entrySet() {
        // Temporary list used to collect all entries from all buckets.
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();

        // Visit every bucket position in the hash table.
        for (int h=0; h < capacity; h++)

            // Only process positions that actually contain a bucket.
            if (table[h] != null)

                // Add every entry from that bucket into the result list.
                for (Entry<K,V> entry : table[h].entrySet())
                    buffer.add(entry);

        // Return all entries stored in the map.
        return buffer;
    }
}