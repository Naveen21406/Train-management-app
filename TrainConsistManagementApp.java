import java.util.*;

/**
 * Train Consist Management App - Main Application
 * 
 * This is the central orchestrator for the Train Consist Management System.
 * It demonstrates how Core Java and fundamental data structures are applied to solve 
 * real-world railway operations challenges.
 * 
 * Learning Approach:
 * - The application is developed incrementally
 * - Each use case introduces one new concept at a time
 * - All operations are deterministic and console-based
 * 
 * Use Cases Covered:
 * UC1  - Initialize Train and Display Components
 * UC2  - Add Passenger Bogies to Train
 * UC3  - Track Unique Bogie IDs (HashSet)
 * UC4  - Maintain Ordered Bogie IDs (LinkedHashSet)
 * UC5  - Preserve Insertion Order (LinkedHashMap)
 * UC6  - Map Bogie to Capacity (HashMap)
 * UC7  - Sort Bogies by Capacity
 * UC8  - Filter Passenger Bogies (Streams)
 * UC9  - Group Bogies by Type
 * UC10 - Count Total Seats in Train
 * UC11 - Validate Train ID & Cargo Configuration
 * UC12 - Safety Compliance Check
 * UC13 - Performance Comparison
 * UC14 - Handle Invalid Bogie Capacity
 * UC15 - Safe Cargo Assignment
 * UC16 - Sort Passenger Bogies by Capacity
 * UC17 - Sort Bogie Names Using Arrangements
 * UC18 - Linear Search for Bogie ID
 * UC19 - Binary Search for Bogie ID
 * UC20 - Exception Handling During Train Operations
 */
public class TrainConsistManagementApp {

    private static final String BANNER = 
        "\n╔════════════════════════════════════════════════════════════════╗\n" +
        "║  TRAIN CONSIST MANAGEMENT APP - Core Java & Data Structures   ║\n" +
        "║                                                                ║\n" +
        "║  Objective: Demonstrate efficient railway operations using    ║\n" +
        "║  Core Java, OOP principles, and fundamental data structures  ║\n" +
        "╚════════════════════════════════════════════════════════════════╝\n";

    private static final Map<Integer, UseCase> useCases = new LinkedHashMap<>();

    static {
        // Initialize all use cases
        useCases.put(1, new UseCase(1, "Initialize Train and Display Components", 
                                   "Basic train setup and component display using ArrayList"));
        useCases.put(2, new UseCase(2, "Add Passenger Bogies to Train", 
                                   "Adding multiple passenger bogies and managing allocation"));
        useCases.put(3, new UseCase(3, "Track Unique Bogie IDs", 
                                   "Preventing duplicate bogies using HashSet"));
        useCases.put(4, new UseCase(4, "Maintain Ordered Bogie IDs", 
                                   "Preserving insertion order while preventing duplicates with LinkedHashSet"));
        useCases.put(5, new UseCase(5, "Preserve Insertion Order", 
                                   "Mapping bogie IDs to details with insertion order using LinkedHashMap"));
        useCases.put(6, new UseCase(6, "Map Bogie to Capacity", 
                                   "Efficient capacity lookup using HashMap"));
        useCases.put(7, new UseCase(7, "Sort Bogies by Capacity", 
                                   "Sorting bogies using Comparator and Collections.sort"));
        useCases.put(8, new UseCase(8, "Filter Passenger Bogies", 
                                   "Filtering operations using Java Streams"));
        useCases.put(9, new UseCase(9, "Group Bogies by Type", 
                                   "Grouping operations using Map and Streams"));
        useCases.put(10, new UseCase(10, "Count Total Seats in Train", 
                                    "Aggregation and calculation operations"));
        useCases.put(11, new UseCase(11, "Validate Train ID & Cargo Configuration", 
                                    "Validation logic for train integrity"));
        useCases.put(12, new UseCase(12, "Safety Compliance Check", 
                                    "Safety validation and compliance tracking"));
        useCases.put(13, new UseCase(13, "Performance Comparison", 
                                    "ArrayList vs LinkedList performance analysis"));
        useCases.put(14, new UseCase(14, "Handle Invalid Bogie Capacity", 
                                    "Validation and custom exception handling"));
        useCases.put(15, new UseCase(15, "Safe Cargo Assignment", 
                                    "Cargo allocation with constraints using HashMap"));
        useCases.put(16, new UseCase(16, "Sort Passenger Bogies by Capacity", 
                                    "Advanced sorting with multiple criteria"));
        useCases.put(17, new UseCase(17, "Sort Bogie Names Using Arrangements", 
                                    "String sorting with custom comparators"));
        useCases.put(18, new UseCase(18, "Linear Search for Bogie ID", 
                                    "Linear search algorithm implementation"));
        useCases.put(19, new UseCase(19, "Binary Search for Bogie ID", 
                                    "Binary search implementation and performance comparison"));
        useCases.put(20, new UseCase(20, "Exception Handling During Train Operations", 
                                    "Comprehensive exception handling and error recovery"));
    }

    private static class UseCase {
        int number;
        String title;
        String description;

        UseCase(int number, String title, String description) {
            this.number = number;
            this.title = title;
            this.description = description;
        }
    }

    public static void main(String[] args) {
        displayBanner();
        
        if (args.length == 0) {
            displayMenu();
        } else {
            try {
                int choice = Integer.parseInt(args[0]);
                executeUseCase(choice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please provide a use case number (1-20).");
            }
        }
    }

    private static void displayBanner() {
        System.out.println(BANNER);
        System.out.println("Project Overview:");
        System.out.println("================\n");
        System.out.println("This project focuses on the design and implementation of a Train Consist");
        System.out.println("Management System to demonstrate how Core Java and fundamental data");
        System.out.println("structures are applied to solve real-world railway operations challenges.\n");
        System.out.println("Key Concepts Covered:");
        System.out.println("- Object-Oriented Design (Classes, Encapsulation, Inheritance)");
        System.out.println("- Collection Framework (ArrayList, LinkedList, Set, Map, Stream)");
        System.out.println("- Sorting and Searching (Comparator, Binary Search, Linear Search)");
        System.out.println("- Exception Handling (Custom Exceptions, Try-Catch-Finally)");
        System.out.println("- Validation and Business Logic");
        System.out.println("- Performance Analysis and Optimization\n");
    }

    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("AVAILABLE USE CASES");
        System.out.println("=".repeat(70) + "\n");

        for (UseCase useCase : useCases.values()) {
            System.out.println(String.format("  %2d. UC%02d - %-35s",
                    useCase.number, useCase.number, useCase.title));
            System.out.println(String.format("      └─ %s\n", useCase.description));
        }

        System.out.println("=".repeat(70));
        System.out.println("\nHow to run a specific use case:");
        System.out.println("  java TrainConsistManagementApp <use_case_number>\n");
        System.out.println("Examples:");
        System.out.println("  java TrainConsistManagementApp 1");
        System.out.println("  java TrainConsistManagementApp 10");
        System.out.println("  java TrainConsistManagementApp 20\n");

        System.out.println("Running all use cases:");
        System.out.println("  for i in {1..20}; do java TrainConsistManagementApp $i; done\n");
    }

    private static void executeUseCase(int caseNumber) {
        UseCase useCase = useCases.get(caseNumber);

        if (useCase == null) {
            System.out.println("Invalid use case number: " + caseNumber);
            System.out.println("Please provide a number between 1 and " + useCases.size());
            return;
        }

        // Execute the corresponding use case
        switch (caseNumber) {
            case 1:
                UC1_InitializeTrainAndDisplay.execute();
                break;
            case 2:
                UC2_AddPassengerBogies.execute();
                break;
            case 3:
                UC3_TrackUniqueBogieIDs.execute();
                break;
            case 4:
                UC4_MaintainOrderedBogieIDs.execute();
                break;
            case 5:
                UC5_PreserveInsertionOrder.execute();
                break;
            case 6:
                UC6_MapBogieToCapacity.execute();
                break;
            case 7:
                UC7_SortBogiesByCapacity.execute();
                break;
            case 8:
                UC8_FilterPassengerBogies.execute();
                break;
            case 9:
                UC9_GroupBogiesByType.execute();
                break;
            case 10:
                UC10_CountTotalSeatsInTrain.execute();
                break;
            case 11:
                UC11_ValidateTrainIDAndCargo.execute();
                break;
            case 12:
                UC12_SafetyComplianceCheck.execute();
                break;
            case 13:
                UC13_PerformanceComparison.execute();
                break;
            case 14:
                UC14_HandleInvalidBogieCapacity.execute();
                break;
            case 15:
                UC15_SafeCargoAssignment.execute();
                break;
            case 16:
                UC16_SortPassengerBogiesByCapacity.execute();
                break;
            case 17:
                UC17_SortBogieNamesUsingArrangements.execute();
                break;
            case 18:
                UC18_LinearSearchForBogieID.execute();
                break;
            case 19:
                UC19_BinarySearchForBogieID.execute();
                break;
            case 20:
                UC20_ExceptionHandlingDuringTrainOperations.execute();
                break;
            default:
                System.out.println("Use case not implemented yet.");
        }
    }
}
