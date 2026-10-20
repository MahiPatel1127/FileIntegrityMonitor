import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIntegrityMonitor {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int choice = 0;

        System.out.println("=================================");
        System.out.println("      FILE INTEGRITY MONITOR");
        System.out.println("=================================");

        while (choice != 3) {

            System.out.println();
            System.out.println("1. Create Baseline");
            System.out.println("2. Check Integrity");
            System.out.println("3. Exit");

            System.out.print("\nEnter your choice: ");

            // Check that the user entered a number.
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();

            if (choice == 1) {
                createBaseline();
            } else if (choice == 2) {
                checkIntegrity();
            } else if (choice == 3) {
                System.out.println("Exiting...");
            } else {
                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }

    // Creates a baseline containing the SHA-256 hash of each monitored file.
    public static void createBaseline() {

        File folder = new File("monitored");

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Monitored folder not found.");
            return;
        }

        File[] files = folder.listFiles();

        System.out.println("Creating baseline...");

        try {
            FileWriter writer = new FileWriter("baseline.txt");

            for (File file : files) {

                if (file.isFile()) {

                    String hash = calculateHash(file);

                    // Store the filename and its hash in the baseline.
                    writer.write(file.getName() + "|" + hash + "\n");

                    System.out.println("Added to baseline: " + file.getName());
                }
            }

            writer.close();

            System.out.println();
            System.out.println("Baseline created successfully.");

        } catch (IOException e) {
            System.out.println("Error creating baseline.");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("SHA-256 algorithm not available.");
        }
    }

    // Compares the current files against the stored baseline.
    public static void checkIntegrity() {

        System.out.println("Checking file integrity...");
        System.out.println();

        File baseline = new File("baseline.txt");

        if (!baseline.exists()) {
            System.out.println("No baseline found.");
            System.out.println("Please create a baseline first.");
            return;
        }

        ArrayList<String> baselineFiles = new ArrayList<>();

        int unchangedCount = 0;
        int modifiedCount = 0;
        int missingCount = 0;
        int newCount = 0;

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("baseline.txt"));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|");

                String fileName = parts[0];
                String originalHash = parts[1];

                baselineFiles.add(fileName);

                File file = new File("monitored", fileName);

                System.out.println("File: " + fileName);

                // Check whether a baseline file has been deleted.
                if (!file.exists()) {
                    System.out.println("Status: MISSING");
                    missingCount++;
                } else {

                    String currentHash = calculateHash(file);

                    // Compare the current hash with the stored hash.
                    if (originalHash.equals(currentHash)) {
                        System.out.println("Status: UNCHANGED");
                        unchangedCount++;
                    } else {
                        System.out.println("Status: MODIFIED");
                        modifiedCount++;
                    }
                }

                System.out.println();
            }

            File folder = new File("monitored");

            if (!folder.exists() || !folder.isDirectory()) {
                System.out.println("Monitored folder not found.");
                reader.close();
                return;
            }

            File[] files = folder.listFiles();

            // Find files that were not present when the baseline was created.
            for (File file : files) {

                if (file.isFile() && !baselineFiles.contains(file.getName())) {
                    System.out.println("File: " + file.getName());
                    System.out.println("Status: NEW");
                    newCount++;
                    System.out.println();
                }
            }

            // Display a summary of the integrity check.
            System.out.println("=================================");
            System.out.println("        INTEGRITY SUMMARY");
            System.out.println("=================================");

            System.out.println("Unchanged files: " + unchangedCount);
            System.out.println("Modified files:  " + modifiedCount);
            System.out.println("Missing files:   " + missingCount);
            System.out.println("New files:       " + newCount);

            System.out.println("=================================");

            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading files.");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("SHA-256 algorithm not available.");
        }
    }

    // Calculates the SHA-256 hash of a file.
    public static String calculateHash(File file)
            throws IOException, NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        FileInputStream input = new FileInputStream(file);

        byte[] buffer = new byte[1024];
        int bytesRead;

        // Read the file in small sections and update the hash.
        while ((bytesRead = input.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }

        input.close();

        byte[] hash = digest.digest();

        StringBuilder hexHash = new StringBuilder();

        // Convert the hash bytes into a readable hexadecimal string.
        for (byte b : hash) {
            hexHash.append(String.format("%02x", b));
        }

        return hexHash.toString();
    }
}