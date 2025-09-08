/*
 * Name: Sayantika Kandar
 * Purpose: Unit tests for the Vehicle model class.
 *
 * - Validates constructor input checks (vehicleId, availability).
 * - Ensures setters also enforce non-negative constraints.
 */

package com.everesteng.courier.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    /**
     * Test constructor validation.
     * - Should throw IllegalArgumentException when:
     *   1. Vehicle ID is negative.
     *   2. Availability time is negative.
     */
    @Test
    void constructor_ShouldThrow_WhenNegativeValues() {
        assertThrows(IllegalArgumentException.class,
                () -> new Vehicle(-1, 0));   // Invalid: negative vehicle ID
        assertThrows(IllegalArgumentException.class,
                () -> new Vehicle(1, -5));  // Invalid: negative availability
    }

    /**
     * Test setter validation.
     * - setVehicleId() should throw if ID is negative.
     * - setAvailability() should throw if availability is negative.
     */
    @Test
    void setters_ShouldThrow_WhenNegative() {
        Vehicle v = new Vehicle(1, 0);

        assertThrows(IllegalArgumentException.class,
                () -> v.setVehicleId(-2));   // Invalid: negative vehicle ID
        assertThrows(IllegalArgumentException.class,
                () -> v.setAvailability(-1)); // Invalid: negative availability
    }
}