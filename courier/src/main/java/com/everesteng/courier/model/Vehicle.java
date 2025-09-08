/*
 * Name: Sayantika Kandar
 * Purpose: Represents a delivery vehicle in the courier service system.
 * Each vehicle has a unique ID and an availability time (i.e., when it
 * becomes free for the next delivery). Validation ensures values remain valid.
 */

package com.everesteng.courier.model;

public class Vehicle {

    // Unique identifier for the vehicle (e.g., vehicle number or ID)
    private int vehicleId;

    // Availability time of the vehicle (e.g., when it is next available for delivery)
    private float availability;

    /**
     * Constructor to initialize a Vehicle with ID and availability.
     *
     * @param vehicleId   unique identifier of the vehicle (must be >= 0)
     * @param availability time when vehicle becomes available (must be >= 0)
     */
    public Vehicle(int vehicleId, float availability) {
        if (vehicleId < 0) {
            throw new IllegalArgumentException("Vehicle ID cannot be negative");
        }
        if (availability < 0) {
            throw new IllegalArgumentException("Availability time cannot be negative");
        }

        this.vehicleId = vehicleId;
        this.availability = availability;
    }

    // ---------------------- Getters ----------------------

    public int getVehicleId() { return vehicleId; }
    public float getAvailability() { return availability; }

    // ---------------------- Setters ----------------------

    /**
     * Updates the vehicle ID.
     *
     * @param vehicleId vehicle identifier (must be >= 0)
     */
    public void setVehicleId(int vehicleId) {
        if (vehicleId < 0) {
            throw new IllegalArgumentException("Vehicle ID cannot be negative");
        }
        this.vehicleId = vehicleId;
    }

    /**
     * Updates the availability of the vehicle.
     *
     * @param availability new availability time (must be >= 0)
     */
    public void setAvailability(float availability) {
        if (availability < 0) {
            throw new IllegalArgumentException("Availability time cannot be negative");
        }
        this.availability = availability;
    }

    // ---------------------- Utility Methods ----------------------

    /**
     * Returns a string representation of the Vehicle object.
     */
    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", availability=" + availability +
                '}';
    }
}