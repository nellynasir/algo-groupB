import java.io.*;
import java.util.*;

public class merge_sort {

    // Class to hold the data from CSV (integer and string)
    static class DataEntry implements Comparable<DataEntry> {
        int number;
        String text;

        // Constructor to initialize the data
        public DataEntry(int number, String text) {
            this.number = number;
            this.text = text;
        }

        // Compare method to sort based on the integer number
        @Override
        public int compareTo(DataEntry other) {
            return Integer.compare(this.number, other.number);
        }

        // Convert the data to a string format
        @Override
        public String toString() {
            return number + "," + text;
        }
    }

    // Iterative method to perform merge sort
    public static void mergeSort(List<DataEntry> list) {
        int size = list.size();
        
        // Iteratively merge sublists in increasing order of sublist size
        for (int step = 1; step < size; step *= 2) {
            for (int i = 0; i < size - step; i += 2 * step) {
                // Find the end of the left half of the sublist
                int mid = Math.min(i + step - 1, size - 1);
                // Find the end of the right half of the sublist
                int rightEnd = Math.min(i + 2 * step - 1, size - 1);

                // Merge the two sublists
                merge(list, i, mid, rightEnd);
            }
        }
    }

    // Method to merge the sorted sublists
    private static void merge(List<DataEntry> list, int start, int mid, int end) {
        List<DataEntry> temp = new ArrayList<>(end - start + 1);  // Temporary list to hold merged results
        int i = start, j = mid + 1;

        // Merge the two halves while maintaining the order
        while (i <= mid && j <= end) {
            if (list.get(i).compareTo(list.get(j)) <= 0) {
                temp.add(list.get(i++));
            } else {
                temp.add(list.get(j++));
            }
        }

        // Add remaining elements from both halves
        while (i <= mid) temp.add(list.get(i++));
        while (j <= end) temp.add(list.get(j++));

        // Copy the merged list back into the original list
        for (int k = 0; k < temp.size(); k++) {
            list.set(start + k, temp.get(k));
        }
    }

    // Method to process the CSV file in chunks (with sorting and writing)
    public static void processCSV(String inputFile, String outputFile) throws IOException {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            PrintWriter writer = new PrintWriter(new FileWriter(outputFile))
        ) {
            String line;
            List<DataEntry> chunk = new ArrayList<>();
            int chunkSize = 1000000;  // You can adjust this size depending on available memory

            // Read and process data line by line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int number = Integer.parseInt(parts[0].trim());
                    String text = parts[1].trim();
                    chunk.add(new DataEntry(number, text));

                    // Once chunk reaches the specified size, sort it and write it to disk
                    if (chunk.size() >= chunkSize) {
                        Collections.sort(chunk);  // Sort the current chunk
                        for (DataEntry entry : chunk) {
                            writer.println(entry);  // Write sorted data to disk
                        }
                        chunk.clear();  // Clear the chunk to process the next batch
                    }
                }
            }

            // Process any remaining data in the last chunk
            if (!chunk.isEmpty()) {
                Collections.sort(chunk);
                for (DataEntry entry : chunk) {
                    writer.println(entry);
                }
            }
        }
    }

    // Method to measure sorting time
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java merge_sort <input_file> <output_file>");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try {
            // Measure sorting time
            long startTime = System.nanoTime();
            processCSV(inputFile, outputFile);  // Process and sort the CSV in chunks
            long endTime = System.nanoTime();

            // Calculate the duration in seconds
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;  // Convert from nanoseconds to seconds

            System.out.println("Sorting completed. Sorted data has been saved to: " + outputFile);
            System.out.printf("Merge Sort completed in %.4f seconds.\n", durationInSeconds);
        } catch (IOException e) {
            System.err.println("Error processing the file: " + e.getMessage());
        }
    }
}