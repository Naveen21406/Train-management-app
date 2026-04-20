import java.util.*;

/**
 * UC3 - Track Unique Bogie IDs (Set Implementation)
 * Demonstrates preventing duplicate bogies using HashSet
 * Data Structure: HashSet for uniqueness
 */
public class UC3_TrackUniqueBogieIDs {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 3: Track Unique Bogie IDs");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Ensure no duplicate bogie IDs exist in the train.");
        System.out.println("Data Structure Used: HashSet for O(1) uniqueness checking");
        System.out.println("-".repeat(70));

        try {
            Train train = new Train("TR003", 8);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // Create a set to track unique bogie IDs
            HashSet<String> uniqueBogieIds = new HashSet<>();
            ArrayList<Bogie> bogies = new ArrayList<>();

            // Create bogies
            String[] bogieIds = {"B001", "B002", "B003", "B004", "B005"};
            String[] bogieNames = {"Coach A", "Coach B", "Coach C", "Coach D", "Coach E"};
            int[] capacities = {100, 100, 80, 80, 60};

            System.out.println("\n✓ Creating bogies and tracking unique IDs...");
            for (int i = 0; i < bogieIds.length; i++) {
                Bogie bogie = new Bogie(bogieIds[i], "Passenger", capacities[i], bogieNames[i]);
                bogies.add(bogie);
                uniqueBogieIds.add(bogieIds[i]);
                System.out.println("  ✓ Added ID: " + bogieIds[i] + " | Unique IDs so far: " + uniqueBogieIds.size());
            }

            // Add bogies to train
            System.out.println("\n✓ Adding bogies to train...");
            for (Bogie bogie : bogies) {
                train.addBogie(bogie);
                System.out.println("  ✓ " + bogie.getBogieId() + " added successfully");
            }

            // Attempt to add duplicate - should fail
            System.out.println("\n✓ Attempting to add duplicate bogie with ID 'B001'...");
            try {
                Bogie duplicateBogie = new Bogie("B001", "Passenger", 100, "Duplicate Coach");
                train.addBogie(duplicateBogie);
                System.out.println("  ✗ ERROR: Duplicate was added (should have been prevented!)");
            } catch (Exception e) {
                System.out.println("  ✓ Duplicate prevention successful: " + e.getMessage());
            }

            // Display results
            System.out.println("\n========== UNIQUE BOGIE TRACKING RESULTS ==========");
            System.out.println("Total Bogies in Train: " + train.getTotalBogies());
            System.out.println("Unique Bogie IDs: " + uniqueBogieIds.size());
            System.out.println("Bogie IDs: " + uniqueBogieIds);
            
            train.displayConfiguration();

            System.out.println("✓ UC3 Completed Successfully!");

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
