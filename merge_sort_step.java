import java.io.*;
import java.util.*;

public class merge_sort_step {
    private static List<String> steps = new ArrayList<>();
    
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java merge_sort_step <csv_file> <start_row> <end_row>");
            System.out.println("Example: java merge_sort_step data_sample_1000.csv 1 7");
            System.out.println("Arguments provided: " + args.length);
            for (int i = 0; i < args.length; i++) {
                System.out.println("Arg " + i + ": " + args[i]);
            }
            return;
        }
        
        String csvFile = args[0];
        int startRow = Integer.parseInt(args[1]);
        int endRow = Integer.parseInt(args[2]);
        
        try {
            // Read data from CSV file
            List<DataEntry> data = readCSV(csvFile, startRow, endRow);
            
            // Perform merge sort with step tracking
            mergeSort(data, 0, data.size() - 1);
            
            // Write steps to output file
            String outputFile = "merge_sort_step_" + startRow + "_" + endRow + ".txt";
            writeStepsToFile(outputFile);
            
            System.out.println("Merge sort steps written to: " + outputFile);
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static List<DataEntry> readCSV(String filename, int startRow, int endRow) throws IOException {
        List<DataEntry> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        int currentRow = 1;
        
        while ((line = br.readLine()) != null && currentRow <= endRow) {
            if (currentRow >= startRow) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    int integer = Integer.parseInt(parts[0]);
                    String string = parts[1];
                    data.add(new DataEntry(integer, string));
                }
            }
            currentRow++;
        }
        br.close();
        
        // Add initial state to steps
        steps.add(arrayToString(data));
        
        return data;
    }
    
    private static void mergeSort(List<DataEntry> arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    
    private static void merge(List<DataEntry> arr, int left, int mid, int right) {
        // Create temporary arrays for left and right subarrays
        List<DataEntry> leftArr = new ArrayList<>();
        List<DataEntry> rightArr = new ArrayList<>();
        
        // Copy data to temporary arrays
        for (int i = left; i <= mid; i++) {
            leftArr.add(arr.get(i));
        }
        for (int j = mid + 1; j <= right; j++) {
            rightArr.add(arr.get(j));
        }
        
        // Merge the temporary arrays back into arr[left..right]
        int i = 0, j = 0;
        int k = left;
        
        while (i < leftArr.size() && j < rightArr.size()) {
            if (leftArr.get(i).integer <= rightArr.get(j).integer) {
                arr.set(k, leftArr.get(i));
                i++;
            } else {
                arr.set(k, rightArr.get(j));
                j++;
            }
            k++;
        }
        
        // Copy remaining elements of leftArr[], if any
        while (i < leftArr.size()) {
            arr.set(k, leftArr.get(i));
            i++;
            k++;
        }
        
        // Copy remaining elements of rightArr[], if any
        while (j < rightArr.size()) {
            arr.set(k, rightArr.get(j));
            j++;
            k++;
        }
        
        // Add current state to steps after merging
        steps.add(arrayToString(arr));
    }
    
    private static String arrayToString(List<DataEntry> arr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < arr.size(); i++) {
            sb.append(arr.get(i).integer).append("/").append(arr.get(i).string);
            if (i < arr.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    private static void writeStepsToFile(String filename) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filename));
        for (String step : steps) {
            writer.println(step);
        }
        writer.close();
    }
    
    // Inner class to represent data entries
    static class DataEntry {
        int integer;
        String string;
        
        public DataEntry(int integer, String string) {
            this.integer = integer;
            this.string = string;
        }
    }
}