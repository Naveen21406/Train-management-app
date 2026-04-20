import java.util.*;
import java.util.stream.Collectors;

/**
 * UC16 - Sort Passenger Bogies by Capacity
 * Demonstrates advanced sorting techniques for passenger bogies
 * Data Structure: ArrayList with Collections.sort and custom comparators
 */
public class UC16_SortPassengerBogiesByCapacity {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 16: Sort Passenger Bogies by Capacity");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Sort passenger bogies with multi-criteria ordering.");
        System.out.println("Data Structure Used: ArrayList with custom Comparators");
        System.out.println("-".repeat(70));

        try {
            Train train = new Train("TR016", 15);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // Create mixed bogies
            String[] bogieData = {
                "B001:Passenger:First-A:150",
                "B002:Passenger:First-B:150",
                "B003:Passenger:Second-A:120",
                "B004:Passenger:Second-B:120",
                "B005:Cargo:Container:200",
                "B006:Passenger:Sleeper:80",
                "B007:Passenger:AC-Tier:90",
                "B008:Cargo:FlatCar:180",
                "B009:Passenger:General:200"
            };

            ArrayList<Bogie> allBogies = new ArrayList<>();
            System.out.println("\n✓ Adding bogies to train...");

            for (String data : bogieData) {
                String[] parts = data.split(":");
                Bogie bogie = new Bogie(parts[0], parts[1], Integer.parseInt(parts[3]), parts[2]);
                allBogies.add(bogie);
                train.addBogie(bogie);
            }

            // Filter passenger bogies
            ArrayList<Bogie> passengerBogies = allBogies.stream()
                    .filter(b -> b.getBogieType().equals("Passenger"))
                    .collect(Collectors.toCollection(ArrayList::new));

            System.out.println("✓ Total bogies: " + allBogies.size());
            System.out.println("✓ Passenger bogies: " + passengerBogies.size());

            // Display original order
            System.out.println("\n========== SORTING RESULTS ==========");
            System.out.println("\n1. ORIGINAL ORDER:");
            displayBogies(passengerBogies, "Original");

            // Sort 1: Ascending by capacity
            ArrayList<Bogie> sortAscending = new ArrayList<>(passengerBogies);
            Collections.sort(sortAscending, new Comparator<Bogie>() {
                @Override
                public int compare(Bogie b1, Bogie b2) {
                    return Integer.compare(b1.getCapacity(), b2.getCapacity());
                }
            });
            System.out.println("\n2. SORTED ASCENDING BY CAPACITY:");
            displayBogies(sortAscending, "Ascending");

            // Sort 2: Descending by capacity
            ArrayList<Bogie> sortDescending = new ArrayList<>(passengerBogies);
            sortDescending.sort((b1, b2) -> Integer.compare(b2.getCapacity(), b1.getCapacity()));
            System.out.println("\n3. SORTED DESCENDING BY CAPACITY:");
            displayBogies(sortDescending, "Descending");

            // Sort 3: By capacity then by name
            ArrayList<Bogie> sortByCapacityThenName = new ArrayList<>(passengerBogies);
            sortByCapacityThenName.sort((b1, b2) -> {
                int capacityCompare = Integer.compare(b1.getCapacity(), b2.getCapacity());
                if (capacityCompare != 0) {
                    return capacityCompare;
                }
                return b1.getBogieName().compareTo(b2.getBogieName());
            });
            System.out.println("\n4. SORTED BY CAPACITY THEN BY NAME:");
            displayBogies(sortByCapacityThenName, "By Capacity & Name");

            // Sort 4: High capacity first (for premium experience)
            ArrayList<Bogie> sortHighFirst = new ArrayList<>(passengerBogies);
            sortHighFirst.sort((b1, b2) -> {
                // High capacity first (descending)
                int compare = Integer.compare(b2.getCapacity(), b1.getCapacity());
                if (compare != 0) return compare;
                // Same capacity: sort by name
                return b1.getBogieName().compareTo(b2.getBogieName());
            });
            System.out.println("\n5. HIGH CAPACITY FIRST (Premium Priority):");
            displayBogies(sortHighFirst, "High Capacity First");

            // Sort 5: Group by capacity range
            System.out.println("\n6. GROUPED BY CAPACITY RANGE:");
            groupAndDisplayByCapacity(passengerBogies);

            // Statistical analysis
            System.out.println("\n========== CAPACITY ANALYSIS ==========");
            analyzeCapacity(passengerBogies);

            // Sorting efficiency comparison
            System.out.println("\n========== SORTING PERFORMANCE ==========");
            compareSortingMethods(new ArrayList<>(passengerBogies));

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static void displayBogies(ArrayList<Bogie> bogies, String sortType) {
        System.out.println("(" + sortType + " - " + bogies.size() + " bogies):");
        int index = 1;
        for (Bogie bogie : bogies) {
            System.out.println(String.format("  %d. %-8s %-20s [Capacity: %3d]",
                    index, bogie.getBogieId(), bogie.getBogieName(), bogie.getCapacity()));
            index++;
        }
    }

    private static void groupAndDisplayByCapacity(ArrayList<Bogie> bogies) {
        // Create capacity groups
        Map<Integer, ArrayList<Bogie>> capacityGroups = new TreeMap<>(Collections.reverseOrder());

        for (Bogie bogie : bogies) {
            capacityGroups.computeIfAbsent(bogie.getCapacity(), k -> new ArrayList<>()).add(bogie);
        }

        for (Map.Entry<Integer, ArrayList<Bogie>> entry : capacityGroups.entrySet()) {
            int capacity = entry.getKey();
            ArrayList<Bogie> bogiesInGroup = entry.getValue();

            System.out.println("\n  Capacity: " + capacity + " (" + bogiesInGroup.size() + " bogies)");
            for (Bogie bogie : bogiesInGroup) {
                System.out.println("    - " + bogie.getBogieId() + ": " + bogie.getBogieName());
            }
        }
    }

    private static void analyzeCapacity(ArrayList<Bogie> bogies) {
        if (bogies.isEmpty()) {
            System.out.println("No bogies to analyze");
            return;
        }

        int[] capacities = bogies.stream()
                .mapToInt(Bogie::getCapacity)
                .toArray();

        Arrays.sort(capacities);

        int totalCapacity = Arrays.stream(capacities).sum();
        int minCapacity = capacities[0];
        int maxCapacity = capacities[capacities.length - 1];
        double avgCapacity = totalCapacity / (double) bogies.size();
        int medianCapacity = capacities[capacities.length / 2];

        System.out.println("Capacity Statistics:");
        System.out.println("  Total Capacity: " + totalCapacity + " seats");
        System.out.println("  Minimum: " + minCapacity + " seats");
        System.out.println("  Maximum: " + maxCapacity + " seats");
        System.out.println("  Average: " + String.format("%.2f", avgCapacity) + " seats");
        System.out.println("  Median: " + medianCapacity + " seats");
        System.out.println("  Range: " + (maxCapacity - minCapacity) + " seats");

        // Capacity distribution
        System.out.println("\nCapacity Distribution:");
        int rangeSize = (maxCapacity - minCapacity) / 3 + 1;
        System.out.println("  Low (< " + (minCapacity + rangeSize) + "): " +
                Arrays.stream(capacities).filter(c -> c < minCapacity + rangeSize).count() + " bogies");
        System.out.println("  Medium (" + (minCapacity + rangeSize) + "-" + (minCapacity + 2*rangeSize) + "): " +
                Arrays.stream(capacities).filter(c -> c >= minCapacity + rangeSize && c < minCapacity + 2*rangeSize).count() + " bogies");
        System.out.println("  High (>= " + (minCapacity + 2*rangeSize) + "): " +
                Arrays.stream(capacities).filter(c -> c >= minCapacity + 2*rangeSize).count() + " bogies");
    }

    private static void compareSortingMethods(ArrayList<Bogie> bogies) {
        System.out.println("Sorting method comparison on " + bogies.size() + " bogies:");

        // Method 1: Collections.sort with Comparator
        ArrayList<Bogie> copy1 = new ArrayList<>(bogies);
        long start1 = System.nanoTime();
        Collections.sort(copy1, (b1, b2) -> Integer.compare(b1.getCapacity(), b2.getCapacity()));
        long time1 = System.nanoTime() - start1;

        // Method 2: ArrayList.sort
        ArrayList<Bogie> copy2 = new ArrayList<>(bogies);
        long start2 = System.nanoTime();
        copy2.sort((b1, b2) -> Integer.compare(b1.getCapacity(), b2.getCapacity()));
        long time2 = System.nanoTime() - start2;

        // Method 3: Streams
        long start3 = System.nanoTime();
        ArrayList<Bogie> copy3 = bogies.stream()
                .sorted((b1, b2) -> Integer.compare(b1.getCapacity(), b2.getCapacity()))
                .collect(Collectors.toCollection(ArrayList::new));
        long time3 = System.nanoTime() - start3;

        System.out.println("  Collections.sort: " + time1 + " ns");
        System.out.println("  ArrayList.sort: " + time2 + " ns");
        System.out.println("  Streams.sorted: " + time3 + " ns");
        System.out.println("  Fastest: " + (Math.min(Math.min(time1, time2), time3) == time1 ? "Collections.sort" :
                                              Math.min(Math.min(time1, time2), time3) == time2 ? "ArrayList.sort" : "Streams"));
    }

    public static void main(String[] args) {
        execute();
    }
}
