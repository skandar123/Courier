/*
 * Name: Sayantika Kandar
 * Purpose: ShipmentBuilder helps in constructing Shipment objects step by step
 * using the Builder design pattern. It allows adding packages, setting
 * delivery time, and assigning vehicles before finally building a Shipment.
 */

package com.everesteng.courier.builder;

import com.everesteng.courier.model.Package;
import com.everesteng.courier.model.Shipment;

import java.util.ArrayList;
import java.util.List;

public class ShipmentBuilder {

    // Holds the list of packages added to this shipment
    private final List<Package> packages = new ArrayList<>();

    // Stores the total weight of all packages in this shipment
    private int totalWeight = 0;

    // Stores the delivery time required for this shipment
    private float time = 0;

    // Stores the vehicle assigned to carry this shipment
    private int vehicle = 0;

    /**
     * Adds a package to the shipment and updates total weight.
     *
     * @param pkg the package to be added
     * @return this builder instance (for method chaining)
     */
    public ShipmentBuilder addPackage(Package pkg) {
        this.packages.add(pkg);
        this.totalWeight += pkg.getWeight();
        return this;
    }

    /**
     * Sets the delivery time for the shipment.
     *
     * @param time estimated time for delivery
     * @return this builder instance (for method chaining)
     */
    public ShipmentBuilder setTime(float time) {
        this.time = time;
        return this;
    }

    /**
     * Sets the vehicle assigned for this shipment.
     *
     * @param vehicle vehicle identifier (e.g., vehicle number or ID)
     * @return this builder instance (for method chaining)
     */
    public ShipmentBuilder setVehicle(int vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    /**
     * Builds the final Shipment object using the collected data.
     *
     * @return a fully constructed Shipment object
     */
    public Shipment build() {
        Shipment shipment = new Shipment();
        // Copy all added packages into the Shipment
        shipment.setPackages(new ArrayList<>(packages));
        shipment.setTotalWeight(totalWeight);
        shipment.setTime(time);
        shipment.setVehicle(vehicle);
        return shipment;
    }
}