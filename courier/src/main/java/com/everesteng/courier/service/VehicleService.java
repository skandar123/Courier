/*
 * Name: Sayantika Kandar
 * Purpose: Service class responsible for assigning vehicles to shipments
 * and updating their availability times.
 *
 * This class ensures:
 *   - Vehicles are assigned fairly using a priority queue (min-heap).
 *   - The vehicle with the earliest availability is always chosen.
 *   - Package delivery times are adjusted based on vehicle availability.
 *   - Vehicle availability is updated after completing each shipment.
 */

package com.everesteng.courier.service;

import com.everesteng.courier.builder.VehicleBuilder;
import com.everesteng.courier.exception.CourierServiceException;
import com.everesteng.courier.model.Package;
import com.everesteng.courier.model.Shipment;
import com.everesteng.courier.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service  // Marks this as a Spring-managed service class
public class VehicleService {

    /**
     * Assigns vehicles to shipments in a way that minimizes idle time.
     *
     * - Vehicles are managed in a priority queue (sorted by availability time).
     * - Always assigns the shipment to the vehicle that becomes available first.
     * - Updates package delivery times by considering vehicle availability delay.
     * - Updates vehicle availability after completing shipment.
     *
     * @param shipments   list of shipments to assign
     * @param numVehicles number of available vehicles
     * @throws CourierServiceException if no vehicles exist or invalid input is provided
     */
    public void assignVehicles(List<Shipment> shipments, int numVehicles)
            throws CourierServiceException {

        // Validate number of vehicles
        if (numVehicles <= 0) {
            throw new CourierServiceException("Number of vehicles must be positive");
        }

        // Priority queue (min-heap) ensures vehicles with earliest availability are chosen first
        PriorityQueue<Vehicle> queue =
                new PriorityQueue<>(Comparator.comparingDouble(Vehicle::getAvailability));

        // 🚗 Initialize all vehicles with availability = 0 using the Builder pattern
        for (int i = 1; i <= numVehicles; i++) {
            Vehicle v = new VehicleBuilder()
                    .setVehicleId(i)
                    .setAvailability(0)
                    .build();
            queue.add(v);
        }

        // 🚚 Assign vehicles to shipments in order
        for (Shipment shipment : shipments) {
            Vehicle v = queue.poll(); // Fetch vehicle with earliest availability
            if (v == null) throw new CourierServiceException("No vehicles available");

            // Assign this vehicle to the shipment
            shipment.setVehicle(v.getVehicleId());

            // Adjust package delivery times based on vehicle's availability delay
            for (Package pkg : shipment.getPackages()) {
                pkg.setDeliveryTime(pkg.getDeliveryTime() + v.getAvailability());
            }

            // Update vehicle availability after completing the shipment
            // (availability = old availability + shipment time)
            v = new VehicleBuilder()
                    .setVehicleId(v.getVehicleId())
                    .setAvailability(v.getAvailability() + shipment.getTime())
                    .build();

            // Push the updated vehicle back into the priority queue
            queue.offer(v);
        }
    }
}