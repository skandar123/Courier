/*
 * Name: Sayantika Kandar
 * Purpose: Unit tests for ShipmentService.
 *
 * - Validates that shipments are created correctly from given packages.
 * - Ensures weight constraints are respected.
 * - Covers edge cases such as empty lists and invalid weight limits.
 * - Uses JUnit 5 testing framework.
 */

package com.everesteng.courier.service;

import com.everesteng.courier.exception.CourierServiceException;
import com.everesteng.courier.model.Package;
import com.everesteng.courier.model.Shipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentServiceTest {

    private ShipmentService shipmentService;
    private List<Package> packages;

    @BeforeEach
    void setUp() {
        // Initialize the service under test
        shipmentService = new ShipmentService();

        // Create a sample list of packages with different weights and distances
        packages = Arrays.asList(
                new Package("PKG1", 50, 30, "OFR001"),
                new Package("PKG2", 75, 125, "OFR002"),
                new Package("PKG3", 110, 60, "OFR003")
        );
    }

    /**
     * ✅ Test case: Valid package list with a sufficient weight limit.
     * Expectation:
     * - Shipments should be created successfully.
     * - The result should not be null or empty.
     * - The sum of shipment weights should be positive.
     */
    @Test
    void testCreateShipmentsValid() throws CourierServiceException {
        List<Shipment> shipments = shipmentService.createShipments(packages, 200);

        assertNotNull(shipments);
        assertFalse(shipments.isEmpty());
        assertTrue(shipments.stream().mapToInt(Shipment::getTotalWeight).sum() > 0);
    }

    /**
     * ✅ Test case: Package exceeds the maximum carriable weight.
     * Expectation:
     * - CourierServiceException should be thrown because no vehicle can carry the package.
     */
    @Test
    void testCreateShipmentsExceedsWeight() {
        List<Package> tooHeavy = List.of(
                new Package("PKG9", 500, 20, "NA")
        );

        assertThrows(CourierServiceException.class,
                () -> shipmentService.createShipments(tooHeavy, 100));
    }

    /**
     * ✅ Test case: Empty package list provided.
     * Expectation:
     * - CourierServiceException should be thrown since shipments cannot be created.
     */
    @Test
    void testCreateShipmentsWithEmptyList() {
        assertThrows(CourierServiceException.class,
                () -> shipmentService.createShipments(List.of(), 100));
    }

    /**
     * ✅ Test case: Negative maximum carriable weight provided.
     * Expectation:
     * - CourierServiceException should be thrown because the constraint is invalid.
     */
    @Test
    void testCreateShipmentsWithNegativeWeightLimit() {
        assertThrows(CourierServiceException.class,
                () -> shipmentService.createShipments(packages, -50));
    }
}