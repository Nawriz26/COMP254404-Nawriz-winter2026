package exercise1;

/*
 ** File:            Exercise1Test.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #7 – Exercise 1 – Search Trees and Sorting
 ** Date:            April 15, 2026
 ** Description:     This program tests the iterative treeSearch() implementation in
 **                  IterativeTreeMap. It inserts several key-value pairs into the binary
 **                  search tree, prints the entries in sorted order, performs search-related
 **                  operations, and tests removal.
 */

import common.Entry;

public class Exercise1Test {

    public static void main(String[] args) {
        // Create a binary search tree map that uses the iterative search method.
        IterativeTreeMap<Integer, String> map = new IterativeTreeMap<>();

        // Insert sample entries.
        map.put(50, "A");
        map.put(30, "B");
        map.put(70, "C");
        map.put(20, "D");
        map.put(40, "E");
        map.put(60, "F");
        map.put(80, "G");
        map.put(10, "H");
        map.put(25, "I");
        map.put(35, "J");
        map.put(45, "K");

        System.out.println("Entries in sorted order:");
        for (Entry<Integer, String> entry : map.entrySet()) {
            // Because entrySet uses inorder traversal, keys appear in ascending order.
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println();
        System.out.println("Search tests:");

        // Exact-match searches.
        System.out.println("get(40): " + map.get(40));
        System.out.println("get(25): " + map.get(25));

        // Unsuccessful search should return null.
        System.out.println("get(99): " + map.get(99));

        // Boundary and relative-entry tests.
        System.out.println("firstEntry(): " + map.firstEntry());
        System.out.println("lastEntry(): " + map.lastEntry());
        System.out.println("floorEntry(34): " + map.floorEntry(34));
        System.out.println("ceilingEntry(34): " + map.ceilingEntry(34));
        System.out.println("lowerEntry(40): " + map.lowerEntry(40));
        System.out.println("higherEntry(40): " + map.higherEntry(40));

        System.out.println();
        System.out.println("Remove test:");

        // Remove a key, then print the tree contents again.
        System.out.println("Removed key 30 -> " + map.remove(30));
        for (Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
