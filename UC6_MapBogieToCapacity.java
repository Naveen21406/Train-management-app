import java.util.*;

/**
 * UC6 - Map Bogie to Capacity (HashMap)
 * Demonstrates using HashMap for efficient capacity lookup by bogie ID
 * Data Structure: HashMap for O(1) capacity retrieval
 */
public class UC6_MapBogieToCapacity {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 6: Map Bogie to Capacity");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Create a mapping of bogie IDs to their capacities for quick lookup.");
        System.out.println("Data Structure Used: HashMap for O(1) capacity retrieval");
        System.out.println("-".repeat(70));

        try {
            Train train = new Train("TR006", 10);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // HashMap for bogie ID to capacity mapping
            HashMap<String, Integer> bogieCapacityMap = new HashMap<>();
            ArrayList<Bogie> bogies = new ArrayList<>();

            // Create bogies
            String[] bogieIds = {"B001", "B002", "B003", "B004", "B005", "B006", "B007"};
            String[] names = {"FC-A", "FC-B", "SC-A", "SC-B", "SLP", "AC", "GEN"};
            int[] capacities = {150, 150, 120, 120, 80, 90, 200};

            System.out.println("\n✓ Creating bogies and building capacity map...");
            for (int i = 0; i < bogieIds.length; i++) {
                Bogie bogie = new Bogie(bogieIds[i], "Passenger", capacities[i], names[i]);
                bogies.add(bogie);
                train.addBogie(bogie);
                bogieCapacityMap.put(bogieIds[i], capacities[i]);
                System.out.println("  ✓ " + bogieIds[i] + " -> " + capacities[i] + " seats");
            }

            // Efficient capacity lookups
            System.out.println("\n========== CAPACITY LOOKUP RESULTS ==========");
            System.out.println("O(1) Capacity Lookups:");

            String[] lookupIds = {"B001", "B003", "B005", "B007"};
            for (String id : lookupIds) {
                Integer capacity = bogieCapacityMap.get(id);
                if (capacity != null) {
                    System.out.println("  ✓ Bogie " + id + " Capacity: " + capacity + " seats");
                } else {
                    System.out.println("  ✗ Bogie " + id + " not found");
                }
            }

            // Calculate statistics using the map
            System.out.println("\nStatistics from HashMap:");
            int totalCapacity = bogieCapacityMap.values().stream().mapToInt(Integer::intValue).sum();
            int maxCapacity = bogieCapacityMap.values().stream().mapToInt(Integer::intValue).max().orElse(0);
            int minCapacity = bogieCapacityMap.values().stream().mapToInt(Integer::intValue).min().orElse(0);

            System.out.println("  Total Capacity: " + totalCapacity + " seats");
            System.out.println("  Maximum Capacity: " + maxCapacity + " seats");
            System.out.println("  Minimum Capacity: " + minCapacity + " seats");
            System.out.println("  Average Capacity: " + String.format("%.2f", totalCapacity / (double)bogieCapacityMap.size()) + " seats");

            // Group by capacity
            System.out.println("\nBogies grouped by capacity:");
            Map<Integer, ArrayList<String>> capacityGroups = new HashMap<>();
            for (Map.Entry<String, Integer> entry : bogieCapacityMap.entrySet()) {
                capacityGroups.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(entry.getKey());
            }
            for (Map.Entry<Integer, ArrayList<String>> entry : capacityGroups.entrySet()) {
                System.out.println("  " + entry.getKey() + " seats: " + entry.getValue());
            }

            train.displayConfiguration();

            System.out.println("✓ UC6 Completed Successfully!");

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
