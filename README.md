# Courier Service

A Spring Boot application that simulates a "courier service system".  
It supports "delivery cost estimation" and "delivery time estimation" using discount strategies, shipment creation and vehicle assignment.

---

## Features

- Cost Estimation Mode
  - Calculates total delivery cost for each package.
  - Applies discount strategies (OFR001, OFR002, OFR003) if applicable.
  - Uses `Strategy Pattern` for extensible discount rules.

- Time Estimation Mode
  - Splits packages into shipments based on max carriable weight.
  - Calculates delivery time for each package and shipment.
  - Assigns shipments to vehicles based on availability.
  - Uses `Builder Pattern` for `Shipment` and `Vehicle`.

- Error Handling
  - Custom exceptions via `CourierServiceException`.
  - Validation for invalid inputs.

- Design Principles
  - Follows SOLID principles.
  - Implements Builder, Strategy, and DI patterns.

---

## Tech Stack

- Java (Java 21+ recommended, project `pom.xml` targets `java.version=24`)
- Spring Boot 3.5.5
- Maven (build tool)

---

## Project Structure

com.everesteng.courier
├── builder # Builders for Shipment and Vehicle
├── exception # Custom exceptions
├── model # Core domain models (Package, Shipment, Vehicle)
├── service # Services (discounts, shipment, vehicle, delivery time, courier orchestration)
├── strategy # Discount strategies (OFR001, OFR002, OFR003, NoDiscount)
└── CourierApplication.java # Entry point (CommandLineRunner)

Build the project
mvn clean install

Run the application
mvn spring-boot:run

Usage

When you run the app, you’ll be prompted to select a mode:

Select Mode: 1 = Delivery Cost Estimation, 2 = Delivery Time Estimation

Mode 1: Delivery Cost Estimation

Input format:

base_delivery_cost no_of_packages
pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1
pkg_id2 pkg_weight2_in_kg distance2_in_km offer_code2
...

Example:

100 3
PKG1 5 5 OFR001
PKG2 15 5 OFR002
PKG3 10 100 OFR003

Output:

=== Delivery Cost Estimation Results ===
PKG1 0 175
PKG2 0 275
PKG3 35 665

Mode 2: Delivery Time Estimation

Input format:

base_delivery_cost no_of_packages
pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1
pkg_id2 pkg_weight2_in_kg distance2_in_km offer_code2
...
no_of_vehicles max_speed max_carriable_weight

Example:

100 5
PKG1 50 30 OFR001
PKG2 75 125 OFFR0008
PKG3 175 100 OFFR003
PKG4 110 60 OFR002
PKG5 155 95 NA
2 70 200

Output:

=== Delivery Time Estimation Results ===
PKG1 0 750 3.98
PKG2 0 1475 1.78
PKG3 0 2350 1.42
PKG4 105 1395 0.85
PKG5 0 2125 4.19

Running Tests
mvn test

Design Patterns & Principles
============================
Builder Pattern → ShipmentBuilder, VehicleBuilder
Strategy Pattern → Discount strategies (OFR001, OFR002, OFR003, NoDiscount)
Dependency Injection (DI) → Spring-managed services

SOLID Principles
================
SRP → Each service handles one responsibility.
OCP → New discount strategies can be added without modifying existing code.
LSP → All discount strategies follow the same contract.
ISP → Interfaces are small and specific (DiscountStrategy).
DIP → High-level modules depend on abstractions.
