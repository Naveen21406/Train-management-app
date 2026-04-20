import java.util.*;

/**
 * UC1 - Initialize Train and Display Components
 * Demonstrates basic train setup and component display
 * Data Structure: ArrayList (basic ArrayList usage for bogies)
 */
public class UC1_InitializeTrainAndDisplay {
    
    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 1: Initialize Train and Display Components");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Create a train, add bogies, and display the train configuration.");
        System.out.println("Data Structure Used: ArrayList");
        System.out.println("-".repeat(70));

        try {
            // Create a train with ID and max capacity
            Train train = new Train("TR001", 5);
            System.out.println("\n✓ Train created: " + train.getTrainId());
            System.out.println("  Max Bogies: " + train.getMaxBogies());

            // Create and add bogies
            Bogie bogie1 = new Bogie("B001", "Passenger", 100, "First Class");
            Bogie bogie2 = new Bogie("B002", "Passenger", 80, "Second Class");
            Bogie bogie3 = new Bogie("B003", "Cargo", 50, "Cargo Compartment");

            System.out.println("\n✓ Creating bogies...");
            System.out.println("  - " + bogie1);
            System.out.println("  - " + bogie2);
            System.out.println("  - " + bogie3);

            // Add bogies to train
            train.addBogie(bogie1);
            train.addBogie(bogie2);
            train.addBogie(bogie3);
            System.out.println("\n✓ All bogies added to train successfully.");

            // Display train configuration
            train.displayConfiguration();

            System.out.println("✓ UC1 Completed Successfully!");

        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
