import java.util.*;

/**
 * Train Class - Represents a complete train consist
 * Manages the arrangement and validation of multiple bogies
 */
public class Train {
    private String trainId;
    private ArrayList<Bogie> bogies;
    private int maxBogies;

    // Constructor
    public Train(String trainId, int maxBogies) {
        this.trainId = trainId;
        this.bogies = new ArrayList<>();
        this.maxBogies = maxBogies;
    }

    // Getters
    public String getTrainId() {
        return trainId;
    }

    public ArrayList<Bogie> getBogies() {
        return new ArrayList<>(bogies);
    }

    public int getTotalBogies() {
        return bogies.size();
    }

    public int getMaxBogies() {
        return maxBogies;
    }

    // Add Bogie
    public boolean addBogie(Bogie bogie) throws Exception {
        if (bogies.size() >= maxBogies) {
            throw new Exception("Train capacity reached. Cannot add more bogies.");
        }
        if (bogieExists(bogie.getBogieId())) {
            throw new Exception("Bogie with ID " + bogie.getBogieId() + " already exists.");
        }
        return bogies.add(bogie);
    }

    // Remove Bogie
    public boolean removeBogie(String bogieId) {
        return bogies.removeIf(b -> b.getBogieId().equals(bogieId));
    }

    // Find Bogie
    public Bogie findBogie(String bogieId) {
        for (Bogie bogie : bogies) {
            if (bogie.getBogieId().equals(bogieId)) {
                return bogie;
            }
        }
        return null;
    }

    // Check if Bogie exists
    public boolean bogieExists(String bogieId) {
        return findBogie(bogieId) != null;
    }

    // Get total seats
    public int getTotalSeats() {
        int total = 0;
        for (Bogie bogie : bogies) {
            total += bogie.getCapacity();
        }
        return total;
    }

    // Get occupied seats
    public int getOccupiedSeats() {
        int occupied = 0;
        for (Bogie bogie : bogies) {
            occupied += bogie.getSeatsOccupied();
        }
        return occupied;
    }

    // Display Train Configuration
    public void displayConfiguration() {
        System.out.println("\n========== TRAIN CONFIGURATION ==========");
        System.out.println("Train ID: " + trainId);
        System.out.println("Total Bogies: " + bogies.size() + "/" + maxBogies);
        System.out.println("Total Capacity: " + getTotalSeats() + " seats");
        System.out.println("Occupied Seats: " + getOccupiedSeats());
        System.out.println("----- Bogies -----");
        int index = 1;
        for (Bogie bogie : bogies) {
            System.out.println(index + ". " + bogie);
            index++;
        }
        System.out.println("=========================================\n");
    }
}
