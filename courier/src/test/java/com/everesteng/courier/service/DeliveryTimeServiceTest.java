/*
 * Name: Sayantika Kandar
 * Purpose: Unit tests for DeliveryTimeService.
 *
 * - Verifies that shipment and package delivery times are calculated correctly.
 * - Ensures validation (speed must be positive).
 * - Uses JUnit 5 for structured testing.
 */

package com.everesteng.courier.service;

import com.everesteng.courier.exception.CourierServiceException;
import com.everesteng.courier.model.Package;
import com.everesteng.courier.model.Shipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTimeServiceTest {

    private DeliveryTimeService service;

    @BeforeEach
    void setUp() {
        // Initialize a fresh DeliveryTimeService before each test
        service = new DeliveryTimeService();
    }

    /**
     * Test case: Shipment time calculation should fail
     * if vehicle speed is not greater than 0.
     */
    @Test
    void calculateShipmentTimes_ShouldThrow_WhenSpeedNotPositive() {
        assertThrows(CourierServiceException.class,
                () -> service.calculateShipmentTimes(Collections.emptyList(), 0));
    }

    /**
     * Test case: Shipment round-trip time should be based on
     * the maximum distance among all packages.
     *
     * Formula: time = (maxDistance / speed) * 2
     * Example: maxDistance = 100, speed = 50
     *          => 100/50 = 2 (one way), *2 = 4.0 (round trip)
     */
    @Test
    void calculateShipmentTimes_ShouldSetCorrectRoundTripTime() throws CourierServiceException {
        Package p1 = new Package("PKG1", 10, 100, "OFR001");
        Package p2 = new Package("PKG2", 5, 50, "OFR002");
        Shipment shipment = new Shipment(Arrays.asList(p1, p2), 15, 0, 0);

        service.calculateShipmentTimes(List.of(shipment), 50);

        assertEquals(4.0f, shipment.getTime());
    }

    /**
     * Test case: Package time calculation should fail
     * if vehicle speed is not greater than 0.
     */
    @Test
    void calculatePackageTimes_ShouldThrow_WhenSpeedNotPositive() {
        assertThrows(CourierServiceException.class,
                () -> service.calculatePackageTimes(Collections.emptyList(), -1));
    }

    /**
     * Test case: Each package’s delivery time should be
     * calculated individually based on its distance.
     *
     * Formula: time = distance / speed
     * Example: p1 distance = 100, speed = 50 => 2.0
     *          p2 distance = 75,  speed = 50 => 1.5
     */
    @Test
    void calculatePackageTimes_ShouldSetCorrectTimes() throws CourierServiceException {
        Package p1 = new Package("PKG1", 10, 100, "OFR001");
        Package p2 = new Package("PKG2", 5, 75, "OFR002");
        Shipment shipment = new Shipment(Arrays.asList(p1, p2), 15, 0, 0);

        service.calculatePackageTimes(List.of(shipment), 50);

        assertEquals(2.0f, p1.getDeliveryTime());
        assertEquals(1.5f, p2.getDeliveryTime());
    }
}