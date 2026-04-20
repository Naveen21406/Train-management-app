import java.util.*;

/**
 * UC7 - Sort Bogies by Capacity
 * Demonstrates sorting using Comparator for different ordering criteria
 * Data Structure: ArrayList with Comparator
 */
public class UC7_SortBogiesByCapacity {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 7: Sort Bogies by Capacity");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Sort bogies by capacity in ascending and descending order.");
        System.out.println("Data Structure Used: ArrayList with Comparator");
        System.out.println("-".repeat(70));

        try {
            Train train = new Train("TR007", 10);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // Create bogies with different capacities
            ArrayList<Bogie> bogies = new ArrayList<>();
            String[] bogieData = {
                "B001:Coach-A:150",
                "B002:Coach-B:120",
                "B003:Coach-C:200",
                "B004:Coach-D:100",
                "B005:Coach-E:180",
                "B006:Coach-F:90"
            };

            System.out.println("\n✓ Adding bogies to train...");
            for (String data : bogieData) {
                String[] parts = data.split(":");
                Bogie bogie = new Bogie(parts[0], "Passenger", Integer.parseInt(parts[2]), parts[1]);
                bogies.add(bogie);
                train.addBogie(bogie);
                System.out.println("  ✓ " + bogie.getBogieName() + " - Capacity: " + bogie.getCapacity());
            }

            // Original order
            System.out.println("\n========== SORTING RESULTS ==========");
            System.out.println("\n1. ORIGINAL ORDER:");
            displayBogies(bogies);

            // Sort ascending by capacity
            ArrayList<Bogie> ascendingByCapacity = new ArrayList<>(bogies);
            ascendingByCapacity.sort((b1, b2) -> Integer.compare(b1.getCapacity(), b2.getCapacity()));
            System.out.println("\n2. SORTED ASCENDING BY CAPACITY:");
            displayBogies(ascendingByCapacity);

            // Sort descending by capacity
            ArrayList<Bogie> descendingByCapacity = new ArrayList<>(bogies);
            descendingByCapacity.sort((b1, b2) -> Integer.compare(b2.getCapacity(), b1.getCapacity()));
            System.out.println("\n3. SORTED DESCENDING BY CAPACITY:");
            displayBogies(descendingByCapacity);

            // Sort by name (alphabetically)
            ArrayList<Bogie> sortedByName = new ArrayList<>(bogies);
            sortedByName.sort((b1, b2) -> b1.getBogieName().compareTo(b2.getBogieName()));
            System.out.println("\n4. SORTED BY NAME (ALPHABETICALLY):");
            displayBogies(sortedByName);

            // Sort by ID
            ArrayList<Bogie> sortedById = new ArrayList<>(bogies);
            sortedById.sort((b1, b2) -> b1.getBogieId().compareTo(b2.getBogieId()));
            System.out.println("\n5. SORTED BY ID:");
            displayBogies(sortedById);

            // Multi-criteria sort: Type -> Capacity (descending)
            ArrayList<Bogie> multiSort = new ArrayList<>(bogies);
            multiSort.sort((b1, b2) -> {
                int typeCompare = b1.getBogieType().compareTo(b2.getBogieType());
                if (typeCompare != 0) {
                    return typeCompare;
                }
                return Integer.compare(b2.getCapacity(), b1.getCapacity());
            });
            System.out.println("\n6. MULTI-CRITERIA SORT (Type -> Capacity Descending):");
            displayBogies(multiSort);

            System.out.println("\n✓ UC7 Completed Successfully!");

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static void displayBogies(ArrayList<Bogie> bogies) {
        int index = 1;
        for (Bogie bogie : bogies) {
            System.out.println("  " + index + ". " + bogie.getBogieId() + " - " + 
                               bogie.getBogieName() + " (Capacity: " + bogie.getCapacity() + ")");
            index++;
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
