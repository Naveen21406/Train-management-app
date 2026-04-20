import java.util.*;

/**
 * UC14 - Handle Invalid Bogie Capacity
 * Demonstrates validation and error handling for invalid configurations
 * Data Structure: ArrayList with try-catch and validation
 */
public class UC14_HandleInvalidBogieCapacity {

    public static class InvalidBogieException extends Exception {
        public InvalidBogieException(String message) {
            super(message);
        }
    }

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 14: Handle Invalid Bogie Capacity");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Validate and handle invalid bogie configurations.");
        System.out.println("Data Structure Used: ArrayList with custom exception handling");
        System.out.println("-".repeat(70));

        try {
            Train train = new Train("TR014", 10);
            System.out.println("\n✓ Train initialized: " + train.getTrainId());

            // Test Case 1: Valid bogies
            System.out.println("\n========== TEST 1: VALID BOGIE ADDITIONS ==========");
            testValidBogies(train);

            // Test Case 2: Invalid capacity values
            System.out.println("\n========== TEST 2: INVALID CAPACITY VALUES ==========");
            testInvalidCapacities(train);

            // Test Case 3: Passenger overflow
            System.out.println("\n========== TEST 3: PASSENGER OVERFLOW ==========");
            testPassengerOverflow(train);

            // Test Case 4: Invalid seat allocation
            System.out.println("\n========== TEST 4: INVALID SEAT ALLOCATION ==========");
            testInvalidSeatAllocation(train);

            // Summary
            displayFinalStatus(train);

        } catch (Exception e) {
            System.out.println("✗ Unexpected Error: " + e.getMessage());
        }
    }

    private static void testValidBogies(Train train) {
        System.out.println("Adding valid bogies:");

        String[] validBogies = {
            "B001:FirstClass:150",
            "B002:SecondClass:120",
            "B003:Sleeper:80",
            "B004:General:200"
        };

        for (String data : validBogies) {
            try {
                String[] parts = data.split(":");
                Bogie bogie = new Bogie(parts[0], "Passenger", Integer.parseInt(parts[2]), parts[1]);
                validateBogie(bogie);
                train.addBogie(bogie);
                System.out.println("  ✓ " + bogie.getBogieId() + " added successfully (Capacity: " + 
                                   bogie.getCapacity() + ")");
            } catch (InvalidBogieException e) {
                System.out.println("  ✗ " + e.getMessage());
            } catch (Exception e) {
                System.out.println("  ✗ Error: " + e.getMessage());
            }
        }
    }

    private static void testInvalidCapacities(Train train) {
        System.out.println("Attempting to add bogies with invalid capacities:");

        String[] invalidBogies = {
            "B101:Negative:test:-50",
            "B102:Zero:test:0",
            "B103:Extreme:test:99999",
            "B104:Decimal:test:150.5"
        };

        for (String data : invalidBogies) {
            try {
                String[] parts = data.split(":");
                int capacity;
                try {
                    capacity = Integer.parseInt(parts[3]);
                } catch (NumberFormatException e) {
                    throw new InvalidBogieException("Invalid capacity format for " + parts[0] + ": " + parts[3]);
                }

                Bogie bogie = new Bogie(parts[0], "Passenger", capacity, parts[2]);
                validateBogie(bogie);
                System.out.println("  ✓ " + bogie.getBogieId() + " validated");

            } catch (InvalidBogieException e) {
                System.out.println("  ✗ " + e.getMessage());
            } catch (Exception e) {
                System.out.println("  ✗ Exception for " + data.split(":")[0] + ": " + e.getMessage());
            }
        }
    }

    private static void testPassengerOverflow(Train train) {
        System.out.println("Testing passenger allocation limits:");

        try {
            Bogie bogie = new Bogie("B200", "Passenger", 100, "TestCoach");
            validateBogie(bogie);
            train.addBogie(bogie);

            // Valid allocation
            System.out.println("  ✓ Allocating 80 passengers to 100-capacity bogie...");
            bogie.addPassengers(80);
            System.out.println("    Success! Available: " + bogie.getAvailableSeats());

            // Overflow attempt
            System.out.println("  ✓ Attempting to add 25 more passengers (would exceed capacity)...");
            try {
                bogie.addPassengers(25);
                System.out.println("    ✗ ERROR: Should have been rejected!");
            } catch (Exception e) {
                System.out.println("    ✓ Correctly rejected: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("  ✗ Error in test: " + e.getMessage());
        }
    }

    private static void testInvalidSeatAllocation(Train train) {
        System.out.println("Testing invalid seat allocations:");

        try {
            Bogie bogie = new Bogie("B300", "Passenger", 150, "TestCoach2");
            validateBogie(bogie);
            train.addBogie(bogie);

            // Test 1: Valid allocation
            System.out.println("  ✓ Setting seats to 100...");
            bogie.setSeatsOccupied(100);
            System.out.println("    Success! Occupied: " + bogie.getSeatsOccupied());

            // Test 2: Exceeded capacity
            System.out.println("  ✓ Attempting to set seats to 200 (exceeds capacity)...");
            try {
                bogie.setSeatsOccupied(200);
                System.out.println("    ✗ ERROR: Should have been rejected!");
            } catch (IllegalArgumentException e) {
                System.out.println("    ✓ Correctly rejected: " + e.getMessage());
            }

            // Test 3: Negative seats
            System.out.println("  ✓ Attempting to set seats to -10 (negative)...");
            try {
                bogie.setSeatsOccupied(-10);
                System.out.println("    ✗ ERROR: Should have been rejected!");
            } catch (IllegalArgumentException e) {
                System.out.println("    ✓ Correctly rejected: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("  ✗ Error in test: " + e.getMessage());
        }
    }

    private static void validateBogie(Bogie bogie) throws InvalidBogieException {
        // Validation Rule 1: ID not empty
        if (bogie.getBogieId() == null || bogie.getBogieId().isEmpty()) {
            throw new InvalidBogieException("Bogie ID cannot be empty");
        }

        // Validation Rule 2: Valid ID format
        if (!bogie.getBogieId().matches("^B\\d{3,4}$")) {
            throw new InvalidBogieException("Invalid Bogie ID format: " + bogie.getBogieId() + 
                                           " (must be B followed by 3-4 digits)");
        }

        // Validation Rule 3: Positive capacity
        if (bogie.getCapacity() <= 0) {
            throw new InvalidBogieException("Bogie capacity must be positive. Got: " + bogie.getCapacity());
        }

        // Validation Rule 4: Reasonable capacity limits
        if (bogie.getCapacity() > 500) {
            throw new InvalidBogieException("Bogie capacity exceeds maximum limit (500). Got: " + 
                                           bogie.getCapacity());
        }

        // Validation Rule 5: Valid bogie type
        if (!Arrays.asList("Passenger", "Cargo", "Mail", "Other").contains(bogie.getBogieType())) {
            throw new InvalidBogieException("Invalid bogie type: " + bogie.getBogieType());
        }

        // Validation Rule 6: Non-empty name
        if (bogie.getBogieName() == null || bogie.getBogieName().isEmpty()) {
            throw new InvalidBogieException("Bogie name cannot be empty");
        }
    }

    private static void displayFinalStatus(Train train) {
        System.out.println("\n========== FINAL TRAIN STATUS ==========");
        System.out.println("Train: " + train.getTrainId());
        System.out.println("Total Valid Bogies: " + train.getTotalBogies());
        System.out.println("Max Capacity: " + train.getMaxBogies());
        System.out.println("\nValid Bogies:");
        
        int index = 1;
        for (Bogie bogie : train.getBogies()) {
            System.out.println("  " + index + ". " + bogie);
            index++;
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
