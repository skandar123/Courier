/*
 * Name: Sayantika Kandar
 * Purpose: Service class to calculate delivery times for shipments and packages.
 * It ensures that delivery times are computed based on vehicle speed
 * and distances, with basic validation and rounding applied.
 */

package com.everesteng.courier.service;

import com.everesteng.courier.exception.CourierServiceException;
import com.everesteng.courier.model.Package;
import com.everesteng.courier.model.Shipment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // Marks this as a Spring-managed service component
public class DeliveryTimeService {

    /**
     * Calculates the round-trip delivery time for each shipment.
     * The shipment time is based on the maximum distance of any package
     * in that shipment (since all packages in the same shipment are delivered together).
     *
     * Formula:
     *   time = (maxDistance / speed), rounded to 2 decimal places
     *   time *= 2  (round trip: going + returning)
     *
     * @param shipments list of shipments to process
     * @param speed     speed of delivery vehicles (must be > 0)
     * @throws CourierServiceException if speed is invalid
     */
    public void calculateShipmentTimes(List<Shipment> shipments, int speed)
            throws CourierServiceException {
        if (speed <= 0) throw new CourierServiceException("Speed must be positive");

        for (Shipment s : shipments) {
            // Find the maximum distance among all packages in the shipment
            int maxDistance = s.getPackages().stream()
                    .mapToInt(Package::getDistance)
                    .max()
                    .orElse(0);

            // Calculate time = distance / speed
            float time = ((float) maxDistance / speed);

            // Round to 2 decimal places
            time = (int) (time * 100) / 100f;

            // Multiply by 2 for round-trip (to deliver and return)
            time *= 2;

            // Set shipment time
            s.setTime(time);
        }
    }

    /**
     * Calculates the one-way delivery time for each package within shipments.
     * Each package delivery time depends only on its own distance.
     *
     * Formula:
     *   time = (distance / speed), rounded to 2 decimal places
     *
     * @param shipments list of shipments containing packages
     * @param speed     speed of delivery vehicles (must be > 0)
     * @throws CourierServiceException if speed is invalid
     */
    public void calculatePackageTimes(List<Shipment> shipments, int speed)
            throws CourierServiceException {
        if (speed <= 0) throw new CourierServiceException("Speed must be positive");

        for (Shipment s : shipments) {
            for (Package pkg : s.getPackages()) {
                // Calculate time = distance / speed
                float time = ((float) pkg.getDistance() / speed);

                // Round to 2 decimal places
                time = (int) (time * 100) / 100f;

                // Set delivery time for the package
                pkg.setDeliveryTime(time);
            }
        }
    }
}