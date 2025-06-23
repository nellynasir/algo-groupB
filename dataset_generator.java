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

    // Method to generate a dataset and write it directly to disk to avoid using excessive memory
    public static void generateDataset(int size, String outputFile) throws IOException {
        Set<Long> numbers = new HashSet<>();
        Random rand = new Random();

        // Create a BufferedWriter to write directly to the output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Loop to generate unique numbers and write them to disk directly
            while (numbers.size() < size) {
                // Generate a random number between 1 billion and 1.2 billion
                long randomNumber = 1_000_000_000L + (long) (rand.nextDouble() * 200_000_000);
                // Ensure the number is unique
                if (!numbers.contains(randomNumber)) {
                    numbers.add(randomNumber); // Add to the set
                    // Write the number and random string to the file
                    writer.write(randomNumber + "," + generateRandomString(6) + "\n");
                }
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
