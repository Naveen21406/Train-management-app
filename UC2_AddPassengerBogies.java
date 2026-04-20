import java.util.*;

/**
 * UC2 - Add Passenger Bogies to Train and Assign Passengers
 * Demonstrates adding passenger bogies and managing passenger allocation
 * Data Structure: ArrayList + Exception Handling
 */
public class UC2_AddPassengerBogies {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 2: Add Passenger Bogies to Train");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Add multiple passenger bogies and allocate passengers.");
        System.out.println("Data Structure Used: ArrayList with capacity management");
        System.out.println("-".repeat(70));

        try {
            // Initialize Train
            Train train = new Train("TR002", 10);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // Create passenger bogies
            ArrayList<Bogie> passengerBogies = new ArrayList<>();
            passengerBogies.add(new Bogie("PB001", "Passenger", 120, "First Class A"));
            passengerBogies.add(new Bogie("PB002", "Passenger", 120, "First Class B"));
            passengerBogies.add(new Bogie("PB003", "Passenger", 100, "Second Class A"));
            passengerBogies.add(new Bogie("PB004", "Passenger", 100, "Second Class B"));
            passengerBogies.add(new Bogie("PB005", "Passenger", 80, "Sleeper Class"));

            System.out.println("\n✓ Adding " + passengerBogies.size() + " passenger bogies to train...");
            for (Bogie bogie : passengerBogies) {
                train.addBogie(bogie);
                System.out.println("  ✓ Added: " + bogie.getBogieName());
            }

            // Allocate passengers
            System.out.println("\n✓ Allocating passengers to bogies...");
            passengerBogies.get(0).addPassengers(110);
            System.out.println("  ✓ PB001: 110 passengers allocated");
            
            passengerBogies.get(1).addPassengers(120);
            System.out.println("  ✓ PB002: 120 passengers allocated");
            
            passengerBogies.get(2).addPassengers(95);
            System.out.println("  ✓ PB003: 95 passengers allocated");
            
            passengerBogies.get(3).addPassengers(100);
            System.out.println("  ✓ PB004: 100 passengers allocated");
            
            passengerBogies.get(4).addPassengers(75);
            System.out.println("  ✓ PB005: 75 passengers allocated");

            // Display train configuration
            train.displayConfiguration();

            // Summary Statistics
            System.out.println("Summary Statistics:");
            System.out.println("  Total Bogies: " + train.getTotalBogies());
            System.out.println("  Total Capacity: " + train.getTotalSeats() + " seats");
            System.out.println("  Total Occupied: " + train.getOccupiedSeats() + " seats");
            System.out.println("  Occupancy Rate: " + String.format("%.2f%%", 
                    (train.getOccupiedSeats() * 100.0 / train.getTotalSeats())));

            System.out.println("\n✓ UC2 Completed Successfully!");

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
