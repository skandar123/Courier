/*
 * Name: Sayantika Kandar
 * Purpose: Unit tests for the Shipment model class.
 *
 * - Verifies constructor input validation (packages, weight, time).
 * - Ensures addPackages correctly updates package list and total weight.
 * - Validates setter methods for time and vehicle ID.
 */

package com.everesteng.courier.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ShipmentTest {

    /**
     * Test constructor validation for Shipment.
     * - Should throw IllegalArgumentException when:
     *   1. Packages list is null.
     *   2. Total weight is negative.
     *   3. Time is negative.
     */
    @Test
    void constructor_ShouldThrow_WhenInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> new Shipment(null, 10, 1.0f, 1));      // Null packages list
        assertThrows(IllegalArgumentException.class,
                () -> new Shipment(List.of(), -10, 1.0f, 1)); // Negative weight
        assertThrows(IllegalArgumentException.class,
                () -> new Shipment(List.of(), 10, -1.0f, 1)); // Negative time
    }

    /**
     * Test addPackages() method.
     * - Ensures that adding a package increases the package count
     *   and updates the total weight correctly.
     */
    @Test
    void addPackages_ShouldIncreaseWeight() {
        Shipment shipment = new Shipment();
        Package pkg = new Package("PKG1", 10, 20, "OFR001");

        shipment.addPackages(pkg, 10);  // Add a 10kg package

        assertEquals(10, shipment.getTotalWeight());  // Total weight updated
        assertEquals(1, shipment.getPackages().size()); // Package count updated
    }

    /**
     * Test setter validations for time and vehicle.
     * - setTime() should throw if time is negative.
     * - setVehicle() should throw if vehicle ID is negative.
     */
    @Test
    void setTimeAndVehicle_ShouldThrow_WhenNegative() {
        Shipment shipment = new Shipment();

        assertThrows(IllegalArgumentException.class, () -> shipment.setTime(-1));    // Negative time
        assertThrows(IllegalArgumentException.class, () -> shipment.setVehicle(-1)); // Negative vehicle ID
    }
}