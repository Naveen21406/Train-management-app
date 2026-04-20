import java.util.*;
import java.util.stream.Collectors;

/**
 * UC9 - Group Bogies by Type
 * Demonstrates grouping operations using Map and Streams
 * Data Structure: Map with ArrayList values for grouping
 */
public class UC9_GroupBogiesByType {

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 9: Group Bogies by Type");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Group bogies by type for better organization and analysis.");
        System.out.println("Data Structure Used: Map<String, ArrayList<Bogie>> with Streams");
        System.out.println("-".repeat(70));

        try {
            Train train = new Train("TR009", 15);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // Create mixed bogies
            String[] bogieData = {
                "B001:Passenger:FirstClass:150",
                "B002:Passenger:SecondClass:120",
                "B003:Cargo:Container:200",
                "B004:Passenger:Sleeper:80",
                "B005:Cargo:Refrigerated:100",
                "B006:Passenger:ACThreeTier:90",
                "B007:Mail:PostalCar:50",
                "B008:Passenger:GeneralClass:180",
                "B009:Cargo:FlatCar:220"
            };

            ArrayList<Bogie> allBogies = new ArrayList<>();

            System.out.println("\n✓ Adding bogies to train...");
            for (String data : bogieData) {
                String[] parts = data.split(":");
                Bogie bogie = new Bogie(parts[0], parts[1], Integer.parseInt(parts[3]), parts[2]);
                allBogies.add(bogie);
                train.addBogie(bogie);
            }

            System.out.println("✓ Total bogies added: " + allBogies.size());

            // Group by type using Streams
            Map<String, ArrayList<Bogie>> bogiesByType = allBogies.stream()
                    .collect(Collectors.groupingBy(
                            Bogie::getBogieType,
                            LinkedHashMap::new,
                            Collectors.toCollection(ArrayList::new)
                    ));

            System.out.println("\n========== GROUPING RESULTS ==========");
            System.out.println("\nBogies grouped by type:");

            for (Map.Entry<String, ArrayList<Bogie>> entry : bogiesByType.entrySet()) {
                String type = entry.getKey();
                ArrayList<Bogie> bogies = entry.getValue();

                System.out.println("\n" + type.toUpperCase() + " (" + bogies.size() + " bogies):");
                System.out.println("-".repeat(50));

                int index = 1;
                for (Bogie bogie : bogies) {
                    System.out.println("  " + index + ". " + bogie.getBogieId() + " - " + 
                                       bogie.getBogieName() + " (Capacity: " + bogie.getCapacity() + ")");
                    index++;
                }
            }

            // Group by capacity range
            System.out.println("\n========== CAPACITY RANGE GROUPING ==========");
            Map<String, ArrayList<Bogie>> bogiesByCapacityRange = allBogies.stream()
                    .collect(Collectors.groupingBy(
                            b -> {
                                if (b.getCapacity() < 100) return "Small (< 100)";
                                else if (b.getCapacity() < 150) return "Medium (100-149)";
                                else return "Large (>= 150)";
                            },
                            LinkedHashMap::new,
                            Collectors.toCollection(ArrayList::new)
                    ));

            for (Map.Entry<String, ArrayList<Bogie>> entry : bogiesByCapacityRange.entrySet()) {
                System.out.println("\n" + entry.getKey() + " (" + entry.getValue().size() + " bogies):");
                for (Bogie bogie : entry.getValue()) {
                    System.out.println("  - " + bogie.getBogieId() + ": " + bogie.getCapacity());
                }
            }

            // Calculate statistics by type
            System.out.println("\n========== TYPE STATISTICS ==========");
            for (Map.Entry<String, ArrayList<Bogie>> entry : bogiesByType.entrySet()) {
                String type = entry.getKey();
                ArrayList<Bogie> bogies = entry.getValue();

                int totalCapacity = bogies.stream().mapToInt(Bogie::getCapacity).sum();
                int avgCapacity = totalCapacity / bogies.size();
                int maxCapacity = bogies.stream().mapToInt(Bogie::getCapacity).max().orElse(0);
                int minCapacity = bogies.stream().mapToInt(Bogie::getCapacity).min().orElse(0);

                System.out.println("\n" + type + ":");
                System.out.println("  Count: " + bogies.size());
                System.out.println("  Total Capacity: " + totalCapacity);
                System.out.println("  Average Capacity: " + avgCapacity);
                System.out.println("  Max Capacity: " + maxCapacity);
                System.out.println("  Min Capacity: " + minCapacity);
            }

            System.out.println("\n✓ UC9 Completed Successfully!");

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
