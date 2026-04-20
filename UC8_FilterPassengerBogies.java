import java.util.*;
import java.util.stream.Collectors;

/**
 * UC8 - Filter Passenger Bogies
 * Demonstrates filtering bogies by type and other criteria
 * Data Structure: ArrayList with Streams (filter operations)
 */
public class UC8_FilterPassengerBogies {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 8: Filter Passenger Bogies");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Filter bogies based on type and other criteria.");
        System.out.println("Data Structure Used: ArrayList with Java Streams");
        System.out.println("-".repeat(70));

        try {
            Train train = new Train("TR008", 15);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // Create mixed bogies
            String[] bogieData = {
                "B001:Passenger:First-Class:150",
                "B002:Passenger:Second-Class:120",
                "B003:Cargo:Container:200",
                "B004:Passenger:Sleeper:80",
                "B005:Cargo:Refrigerated:100",
                "B006:Passenger:AC-3-Tier:90",
                "B007:Mail:PostalCar:50",
                "B008:Passenger:GeneralClass:180"
            };

            ArrayList<Bogie> allBogies = new ArrayList<>();

            System.out.println("\n✓ Adding mixed bogies to train...");
            for (String data : bogieData) {
                String[] parts = data.split(":");
                Bogie bogie = new Bogie(parts[0], parts[1], Integer.parseInt(parts[3]), parts[2]);
                allBogies.add(bogie);
                train.addBogie(bogie);
                System.out.println("  ✓ " + bogie.getBogieId() + " (" + bogie.getBogieType() + ") - " + bogie.getBogieName());
            }

            System.out.println("\n========== FILTERING RESULTS ==========");

            // Filter 1: Passenger bogies only
            System.out.println("\n1. PASSENGER BOGIES ONLY:");
            ArrayList<Bogie> passengerBogies = allBogies.stream()
                    .filter(b -> b.getBogieType().equals("Passenger"))
                    .collect(Collectors.toCollection(ArrayList::new));
            displayBogies(passengerBogies);
            System.out.println("  Total: " + passengerBogies.size() + " bogies");

            // Filter 2: Cargo bogies only
            System.out.println("\n2. CARGO BOGIES ONLY:");
            ArrayList<Bogie> cargoBogies = allBogies.stream()
                    .filter(b -> b.getBogieType().equals("Cargo"))
                    .collect(Collectors.toCollection(ArrayList::new));
            displayBogies(cargoBogies);
            System.out.println("  Total: " + cargoBogies.size() + " bogies");

            // Filter 3: Bogies with capacity >= 100
            System.out.println("\n3. BOGIES WITH CAPACITY >= 100:");
            ArrayList<Bogie> highCapacityBogies = allBogies.stream()
                    .filter(b -> b.getCapacity() >= 100)
                    .collect(Collectors.toCollection(ArrayList::new));
            displayBogies(highCapacityBogies);
            System.out.println("  Total: " + highCapacityBogies.size() + " bogies");

            // Filter 4: Passenger bogies with capacity < 100
            System.out.println("\n4. PASSENGER BOGIES WITH CAPACITY < 100:");
            ArrayList<Bogie> smallPassengerBogies = allBogies.stream()
                    .filter(b -> b.getBogieType().equals("Passenger") && b.getCapacity() < 100)
                    .collect(Collectors.toCollection(ArrayList::new));
            displayBogies(smallPassengerBogies);
            System.out.println("  Total: " + smallPassengerBogies.size() + " bogies");

            // Filter 5: High-capacity bogies (>= 150)
            System.out.println("\n5. HIGH-CAPACITY BOGIES (>= 150):");
            ArrayList<Bogie> veryHighCapacity = allBogies.stream()
                    .filter(b -> b.getCapacity() >= 150)
                    .sorted((b1, b2) -> Integer.compare(b2.getCapacity(), b1.getCapacity()))
                    .collect(Collectors.toCollection(ArrayList::new));
            displayBogies(veryHighCapacity);
            System.out.println("  Total: " + veryHighCapacity.size() + " bogies");

            // Summary statistics
            System.out.println("\n========== SUMMARY STATISTICS ==========");
            System.out.println("Total Bogies: " + allBogies.size());
            System.out.println("Passenger Bogies: " + passengerBogies.size());
            System.out.println("Cargo Bogies: " + cargoBogies.size());
            System.out.println("Non-Passenger/Non-Cargo: " + (allBogies.size() - passengerBogies.size() - cargoBogies.size()));

            System.out.println("\n✓ UC8 Completed Successfully!");

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private static void displayBogies(ArrayList<Bogie> bogies) {
        if (bogies.isEmpty()) {
            System.out.println("  (No bogies match this filter)");
            return;
        }
        int index = 1;
        for (Bogie bogie : bogies) {
            System.out.println("  " + index + ". " + bogie.getBogieId() + " (" + bogie.getBogieType() + 
                               ") - " + bogie.getBogieName() + " [Capacity: " + bogie.getCapacity() + "]");
            index++;
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
