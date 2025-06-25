import java.io.*;
import java.util.*;

public class quick_sort_step {

    static List<String> steps = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("Usage: java quick_sort_step <csv_file> <startRow> <endRow>");
            return;
        }

        String csvFile = args[0];
        int startRow = Integer.parseInt(args[1]);
        int endRow = Integer.parseInt(args[2]);

        List<Data> data = readDataset(csvFile, startRow, endRow);
        steps.add(formatList(data)); // Add initial state

        quickSort(data, 0, data.size() - 1);

        String outputFile = String.format("quick_sort_step_%d_%d.txt", startRow, endRow);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String step : steps) {
                writer.write(step + "\n");
            }
        }

        System.out.println("Steps saved to " + outputFile);
    }

    static class Data {
        int number;
        String text;

        Data(int number, String text) {
            this.number = number;
            this.text = text;
        }

        @Override
        public String toString() {
            return number + "/" + text;
        }
    }

    static List<Data> readDataset(String filename, int startRow, int endRow) throws Exception {
        List<Data> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int lineNum = 0;

        while ((line = reader.readLine()) != null) {
            lineNum++;
            if (lineNum >= startRow && lineNum <= endRow) {
                String[] parts = line.split(",");
                result.add(new Data(Integer.parseInt(parts[0]), parts[1]));
            }
        }

        reader.close();
        return result;
    }

    static void quickSort(List<Data> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            steps.add("pi=" + pi + " " + formatList(arr));
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    static int partition(List<Data> arr, int low, int high) {
        int pivot = arr.get(high).number;
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr.get(j).number < pivot) {
                i++;
                Collections.swap(arr, i, j);
            }
        }

        Collections.swap(arr, i + 1, high);
        return i + 1;
    }

    static String formatList(List<Data> arr) {
        List<String> formatted = new ArrayList<>();
        for (Data d : arr) {
            formatted.add(d.toString());
        }
        return formatted.toString();
    }
}
