/*
 ** File:            SortedTableMapContainsKeyTest.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #6 – Using Maps and Hash Tables – Exercise 2
 ** Date:            April 6, 2026
 ** Description:     Tests the containKey(k) method in SortedTableMap, including cases
 **                  where a key exists with a null value and where a key does not exist.
 */

package exercise2;

public class SortedTableMapContainsKeyTest {

    public static void main(String[] args) {
        // Create an empty sorted table map with Integer keys and String values.
        SortedTableMap<Integer,String> map = new SortedTableMap<>();

        // Insert sample entries into the map.
        map.put(10, "ten");
        map.put(20, null);       // key 20 exists, but its value is null
        map.put(30, "thirty");
        map.put(40, "forty");

        // Display the title for the test output.
        System.out.println("Exercise 2 - Testing containKey(k)");
        System.out.println("-----------------------------------");

        // Show that get(20) returns null because the stored value is actually null.
        System.out.println("get(20) = " + map.get(20));

        // containKey(20) should still return true because the key exists,
        // even though its value is null.
        System.out.println("containKey(20) = " + map.containKey(20) + "   <- key exists even though value is null");

        // Show that get(25) returns null because key 25 is not present in the map.
        System.out.println("get(25) = " + map.get(25));

        // containKey(25) should return false because key 25 does not exist.
        System.out.println("containKey(25) = " + map.containKey(25) + "   <- key does not exist");
        System.out.println();

        // Additional test cases for keys that do and do not exist.
        System.out.println("Additional checks:");
        System.out.println("containKey(10) = " + map.containKey(10));
        System.out.println("containKey(30) = " + map.containKey(30));
        System.out.println("containKey(99) = " + map.containKey(99));
    }
}