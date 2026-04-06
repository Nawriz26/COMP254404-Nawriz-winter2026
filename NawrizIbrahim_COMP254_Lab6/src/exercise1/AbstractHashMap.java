/*
 ** File:            AbstractHashMap.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #6 – Using Maps and Hash Tables – Exercise 1
 ** Date:            April 6, 2026
 ** Description:     Abstract hash map implementation with a user-defined maximum load factor,
 **                  resizing support, and common hash-table operations for concrete subclasses.
 */

package exercise1;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V> {

    // Number of entries currently stored in the hash map.
    protected int n = 0;

    // Current size of the hash table array.
    protected int capacity;

    // Prime number used in the MAD compression function.
    private int prime;

    // Random scale and shift values used by the hash function.
    private long scale, shift;

    // Maximum allowed load factor before resizing.
    private double maxLoadFactor;

    public AbstractHashMap(int cap, int p, double maxLoadFactor) {
        // Capacity must be a positive number.
        if (cap <= 0)
            throw new IllegalArgumentException("Capacity must be greater than 0.");

        // Maximum load factor must also be positive.
        if (maxLoadFactor <= 0)
            throw new IllegalArgumentException("Maximum load factor must be greater than 0.");

        // Store the prime number used by the hash function.
        prime = p;

        // Set the table capacity.
        capacity = cap;

        // Store the user-defined maximum load factor.
        this.maxLoadFactor = maxLoadFactor;

        // Generate random values for MAD hashing:
        // hash = ((hashCode * scale + shift) mod prime) mod capacity
        Random rand = new Random();
        scale = rand.nextInt(prime - 1) + 1;   // scale must be between 1 and prime-1
        shift = rand.nextInt(prime);           // shift must be between 0 and prime-1

        // Let the concrete subclass create its actual table structure.
        createTable();
    }

    // Constructor with default load factor 0.5
    public AbstractHashMap(int cap, int p) { this(cap, p, 0.5); }

    // Constructor with default prime value
    public AbstractHashMap(int cap, double maxLoadFactor) { this(cap, 109345121, maxLoadFactor); }

    // Constructor with default prime and default load factor
    public AbstractHashMap(int cap) { this(cap, 109345121, 0.5); }

    // Constructor with default capacity and prime, but custom load factor
    public AbstractHashMap(double maxLoadFactor) { this(17, 109345121, maxLoadFactor); }

    // Fully default constructor
    public AbstractHashMap() { this(17, 109345121, 0.5); }

    @Override
    public int size() {
        // Return the number of entries stored in the map.
        return n;
    }

    @Override
    public V get(K key) {
        // Compute the hash index and let the subclass retrieve the value.
        return bucketGet(hashValue(key), key);
    }

    @Override
    public V remove(K key) {
        // Compute the hash index and let the subclass remove the entry.
        return bucketRemove(hashValue(key), key);
    }

    @Override
    public V put(K key, V value) {
        // Insert or replace the key-value pair in the appropriate bucket.
        V answer = bucketPut(hashValue(key), key, value);

        // Resize the table if the current number of entries exceeds
        // capacity × maximum load factor.
        if (n > capacity * maxLoadFactor)
            resize(2 * capacity - 1);

        // Return the old value if the key already existed, otherwise null.
        return answer;
    }

    protected int hashValue(K key) {
        // Compute a compressed hash value using the MAD method:
        // ((|hashCode * scale + shift| mod prime) mod capacity)
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    public double getMaxLoadFactor() {
        // Return the maximum allowed load factor.
        return maxLoadFactor;
    }

    public double getCurrentLoadFactor() {
        // Current load factor = number of entries / table capacity.
        // If capacity is somehow 0, return 0.0 to avoid division by zero.
        return (capacity == 0) ? 0.0 : ((double) n / capacity);
    }

    public int getCapacity() {
        // Return the current table capacity.
        return capacity;
    }

    public void setMaxLoadFactor(double maxLoadFactor) {
        // The new maximum load factor must be positive.
        if (maxLoadFactor <= 0)
            throw new IllegalArgumentException("Maximum load factor must be greater than 0.");

        // Update the load factor limit.
        this.maxLoadFactor = maxLoadFactor;

        // If the current number of entries now exceeds the new limit,
        // resize immediately to maintain the load factor requirement.
        if (n > capacity * this.maxLoadFactor)
            resize(2 * capacity - 1);
    }

    private void resize(int newCap) {
        // Temporary list used to store all existing entries before rebuilding.
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);

        // Copy every entry from the current table into the buffer.
        for (Entry<K,V> e : entrySet())
            buffer.add(e);

        // Update the capacity to the new size.
        capacity = newCap;

        // Ask the subclass to create a fresh empty table of the new size.
        createTable();

        // Reset entry count because entries will be reinserted.
        n = 0;

        // Reinsert all saved entries into the resized table.
        // Rehashing is necessary because the capacity has changed.
        for (Entry<K,V> e : buffer)
            bucketPut(hashValue(e.getKey()), e.getKey(), e.getValue());
    }

    // Create the concrete table structure (array of buckets, probe table, etc.)
    protected abstract void createTable();

    // Retrieve a value from bucket h for key k.
    protected abstract V bucketGet(int h, K k);

    // Insert a key-value pair into bucket h.
    protected abstract V bucketPut(int h, K k, V v);

    // Remove the entry with key k from bucket h.
    protected abstract V bucketRemove(int h, K k);
}