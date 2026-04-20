import java.util.*;

/**
 * UC11 - Validate Train ID & Cargo Configuration
 * Demonstrates validation logic for train integrity
 * Data Structure: ArrayList with validation methods
 */
public class UC11_ValidateTrainIDAndCargo {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 11: Validate Train ID & Cargo Configuration");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Validate train configuration for operational readiness.");
        System.out.println("Data Structure Used: ArrayList with custom validation");
        System.out.println("-".repeat(70));

        try {
            // Test Case 1: Valid train configuration
            System.out.println("\n========== TEST CASE 1: VALID TRAIN CONFIGURATION ==========");
            validateAndDisplayTrain("TR001", createValidTrain("TR001"));

            // Test Case 2: Train with mixed bogies
            System.out.println("\n========== TEST CASE 2: MIXED BOGIES CONFIGURATION ==========");
            validateAndDisplayTrain("TR002", createMixedBogiesTrain("TR002"));

            // Test Case 3: Minimal train
            System.out.println("\n========== TEST CASE 3: MINIMAL CONFIGURATION ==========");
            validateAndDisplayTrain("TR003", createMinimalTrain("TR003"));

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static Train createValidTrain(String trainId) throws Exception {
        Train train = new Train(trainId, 8);
        train.addBogie(new Bogie("P001", "Passenger", 150, "First Class"));
        train.addBogie(new Bogie("P002", "Passenger", 120, "Second Class"));
        train.addBogie(new Bogie("C001", "Cargo", 200, "Cargo Container"));
        return train;
    }

    private static Train createMixedBogiesTrain(String trainId) throws Exception {
        Train train = new Train(trainId, 10);
        train.addBogie(new Bogie("P001", "Passenger", 150, "FirstClass"));
        train.addBogie(new Bogie("P002", "Passenger", 120, "SecondClass"));
        train.addBogie(new Bogie("C001", "Cargo", 200, "Container"));
        train.addBogie(new Bogie("C002", "Cargo", 180, "Refrigerated"));
        train.addBogie(new Bogie("M001", "Mail", 100, "PostalCar"));
        return train;
    }

    private static Train createMinimalTrain(String trainId) throws Exception {
        Train train = new Train(trainId, 5);
        train.addBogie(new Bogie("B001", "Passenger", 100, "Coach"));
        return train;
    }

    private static void validateAndDisplayTrain(String trainId, Train train) {
        System.out.println("\nTrain: " + trainId);
        System.out.println("-".repeat(50));

        // Validation checks
        ArrayList<String> validationResults = new ArrayList<>();

        // Check 1: Valid Train ID format
        if (isValidTrainId(train.getTrainId())) {
            validationResults.add("✓ Train ID Format: VALID");
        } else {
            validationResults.add("✗ Train ID Format: INVALID");
        }

        // Check 2: Minimum bogies
        if (train.getTotalBogies() >= 1) {
            validationResults.add("✓ Minimum Bogies: " + train.getTotalBogies() + " (Required: >= 1)");
        } else {
            validationResults.add("✗ Minimum Bogies: INSUFFICIENT");
        }

        // Check 3: Bogie capacity validation
        boolean allCapacitiesValid = train.getBogies().stream()
                .allMatch(b -> b.getCapacity() > 0);
        if (allCapacitiesValid) {
            validationResults.add("✓ All Bogie Capacities: VALID");
        } else {
            validationResults.add("✗ Some Bogie Capacities: INVALID");
        }

        // Check 4: No duplicate IDs
        Set<String> uniqueIds = new HashSet<>();
        for (Bogie bogie : train.getBogies()) {
            uniqueIds.add(bogie.getBogieId());
        }
        if (uniqueIds.size() == train.getTotalBogies()) {
            validationResults.add("✓ Unique Bogie IDs: VALIDATED");
        } else {
            validationResults.add("✗ Duplicate Bogie IDs: DETECTED");
        }

        // Check 5: Cargo configuration (if cargo present)
        ArrayList<Bogie> cargoBogies = new ArrayList<>();
        for (Bogie bogie : train.getBogies()) {
            if (bogie.getBogieType().equals("Cargo")) {
                cargoBogies.add(bogie);
            }
        }
        if (cargoBogies.isEmpty()) {
            validationResults.add("⚠ Cargo Bogies: NONE (Optional)");
        } else {
            validationResults.add("✓ Cargo Bogies: " + cargoBogies.size() + " (Valid)");
            int totalCargoCapacity = cargoBogies.stream()
                    .mapToInt(Bogie::getCapacity)
                    .sum();
            validationResults.add("✓ Total Cargo Capacity: " + totalCargoCapacity);
        }

        // Check 6: Bogie type diversity
        Set<String> bogieTypes = new HashSet<>();
        for (Bogie bogie : train.getBogies()) {
            bogieTypes.add(bogie.getBogieType());
        }
        validationResults.add("✓ Bogie Types: " + bogieTypes);

        // Display results
        System.out.println("\nValidation Results:");
        for (String result : validationResults) {
            System.out.println("  " + result);
        }

        // Overall status
        boolean allPassed = validationResults.stream()
                .noneMatch(r -> r.startsWith("✗"));
        System.out.println("\nOverall Status: " + (allPassed ? "✓ PASSED" : "⚠ WARNINGS/FAILURES"));

        // Display bogies
        System.out.println("\nBogies Configuration:");
        int index = 1;
        for (Bogie bogie : train.getBogies()) {
            System.out.println("  " + index + ". [" + bogie.getBogieId() + "] " + 
                               bogie.getBogieName() + " (Type: " + bogie.getBogieType() + 
                               ", Capacity: " + bogie.getCapacity() + ")");
            index++;
        }
    }

    private static boolean isValidTrainId(String trainId) {
        // Valid format: TR followed by 3-4 digits
        return trainId != null && trainId.matches("^TR\\d{3,4}$");
    }

    public static void main(String[] args) {
        execute();
    }
}
