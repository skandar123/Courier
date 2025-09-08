/*
 * Name: Sayantika Kandar
 * Purpose: Unit tests for the VehicleBuilder class.
 *
 * - Verifies that VehicleBuilder correctly constructs Vehicle objects.
 * - Tests include setting valid values, default values, and fluent API chaining.
 */

package com.everesteng.courier.builder;

import com.everesteng.courier.model.Vehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleBuilderTest {

    /**
     * Test building a Vehicle with valid values.
     * Ensures VehicleBuilder correctly sets vehicleId and availability.
     */
    @Test
    void testBuildVehicleValid() {
        // Build vehicle with custom values
        Vehicle vehicle = new VehicleBuilder()
                .setVehicleId(1)
                .setAvailability(5.5F)
                .build();

        // Assertions
        assertNotNull(vehicle);                        // Vehicle should not be null
        assertEquals(1, vehicle.getVehicleId());       // Vehicle ID set correctly
        assertEquals(5.5, vehicle.getAvailability());  // Availability set correctly
    }

    /**
     * Test building a Vehicle without setting any values.
     * Ensures default values (0 for ID, 0.0 for availability) are applied.
     */
    @Test
    void testBuildVehicleDefaultValues() {
        // Build vehicle with no properties set
        Vehicle vehicle = new VehicleBuilder().build();

        // Assertions
        assertNotNull(vehicle);                         // Vehicle should still be created
        assertEquals(0, vehicle.getVehicleId());        // Default ID = 0
        assertEquals(0.0, vehicle.getAvailability());   // Default availability = 0.0
    }

    /**
     * Test fluent API chaining of VehicleBuilder methods.
     * Ensures method chaining works properly and values persist in the final object.
     */
    @Test
    void testFluentApiChaining() {
        // Create builder and chain methods
        VehicleBuilder builder = new VehicleBuilder()
                .setVehicleId(2)
                .setAvailability(10.0F);

        // Build final vehicle
        Vehicle vehicle = builder.build();

        // Assertions
        assertEquals(2, vehicle.getVehicleId());        // Vehicle ID set via chaining
        assertEquals(10.0, vehicle.getAvailability());  // Availability set via chaining
    }
}