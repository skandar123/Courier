/*
 * Name: Sayantika Kandar
 * Purpose: Represents a shipment in the courier service system.
 * A shipment groups multiple packages, tracks their total weight,
 * delivery time, and the assigned vehicle. Validation is applied
 * to maintain data integrity.
 */

package com.everesteng.courier.model;

import java.util.ArrayList;
import java.util.List;

public class Shipment {

    // List of packages included in this shipment
    private List<Package> packages;

    // Combined weight of all packages in the shipment
    private int totalWeight;

    // Delivery time required for this shipment
    private float time;

    // Vehicle ID assigned to deliver this shipment
    private int vehicle;

    /**
     * Default constructor initializes shipment with empty package list
     * and default values for other fields.
     */
    public Shipment() {
        this.packages = new ArrayList<>();
        this.totalWeight = 0;
        this.time = 0.0f;
        this.vehicle = 0;
    }

    /**
     * Parameterized constructor to create a Shipment with given details.
     *
     * @param packages    list of packages in the shipment (cannot be null)
     * @param totalWeight total weight of all packages (must be >= 0)
     * @param time        delivery time in hours (must be >= 0)
     * @param vehicle     vehicle ID assigned for delivery
     */
    public Shipment(List<Package> packages, int totalWeight, float time, int vehicle) {
        if (packages == null) {
            throw new IllegalArgumentException("Packages list cannot be null");
        }
        if (totalWeight < 0) {
            throw new IllegalArgumentException("Total weight cannot be negative");
        }
        if (time < 0) {
            throw new IllegalArgumentException("Time cannot be negative");
        }

        // Defensive copy of package list to prevent external modifications
        this.packages = new ArrayList<>(packages);
        this.totalWeight = totalWeight;
        this.time = time;
        this.vehicle = vehicle;
    }

    // ---------------------- Getters ----------------------

    /**
     * Returns a copy of the packages list to preserve encapsulation.
     */
    public List<Package> getPackages() {
        return new ArrayList<>(packages);
    }

    public int getTotalWeight() { return totalWeight; }
    public float getTime() { return time; }
    public int getVehicle() { return vehicle; }

    // ---------------------- Setters ----------------------

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    /**
     * Sets delivery time for the shipment.
     *
     * @param time delivery time (must be >= 0)
     */
    public void setTime(float time) {
        if (time < 0) {
            throw new IllegalArgumentException("Time cannot be negative");
        }
        this.time = time;
    }

    /**
     * Sets the vehicle ID for this shipment.
     *
     * @param vehicle vehicle identifier (must be >= 0)
     */
    public void setVehicle(int vehicle) {
        if (vehicle < 0) {
            throw new IllegalArgumentException("Vehicle ID cannot be negative");
        }
        this.vehicle = vehicle;
    }

    /**
     * Adds a single package to the shipment and updates total weight.
     *
     * @param pkg    the package to be added (cannot be null)
     * @param weight weight of the package (must be >= 0)
     */
    public void addPackages(Package pkg, int weight) {
        if (pkg == null) {
            throw new IllegalArgumentException("Package cannot be null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }

        packages.add(pkg);
        totalWeight += weight;
    }

    /**
     * Returns a summary string of the shipment details.
     */
    @Override
    public String toString() {
        return "Shipment{" +
                "packages=" + packages.size() +   // print number of packages instead of full list
                ", totalWeight=" + totalWeight +
                ", time=" + time +
                ", vehicle=" + vehicle +
                '}';
    }
}