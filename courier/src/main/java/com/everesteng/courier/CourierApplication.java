/*
 * Name: Sayantika Kandar
 * Purpose: Main entry point for the Courier Service Spring Boot application.
 *
 * - Uses Spring Boot's @SpringBootApplication for auto-configuration and startup.
 * - Implements CommandLineRunner to allow interactive input from the console.
 * - Provides two modes:
 *      1. Delivery Cost Estimation
 *      2. Delivery Time Estimation
 * - Delegates core business logic to CourierService.
 */

package com.everesteng.courier;

import com.everesteng.courier.exception.CourierServiceException;
import com.everesteng.courier.model.Package;
import com.everesteng.courier.model.Shipment;
import com.everesteng.courier.service.CourierService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication  // Marks this as a Spring Boot application
public class CourierApplication implements CommandLineRunner {

	private final CourierService courierService;

	// Constructor-based dependency injection of CourierService
	public CourierApplication(CourierService courierService) {
		this.courierService = courierService;
	}

	// Main method: Launches Spring Boot application
	public static void main(String[] args) {
		SpringApplication.run(CourierApplication.class, args);
	}

	/**
	 * Overridden run method from CommandLineRunner.
	 * Executes after Spring Boot application starts.
	 *
	 * Provides interactive CLI mode:
	 *   - Mode 1: Cost Estimation
	 *   - Mode 2: Time Estimation
	 */
	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		// Prompt user for mode selection
		System.out.println("Select Mode: 1 = Delivery Cost Estimation, 2 = Delivery Time Estimation");
		String mode = scanner.nextLine().trim();

		// Route to appropriate mode handler
		if (mode.equals("1")) {
			handleCostMode(scanner);
		} else if (mode.equals("2")) {
			handleTimeMode(scanner);
		} else {
			System.err.println("Invalid mode selected. Exiting...");
		}

		scanner.close();
	}

	/**
	 * Handles Delivery Cost Estimation mode.
	 *
	 * Steps:
	 *   - Read base cost and number of packages.
	 *   - Collect package details (id, weight, distance, offer code).
	 *   - Calculate final cost and discount using CourierService.
	 *   - Print results for each package.
	 */
	private void handleCostMode(Scanner scanner) {
		try {
			System.out.println("Enter input in the following format:\n" +
					"base_delivery_cost no_of_packages\n" +
					"pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1\n" +
					"....");

			int baseCost = scanner.nextInt();
			int n = scanner.nextInt();
			scanner.nextLine(); // consume newline

			// Collect package data
			List<Package> packages = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				String[] parts = scanner.nextLine().trim().split("\\s+");
				packages.add(new Package(parts[0],
						Integer.parseInt(parts[1]),
						Integer.parseInt(parts[2]),
						parts[3]));
			}

			System.out.println("=== Delivery Cost Estimation Results ===");
			for (Package pkg : packages) {
				int finalCost = courierService.calculateFinalCost(pkg, baseCost);
				int totalCost = baseCost + (pkg.getWeight() * 10) + (pkg.getDistance() * 5);
				int discount = totalCost - finalCost;

				// Print: packageId, discount applied, final cost
				System.out.printf("%s %d %d%n", pkg.getId(), discount, finalCost);
			}

		} catch (Exception e) {
			System.err.println("Error in cost mode: " + e.getMessage());
		}
	}

	/**
	 * Handles Delivery Time Estimation mode.
	 *
	 * Steps:
	 *   - Read base cost, number of packages, package details.
	 *   - Read vehicle constraints (numVehicles, maxSpeed, maxCarriableWeight).
	 *   - Call CourierService to process deliveries and assign vehicles.
	 *   - Print results including package delivery times.
	 */
	private void handleTimeMode(Scanner scanner) {
		try {
			System.out.println("Enter input in the following format:\n" +
					"base_delivery_cost no_of_packages\n" +
					"pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1\n" +
					"....\n" +
					"no_of_vehicles max_speed max_carriable_weight");

			int baseCost = scanner.nextInt();
			int n = scanner.nextInt();
			scanner.nextLine(); // consume newline

			// Collect package data
			List<Package> packages = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				String[] parts = scanner.nextLine().trim().split("\\s+");
				packages.add(new Package(parts[0],
						Integer.parseInt(parts[1]),
						Integer.parseInt(parts[2]),
						parts[3]));
			}

			// Read vehicle constraints
			int numVehicles = scanner.nextInt();
			int maxSpeed = scanner.nextInt();
			int maxWeight = scanner.nextInt();

			// Process shipments with CourierService
			List<Shipment> shipments = courierService.processDeliveries(
					packages, baseCost, maxWeight, maxSpeed, numVehicles);

			System.out.println("=== Delivery Time Estimation Results ===");
			for (Package pkg : packages) {
				int finalCost = courierService.calculateFinalCost(pkg, baseCost);
				int totalCost = baseCost + (pkg.getWeight() * 10) + (pkg.getDistance() * 5);
				int discount = totalCost - finalCost;

				// Print: packageId, discount applied, final cost, delivery time
				System.out.printf("%s %d %d %.2f%n",
						pkg.getId(), discount, finalCost, pkg.getDeliveryTime());
			}

		} catch (CourierServiceException e) {
			System.err.println("Error in time mode: " + e.getMessage());
		}
	}
}