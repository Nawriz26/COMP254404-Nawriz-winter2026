/*
 ** File              : tenLargest.java
 ** Student           : Nawriz Ibrahim
 ** Student number    : 301161181
 ** Course            : Data Structures & Algorithms - COMP254 - Winter 2026
 ** Assignment        : Midterm_Lab - ex3
 ** Date              : February 24, 2026
 ** Description       : Finds the ten largest elements in an array efficiently.
 *                      Uses an auxiliary index array and ignores previously chosen elements where 10 is a constant
 */

package ex3;

import java.util.Arrays;
import java.util.Random;


public class tenLargest {
    public static int[] tenLargestElements(int[] A) {
        if (A == null || A.length == 0) {
            throw new IllegalArgumentException("Array must be not null or empty");
        }
        int n = A.length;
        if (n == 0) {
            return new int[0];
        }
        int k = Math.min(10, n);
        int[] result = new int[k];
        int[] topIndices = new int[k];
        boolean[] visited = new boolean[n];

        for (int i = 0; i < k; i++) {
            int bestIdx = -1;
            for (int j = 0; j < n; j++) {
                if(visited[j] == false && A[j] > A[bestIdx]) {
                    continue;
                }
                if(bestIdx == -1 || A[j] > A[bestIdx]) {
                    bestIdx = j;
                }
            }
            topIndices[i] = bestIdx;
            visited[bestIdx] = true;
            result[i] = A[bestIdx];
        }
        return result;
    }
}
