// dataset_generator.java
import java.io.*;
import java.util.*;

public class dataset_generator {
    public static String generateRandomString(int length) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(rand.nextInt(alphabet.length())));
        }
        return sb.toString();
    }

    public static void generateDataset(int size, String outputFile) throws IOException {
        Set<Integer> numbers = new HashSet<>();
        Random rand = new Random();

        while (numbers.size() < size) {
            numbers.add(rand.nextInt(1_000_000_000));
        }

        try (PrintWriter writer = new PrintWriter(outputFile)) {
            for (int num : numbers) {
                writer.println(num + "," + generateRandomString(6));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java dataset_generator <size>");
            return;
        }
        int size = Integer.parseInt(args[0]);
        generateDataset(size, "dataset_" + size + ".csv");
    }
}
