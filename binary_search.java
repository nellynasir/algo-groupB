import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class binary_search {

    public static List<int[]> readDataset(String filename) throws Exception {
        List<int[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                data.add(new int[]{Integer.parseInt(parts[0])});
            }
        }
        return data;
    }

    public static int binarySearch(List<int[]> data, int target) {
        int left = 0, right = data.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (data.get(mid)[0] == target) return mid;
            else if (data.get(mid)[0] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    public static double measureTime(List<int[]> data, int target, int repeat) {
        // JVM Warm-up
        for (int i = 0; i < 1000; i++) {
            binarySearch(data, data.get(0)[0]);
        }

        long start = System.nanoTime();
        for (int i = 0; i < repeat; i++) {
            binarySearch(data, target);
        }
        long end = System.nanoTime();

        long totalNs = end - start;
        return totalNs / 1_000_000.0 / repeat; // average time in ms
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java binary_search <sorted_dataset.csv>");
            return;
        }

        List<int[]> data = readDataset(args[0]);
        data.sort(Comparator.comparingInt(a -> a[0]));

        int size = data.size();
        int repeat = size; // repeat once per element size

        // Fixed targets
        int best = data.get(size / 2)[0];
        int avg = data.get((int)(size * 0.37))[0];
        int worst = -1;

        double bestTime = measureTime(data, best, repeat);
        double avgTime = measureTime(data, avg, repeat);
        double worstTime = measureTime(data, worst, repeat);

        DecimalFormat df = new DecimalFormat("0.000000");

        String outFile = "binary_search_" + size + ".txt";
        try (PrintWriter writer = new PrintWriter(outFile)) {
            writer.println("Best case time   : " + df.format(bestTime) + " ms");
            writer.println("Average case time: " + df.format(avgTime) + " ms");
            writer.println("Worst case time  : " + df.format(worstTime) + " ms");
        }

        System.out.println("Done. Output written to: " + outFile);
    }
}
