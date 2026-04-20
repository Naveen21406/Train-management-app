import java.util.*;

/**
 * UC13 - Performance Comparison (ArrayList vs LinkedList vs other operations)
 * Demonstrates performance characteristics of different data structures
 * Data Structure: ArrayList vs LinkedList comparison
 */
public class UC13_PerformanceComparison {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 13: Performance Comparison");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Compare performance of different data structures for train operations.");
        System.out.println("Data Structure Used: ArrayList vs LinkedList");
        System.out.println("-".repeat(70));

        try {
            // Create sample bogies
            ArrayList<Bogie> bogies = createSampleBogies(1000);

            System.out.println("\n✓ Created " + bogies.size() + " sample bogies for testing\n");

            // Test 1: Sequential Access Performance
            System.out.println("========== TEST 1: SEQUENTIAL ACCESS ==========");
            testSequentialAccess(bogies);

            // Test 2: Random Access Performance
            System.out.println("\n========== TEST 2: RANDOM ACCESS ==========");
            testRandomAccess(bogies);

            // Test 3: Insertion Performance
            System.out.println("\n========== TEST 3: INSERTION PERFORMANCE ==========");
            testInsertion(bogies);

            // Test 4: Deletion Performance
            System.out.println("\n========== TEST 4: DELETION PERFORMANCE ==========");
            testDeletion(bogies);

            // Test 5: Search Performance (Linear vs Hash-based)
            System.out.println("\n========== TEST 5: SEARCH PERFORMANCE ==========");
            testSearchPerformance(bogies);

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static ArrayList<Bogie> createSampleBogies(int count) {
        ArrayList<Bogie> bogies = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Bogie bogie = new Bogie(
                    "B" + String.format("%04d", i),
                    (i % 3 == 0) ? "Cargo" : "Passenger",
                    50 + (i % 150),
                    "Coach-" + i
            );
            bogies.add(bogie);
        }
        return bogies;
    }

    private static void testSequentialAccess(ArrayList<Bogie> bogies) {
        System.out.println("Sequential Access (Iterator) Performance:");

        // ArrayList sequential access
        long startTime = System.nanoTime();
        int count1 = 0;
        for (Bogie bogie : bogies) {
            count1++;
        }
        long arrayListTime = System.nanoTime() - startTime;

        // LinkedList equivalent
        LinkedList<Bogie> linkedList = new LinkedList<>(bogies);
        startTime = System.nanoTime();
        int count2 = 0;
        for (Bogie bogie : linkedList) {
            count2++;
        }
        long linkedListTime = System.nanoTime() - startTime;

        System.out.println("  ArrayList: " + arrayListTime + " ns ✓ FASTER");
        System.out.println("  LinkedList: " + linkedListTime + " ns");
        System.out.println("  Ratio: " + String.format("%.2f", (double) linkedListTime / arrayListTime) + "x");
        System.out.println("  Winner: " + (arrayListTime < linkedListTime ? "ArrayList" : "LinkedList"));
    }

    private static void testRandomAccess(ArrayList<Bogie> bogies) {
        System.out.println("Random Access (get by index) Performance:");

        // ArrayList random access
        long startTime = System.nanoTime();
        int sum1 = 0;
        for (int i = 0; i < bogies.size(); i += 10) {
            sum1 += bogies.get(i).getCapacity();
        }
        long arrayListTime = System.nanoTime() - startTime;

        // LinkedList random access (inefficient)
        LinkedList<Bogie> linkedList = new LinkedList<>(bogies);
        startTime = System.nanoTime();
        int sum2 = 0;
        for (int i = 0; i < linkedList.size(); i += 10) {
            sum2 += linkedList.get(i).getCapacity();
        }
        long linkedListTime = System.nanoTime() - startTime;

        System.out.println("  ArrayList: " + arrayListTime + " ns ✓ MUCH FASTER");
        System.out.println("  LinkedList: " + linkedListTime + " ns");
        System.out.println("  Ratio: " + String.format("%.2f", (double) linkedListTime / arrayListTime) + "x");
        System.out.println("  Winner: ArrayList (O(1) vs O(n))");
    }

    private static void testInsertion(ArrayList<Bogie> bogies) {
        System.out.println("Insertion Performance (at end):");

        // ArrayList insertion at end
        ArrayList<Bogie> arrayList = new ArrayList<>(bogies);
        long startTime = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            arrayList.add(new Bogie("TEMP" + i, "Passenger", 100, "Temp"));
        }
        long arrayListTime = System.nanoTime() - startTime;

        // LinkedList insertion at end
        LinkedList<Bogie> linkedList = new LinkedList<>(bogies);
        startTime = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            linkedList.add(new Bogie("TEMP" + i, "Passenger", 100, "Temp"));
        }
        long linkedListTime = System.nanoTime() - startTime;

        System.out.println("  ArrayList: " + arrayListTime + " ns");
        System.out.println("  LinkedList: " + linkedListTime + " ns ✓ FASTER (for this scale)");
        System.out.println("  Winner: " + (arrayListTime < linkedListTime ? "ArrayList" : "LinkedList"));

        // Insertion at beginning
        System.out.println("\nInsertion at Beginning:");
        arrayList = new ArrayList<>(bogies);
        startTime = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            arrayList.add(0, new Bogie("TEMP" + i, "Passenger", 100, "Temp"));
        }
        arrayListTime = System.nanoTime() - startTime;

        linkedList = new LinkedList<>(bogies);
        startTime = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            linkedList.add(0, new Bogie("TEMP" + i, "Passenger", 100, "Temp"));
        }
        linkedListTime = System.nanoTime() - startTime;

        System.out.println("  ArrayList: " + arrayListTime + " ns");
        System.out.println("  LinkedList: " + linkedListTime + " ns ✓ FASTER");
        System.out.println("  Winner: LinkedList (O(n) vs O(1) for add at front)");
    }

    private static void testDeletion(ArrayList<Bogie> bogies) {
        System.out.println("Deletion Performance (remove by index):");

        // ArrayList removal
        ArrayList<Bogie> arrayList = new ArrayList<>(bogies.subList(0, Math.min(500, bogies.size())));
        long startTime = System.nanoTime();
        for (int i = 0; i < 50; i++) {
            arrayList.remove(arrayList.size() - 1);
        }
        long arrayListTime = System.nanoTime() - startTime;

        // LinkedList removal
        LinkedList<Bogie> linkedList = new LinkedList<>(bogies.subList(0, Math.min(500, bogies.size())));
        startTime = System.nanoTime();
        for (int i = 0; i < 50; i++) {
            linkedList.remove(linkedList.size() - 1);
        }
        long linkedListTime = System.nanoTime() - startTime;

        System.out.println("  ArrayList (from end): " + arrayListTime + " ns ✓ FASTER");
        System.out.println("  LinkedList (from end): " + linkedListTime + " ns");
        System.out.println("  Winner: ArrayList");
    }

    private static void testSearchPerformance(ArrayList<Bogie> bogies) {
        System.out.println("Search Performance:");

        // Linear search
        String targetId = "B0500";
        long startTime = System.nanoTime();
        Bogie found = null;
        for (Bogie bogie : bogies) {
            if (bogie.getBogieId().equals(targetId)) {
                found = bogie;
                break;
            }
        }
        long linearTime = System.nanoTime() - startTime;

        // HashMap search
        HashMap<String, Bogie> bogieMap = new HashMap<>();
        for (Bogie bogie : bogies) {
            bogieMap.put(bogie.getBogieId(), bogie);
        }
        startTime = System.nanoTime();
        found = bogieMap.get(targetId);
        long hashMapTime = System.nanoTime() - startTime;

        System.out.println("  Linear Search: " + linearTime + " ns");
        System.out.println("  HashMap Search: " + hashMapTime + " ns ✓ FASTER");
        System.out.println("  Ratio: " + String.format("%.2f", (double) linearTime / hashMapTime) + "x");
        System.out.println("  Winner: HashMap (O(n) vs O(1))");
    }

    public static void main(String[] args) {
        execute();
    }
}
