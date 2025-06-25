import java.io.*;
import java.util.*;

public class quick_sort {

    public static void main(String[] args) throws IOException {
        // Check if input filename is provided
        if (args.length != 1) {
            System.out.println("Usage: java quick_sort <dataset_filename.csv>");
            return;
        }

        String filename = args[0];
        List<String[]> tempList = new ArrayList<>();

        // Step 1: Read the dataset from CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                // Store each row as [integer, string] in a temporary list
                tempList.add(new String[] { parts[0].trim(), parts[1].trim() });
            }
        }

        // Step 2: Convert list to array for better memory handling
        String[][] data = tempList.toArray(new String[0][]);
        tempList.clear(); // Clear the list to free memory

        // Step 3: Sort using Quick Sort (last element as pivot)
        long start = System.nanoTime();
        quickSort(data, 0, data.length - 1); // Perform sorting
        long end = System.nanoTime();

        double timeInSeconds = (end - start) / 1_000_000_000.0;
        System.out.printf("Sorted %d rows in %.4f seconds\n", data.length, timeInSeconds);        

        // Step 4: Write sorted output to new CSV file
        String outFile = "quick_sort_" + data.length + ".csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) {
            for (String[] row : data) {
                bw.write(row[0] + "," + row[1]);
                bw.newLine();
            }
        }

        // Step 5: Print execution time
        System.out.println("Sorted " + data.length + " rows in " + (end - start) + " ms");
    }

    // Recursive Quick Sort function using last element as pivot
    public static void quickSort(String[][] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high); // Partition the array
            quickSort(arr, low, pi - 1); // Recursively sort left subarray
            quickSort(arr, pi + 1, high); // Recursively sort right subarray
        }
    }

    // Partition function: places pivot at correct position and rearranges other
    // elements
    public static int partition(String[][] arr, int low, int high) {
        int pivot = Integer.parseInt(arr[high][0]); // Last element as pivot
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (Integer.parseInt(arr[j][0]) < pivot) {
                i++;
                // Swap arr[i] and arr[j]
                String[] temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Place pivot in the correct position
        String[] temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1; // Return the pivot index
    }
}
