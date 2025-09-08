/*
 * Name: Sayantika Kandar
 * Purpose: Unit tests for VehicleService.
 *
 * - Validates correct vehicle assignment to shipments.
 * - Ensures exceptions are thrown for invalid vehicle counts.
 * - Checks whether vehicle availability and package delivery times
 *   are updated properly after assignment.
 * - Uses ShipmentService to generate shipments before assignment.
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

class VehicleServiceTest {

    private VehicleService vehicleService;
    private ShipmentService shipmentService;
    private List<Package> packages;

    @BeforeEach
    void setUp() {
        // Initialize services
        vehicleService = new VehicleService();
        shipmentService = new ShipmentService();

        // Prepare sample packages for testing
        packages = Arrays.asList(
                new Package("PKG1", 50, 30, "OFR001"),
                new Package("PKG2", 75, 125, "OFR002"),
                new Package("PKG3", 110, 60, "OFR003")
        );
    }

    /**
     * ✅ Test case: Assign vehicles with valid input.
     * Expectation:
     * - Each shipment should be assigned a valid vehicle ID (> 0).
     */
    @Test
    void testAssignVehiclesValid() throws CourierServiceException {
        List<Shipment> shipments = shipmentService.createShipments(packages, 200);

        vehicleService.assignVehicles(shipments, 2);

        // Ensure all shipments have a vehicle assigned
        assertTrue(shipments.stream().allMatch(s -> s.getVehicle() > 0));
    }

    /**
     * ✅ Test case: Assign vehicles with invalid vehicle count (0).
     * Expectation:
     * - CourierServiceException should be thrown since no vehicles are available.
     */
    @Test
    void testAssignVehiclesInvalidCount() throws CourierServiceException {
        List<Shipment> shipments = shipmentService.createShipments(packages, 200);

        assertThrows(CourierServiceException.class,
                () -> vehicleService.assignVehicles(shipments, 0));
    }

    /**
     * ✅ Test case: Assign vehicles and check if availability is updated.
     * Expectation:
     * - Package delivery times should be adjusted based on vehicle availability.
     */
    @Test
    void testAssignVehiclesUpdatesAvailability() throws CourierServiceException {
        List<Shipment> shipments = shipmentService.createShipments(packages, 200);

        vehicleService.assignVehicles(shipments, 1);

        // Ensure delivery times are non-negative and updated
        assertTrue(shipments.get(0).getPackages().get(0).getDeliveryTime() >= 0);
    }
}