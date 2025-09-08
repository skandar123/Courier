/*
 * Name: Sayantika Kandar
 * Purpose: Service class responsible for creating shipments by grouping packages
 * based on the maximum carriable weight of a vehicle.
 *
 * This class applies a greedy approach:
 *   - Sorts packages by descending weight
 *   - Iteratively assigns them into shipments using ShipmentBuilder
 *   - Ensures no package exceeds the max carriable weight
 *   - Distributes all packages across shipments
 */

package com.everesteng.courier.service;

import com.everesteng.courier.builder.ShipmentBuilder;
import com.everesteng.courier.exception.CourierServiceException;
import com.everesteng.courier.model.Package;
import com.everesteng.courier.model.Shipment;
import org.springframework.stereotype.Service;

import java.util.*;

@Service  // Marks this class as a Spring-managed service
public class ShipmentService {

    /**
     * Creates shipments from a given list of packages based on the max carriable weight.
     *
     * - Packages are sorted by descending weight for efficient packing.
     * - Uses a greedy algorithm: tries to fill a shipment until weight limit is reached.
     * - If a package exceeds the max carriable weight, an exception is thrown.
     * - All packages are guaranteed to be included in one of the shipments.
     *
     * @param packages            list of packages to be shipped
     * @param maxCarriableWeight  maximum weight capacity of a single shipment
     * @return a list of shipments sorted by total weight in descending order
     * @throws CourierServiceException if input validation fails or a package exceeds max weight
     */
    public List<Shipment> createShipments(List<Package> packages, int maxCarriableWeight)
            throws CourierServiceException {

        // Validate inputs
        if (packages == null || packages.isEmpty()) {
            throw new CourierServiceException("Packages list cannot be null or empty");
        }
        if (maxCarriableWeight <= 0) {
            throw new CourierServiceException("Max carriable weight must be positive");
        }

        // Sort packages in descending order by weight (heaviest first)
        List<Package> sortedPackages = packages.stream()
                .sorted(Comparator.comparingInt(Package::getWeight).reversed())
                .toList();

        List<Shipment> shipments = new ArrayList<>();   // Final list of shipments
        Set<Package> added = new HashSet<>();           // Tracks already assigned packages

        // Continue until all packages are assigned to a shipment
        while (added.size() < sortedPackages.size()) {
            ShipmentBuilder builder = new ShipmentBuilder(); // Helps construct shipments
            int currentWeight = 0;

            // Try to fit remaining packages into the current shipment
            for (Package pkg : sortedPackages) {
                if (added.contains(pkg)) continue;  // Skip if already assigned

                // Package exceeds weight capacity of vehicle → invalid case
                if (pkg.getWeight() > maxCarriableWeight) {
                    throw new CourierServiceException(
                            "Some packages cannot be assigned due to exceeding weight limit"
                    );
                }

                // Add package to shipment if it fits within weight limit
                if (pkg.getWeight() + currentWeight <= maxCarriableWeight) {
                    builder.addPackage(pkg);
                    currentWeight += pkg.getWeight();
                    added.add(pkg);
                }
            }

            // Finalize the shipment and add it to the list
            Shipment shipment = builder.build();
            if (!shipment.getPackages().isEmpty()) {
                shipments.add(shipment);
            }
        }

        // Sort shipments by total weight in descending order (heaviest first)
        shipments.sort(Comparator.comparingInt(Shipment::getTotalWeight).reversed());
        return shipments;
    }
}