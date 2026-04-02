import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Welcome message
        System.out.println("=== Train Consist    Management App ===");

        // Initialize empty list for bogies
        List<String> bogies = new ArrayList<>();

        // Display initial bogie count
        System.out.println("Train initialized successfully.");
        System.out.println("Initial number of bogies: " + bogies.size());

        // Continue program
        System.out.println("Program is ready to add bogies...");
    }
}