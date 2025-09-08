/*
 * Name: Sayantika Kandar
 * Purpose: Main service class orchestrating the courier system workflow.
 * It coordinates shipment creation, delivery time calculation,
 * vehicle assignment, and cost calculation by using other services.
 */

package com.everesteng.courier.service;

import com.everesteng.courier.exception.CourierServiceException;
import com.everesteng.courier.model.Package;
import com.everesteng.courier.model.Shipment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // Marks this class as a Spring-managed service component
public class CourierService {

    // Service to calculate applicable discounts on package costs
    private final DiscountService discountService;

    // Service to group packages into shipments based on constraints (e.g., max weight)
    private final ShipmentService shipmentService;

    // Service to calculate delivery times for packages and shipments
    private final DeliveryTimeService deliveryTimeService;

    // Service to assign vehicles to shipments for delivery
    private final VehicleService vehicleService;

    /**
     * Constructor-based dependency injection of required services.
     *
     * @param discountService      handles discount calculation
     * @param shipmentService      creates shipments from packages
     * @param deliveryTimeService  calculates package and shipment delivery times
     * @param vehicleService       assigns vehicles to shipments
     */
    public CourierService(DiscountService discountService,
                          ShipmentService shipmentService,
                          DeliveryTimeService deliveryTimeService,
                          VehicleService vehicleService) {
        this.discountService = discountService;
        this.shipmentService = shipmentService;
        this.deliveryTimeService = deliveryTimeService;
        this.vehicleService = vehicleService;
    }

    /**
     * Processes deliveries by creating shipments, calculating times,
     * and assigning vehicles.
     *
     * @param packages    list of packages to be delivered
     * @param baseCost    base delivery cost
     * @param maxWeight   maximum weight allowed per shipment
     * @param speed       delivery vehicle speed
     * @param numVehicles total number of vehicles available
     * @return list of shipments ready for delivery
     * @throws CourierServiceException if shipment creation or assignment fails
     */
    public List<Shipment> processDeliveries(List<Package> packages,
                                            int baseCost,
                                            int maxWeight,
                                            int speed,
                                            int numVehicles) throws CourierServiceException {

        // Step 1: Create shipments from packages based on weight constraints
        List<Shipment> shipments = shipmentService.createShipments(packages, maxWeight);

        // Step 2: Calculate delivery time for each package and shipment
        deliveryTimeService.calculatePackageTimes(shipments, speed);
        deliveryTimeService.calculateShipmentTimes(shipments, speed);

        // Step 3: Assign vehicles to shipments
        vehicleService.assignVehicles(shipments, numVehicles);

        return shipments;
    }

    /**
     * Calculates the final delivery cost of a package after applying discounts.
     *
     * @param pkg      the package to calculate cost for
     * @param baseCost base delivery cost (fixed charge)
     * @return final cost after discount
     */
    public int calculateFinalCost(Package pkg, int baseCost) {
        // Base cost + (weight * 10) + (distance * 5)
        int totalCost = baseCost + (pkg.getWeight() * 10) + (pkg.getDistance() * 5);

        // Apply discount via DiscountService
        int discount = discountService.calculateDiscount(pkg, totalCost);

        return totalCost - discount;
    }
}