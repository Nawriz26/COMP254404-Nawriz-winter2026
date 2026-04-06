/*
 ** File:            HashMapLoadFactorExperiment.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #6 – Using Maps and Hash Tables – Exercise 1
 ** Date:            April 6, 2026
 ** Description:     Runs timing experiments on ChainHashMap and ProbeHashMap using random
 **                  unique keys and different maximum load factor settings.
 */

package exercise1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HashMapLoadFactorExperiment {

    // Default number of random keys to test.
    private static final int DEFAULT_SIZE = 50000;

    // Default number of times each configuration is repeated.
    private static final int DEFAULT_TRIALS = 3;

    // Load factor settings to compare in the experiment.
    private static final double[] LOAD_FACTORS = {0.50, 0.75, 0.90};

    public static void main(String[] args) {
        // Start with default values.
        int size = DEFAULT_SIZE;
        int trials = DEFAULT_TRIALS;

        // Allow the user to optionally provide size and trial count
        // from the command line.
        if (args.length >= 1) size = Integer.parseInt(args[0]);
        if (args.length >= 2) trials = Integer.parseInt(args[1]);

        // Generate one fixed set of unique random keys so that all
        // map implementations are tested with the same input data.
        Integer[] keys = generateUniqueRandomKeys(size, 2026L);

        // Display experiment title and settings.
        System.out.println("Exercise 1 - Hash Table Load Factor Experiment");
        System.out.println("Random unique keys: " + size);
        System.out.println("Trials per configuration: " + trials);
        System.out.println();

        // Print a formatted table header for the results.
        System.out.printf("%-14s %-10s %-15s %-15s %-15s %-10s %-10s%n",
                "Implementation", "MaxLoad", "Put(ms)", "Get(ms)", "Remove(ms)", "Capacity", "FinalLoad");
        System.out.println("------------------------------------------------------------------------------------------------");

        // Run the experiment for each load factor on both implementations.
        for (double load : LOAD_FACTORS) {
            runTrials("ChainHashMap", load, trials, keys, true);
            runTrials("ProbeHashMap", load, trials, keys, false);
        }
    }

    private static void runTrials(String name, double load, int trials, Integer[] keys, boolean chain) {
        // These variables accumulate the total running times across all trials.
        long putTotal = 0;
        long getTotal = 0;
        long removeTotal = 0;

        // These store the final table statistics from the last trial.
        int finalCapacity = 0;
        double finalLoad = 0.0;

        // Repeat the same test several times to average the results.
        for (int t = 0; t < trials; t++) {

            // Create either a ChainHashMap or ProbeHashMap with the given
            // initial capacity and maximum load factor.
            AbstractHashMap<Integer,Integer> map = chain
                    ? new ChainHashMap<>(17, load)
                    : new ProbeHashMap<>(17, load);

            // Measure total time to insert all keys.
            long startPut = System.nanoTime();
            for (Integer key : keys)
                map.put(key, key);
            long endPut = System.nanoTime();

            // Make a shuffled copy of the keys so get and remove operations
            // are performed in random order rather than insertion order.
            Integer[] shuffledKeys = Arrays.copyOf(keys, keys.length);
            shuffleArray(shuffledKeys, 9000L + t);

            // Measure total time to retrieve all keys.
            long startGet = System.nanoTime();
            for (Integer key : shuffledKeys)
                map.get(key);
            long endGet = System.nanoTime();

            // Measure total time to remove all keys.
            long startRemove = System.nanoTime();
            for (Integer key : shuffledKeys)
                map.remove(key);
            long endRemove = System.nanoTime();

            // Add this trial's times to the running totals.
            putTotal += (endPut - startPut);
            getTotal += (endGet - startGet);
            removeTotal += (endRemove - startRemove);

            // Save the final table capacity and final load factor.
            // After all removals, the final load factor will usually be 0.
            finalCapacity = map.getCapacity();
            finalLoad = map.getCurrentLoadFactor();
        }

        // Print the average running times in milliseconds for this configuration.
        System.out.printf("%-14s %-10.2f %-15.3f %-15.3f %-15.3f %-10d %-10.3f%n",
                name,
                load,
                nanosToMillis(putTotal / (double) trials),
                nanosToMillis(getTotal / (double) trials),
                nanosToMillis(removeTotal / (double) trials),
                finalCapacity,
                finalLoad);
    }

    private static double nanosToMillis(double nanos) {
        // Convert nanoseconds to milliseconds for easier reading.
        return nanos / 1_000_000.0;
    }

    private static Integer[] generateUniqueRandomKeys(int size, long seed) {
        // Use a fixed seed so the random data is reproducible.
        Random random = new Random(seed);

        // A HashSet is used to guarantee uniqueness of keys.
        Set<Integer> set = new HashSet<>();

        // Keep generating random integers until the requested number
        // of unique keys has been produced.
        while (set.size() < size)
            set.add(random.nextInt(Integer.MAX_VALUE));

        // Convert the set into an Integer array for easier iteration.
        return set.toArray(new Integer[0]);
    }

    private static void shuffleArray(Integer[] array, long seed) {
        // Use a fixed seed so the shuffle is reproducible.
        Random random = new Random(seed);

        // Fisher-Yates shuffle:
        // repeatedly swap each position with a random earlier position.
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Integer temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}