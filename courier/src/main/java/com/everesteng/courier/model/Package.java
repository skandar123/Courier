/*
 * Name: Sayantika Kandar
 * Purpose: Represents a package in the courier service system.
 * Each package has an ID, weight, distance to be delivered, an optional offer code,
 * and a calculated delivery time. Validation is applied to ensure data consistency.
 */

package com.everesteng.courier.model;

import java.util.Objects;

public class Package {
    // Unique identifier for the package
    private String id;

    // Weight of the package (must be non-negative)
    private int weight;

    // Distance to be covered for delivery (must be non-negative)
    private int distance;

    // Promotional offer code (if any), can be empty but not null
    private String offerCode;

    // Delivery time (calculated later, initialized to 0 by default)
    private float deliveryTime;

    /**
     * Constructor to create a new Package instance.
     *
     * @param id        unique identifier for the package (cannot be null or empty)
     * @param weight    weight of the package (must be >= 0)
     * @param distance  delivery distance for the package (must be >= 0)
     * @param offerCode discount/promo code (nullable, defaults to empty string if null)
     */
    public Package(String id, int weight, int distance, String offerCode) {
        // Validate package ID
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Package ID cannot be null or empty");
        }
        // Validate weight
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        // Validate distance
        if (distance < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }

        this.id = id.trim(); // trim spaces from ID
        this.weight = weight;
        this.distance = distance;
        this.offerCode = (offerCode != null) ? offerCode.trim() : ""; // handle null offerCode
        this.deliveryTime = 0.0f; // default delivery time
    }

    // ---------------------- Getters ----------------------

    public String getId() { return id; }
    public int getWeight() { return weight; }
    public int getDistance() { return distance; }
    public String getOfferCode() { return offerCode; }
    public float getDeliveryTime() { return deliveryTime; }

    // ---------------------- Setters ----------------------

    /**
     * Sets the delivery time for the package.
     *
     * @param deliveryTime delivery time in hours (must be >= 0)
     */
    public void setDeliveryTime(float deliveryTime) {
        if (deliveryTime < 0) {
            throw new IllegalArgumentException("Delivery time cannot be negative");
        }
        this.deliveryTime = deliveryTime;
    }

    // ---------------------- Utility Methods ----------------------

    /**
     * Returns a string representation of the Package object.
     */
    @Override
    public String toString() {
        return "Package{" +
                "id='" + id + '\'' +
                ", weight=" + weight +
                ", distance=" + distance +
                ", offerCode='" + offerCode + '\'' +
                ", deliveryTime=" + deliveryTime +
                '}';
    }

    /**
     * Checks equality based on the unique package ID.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // same reference
        if (obj == null || getClass() != obj.getClass()) return false; // null or different class
        Package pkg = (Package) obj;
        return Objects.equals(id, pkg.id); // compare by package ID only
    }

    /**
     * Generates a hash code consistent with equals(), based on package ID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}