import java.util.*;

/**
 * UC4 - Maintain Ordered Bogie IDs (LinkedHashSet)
 * Demonstrates preserving insertion order while maintaining uniqueness
 * Data Structure: LinkedHashSet for order preservation
 */
public class UC4_MaintainOrderedBogieIDs {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 4: Maintain Ordered Bogie IDs");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Track bogie IDs in insertion order while preventing duplicates.");
        System.out.println("Data Structure Used: LinkedHashSet (ordered set)");
        System.out.println("-".repeat(70));

        try {
            Train train = new Train("TR004", 10);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // LinkedHashSet maintains insertion order
            LinkedHashSet<String> orderedBogieIds = new LinkedHashSet<>();
            ArrayList<Bogie> bogies = new ArrayList<>();

            // Add bogies in specific order
            String[][] bogieData = {
                {"B001", "Coach First A", "120"},
                {"B002", "Coach First B", "120"},
                {"B003", "Coach Second A", "100"},
                {"B004", "Coach Sleeper", "60"},
                {"B005", "Coach AC", "80"},
                {"B006", "Coach General", "150"}
            };

            System.out.println("\n✓ Adding bogies in order...");
            for (String[] data : bogieData) {
                Bogie bogie = new Bogie(data[0], "Passenger", Integer.parseInt(data[2]), data[1]);
                bogies.add(bogie);
                train.addBogie(bogie);
                orderedBogieIds.add(data[0]);
                System.out.println("  " + (orderedBogieIds.size()) + ". ID: " + data[0] + " | Name: " + data[1]);
            }

            // Display preserved order
            System.out.println("\n========== ORDERED BOGIE TRACKING RESULTS ==========");
            System.out.println("LinkedHashSet preserves insertion order:");
            int position = 1;
            for (String id : orderedBogieIds) {
                System.out.println("  Position " + position + ": " + id);
                position++;
            }

            System.out.println("\nComparison with HashSet (unordered):");
            HashSet<String> unorderedIds = new HashSet<>(orderedBogieIds);
            System.out.println("  HashSet (unordered): " + unorderedIds);
            System.out.println("  LinkedHashSet (ordered): " + orderedBogieIds);

            train.displayConfiguration();

            System.out.println("✓ UC4 Completed Successfully!");

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
