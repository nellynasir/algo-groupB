import java.io.*;
import java.util.*;

public class binary_search_step {
    
    public static void main(String[] args) {
        // Create a Scanner object to get input from the user
        Scanner scanner = new Scanner(System.in);
        
        // Prompt the user to enter a target value
        System.out.print("Please enter a target value: ");
        int target = scanner.nextInt();  // Read the integer input from the user
        
        // File name of the unsorted dataset
        String datasetFile = "dataset_sample_1000.csv";
        
        // Read the dataset from the CSV file
        List<Map.Entry<Integer, String>> dataset = readDatasetFromCSV(datasetFile);
        
        // Manually sort the dataset using selection sort
        selectionSort(dataset);
        
        // List to hold the steps of the binary search
        List<String> searchPath = new ArrayList<>();
        
        // Perform binary search and track the steps
        int result = binarySearch(dataset, target, searchPath);
        
        // Write the search steps to a text file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("binary_search_step_" + target + ".txt"))) {
            for (String step : searchPath) {
                writer.write(step);
                writer.newLine();
            }
            // Append the result (either the found target or -1 if not found)
            if (result == -1) {
                writer.write("-1");
            } else {
                writer.write("Target " + target + " found at index " + (result + 1));  // Adjust to 1-based index
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Close the scanner
        scanner.close();
    }

    // Function to read the dataset from a CSV file
    public static List<Map.Entry<Integer, String>> readDatasetFromCSV(String filename) {
        List<Map.Entry<Integer, String>> dataset = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int value = Integer.parseInt(parts[0].trim());  // Parse the integer
                String key = parts[1].trim();  // String is the second column
                dataset.add(new AbstractMap.SimpleEntry<>(value, key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return dataset;
    }

    // Manually sorts the dataset using selection sort
    public static void selectionSort(List<Map.Entry<Integer, String>> dataset) {
        int n = dataset.size();
        
        // Selection Sort Algorithm
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (dataset.get(j).getKey() < dataset.get(minIndex).getKey()) {
                    minIndex = j;
                }
            }
            // Swap if we found a smaller element
            Collections.swap(dataset, i, minIndex);
        }
    }

    // Binary search function that tracks the search steps and adds the index of each comparison
    public static int binarySearch(List<Map.Entry<Integer, String>> dataset, int target, List<String> searchPath) {
        int left = 0;
        int right = dataset.size() - 1;
        
        // Make sure we don't enter an infinite loop by checking the proper conditions
        while (left <= right) {
            int mid = left + (right - left) / 2; // Calculate the middle index
            Map.Entry<Integer, String> midElement = dataset.get(mid);
            
            // Record the comparison step with row number (index adjusted to 1-based index)
            searchPath.add((mid + 1) + ": " + midElement.getKey() + "/" + midElement.getValue());
            
            // If the target is found
            if (midElement.getKey() == target) {
                return mid;  // Target found at the mid index
            }
            
            // If the target is greater, ignore the left half
            if (midElement.getKey() < target) {
                left = mid + 1;
            }
            // If the target is smaller, ignore the right half
            else {
                right = mid - 1;
            }
        }
        
        // If the target is not found, return -1
        return -1;  
    }
}
