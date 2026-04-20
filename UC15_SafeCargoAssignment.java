import java.util.*;

/**
 * UC15 - Safe Cargo Assignment
 * Demonstrates cargo allocation with weight and capacity constraints
 * Data Structure: ArrayList with HashMap for cargo tracking
 */
public class UC15_SafeCargoAssignment {

    private static class CargoItem {
        String cargoId;
        String description;
        int weight; // in units (e.g., kg)
        int volume; // in units (e.g., m³)

        CargoItem(String cargoId, String description, int weight, int volume) {
            this.cargoId = cargoId;
            this.description = description;
            this.weight = weight;
            this.volume = volume;
        }

        @Override
        public String toString() {
            return cargoId + " (" + description + "): Weight=" + weight + ", Volume=" + volume;
        }
    }

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 15: Safe Cargo Assignment");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Assign cargo to bogies safely, respecting capacity constraints.");
        System.out.println("Data Structure Used: ArrayList with HashMap cargo tracking");
        System.out.println("-".repeat(70));

        try {
            Train train = new Train("TR015", 10);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // Setup: Add cargo bogies
            System.out.println("\n✓ Setting up cargo bogies...");
            ArrayList<Bogie> cargoBogies = new ArrayList<>();

            String[] cargoBogieDef = {
                "CB001:Container-A:300",
                "CB002:Container-B:300",
                "CB003:Flat-Car:400",
                "CB004:Refrigerated:250"
            };

            for (String def : cargoBogieDef) {
                String[] parts = def.split(":");
                Bogie bogie = new Bogie(parts[0], "Cargo", Integer.parseInt(parts[2]), parts[1]);
                cargoBogies.add(bogie);
                train.addBogie(bogie);
                System.out.println("  ✓ " + parts[0] + " - Capacity: " + parts[2]);
            }

            // Create cargo items
            ArrayList<CargoItem> cargoInventory = createCargoInventory();

            System.out.println("\n✓ Cargo inventory created (" + cargoInventory.size() + " items)");

            // Assignment scenarios
            System.out.println("\n========== SCENARIO 1: SAFE ASSIGNMENTS ==========");
            assignCargoSafely(cargoBogies, cargoInventory);

            System.out.println("\n========== SCENARIO 2: OVERLOAD ATTEMPTS ==========");
            demonstrateOverloadPrevention(cargoBogies, cargoInventory);

            System.out.println("\n========== SCENARIO 3: OPTIMIZED PACKING ==========");
            demonstrateOptimizedPacking(cargoBogies, cargoInventory);

            // Display final status
            displayCargoStatus(train);

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static ArrayList<CargoItem> createCargoInventory() {
        ArrayList<CargoItem> inventory = new ArrayList<>();
        inventory.add(new CargoItem("C001", "Electronics", 150, 50));
        inventory.add(new CargoItem("C002", "Textiles", 200, 100));
        inventory.add(new CargoItem("C003", "Machinery", 280, 120));
        inventory.add(new CargoItem("C004", "Chemicals", 180, 60));
        inventory.add(new CargoItem("C005", "Food Items", 100, 80));
        inventory.add(new CargoItem("C006", "Steel Coils", 300, 100));
        inventory.add(new CargoItem("C007", "Cement Bags", 250, 90));
        inventory.add(new CargoItem("C008", "Wood Products", 120, 150));
        return inventory;
    }

    private static void assignCargoSafely(ArrayList<Bogie> cargoBogies, ArrayList<CargoItem> inventory) {
        System.out.println("Assigning cargo with safety constraints:");

        // Track assignments
        HashMap<String, ArrayList<CargoItem>> bogieAssignments = new HashMap<>();
        HashMap<String, Integer> bogieWeights = new HashMap<>();

        for (Bogie bogie : cargoBogies) {
            bogieAssignments.put(bogie.getBogieId(), new ArrayList<>());
            bogieWeights.put(bogie.getBogieId(), 0);
        }

        // Assignment logic with constraints
        for (CargoItem item : inventory) {
            boolean assigned = false;

            // Try to assign to first available bogie
            for (Bogie bogie : cargoBogies) {
                int currentWeight = bogieWeights.get(bogie.getBogieId());
                int availableCapacity = bogie.getCapacity() - currentWeight;

                // Check if cargo fits
                if (item.weight <= availableCapacity && item.weight <= 0.8 * bogie.getCapacity()) {
                    bogieAssignments.get(bogie.getBogieId()).add(item);
                    bogieWeights.put(bogie.getBogieId(), currentWeight + item.weight);
                    System.out.println("  ✓ " + item + " → " + bogie.getBogieId());
                    assigned = true;
                    break;
                }
            }

            if (!assigned) {
                System.out.println("  ⚠ " + item.cargoId + " - No suitable bogie found");
            }
        }

        // Display assignments
        System.out.println("\nAssignment Summary:");
        for (Map.Entry<String, ArrayList<CargoItem>> entry : bogieAssignments.entrySet()) {
            String bogieId = entry.getKey();
            ArrayList<CargoItem> items = entry.getValue();
            int weight = bogieWeights.get(bogieId);
            int capacity = findBogieCapacity(cargoBogies, bogieId);

            System.out.println("\n  " + bogieId + " (" + weight + "/" + capacity + " capacity used):");
            if (items.isEmpty()) {
                System.out.println("    (No cargo assigned)");
            } else {
                for (CargoItem item : items) {
                    System.out.println("    - " + item.cargoId);
                }
            }
        }
    }

    private static void demonstrateOverloadPrevention(ArrayList<Bogie> cargoBogies, 
                                                      ArrayList<CargoItem> inventory) {
        System.out.println("Attempting to overload cargo bogies:");

        Bogie testBogie = cargoBogies.get(0);
        System.out.println("\nUsing bogie: " + testBogie.getBogieId() + " (Capacity: " + testBogie.getCapacity() + ")");

        // Safe assignment
        System.out.println("  ✓ Assigning C001 (150 weight)...");
        System.out.println("    Available: " + testBogie.getCapacity() + ", Requested: 150");
        if (150 <= testBogie.getCapacity()) {
            System.out.println("    Result: ✓ ACCEPTED");
        }

        // Risky assignment
        System.out.println("  ✓ Attempting to assign C006 (300 weight)...");
        System.out.println("    Available: " + (testBogie.getCapacity() - 150) + ", Requested: 300");
        if (300 <= (testBogie.getCapacity() - 150)) {
            System.out.println("    Result: ✓ ACCEPTED");
        } else {
            System.out.println("    Result: ✗ REJECTED (Exceeds capacity)");
        }

        // Dangerous assignment
        System.out.println("  ✓ Attempting to assign entire bogie weight in one item...");
        int itemWeight = testBogie.getCapacity() + 100;
        System.out.println("    Requested: " + itemWeight + ", Capacity: " + testBogie.getCapacity());
        System.out.println("    Result: ✗ REJECTED (Exceeds capacity)");

        // Apply safety margin
        System.out.println("\n  Note: 20% safety margin applied (max 80% of capacity)");
        System.out.println("    Effective Capacity: " + (int)(testBogie.getCapacity() * 0.8));
    }

    private static void demonstrateOptimizedPacking(ArrayList<Bogie> cargoBogies, 
                                                    ArrayList<CargoItem> inventory) {
        System.out.println("Optimized cargo packing strategy:");

        // Sort by weight for better packing
        ArrayList<CargoItem> sortedByWeight = new ArrayList<>(inventory);
        sortedByWeight.sort((c1, c2) -> Integer.compare(c2.weight, c1.weight)); // Descending

        System.out.println("\nCargo sorted by weight (descending):");
        for (CargoItem item : sortedByWeight) {
            System.out.println("  - " + item);
        }

        // First-Fit Decreasing algorithm
        System.out.println("\nFirst-Fit Decreasing Packing:");
        HashMap<String, Integer> bogieWeights = new HashMap<>();
        for (Bogie bogie : cargoBogies) {
            bogieWeights.put(bogie.getBogieId(), 0);
        }

        for (CargoItem item : sortedByWeight) {
            for (Bogie bogie : cargoBogies) {
                int currentWeight = bogieWeights.get(bogie.getBogieId());
                int maxWeight = (int)(bogie.getCapacity() * 0.8);

                if (item.weight + currentWeight <= maxWeight) {
                    bogieWeights.put(bogie.getBogieId(), currentWeight + item.weight);
                    System.out.println("  ✓ " + item.cargoId + " → " + bogie.getBogieId() + 
                                     " (Total: " + (currentWeight + item.weight) + "/" + maxWeight + ")");
                    break;
                }
            }
        }
    }

    private static int findBogieCapacity(ArrayList<Bogie> bogies, String bogieId) {
        for (Bogie bogie : bogies) {
            if (bogie.getBogieId().equals(bogieId)) {
                return bogie.getCapacity();
            }
        }
        return 0;
    }

    private static void displayCargoStatus(Train train) {
        System.out.println("\n========== FINAL TRAIN STATUS ==========");
        System.out.println("Train: " + train.getTrainId());
        System.out.println("Total Bogies: " + train.getTotalBogies());
        System.out.println("Cargo Bogies: " + train.getBogies().stream()
                .filter(b -> b.getBogieType().equals("Cargo"))
                .count());
        System.out.println("Total Cargo Capacity: " + train.getBogies().stream()
                .filter(b -> b.getBogieType().equals("Cargo"))
                .mapToInt(Bogie::getCapacity)
                .sum());
    }

    public static void main(String[] args) {
        execute();
    }
}
