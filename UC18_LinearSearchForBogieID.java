import java.util.*;

/**
 * UC18 - Linear Search for Bogie ID
 * Demonstrates linear search algorithm with performance analysis
 * Data Structure: ArrayList with linear search
 */
public class UC18_LinearSearchForBogieID {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 18: Linear Search for Bogie ID");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Implement and demonstrate linear search on bogie IDs.");
        System.out.println("Data Structure Used: ArrayList with linear search algorithm");
        System.out.println("-".repeat(70));

        try {
            // Create train with bogies
            Train train = createTrainWithBogies("TR018", 50);
            ArrayList<Bogie> bogies = train.getBogies();

            System.out.println("\n✓ Train created with " + bogies.size() + " bogies");

            // Test Case 1: Search for existing bogies
            System.out.println("\n========== TEST 1: SEARCHING EXISTING BOGIES ==========");
            String[] searchQueries = {"B010", "B025", "B045", "B001", "B050"};

            for (String bogieId : searchQueries) {
                linearSearchBogieWithStats(bogies, bogieId);
            }

            // Test Case 2: Search for non-existing bogies
            System.out.println("\n========== TEST 2: SEARCHING NON-EXISTING BOGIES ==========");
            String[] nonExistingIds = {"B100", "B999", "X001", "INVALID"};

            for (String bogieId : nonExistingIds) {
                linearSearchBogieWithStats(bogies, bogieId);
            }

            // Test Case 3: Performance analysis
            System.out.println("\n========== TEST 3: PERFORMANCE ANALYSIS ==========");
            performanceAnalysis(bogies);

            // Test Case 4: Search with conditions
            System.out.println("\n========== TEST 4: CONDITIONAL SEARCH ==========");
            conditionalSearch(bogies);

            // Test Case 5: Pattern-based search
            System.out.println("\n========== TEST 5: PATTERN-BASED SEARCH ==========");
            patternBasedSearch(bogies);

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static Train createTrainWithBogies(String trainId, int bogieCount) throws Exception {
        Train train = new Train(trainId, bogieCount);

        for (int i = 1; i <= bogieCount; i++) {
            String bogieType = (i % 5 == 0) ? "Cargo" : "Passenger";
            int capacity = 50 + (i % 150);
            String bogieName = bogieType + "-" + i;
            Bogie bogie = new Bogie("B" + String.format("%03d", i), bogieType, capacity, bogieName);
            train.addBogie(bogie);
        }

        return train;
    }

    private static void linearSearchBogieWithStats(ArrayList<Bogie> bogies, String searchId) {
        long startTime = System.nanoTime();
        int comparisons = 0;
        int foundIndex = -1;
        Bogie foundBogie = null;

        for (int i = 0; i < bogies.size(); i++) {
            comparisons++;
            if (bogies.get(i).getBogieId().equals(searchId)) {
                foundIndex = i;
                foundBogie = bogies.get(i);
                break;
            }
        }

        long endTime = System.nanoTime();
        long timeNanos = endTime - startTime;

        System.out.println("Search: " + searchId);
        if (foundBogie != null) {
            System.out.println("  ✓ FOUND at index " + foundIndex + " (Position " + (foundIndex + 1) + ")");
            System.out.println("    Details: " + foundBogie.getBogieName() + " (Capacity: " + 
                             foundBogie.getCapacity() + ")");
        } else {
            System.out.println("  ✗ NOT FOUND");
        }
        System.out.println("  Comparisons: " + comparisons);
        System.out.println("  Time: " + timeNanos + " ns");
    }

    private static void performanceAnalysis(ArrayList<Bogie> bogies) {
        System.out.println("Performance Metrics:");
        System.out.println("  Dataset Size: " + bogies.size() + " bogies");

        // Best case: First element
        long startTime = System.nanoTime();
        int comparisonsBest = 0;
        for (Bogie bogie : bogies) {
            comparisonsBest++;
            if (bogie.getBogieId().equals("B001")) break;
        }
        long timeBest = System.nanoTime() - startTime;

        System.out.println("\n  Best Case (First Element B001):");
        System.out.println("    Comparisons: " + comparisonsBest);
        System.out.println("    Time: " + timeBest + " ns");

        // Worst case: Last element
        startTime = System.nanoTime();
        int comparisonsWorst = 0;
        String lastBogieId = "B" + String.format("%03d", bogies.size());
        for (Bogie bogie : bogies) {
            comparisonsWorst++;
            if (bogie.getBogieId().equals(lastBogieId)) break;
        }
        long timeWorst = System.nanoTime() - startTime;

        System.out.println("\n  Worst Case (Last Element " + lastBogieId + "):");
        System.out.println("    Comparisons: " + comparisonsWorst);
        System.out.println("    Time: " + timeWorst + " ns");

        // Average case: Middle element
        startTime = System.nanoTime();
        int comparisonsAvg = 0;
        String middleBogieId = "B" + String.format("%03d", bogies.size() / 2);
        for (Bogie bogie : bogies) {
            comparisonsAvg++;
            if (bogie.getBogieId().equals(middleBogieId)) break;
        }
        long timeAvg = System.nanoTime() - startTime;

        System.out.println("\n  Average Case (Middle Element " + middleBogieId + "):");
        System.out.println("    Comparisons: " + comparisonsAvg);
        System.out.println("    Time: " + timeAvg + " ns");

        // Time Complexity
        System.out.println("\n  Time Complexity Analysis:");
        System.out.println("    Best Case: O(1) - " + comparisonsBest + " comparison(s)");
        System.out.println("    Average Case: O(n) - ~" + comparisonsAvg + " comparisons");
        System.out.println("    Worst Case: O(n) - " + comparisonsWorst + " comparisons");
        System.out.println("    Space Complexity: O(1) - No extra space");
    }

    private static void conditionalSearch(ArrayList<Bogie> bogies) {
        System.out.println("Conditional Search Examples:");

        // Find first Cargo bogie
        System.out.println("\n  1. Search for first CARGO bogie:");
        int index = 0;
        for (int i = 0; i < bogies.size(); i++) {
            if (bogies.get(i).getBogieType().equals("Cargo")) {
                index = i;
                System.out.println("    ✓ Found at index " + i + ": " + bogies.get(i).getBogieId());
                break;
            }
        }

        // Find first bogie with capacity > 150
        System.out.println("\n  2. Search for first bogie with capacity > 150:");
        for (int i = 0; i < bogies.size(); i++) {
            if (bogies.get(i).getCapacity() > 150) {
                System.out.println("    ✓ Found at index " + i + ": " + bogies.get(i).getBogieId() + 
                                 " (Capacity: " + bogies.get(i).getCapacity() + ")");
                break;
            }
        }

        // Count all Passenger bogies
        System.out.println("\n  3. Count all PASSENGER bogies:");
        int passengerCount = 0;
        for (Bogie bogie : bogies) {
            if (bogie.getBogieType().equals("Passenger")) {
                passengerCount++;
            }
        }
        System.out.println("    ✓ Total Passenger bogies: " + passengerCount);

        // Find bogie with minimum capacity
        System.out.println("\n  4. Find bogie with minimum capacity:");
        Bogie minBogie = bogies.get(0);
        for (Bogie bogie : bogies) {
            if (bogie.getCapacity() < minBogie.getCapacity()) {
                minBogie = bogie;
            }
        }
        System.out.println("    ✓ " + minBogie.getBogieId() + " with capacity " + minBogie.getCapacity());

        // Find bogie with maximum capacity
        System.out.println("\n  5. Find bogie with maximum capacity:");
        Bogie maxBogie = bogies.get(0);
        for (Bogie bogie : bogies) {
            if (bogie.getCapacity() > maxBogie.getCapacity()) {
                maxBogie = bogie;
            }
        }
        System.out.println("    ✓ " + maxBogie.getBogieId() + " with capacity " + maxBogie.getCapacity());
    }

    private static void patternBasedSearch(ArrayList<Bogie> bogies) {
        System.out.println("Pattern-Based Search:");

        // Search for bogies starting with 'B0'
        System.out.println("\n  1. Bogies with ID matching pattern 'B0*':");
        int count = 0;
        for (Bogie bogie : bogies) {
            if (bogie.getBogieId().startsWith("B0")) {
                count++;
                if (count <= 5) { // Display first 5
                    System.out.println("    - " + bogie.getBogieId());
                }
            }
        }
        System.out.println("    Total matches: " + count);

        // Search for bogies with names containing 'Passenger'
        System.out.println("\n  2. Bogies with name containing pattern:");
        count = 0;
        String pattern = "Passenger";
        for (Bogie bogie : bogies) {
            if (bogie.getBogieName().contains(pattern)) {
                count++;
                if (count <= 3) {
                    System.out.println("    - " + bogie.getBogieId() + ": " + bogie.getBogieName());
                }
            }
        }
        System.out.println("    Total matches: " + count);

        // Search by ID range
        System.out.println("\n  3. Bogies in ID range (B010 to B020):");
        count = 0;
        for (Bogie bogie : bogies) {
            String id = bogie.getBogieId();
            if (id.compareTo("B010") >= 0 && id.compareTo("B020") <= 0) {
                System.out.println("    - " + bogie.getBogieId());
                count++;
            }
        }
        System.out.println("    Total in range: " + count);
    }

    public static void main(String[] args) {
        execute();
    }
}
