import java.io.*;
import java.util.*;

public class quick_sort_step {

    // Read rows from CSV file between specified start and end rows (1-based index)
    public static List<String[]> readRows(String filename, int start, int end) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int rowNum = 0;
            while ((line = br.readLine()) != null) {
                rowNum++;
                if (rowNum >= start && rowNum <= end) {
                    String[] parts = line.split(",");
                    rows.add(new String[] { parts[0].trim(), parts[1].trim() });
                }
            }
        }
        return rows;
    }

    // Recursive quick sort with logging of steps after each partition
    public static void quickSortWithSteps(List<String[]> data, int low, int high, List<String> steps) {
        if (low < high) {
            int pi = partition(data, low, high);
            steps.add("pi=" + pi + " " + formatData(data)); // Log array after partition
            quickSortWithSteps(data, low, pi - 1, steps); // Sort left part
            quickSortWithSteps(data, pi + 1, high, steps); // Sort right part
        }
    }

    // Partition function using last element as pivot
    public static int partition(List<String[]> data, int low, int high) {
        int pivot = Integer.parseInt(data.get(high)[0]);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            int val = Integer.parseInt(data.get(j)[0]);
            if (val < pivot) {
                i++;
                Collections.swap(data, i, j); // Swap smaller elements to the left
            }
        }

        Collections.swap(data, i + 1, high); // Place pivot in the correct position
        return i + 1;
    }

    // Format the list of (int, string) pairs as a readable string for logging
    public static String formatData(List<String[]> data) {
        List<String> result = new ArrayList<>();
        for (String[] pair : data) {
            result.add(pair[0] + "/" + pair[1]);
        }
        return result.toString(); // Convert to printable list format
    }

    public static void main(String[] args) throws IOException {
        // Check for proper argument count
        if (args.length != 3) {
            System.out.println("Usage: java quick_sort_step <csv_file> <start_row> <end_row>");
            return;
        }

        String filename = args[0];
        int startRow = Integer.parseInt(args[1]);
        int endRow = Integer.parseInt(args[2]);

        // Step 1: Read dataset from given row range
        List<String[]> data = readRows(filename, startRow, endRow);

        // Step 2: Initialize step list with original array
        List<String> steps = new ArrayList<>();
        steps.add(formatData(data));

        // Step 3: Perform quick sort with step tracking
        quickSortWithSteps(data, 0, data.size() - 1, steps);

        // Step 4: Write sorting steps to output file
        String outputFile = "quick_sort_step_" + startRow + "_" + endRow + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (String step : steps) {
                bw.write(step);
                bw.newLine();
            }
        }

        System.out.println("Steps saved to " + outputFile);
    }
}
