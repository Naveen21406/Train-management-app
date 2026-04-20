import java.util.*;

/**
 * UC20 - Exception Handling During Train Operations
 * Demonstrates comprehensive exception handling and error recovery
 * Data Structure: ArrayList with try-catch-finally and custom exceptions
 */
public class UC20_ExceptionHandlingDuringTrainOperations {

    // Custom exceptions
    public static class TrainException extends Exception {
        public TrainException(String message) {
            super(message);
        }

        public TrainException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InvalidTrainStateException extends TrainException {
        public InvalidTrainStateException(String message) {
            super(message);
        }

        public InvalidTrainStateException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class BogieAllocationException extends TrainException {
        public BogieAllocationException(String message) {
            super(message);
        }

        public BogieAllocationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static void execute() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("USE CASE 20: Exception Handling During Train Operations");
        System.out.println("=".repeat(70));
        System.out.println("Objective: Demonstrate comprehensive exception handling in train operations.");
        System.out.println("Data Structure Used: ArrayList with custom exception hierarchy");
        System.out.println("-".repeat(70));

        try {
            // Scenario 1: Valid train operations
            System.out.println("\n========== SCENARIO 1: VALID OPERATIONS ==========");
            validOperations();

            // Scenario 2: Handle invalid inputs
            System.out.println("\n========== SCENARIO 2: INVALID INPUT HANDLING ==========");
            invalidInputHandling();

            // Scenario 3: Capacity violations
            System.out.println("\n========== SCENARIO 3: CAPACITY VIOLATION HANDLING ==========");
            capacityViolations();

            // Scenario 4: Null pointer prevention
            System.out.println("\n========== SCENARIO 4: NULL POINTER HANDLING ==========");
            nullPointerHandling();

            // Scenario 5: Resource management with finally
            System.out.println("\n========== SCENARIO 5: RESOURCE MANAGEMENT ==========");
            resourceManagement();

            // Scenario 6: Chained exceptions
            System.out.println("\n========== SCENARIO 6: CHAINED EXCEPTIONS ==========");
            chainedExceptions();

        } catch (Exception e) {
            System.out.println("✗ Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void validOperations() {
        System.out.println("Performing valid train operations with proper exception handling:");

        Train train = null;
        try {
            // Create train
            train = new Train("TR020", 5);
            System.out.println("✓ Train created successfully: " + train.getTrainId());

            // Add bogies
            ArrayList<Bogie> bogies = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                Bogie bogie = new Bogie("B" + i, "Passenger", 100 + i*10, "Coach-" + i);
                train.addBogie(bogie);
                bogies.add(bogie);
                System.out.println("✓ Bogie " + bogie.getBogieId() + " added");
            }

            // Allocate passengers
            for (Bogie bogie : bogies) {
                int passengers = 50 + bogies.indexOf(bogie) * 10;
                bogie.addPassengers(passengers);
                System.out.println("✓ " + passengers + " passengers allocated to " + bogie.getBogieId());
            }

            train.displayConfiguration();

        } catch (Exception e) {
            System.out.println("✗ Error during valid operations: " + e.getMessage());
        }
    }

    private static void invalidInputHandling() {
        System.out.println("Testing invalid input scenarios:\n");

        Train train = null;
        try {
            train = new Train("TR021", 5);
        } catch (Exception e) {
            System.out.println("✗ Failed to create train: " + e.getMessage());
            return;
        }

        // Test 1: Null bogie
        System.out.println("  1. Adding null bogie:");
        try {
            train.addBogie(null);
            System.out.println("     ✗ ERROR: Null was accepted!");
        } catch (NullPointerException e) {
            System.out.println("     ✓ Correctly rejected null bogie: " + e.getClass().getSimpleName());
        } catch (Exception e) {
            System.out.println("     ✓ Handled: " + e.getMessage());
        }

        // Test 2: Invalid bogie ID
        System.out.println("\n  2. Adding bogie with invalid ID format:");
        try {
            Bogie bogie = new Bogie(null, "Passenger", 100, "Coach");
            System.out.println("     ✗ ERROR: Invalid ID was accepted!");
        } catch (NullPointerException e) {
            System.out.println("     ✓ Correctly rejected null ID");
        } catch (Exception e) {
            System.out.println("     ✓ Handled: " + e.getMessage());
        }

        // Test 3: Invalid bogie type
        System.out.println("\n  3. Adding bogie with invalid type:");
        try {
            Bogie bogie = new Bogie("B1", null, 100, "Coach");
            System.out.println("     ⚠ Accepted null type (type validation may be optional)");
        } catch (Exception e) {
            System.out.println("     ✓ Rejected invalid type: " + e.getMessage());
        }
    }

    private static void capacityViolations() {
        System.out.println("Testing capacity violation scenarios:\n");

        Train train = null;
        try {
            train = new Train("TR022", 3);
            System.out.println("✓ Train created with max 3 bogies\n");
        } catch (Exception e) {
            System.out.println("✗ Failed to create train");
            return;
        }

        // Test 1: Exceed train capacity
        System.out.println("  1. Exceeding train bogie capacity:");
        try {
            for (int i = 1; i <= 5; i++) {
                Bogie bogie = new Bogie("B" + i, "Passenger", 100, "Coach-" + i);
                train.addBogie(bogie);
                System.out.println("     ✓ Bogie " + i + " added");
            }
        } catch (Exception e) {
            System.out.println("     ✓ Correctly rejected: " + e.getMessage());
            System.out.println("     Current train size: " + train.getTotalBogies() + "/" + train.getMaxBogies());
        }

        // Test 2: Duplicate bogie ID
        System.out.println("\n  2. Adding duplicate bogie ID:");
        try {
            if (train.getTotalBogies() < train.getMaxBogies()) {
                Bogie bogie1 = new Bogie("B_DUP", "Passenger", 100, "Coach-A");
                train.addBogie(bogie1);
                System.out.println("     ✓ First bogie added");

                Bogie bogie2 = new Bogie("B_DUP", "Passenger", 100, "Coach-B");
                train.addBogie(bogie2);
                System.out.println("     ✗ ERROR: Duplicate was accepted!");
            }
        } catch (Exception e) {
            System.out.println("     ✓ Correctly rejected duplicate: " + e.getMessage());
        }

        // Test 3: Passenger overflow
        System.out.println("\n  3. Passenger allocation overflow:");
        try {
            Bogie bogie = new Bogie("B_FULL", "Passenger", 50, "Limited Coach");
            train.addBogie(bogie);

            bogie.addPassengers(40);
            System.out.println("     ✓ Added 40 passengers (40/50)");

            bogie.addPassengers(15);
            System.out.println("     ✗ ERROR: Added 15 more (total 55/50) - should have failed!");

        } catch (Exception e) {
            System.out.println("     ✓ Correctly rejected overflow: " + e.getMessage());
        }
    }

    private static void nullPointerHandling() {
        System.out.println("Testing null pointer prevention:\n");

        // Test 1: Null train reference
        System.out.println("  1. Operations on null train:");
        Train nullTrain = null;
        try {
            if (nullTrain == null) {
                throw new InvalidTrainStateException("Train reference is null");
            }
            nullTrain.displayConfiguration();
        } catch (InvalidTrainStateException e) {
            System.out.println("     ✓ Handled null train: " + e.getMessage());
        }

        // Test 2: Null bogie in list
        System.out.println("\n  2. Processing null bogie in collection:");
        ArrayList<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("B1", "Passenger", 100, "Coach-1"));
        bogies.add(null);
        bogies.add(new Bogie("B2", "Passenger", 100, "Coach-2"));

        try {
            for (Bogie bogie : bogies) {
                if (bogie == null) {
                    System.out.println("     ⚠ Found null bogie in collection");
                    continue;
                }
                System.out.println("     - " + bogie.getBogieId());
            }
            System.out.println("     ✓ Handled null gracefully");
        } catch (NullPointerException e) {
            System.out.println("     ✗ Unhandled null pointer: " + e.getMessage());
        }
    }

    private static void resourceManagement() {
        System.out.println("Testing resource management with finally:\n");

        ArrayList<String> logs = new ArrayList<>();
        Train train = null;

        try {
            System.out.println("  Entering try block...");
            logs.add("Try block started");

            train = new Train("TR023", 5);
            logs.add("Train created");

            for (int i = 1; i <= 3; i++) {
                Bogie bogie = new Bogie("B" + i, "Passenger", 100, "Coach-" + i);
                train.addBogie(bogie);
                logs.add("Bogie B" + i + " added");
            }

            System.out.println("  ✓ Operations completed");

        } catch (Exception e) {
            System.out.println("  ✗ Exception caught: " + e.getMessage());
            logs.add("Exception: " + e.getMessage());

        } finally {
            System.out.println("  Entering finally block (cleanup)...");
            System.out.println("  ✓ Cleanup executed");

            // Cleanup: Display logs
            System.out.println("\n  Execution Log:");
            for (String log : logs) {
                System.out.println("    - " + log);
            }

            // Cleanup: Display final state
            if (train != null) {
                System.out.println("\n  Final Train State:");
                System.out.println("    - ID: " + train.getTrainId());
                System.out.println("    - Bogies: " + train.getTotalBogies() + "/" + train.getMaxBogies());
            }
        }
    }

    private static void chainedExceptions() {
        System.out.println("Testing chained exceptions:\n");

        System.out.println("  1. Basic exception chaining:");
        try {
            try {
                int result = 100 / 0; // ArithmeticException
            } catch (ArithmeticException e) {
                throw new BogieAllocationException("Failed to allocate passengers", e);
            }
        } catch (BogieAllocationException e) {
            System.out.println("     ✓ Caught chained exception: " + e.getMessage());
            System.out.println("     ✓ Root cause: " + e.getCause().getClass().getSimpleName());
        }

        System.out.println("\n  2. Multi-level exception handling:");
        try {
            operationWithChainedErrors();
        } catch (TrainException e) {
            System.out.println("     ✓ Caught: " + e.getClass().getSimpleName());
            System.out.println("     Message: " + e.getMessage());

            Throwable cause = e.getCause();
            while (cause != null) {
                System.out.println("     Caused by: " + cause.getClass().getSimpleName() + " - " + cause.getMessage());
                cause = cause.getCause();
            }
        }
    }

    private static void operationWithChainedErrors() throws BogieAllocationException {
        try {
            String str = null;
            int length = str.length(); // NullPointerException

        } catch (NullPointerException e) {
            try {
                throw new InvalidTrainStateException("Train state corrupted", e);

            } catch (InvalidTrainStateException e2) {
                throw new BogieAllocationException("Cannot allocate bogies due to train error", e2);
            }
        }
    }

    public static void main(String[] args) {
        execute();
    }
}
