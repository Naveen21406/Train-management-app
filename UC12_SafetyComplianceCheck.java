import java.util.*;

/**
 * UC12 - Safety Compliance Check
 * Demonstrates safety validation for train operations
 * Data Structure: ArrayList with compliance rules
 */
public class UC12_SafetyComplianceCheck {

    private static class ComplianceRule {
        String ruleName;
        String description;
        boolean passed;
        String details;

        ComplianceRule(String ruleName, String description, boolean passed, String details) {
            this.ruleName = ruleName;
            this.description = description;
            this.passed = passed;
            this.details = details;
        }
    }

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 12: Safety Compliance Check");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Validate train configuration meets safety compliance standards.");
        System.out.println("Data Structure Used: ArrayList with compliance tracking");
        System.out.println("-".repeat(70));

        try {
            // Scenario 1: Compliant train
            System.out.println("\n========== SCENARIO 1: COMPLIANT TRAIN ==========");
            Train compliantTrain = createCompliantTrain("TR001");
            performSafetyCheck(compliantTrain);

            // Scenario 2: Non-compliant train (too many cargo bogies)
            System.out.println("\n========== SCENARIO 2: NON-COMPLIANT TRAIN (Cargo Heavy) ==========");
            Train nonCompliantTrain1 = createNonCompliantTrain_CargoHeavy("TR002");
            performSafetyCheck(nonCompliantTrain1);

            // Scenario 3: Imbalanced train
            System.out.println("\n========== SCENARIO 3: IMBALANCED CONFIGURATION ==========");
            Train imbalancedTrain = createImbalancedTrain("TR003");
            performSafetyCheck(imbalancedTrain);

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static Train createCompliantTrain(String trainId) throws Exception {
        Train train = new Train(trainId, 10);
        train.addBogie(new Bogie("P001", "Passenger", 150, "First Class"));
        train.addBogie(new Bogie("P002", "Passenger", 150, "First Class"));
        train.addBogie(new Bogie("P003", "Passenger", 120, "Second Class"));
        train.addBogie(new Bogie("P004", "Passenger", 120, "Second Class"));
        train.addBogie(new Bogie("C001", "Cargo", 150, "Container"));
        return train;
    }

    private static Train createNonCompliantTrain_CargoHeavy(String trainId) throws Exception {
        Train train = new Train(trainId, 10);
        train.addBogie(new Bogie("C001", "Cargo", 200, "Heavy Container"));
        train.addBogie(new Bogie("C002", "Cargo", 200, "Heavy Container"));
        train.addBogie(new Bogie("C003", "Cargo", 180, "Container"));
        train.addBogie(new Bogie("C004", "Cargo", 180, "Container"));
        train.addBogie(new Bogie("P001", "Passenger", 100, "Coach"));
        return train;
    }

    private static Train createImbalancedTrain(String trainId) throws Exception {
        Train train = new Train(trainId, 8);
        train.addBogie(new Bogie("P001", "Passenger", 100, "Coach A"));
        train.addBogie(new Bogie("P002", "Passenger", 200, "Coach B"));
        train.addBogie(new Bogie("C001", "Cargo", 50, "Small Cargo"));
        return train;
    }

    private static void performSafetyCheck(Train train) {
        ArrayList<ComplianceRule> rules = new ArrayList<>();

        // Rule 1: Minimum bogies
        boolean rule1Pass = train.getTotalBogies() >= 3;
        rules.add(new ComplianceRule(
                "R1: Minimum Bogies",
                "Train must have at least 3 bogies",
                rule1Pass,
                "Current: " + train.getTotalBogies() + " bogies"
        ));

        // Rule 2: Passenger to cargo ratio (at least 50% passenger)
        long passengerCount = train.getBogies().stream()
                .filter(b -> b.getBogieType().equals("Passenger"))
                .count();
        boolean rule2Pass = (passengerCount * 100 / train.getTotalBogies()) >= 40;
        rules.add(new ComplianceRule(
                "R2: Passenger Ratio",
                "Passenger bogies must be at least 40% of total",
                rule2Pass,
                "Current: " + (passengerCount * 100 / train.getTotalBogies()) + "%"
        ));

        // Rule 3: No single bogie exceeds 50% total capacity
        int totalCapacity = train.getBogies().stream()
                .mapToInt(Bogie::getCapacity)
                .sum();
        boolean rule3Pass = train.getBogies().stream()
                .allMatch(b -> b.getCapacity() <= (totalCapacity / 2));
        rules.add(new ComplianceRule(
                "R3: Capacity Balance",
                "No single bogie should exceed 50% of total capacity",
                rule3Pass,
                "Largest bogie: " + train.getBogies().stream()
                        .mapToInt(Bogie::getCapacity)
                        .max()
                        .orElse(0) + " / " + totalCapacity
        ));

        // Rule 4: All bogies have valid IDs
        Set<String> uniqueIds = new HashSet<>();
        train.getBogies().forEach(b -> uniqueIds.add(b.getBogieId()));
        boolean rule4Pass = uniqueIds.size() == train.getTotalBogies();
        rules.add(new ComplianceRule(
                "R4: Unique Identifiers",
                "All bogies must have unique IDs",
                rule4Pass,
                "Unique IDs: " + uniqueIds.size() + ", Total Bogies: " + train.getTotalBogies()
        ));

        // Rule 5: Maximum bogies not exceeded
        boolean rule5Pass = train.getTotalBogies() <= train.getMaxBogies();
        rules.add(new ComplianceRule(
                "R5: Train Capacity",
                "Total bogies must not exceed train max capacity",
                rule5Pass,
                "Current: " + train.getTotalBogies() + " / " + train.getMaxBogies()
        ));

        // Rule 6: No bogies with zero capacity
        boolean rule6Pass = train.getBogies().stream()
                .allMatch(b -> b.getCapacity() > 0);
        rules.add(new ComplianceRule(
                "R6: Positive Capacity",
                "All bogies must have positive capacity",
                rule6Pass,
                "All bogies verified"
        ));

        // Display results
        System.out.println("\nTrain: " + train.getTrainId());
        System.out.println("-".repeat(70));
        System.out.println("Compliance Check Results:");
        System.out.println("");

        for (int i = 0; i < rules.size(); i++) {
            ComplianceRule rule = rules.get(i);
            String status = rule.passed ? "✓ PASS" : "✗ FAIL";
            System.out.println(status + " | " + rule.ruleName);
            System.out.println("       " + rule.description);
            System.out.println("       " + rule.details);
        }

        // Overall compliance status
        boolean allPassed = rules.stream().allMatch(r -> r.passed);
        int passedCount = (int) rules.stream().filter(r -> r.passed).count();
        int totalRules = rules.size();

        System.out.println("\n" + "=".repeat(70));
        System.out.println("Overall Status: " + (allPassed ? "✓ FULLY COMPLIANT" : "✗ NON-COMPLIANT"));
        System.out.println("Compliance Score: " + passedCount + "/" + totalRules + " (" + 
                           String.format("%.1f%%", passedCount * 100.0 / totalRules) + ")");
        System.out.println("=".repeat(70));

        // Train details
        System.out.println("\nTrain Configuration:");
        System.out.println("  Total Bogies: " + train.getTotalBogies());
        System.out.println("  Total Capacity: " + totalCapacity + " seats");
        System.out.println("  Passenger Bogies: " + passengerCount);
        System.out.println("  Cargo Bogies: " + train.getBogies().stream()
                .filter(b -> b.getBogieType().equals("Cargo"))
                .count());
    }

    public static void main(String[] args) {
        execute();
    }
}
