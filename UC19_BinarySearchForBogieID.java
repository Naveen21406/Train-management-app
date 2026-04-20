import java.util.*;

/**
 * UC19 - Binary Search for Bogie ID
 * Demonstrates binary search algorithm with performance comparison to linear search
 * Data Structure: ArrayList with binary search
 */
public class UC19_BinarySearchForBogieID {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 19: Binary Search for Bogie ID");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Implement and compare binary search with linear search.");
        System.out.println("Data Structure Used: ArrayList (sorted) with binary search");
        System.out.println("-".repeat(70));

        try {
            // Create train with bogies
            Train train = createTrainWithBogies("TR019", 128);
            ArrayList<Bogie> bogies = train.getBogies();

            System.out.println("\n✓ Train created with " + bogies.size() + " bogies");

            // Sort bogies by ID for binary search
            ArrayList<Bogie> sortedBogies = new ArrayList<>(bogies);
            sortedBogies.sort((b1, b2) -> b1.getBogieId().compareTo(b2.getBogieId()));

            System.out.println("✓ Bogies sorted by ID for binary search");

            // Test Case 1: Compare search methods
            System.out.println("\n========== TEST 1: LINEAR VS BINARY SEARCH COMPARISON ==========");
            String[] searchQueries = {"B001", "B064", "B128", "B032", "B096"};

            for (String bogieId : searchQueries) {
                compareSearchMethods(bogies, sortedBogies, bogieId);
            }

            // Test Case 2: Performance analysis
            System.out.println("\n========== TEST 2: PERFORMANCE ANALYSIS ==========");
            performanceAnalysis(bogies, sortedBogies);

            // Test Case 3: Binary search edge cases
            System.out.println("\n========== TEST 3: EDGE CASES ==========");
            testEdgeCases(sortedBogies);

            // Test Case 4: Range search using binary search
            System.out.println("\n========== TEST 4: RANGE SEARCH ==========");
            rangeSearch(sortedBogies);

            // Test Case 5: Implementation details
            System.out.println("\n========== TEST 5: ALGORITHM WALKTHROUGH ==========");
            algorithmWalkthrough(sortedBogies, "B032");

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

    private static void compareSearchMethods(ArrayList<Bogie> unsorted, 
                                             ArrayList<Bogie> sorted, 
                                             String searchId) {
        System.out.println("\nSearch Query: " + searchId);
        System.out.println("-".repeat(50));

        // Linear search on unsorted
        long linearStart = System.nanoTime();
        int linearComparisons = 0;
        Bogie linearResult = null;
        for (Bogie bogie : unsorted) {
            linearComparisons++;
            if (bogie.getBogieId().equals(searchId)) {
                linearResult = bogie;
                break;
            }
        }
        long linearTime = System.nanoTime() - linearStart;

        // Binary search on sorted
        long binaryStart = System.nanoTime();
        SearchResult binaryResult = binarySearch(sorted, searchId);
        long binaryTime = System.nanoTime() - binaryStart;

        // Display results
        System.out.println("LINEAR SEARCH (Unsorted Data):");
        if (linearResult != null) {
            System.out.println("  ✓ FOUND: " + linearResult.getBogieId() + " - " + linearResult.getBogieName());
        } else {
            System.out.println("  ✗ NOT FOUND");
        }
        System.out.println("  Comparisons: " + linearComparisons);
        System.out.println("  Time: " + linearTime + " ns");

        System.out.println("\nBINARY SEARCH (Sorted Data):");
        if (binaryResult.found) {
            System.out.println("  ✓ FOUND at index " + binaryResult.index);
            System.out.println("  Bogie: " + sorted.get(binaryResult.index).getBogieId());
        } else {
            System.out.println("  ✗ NOT FOUND");
        }
        System.out.println("  Comparisons: " + binaryResult.comparisons);
        System.out.println("  Time: " + binaryTime + " ns");

        // Comparison
        System.out.println("\nCOMPARISON:");
        System.out.println("  Linear: " + linearComparisons + " comparisons vs Binary: " + binaryResult.comparisons + " comparisons");
        System.out.println("  Speedup: " + String.format("%.2f", (double) linearComparisons / binaryResult.comparisons) + "x");
        System.out.println("  Time saved: " + (linearTime - binaryTime) + " ns");
    }

    private static class SearchResult {
        int index;
        int comparisons;
        boolean found;

        SearchResult(int index, int comparisons, boolean found) {
            this.index = index;
            this.comparisons = comparisons;
            this.found = found;
        }
    }

    private static SearchResult binarySearch(ArrayList<Bogie> sortedBogies, String target) {
        int left = 0;
        int right = sortedBogies.size() - 1;
        int comparisons = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            comparisons++;

            String midId = sortedBogies.get(mid).getBogieId();
            int compareResult = midId.compareTo(target);

            if (compareResult == 0) {
                return new SearchResult(mid, comparisons, true);
            } else if (compareResult < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return new SearchResult(-1, comparisons, false);
    }

    private static void performanceAnalysis(ArrayList<Bogie> unsorted, ArrayList<Bogie> sorted) {
        System.out.println("Performance Metrics:");
        System.out.println("  Dataset Size: " + sorted.size() + " bogies");

        // Linear search average
        long linearTotalComparisons = 0;
        long linearTotalTime = 0;
        int sampleSize = 10;

        System.out.println("\nLinear Search (Average of " + sampleSize + " queries):");
        for (int i = 0; i < sampleSize; i++) {
            String searchId = "B" + String.format("%03d", (i + 1) * (sorted.size() / sampleSize));
            
            long start = System.nanoTime();
            int comparisons = 0;
            for (Bogie bogie : unsorted) {
                comparisons++;
                if (bogie.getBogieId().equals(searchId)) break;
            }
            long time = System.nanoTime() - start;

            linearTotalComparisons += comparisons;
            linearTotalTime += time;
        }

        double avgLinearComparisons = linearTotalComparisons / (double) sampleSize;
        double avgLinearTime = linearTotalTime / (double) sampleSize;

        System.out.println("  Avg Comparisons: " + String.format("%.1f", avgLinearComparisons));
        System.out.println("  Avg Time: " + String.format("%.1f", avgLinearTime) + " ns");

        // Binary search average
        long binaryTotalComparisons = 0;
        long binaryTotalTime = 0;

        System.out.println("\nBinary Search (Average of " + sampleSize + " queries):");
        for (int i = 0; i < sampleSize; i++) {
            String searchId = "B" + String.format("%03d", (i + 1) * (sorted.size() / sampleSize));

            long start = System.nanoTime();
            SearchResult result = binarySearch(sorted, searchId);
            long time = System.nanoTime() - start;

            binaryTotalComparisons += result.comparisons;
            binaryTotalTime += time;
        }

        double avgBinaryComparisons = binaryTotalComparisons / (double) sampleSize;
        double avgBinaryTime = binaryTotalTime / (double) sampleSize;

        System.out.println("  Avg Comparisons: " + String.format("%.1f", avgBinaryComparisons));
        System.out.println("  Avg Time: " + String.format("%.1f", avgBinaryTime) + " ns");

        // Time Complexity
        System.out.println("\nTime Complexity Analysis:");
        System.out.println("  Linear Search: O(n) - Avg: " + String.format("%.1f", avgLinearComparisons) + " comparisons");
        System.out.println("  Binary Search: O(log n) - Avg: " + String.format("%.1f", avgBinaryComparisons) + " comparisons");
        System.out.println("  Speedup: " + String.format("%.1f", avgLinearComparisons / avgBinaryComparisons) + "x faster");

        int n = sorted.size();
        int theoreticalBinary = (int) Math.ceil(Math.log(n) / Math.log(2));
        System.out.println("\n  Theoretical Max Comparisons:");
        System.out.println("    Linear: " + n);
        System.out.println("    Binary: " + theoreticalBinary + " (log₂(" + n + "))");
    }

    private static void testEdgeCases(ArrayList<Bogie> sortedBogies) {
        System.out.println("Edge Case Testing:");

        // First element
        System.out.println("\n  1. Search for FIRST element (" + sortedBogies.get(0).getBogieId() + "):");
        SearchResult result = binarySearch(sortedBogies, sortedBogies.get(0).getBogieId());
        System.out.println("     Comparisons: " + result.comparisons + " | Found: " + result.found);

        // Last element
        String lastId = sortedBogies.get(sortedBogies.size() - 1).getBogieId();
        System.out.println("\n  2. Search for LAST element (" + lastId + "):");
        result = binarySearch(sortedBogies, lastId);
        System.out.println("     Comparisons: " + result.comparisons + " | Found: " + result.found);

        // Middle element
        String middleId = sortedBogies.get(sortedBogies.size() / 2).getBogieId();
        System.out.println("\n  3. Search for MIDDLE element (" + middleId + "):");
        result = binarySearch(sortedBogies, middleId);
        System.out.println("     Comparisons: " + result.comparisons + " | Found: " + result.found);

        // Non-existent element
        System.out.println("\n  4. Search for NON-EXISTENT element (B999):");
        result = binarySearch(sortedBogies, "B999");
        System.out.println("     Comparisons: " + result.comparisons + " | Found: " + result.found);
    }

    private static void rangeSearch(ArrayList<Bogie> sortedBogies) {
        System.out.println("Range Search Examples:");

        // Find range B010 to B020
        String start = "B010";
        String end = "B020";

        System.out.println("\n  1. Find all bogies between " + start + " and " + end + ":");
        
        int startIdx = -1;
        int endIdx = -1;

        // Find start index
        for (int i = 0; i < sortedBogies.size(); i++) {
            if (sortedBogies.get(i).getBogieId().compareTo(start) >= 0) {
                startIdx = i;
                break;
            }
        }

        // Find end index
        for (int i = sortedBogies.size() - 1; i >= 0; i--) {
            if (sortedBogies.get(i).getBogieId().compareTo(end) <= 0) {
                endIdx = i;
                break;
            }
        }

        if (startIdx != -1 && endIdx != -1 && startIdx <= endIdx) {
            System.out.println("    Found " + (endIdx - startIdx + 1) + " bogies:");
            for (int i = startIdx; i <= endIdx; i++) {
                System.out.println("      - " + sortedBogies.get(i).getBogieId());
            }
        } else {
            System.out.println("    No bogies found in range");
        }
    }

    private static void algorithmWalkthrough(ArrayList<Bogie> sortedBogies, String target) {
        System.out.println("Algorithm Walkthrough for searching '" + target + "':");
        System.out.println("Total bogies: " + sortedBogies.size());

        int left = 0;
        int right = sortedBogies.size() - 1;
        int step = 1;

        System.out.println("\nStep-by-step execution:");

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midId = sortedBogies.get(mid).getBogieId();

            System.out.println("\n  Step " + step + ":");
            System.out.println("    Left: " + left + " (" + sortedBogies.get(left).getBogieId() + 
                             ") | Right: " + right + " (" + sortedBogies.get(right).getBogieId() + ")");
            System.out.println("    Mid: " + mid + " (" + midId + ")");

            int compareResult = midId.compareTo(target);

            if (compareResult == 0) {
                System.out.println("    Result: ✓ FOUND at index " + mid);
                System.out.println("    Total steps: " + step);
                return;
            } else if (compareResult < 0) {
                System.out.println("    Decision: '" + midId + "' < '" + target + "' → Search RIGHT half");
                left = mid + 1;
            } else {
                System.out.println("    Decision: '" + midId + "' > '" + target + "' → Search LEFT half");
                right = mid - 1;
            }

            step++;
        }

        System.out.println("\n  Result: ✗ NOT FOUND");
        System.out.println("  Total steps: " + step);
    }

    public static void main(String[] args) {
        execute();
    }
}
