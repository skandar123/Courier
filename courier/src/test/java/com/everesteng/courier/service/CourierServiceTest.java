/*
 * Name: Sayantika Kandar
 * Purpose: Unit tests for the CourierService class.
 *
 * - Uses Mockito to mock dependent services (DiscountService, ShipmentService,
 *   DeliveryTimeService, VehicleService).
 * - Validates business logic such as final cost calculation and delivery processing.
 */

package com.everesteng.courier.service;

import com.everesteng.courier.exception.CourierServiceException;
import com.everesteng.courier.model.Package;
import com.everesteng.courier.model.Shipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // ✅ Enables Mockito for JUnit 5 tests
class CourierServiceTest {

    // Mocked service dependencies
    @Mock
    private DiscountService discountService;

    @Mock
    private ShipmentService shipmentService;

    @Mock
    private DeliveryTimeService deliveryTimeService;

    @Mock
    private VehicleService vehicleService;

    // Class under test, with mocks automatically injected
    @InjectMocks
    private CourierService courierService;

    @BeforeEach
    void setUp() {
        // No manual setup required since @InjectMocks handles dependency injection
    }

    /**
     * Test case: Validates that discount is applied correctly
     * when calculating the final delivery cost of a package.
     */
    @Test
    void calculateFinalCost_ShouldSubtractDiscount() {
        Package pkg = new Package("PKG1", 10, 20, "OFR001");

        // Mocking discount service to return a fixed discount
        when(discountService.calculateDiscount(any(), anyInt())).thenReturn(50);

        // Calculate final cost
        int result = courierService.calculateFinalCost(pkg, 100);

        // Expected cost = baseCost + (weight * 10) + (distance * 5) - discount
        int expected = (100 + (10 * 10) + (20 * 5)) - 50;

        assertEquals(expected, result);
    }

    /**
     * Test case: Ensures CourierService delegates properly to
     * dependent services when processing deliveries.
     */
    @Test
    void processDeliveries_ShouldCallDependencies() throws CourierServiceException {
        Package pkg = new Package("PKG1", 10, 20, "OFR001");
        Shipment shipment = new Shipment(List.of(pkg), 10, 0, 0);

        // Mocking shipment creation service
        when(shipmentService.createShipments(anyList(), anyInt()))
                .thenReturn(List.of(shipment));

        // Execute method under test
        List<Shipment> result = courierService.processDeliveries(List.of(pkg), 100, 50, 60, 2);

        // ✅ Verify that CourierService calls its dependencies in order
        verify(shipmentService).createShipments(anyList(), eq(50));
        verify(deliveryTimeService).calculatePackageTimes(anyList(), eq(60));
        verify(deliveryTimeService).calculateShipmentTimes(anyList(), eq(60));
        verify(vehicleService).assignVehicles(anyList(), eq(2));

        // Assert that a shipment was returned
        assertEquals(1, result.size());
    }
}