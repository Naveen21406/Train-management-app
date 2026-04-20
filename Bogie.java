/**
 * Bogie Class - Represents a single coach unit in a train
 * This class encapsulates the properties and behavior of a railway coach
 */
public class Bogie {
    private String bogieId;
    private String bogieType; // "Passenger" or "Cargo"
    private int capacity;
    private String bogieName;
    private int seatsOccupied;

    // Constructor
    public Bogie(String bogieId, String bogieType, int capacity, String bogieName) {
        this.bogieId = bogieId;
        this.bogieType = bogieType;
        this.capacity = capacity;
        this.bogieName = bogieName;
        this.seatsOccupied = 0;
    }

    // Getters
    public String getBogieId() {
        return bogieId;
    }

    public String getBogieType() {
        return bogieType;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getBogieName() {
        return bogieName;
    }

    public int getSeatsOccupied() {
        return seatsOccupied;
    }

    // Setters
    public void setSeatsOccupied(int seatsOccupied) {
        if (seatsOccupied >= 0 && seatsOccupied <= capacity) {
            this.seatsOccupied = seatsOccupied;
        } else {
            throw new IllegalArgumentException("Invalid seats count: " + seatsOccupied);
        }
    }

    // Methods
    public int getAvailableSeats() {
        return capacity - seatsOccupied;
    }

    public void addPassengers(int count) throws Exception {
        if (seatsOccupied + count > capacity) {
            throw new Exception("Cannot add " + count + " passengers. Only " + getAvailableSeats() + " seats available.");
        }
        seatsOccupied += count;
    }

    public boolean canAccommodate(int cargoWeight) {
        return capacity >= cargoWeight;
    }

    @Override
    public String toString() {
        return String.format("[ID: %s | Type: %s | Name: %s | Capacity: %d | Occupied: %d | Available: %d]",
                bogieId, bogieType, bogieName, capacity, seatsOccupied, getAvailableSeats());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Bogie bogie = (Bogie) obj;
        return bogieId.equals(bogie.bogieId);
    }

    @Override
    public int hashCode() {
        return bogieId.hashCode();
    }
}
