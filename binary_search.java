import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class binary_search {

    // Method to read the dataset from a CSV file
    public static List<int[]> readDataset(String filename) throws Exception {
        List<int[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line of the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");  // Split the line by comma
                // Add the parsed integer from the first column to the data list
                data.add(new int[]{Integer.parseInt(parts[0])});
            }
        }
        return data;  // Return the list containing all the data entries
    }

    // Method to perform binary search
    public static int binarySearch(List<int[]> data, int target) {
        int left = 0, right = data.size() - 1;  // Initialize the left and right pointers for the search range
        
        // Continue searching while the left pointer is less than or equal to the right pointer
        while (left <= right) {
            int mid = (left + right) / 2;  // Calculate the middle index of the current search range
            
            // If the middle element equals the target, return the index
            if (data.get(mid)[0] == target) return mid;
            // If the middle element is smaller than the target, move the left pointer to mid + 1
            else if (data.get(mid)[0] < target) left = mid + 1;
            // If the middle element is greater than the target, move the right pointer to mid - 1
            else right = mid - 1;
        }
        
        return -1;  // If the target is not found, return -1
    }

    // Method to measure the time taken for binary search on the dataset for a fixed number of repetitions
    public static double measureTime(List<int[]> data, int target, int repeat) {
        // JVM Warm-up: Run binary search 1000 times to avoid JVM optimizations affecting performance measurements
        for (int i = 0; i < 1000; i++) {
            binarySearch(data, data.get(0)[0]);  // Warm-up search (not relevant to target)
        }

        long start = System.nanoTime();  // Start measuring the time
        for (int i = 0; i < repeat; i++) {
            binarySearch(data, target);  // Perform the binary search for the target
        }
        long end = System.nanoTime();  // End measuring the time

        long totalNs = end - start;  // Calculate the total time taken
        return totalNs / 1_000_000.0 / repeat;  // Return the average time per binary search in milliseconds
    }

    // Main method where the program execution begins
    public static void main(String[] args) throws Exception {
        // Check if command line arguments are provided
        if (args.length != 1) {
            System.out.println("Usage: java binary_search <sorted_dataset.csv>");
            return;
        }

        // Read the dataset from the provided file
        List<int[]> data = readDataset(args[0]);

        // Sort the data (though it should already be sorted in the CSV, just in case)
        data.sort(Comparator.comparingInt(a -> a[0]));

        int size = data.size();  // Get the size of the dataset
        int repeat = size;  // Define the number of repetitions for performance measurement

        // Define the target values for best, average, and worst case search scenarios
        int best = data.get(size / 2)[0];  // Best case: target is in the middle of the dataset
        int avg = data.get((int)(size * 0.37))[0];  // Average case: target is approximately 37% through the list
        int worst = -1;  // Worst case: target is not in the dataset

        // Measure the time taken to search for each target (best, average, and worst cases)
        double bestTime = measureTime(data, best, repeat);
        double avgTime = measureTime(data, avg, repeat);
        double worstTime = measureTime(data, worst, repeat);

        // Format the results to show up to 6 decimal places
        DecimalFormat df = new DecimalFormat("0.000000");

        // Define the output file to store the search times
        String outFile = "binary_search_" + size + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(outFile))) {
            // Write the results to the output file
            writer.println("Best case time   : " + df.format(bestTime) + " ms");
            writer.println("Average case time: " + df.format(avgTime) + " ms");
            writer.println("Worst case time  : " + df.format(worstTime) + " ms");
        }

        // Print a message indicating the process is done and output file is created
        System.out.println("Done. Output written to: " + outFile);
    }
}
