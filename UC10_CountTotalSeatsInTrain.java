import java.util.*;

/**
 * UC10 - Count Total Seats in Train
 * Demonstrates aggregation and calculation operations
 * Data Structure: ArrayList with aggregation methods
 */
public class UC10_CountTotalSeatsInTrain {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 10: Count Total Seats in Train");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Calculate total, occupied, and available seats in the train.");
        System.out.println("Data Structure Used: ArrayList with aggregation calculations");
        System.out.println("-".repeat(70));

        try {
            Train train = new Train("TR010", 10);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // Create and add bogies with passengers
            String[] bogieData = {
                "B001:FirstClass-A:150:120",
                "B002:FirstClass-B:150:145",
                "B003:SecondClass-A:120:100",
                "B004:SecondClass-B:120:110",
                "B005:Sleeper:80:70",
                "B006:AC-Tier:90:85",
                "B007:GeneralClass:180:175"
            };

            System.out.println("\n✓ Adding bogies with passenger allocations...");
            ArrayList<Bogie> bogies = new ArrayList<>();

            for (String data : bogieData) {
                String[] parts = data.split(":");
                Bogie bogie = new Bogie(parts[0], "Passenger", Integer.parseInt(parts[2]), parts[1]);
                bogie.setSeatsOccupied(Integer.parseInt(parts[3]));
                bogies.add(bogie);
                train.addBogie(bogie);
                System.out.println("  ✓ " + bogie.getBogieName() + 
                                   " - Capacity: " + bogie.getCapacity() + 
                                   ", Occupied: " + bogie.getSeatsOccupied());
            }

            System.out.println("\n========== SEAT CALCULATIONS ==========");

            // Calculate totals
            int totalCapacity = 0;
            int totalOccupied = 0;
            int totalAvailable = 0;

            for (Bogie bogie : bogies) {
                totalCapacity += bogie.getCapacity();
                totalOccupied += bogie.getSeatsOccupied();
                totalAvailable += bogie.getAvailableSeats();
            }

            System.out.println("\nTrain Seating Summary:");
            System.out.println("  Total Bogies: " + bogies.size());
            System.out.println("  Total Capacity: " + totalCapacity + " seats");
            System.out.println("  Total Occupied: " + totalOccupied + " seats");
            System.out.println("  Total Available: " + totalAvailable + " seats");
            System.out.println("  Occupancy Rate: " + String.format("%.2f%%", 
                    (totalOccupied * 100.0 / totalCapacity)));

            // Bogie-wise breakdown
            System.out.println("\nBogie-wise Breakdown:");
            System.out.println(String.format("%-5s %-20s %-12s %-12s %-12s %-12s", 
                    "ID", "Name", "Capacity", "Occupied", "Available", "Occupancy%"));
            System.out.println("-".repeat(70));

            for (Bogie bogie : bogies) {
                double occupancyRate = (bogie.getSeatsOccupied() * 100.0 / bogie.getCapacity());
                System.out.println(String.format("%-5s %-20s %-12d %-12d %-12d %-12.2f%%",
                        bogie.getBogieId(),
                        bogie.getBogieName(),
                        bogie.getCapacity(),
                        bogie.getSeatsOccupied(),
                        bogie.getAvailableSeats(),
                        occupancyRate));
            }

            // Find bogies with occupancy metrics
            System.out.println("\n========== OCCUPANCY ANALYSIS ==========");

            // Most occupied
            Bogie mostOccupied = bogies.stream()
                    .max((b1, b2) -> Integer.compare(b1.getSeatsOccupied(), b2.getSeatsOccupied()))
                    .orElse(null);
            if (mostOccupied != null) {
                System.out.println("\nMost Occupied Bogie:");
                System.out.println("  " + mostOccupied.getBogieId() + " - " + mostOccupied.getBogieName());
                System.out.println("  Occupied: " + mostOccupied.getSeatsOccupied() + "/" + mostOccupied.getCapacity());
            }

            // Least occupied
            Bogie leastOccupied = bogies.stream()
                    .min((b1, b2) -> Integer.compare(b1.getSeatsOccupied(), b2.getSeatsOccupied()))
                    .orElse(null);
            if (leastOccupied != null) {
                System.out.println("\nLeast Occupied Bogie:");
                System.out.println("  " + leastOccupied.getBogieId() + " - " + leastOccupied.getBogieName());
                System.out.println("  Occupied: " + leastOccupied.getSeatsOccupied() + "/" + leastOccupied.getCapacity());
            }

            // Fully occupied
            long fullyOccupied = bogies.stream()
                    .filter(b -> b.getSeatsOccupied() == b.getCapacity())
                    .count();
            System.out.println("\nFully Occupied Bogies: " + fullyOccupied);

            // Completely empty
            long completelyEmpty = bogies.stream()
                    .filter(b -> b.getSeatsOccupied() == 0)
                    .count();
            System.out.println("Completely Empty Bogies: " + completelyEmpty);

            train.displayConfiguration();

            System.out.println("✓ UC10 Completed Successfully!");

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
