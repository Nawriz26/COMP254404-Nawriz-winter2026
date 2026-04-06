/*
 ** File:            SortedTableMap.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #6 – Using Maps and Hash Tables – Exercise 2
 ** Date:            April 6, 2026
 ** Description:     Sorted map implementation using an ArrayList and binary search,
 **                  including the containKey(k) method to distinguish null values
 **                  from missing keys.
 */

package exercise2;

import java.util.ArrayList;
import java.util.Comparator;

public class SortedTableMap<K,V> extends AbstractSortedMap<K,V> {

    // List used to store map entries in sorted order by key.
    private final ArrayList<MapEntry<K,V>> table = new ArrayList<>();

    // Constructors that use either the default comparator or a custom comparator.
    public SortedTableMap() { super(); }
    public SortedTableMap(Comparator<K> comp) { super(comp); }

    private int findIndex(K key, int low, int high) {
        // Base case:
        // if the search range is empty, return the insertion point.
        if (high < low) return high + 1;

        // Compute the middle index for binary search.
        int mid = (low + high) / 2;

        // Compare the target key with the key at the middle entry.
        int comp = compare(key, table.get(mid));

        // If keys match, return the exact index.
        if (comp == 0)
            return mid;

            // If the target key is smaller, search the left half.
        else if (comp < 0)
            return findIndex(key, low, mid - 1);

            // If the target key is larger, search the right half.
        else
            return findIndex(key, mid + 1, high);
    }

    private int findIndex(K key) {
        // Convenience version of findIndex that searches the full table.
        return findIndex(key, 0, table.size() - 1);
    }

    public boolean containKey(K key) {
        // Validate that the key is legal for this map.
        checkKey(key);

        // Find the position where the key is or should be.
        int j = findIndex(key);

        // The key exists only if j is within bounds and the entry at j matches the key.
        return j < size() && compare(key, table.get(j)) == 0;
    }

    @Override
    public int size() {
        // Return the number of entries stored in the table.
        return table.size();
    }

    @Override
    public V get(K key) throws IllegalArgumentException {
        // Validate the key before searching.
        checkKey(key);

        // Find where the key should be located.
        int j = findIndex(key);

        // If j is past the end or the key does not match, it is not in the map.
        if (j == size() || compare(key, table.get(j)) != 0) return null;

        // Otherwise return the associated value.
        return table.get(j).getValue();
    }

    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        // Validate the key before insertion.
        checkKey(key);

        // Find the correct sorted position for this key.
        int j = findIndex(key);

        // If the key already exists, replace its value and return the old one.
        if (j < size() && compare(key, table.get(j)) == 0)
            return table.get(j).setValue(value);

        // Otherwise insert a new entry at index j to keep the list sorted.
        table.add(j, new MapEntry<>(key, value));
        return null;
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
        // Validate the key before removal.
        checkKey(key);

        // Find the position where the key should be.
        int j = findIndex(key);

        // If the key is not found, return null.
        if (j == size() || compare(key, table.get(j)) != 0) return null;

        // Remove the entry and return its value.
        return table.remove(j).getValue();
    }

    private Entry<K,V> safeEntry(int j) {
        // Return null if the index is outside the valid range.
        if (j < 0 || j >= table.size()) return null;

        // Otherwise return the entry at index j.
        return table.get(j);
    }

    @Override
    public Entry<K,V> firstEntry() {
        // Return the first entry in sorted order, or null if empty.
        return safeEntry(0);
    }

    @Override
    public Entry<K,V> lastEntry() {
        // Return the last entry in sorted order, or null if empty.
        return safeEntry(table.size() - 1);
    }

    @Override
    public Entry<K,V> ceilingEntry(K key) throws IllegalArgumentException {
        // The ceiling entry is the first entry whose key is >= the given key.
        return safeEntry(findIndex(key));
    }

    @Override
    public Entry<K,V> floorEntry(K key) throws IllegalArgumentException {
        // Find the position where the key is or should be.
        int j = findIndex(key);

        // If the key is not found exactly, move one step left
        // to get the greatest key smaller than the target.
        if (j == size() || !key.equals(table.get(j).getKey()))
            j--;

        return safeEntry(j);
    }

    @Override
    public Entry<K,V> lowerEntry(K key) throws IllegalArgumentException {
        // The lower entry is the greatest entry with key strictly less than the target.
        return safeEntry(findIndex(key) - 1);
    }

    @Override
    public Entry<K,V> higherEntry(K key) throws IllegalArgumentException {
        // Find where the key is or should be.
        int j = findIndex(key);

        // If the key exists exactly, move to the next entry
        // because higherEntry requires a strictly greater key.
        if (j < size() && key.equals(table.get(j).getKey()))
            j++;

        return safeEntry(j);
    }

    private Iterable<Entry<K,V>> snapshot(int startIndex, K stop) {
        // Temporary list used to collect a range of entries.
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();

        // Start scanning from the given index.
        int j = startIndex;

        // Keep adding entries while still within range and before the stop key.
        while (j < table.size() && (stop == null || compare(stop, table.get(j)) > 0))
            buffer.add(table.get(j++));

        return buffer;
    }

    @Override
    public Iterable<Entry<K,V>> entrySet() {
        // Return all entries in sorted order.
        return snapshot(0, null);
    }

    @Override
    public Iterable<Entry<K,V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
        // Return all entries with keys in the range [fromKey, toKey).
        return snapshot(findIndex(fromKey), toKey);
    }
}