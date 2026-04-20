import java.util.*;

/**
 * UC5 - Preserve Insertion Order (LinkedHashMap)
 * Demonstrates using LinkedHashMap to maintain insertion order with key-value pairs
 * Data Structure: LinkedHashMap for ordered key-value mapping
 */
public class UC5_PreserveInsertionOrder {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 5: Preserve Insertion Order");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Maintain insertion order while mapping bogie IDs to details.");
        System.out.println("Data Structure Used: LinkedHashMap (ordered map)");
        System.out.println("-".repeat(70));

        try {
            Train train = new Train("TR005", 8);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // LinkedHashMap maintains insertion order
            LinkedHashMap<String, Bogie> bogieRegistry = new LinkedHashMap<>();

            // Create bogies in specific order
            String[][] bogieData = {
                {"B001", "First Class A", "150"},
                {"B002", "First Class B", "150"},
                {"B003", "Second Class A", "120"},
                {"B004", "Second Class B", "120"},
                {"B005", "Sleeper Class", "80"},
                {"B006", "AC 3-Tier", "90"}
            };

            System.out.println("\n✓ Adding bogies to LinkedHashMap (insertion order preserved)...");
            for (String[] data : bogieData) {
                Bogie bogie = new Bogie(data[0], "Passenger", Integer.parseInt(data[2]), data[1]);
                bogieRegistry.put(data[0], bogie);
                train.addBogie(bogie);
                System.out.println("  ✓ Registered: " + data[0] + " -> " + data[1]);
            }

            // Display in insertion order
            System.out.println("\n========== INSERTION ORDER PRESERVED RESULTS ==========");
            System.out.println("Iterating LinkedHashMap in insertion order:");
            int position = 1;
            for (Map.Entry<String, Bogie> entry : bogieRegistry.entrySet()) {
                System.out.println("  Position " + position + ": " + entry.getKey() + " -> " + entry.getValue().getBogieName());
                position++;
            }

            // Compare with HashMap (random order)
            System.out.println("\n--- Comparison ---");
            HashMap<String, Bogie> unorderedRegistry = new HashMap<>(bogieRegistry);
            System.out.println("HashMap may iterate in different order (unordered)");
            System.out.println("LinkedHashMap iteration (always in insertion order):");
            for (String id : bogieRegistry.keySet()) {
                System.out.println("  - " + id);
            }

            train.displayConfiguration();

            System.out.println("✓ UC5 Completed Successfully!");

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
