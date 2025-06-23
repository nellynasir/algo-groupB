import java.io.*;
import java.util.*;

public class quick_sort {

    // Read dataset from CSV file
    public static List<String[]> readDataset(String filename) {
        List<String[]> dataset = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                dataset.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error reading dataset: " + e.getMessage());
        }
        return dataset;
    }

    // Quick Sort using last element as pivot
    public static void quickSort(List<String[]> data, int low, int high) {
        if (low < high) {
            int pi = partition(data, low, high);
            quickSort(data, low, pi - 1);
            quickSort(data, pi + 1, high);
        }
    }

    public static int partition(List<String[]> data, int low, int high) {
        int pivot = Integer.parseInt(data.get(high)[0]);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            int current = Integer.parseInt(data.get(j)[0]);
            if (current <= pivot) {
                i++;
                Collections.swap(data, i, j);
            }
        }

        Collections.swap(data, i + 1, high);
        return i + 1;
    }

    // Write sorted dataset to CSV
    public static void writeSortedDataset(List<String[]> data, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String[] row : data) {
                writer.write(row[0] + "," + row[1]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing sorted dataset: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java quick_sort <dataset.csv>");
            return;
        }

        String inputFile = args[0];
        List<String[]> dataset = readDataset(inputFile);
        int size = dataset.size();

        long startTime = System.nanoTime();
        quickSort(dataset, 0, size - 1);
        long endTime = System.nanoTime();

        String outputFile = "quick_sort_" + size + ".csv";
        writeSortedDataset(dataset, outputFile);

        double elapsedTimeInSec = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("Sorting completed in %.4f seconds%n", elapsedTimeInSec);
        System.out.println("Sorted output saved to " + outputFile);
    }
}
