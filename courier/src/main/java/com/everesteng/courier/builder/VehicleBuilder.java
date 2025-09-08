/*
 * Name: Sayantika Kandar
 * Purpose: VehicleBuilder helps in constructing Vehicle objects
 * step by step using the Builder design pattern.
 * It allows setting vehicleId and availability before building the Vehicle.
 */

package com.everesteng.courier.builder;

import com.everesteng.courier.model.Vehicle;

public class VehicleBuilder {

    // Unique identifier for the vehicle (e.g., ID or number)
    private int vehicleId;

    // Availability of the vehicle (e.g., time when the vehicle is free for the next shipment)
    private float availability;

    /**
     * Sets the ID of the vehicle.
     *
     * @param vehicleId the unique vehicle identifier
     * @return this builder instance (to allow method chaining)
     */
    public VehicleBuilder setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    /**
     * Sets the availability time of the vehicle.
     *
     * @param availability float value representing availability (e.g., in hours)
     * @return this builder instance (to allow method chaining)
     */
    public VehicleBuilder setAvailability(float availability) {
        this.availability = availability;
        return this;
    }

    /**
     * Builds the final Vehicle object using the data set in this builder.
     *
     * @return a fully constructed Vehicle object
     */
    public Vehicle build() {
        // Create a new Vehicle with the provided values
        Vehicle vehicle = new Vehicle(vehicleId, availability);

        // Explicitly set properties (may be redundant if constructor already sets them)
        vehicle.setVehicleId(vehicleId);
        vehicle.setAvailability(availability);

        return vehicle;
    }
}