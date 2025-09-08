/*
 * Name: Sayantika Kandar
 * Purpose: Unit tests for the ShipmentBuilder class.
 *
 * - Verifies that ShipmentBuilder correctly constructs Shipment objects.
 * - Tests include adding packages, setting attributes, and ensuring fluent API chaining works.
 */

package com.everesteng.courier.builder;

import com.everesteng.courier.model.Package;
import com.everesteng.courier.model.Shipment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentBuilderTest {

    /**
     * Test that a Shipment built with multiple packages,
     * delivery time, and vehicle assignment is constructed correctly.
     */
    @Test
    void testBuildShipmentWithPackages() {
        // Create test packages
        Package pkg1 = new Package("PKG1", 50, 30, "OFR001");
        Package pkg2 = new Package("PKG2", 70, 100, "OFR002");

        // Build shipment using builder pattern
        Shipment shipment = new ShipmentBuilder()
                .addPackage(pkg1)
                .addPackage(pkg2)
                .setTime(3.5f)
                .setVehicle(1)
                .build();

        // Assertions
        assertNotNull(shipment);                     // Shipment should not be null
        assertEquals(2, shipment.getPackages().size()); // 2 packages added
        assertEquals(120, shipment.getTotalWeight());   // Total weight = 50 + 70
        assertEquals(3.5f, shipment.getTime());         // Time set correctly
        assertEquals(1, shipment.getVehicle());         // Vehicle ID set correctly
    }

    /**
     * Test that an empty Shipment (no packages, no time, no vehicle)
     * is handled gracefully by the builder.
     */
    @Test
    void testBuildShipmentEmpty() {
        // Build shipment with no properties set
        Shipment shipment = new ShipmentBuilder().build();

        // Assertions
        assertNotNull(shipment);                     // Shipment object should still be created
        assertTrue(shipment.getPackages().isEmpty());   // No packages
        assertEquals(0, shipment.getTotalWeight());     // Weight = 0
        assertEquals(0, shipment.getTime());            // Time = 0
        assertEquals(0, shipment.getVehicle());         // Vehicle = 0
    }

    /**
     * Test fluent API chaining of ShipmentBuilder methods.
     * Ensures that method chaining works properly and final object is correct.
     */
    @Test
    void testFluentApiChaining() {
        // Create single package
        Package pkg = new Package("PKG3", 20, 15, "NA");

        // Build shipment using fluent chaining
        ShipmentBuilder builder = new ShipmentBuilder()
                .addPackage(pkg)
                .setTime(2.0f)
                .setVehicle(2);

        // Build final shipment
        Shipment shipment = builder.build();

        // Assertions
        assertEquals(20, shipment.getTotalWeight());   // Weight = 20
        assertEquals(2.0f, shipment.getTime());        // Time = 2.0
        assertEquals(2, shipment.getVehicle());        // Vehicle ID = 2
    }
}