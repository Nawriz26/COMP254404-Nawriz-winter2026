/*
 ** File              : RecursiveMaxApp.java
 ** Student           : Nawriz Ibrahim
 ** Student number    : 301161181
 ** Course            : Data Structures & Algorithms - COMP254 - Winter 2026
 ** Assignment        : Midterm_Lab - ex1
 ** Date              : February 24, 2026
 ** Description       : Recursively finds the maximum element in an int array.
 **                     Includes also a small test driver (I could not add a test driver due to time)
 */

package ex1;

import java.util.Arrays;
import java.util.Random;

public class RecursiveMaxApp {
    // returns the maximum value in array A using recursion
    public static int recursiveMax(int[] A) {
        if (A == null || A.length == 0) {
            throw new IllegalArgumentException("Array should NOT be empty or NULL");
        }
        return recursiveMax(A, A.length);
    }

    // returns max among the first n elements of A
    public static int recursiveMax(int[] A, int n) {
        // Base case
        if (n == 1) {
            return A[0];
        }

        // Recursive case
        int maxOfPrefix = recursiveMax(A, n - 1);
        return Math.max(maxOfPrefix, A[n - 1]);
    }

    private static void assertEquals(int expected, int actual, String testName) {
        if (expected != actual) {
            throw new IllegalArgumentException(testName + ": Expected " + expected + ", but found " + actual);
        }
        System.out.println(testName + ": Expected " + expected + ", found " + actual);
    }

    private static int maxByLoop(int[] A) {
        int m = A[0];
        for (int i = 1; i < A.length; i++) {
            if (A[i] > m) {
                m = A[i];
            }
        }
        return m;
    }

}
