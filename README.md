Courier Service – Spring Boot Project
📌 Overview

This project implements a Courier Service application in Java (Spring Boot).
It supports:

Delivery Cost Estimation – calculates final delivery cost for packages, applying discounts using offer codes.

Delivery Time Estimation – estimates package delivery times by assigning shipments to vehicles with weight limits and availability.

The project uses design patterns such as Builder (for Shipment and Vehicle) and Strategy (for discount calculation).
It also follows SOLID principles for clean, testable code.

📖 Problem Statement

The project solves two main problems:

1️⃣ Delivery Cost Estimation

Apply one offer code per package (OFR001, OFR002, OFR003).

Discounts:

OFR001 → 10% if distance < 200 & weight 70–200.

OFR002 → 7% if distance 50–150 & weight 100–250.

OFR003 → 5% if distance 50–250 & weight 10–150.

If no valid code, discount = 0.

Example Input

100 3
PKG1 5 5 OFR001
PKG2 15 5 OFR002
PKG3 10 100 OFR003


Example Output

PKG1 0 175
PKG2 0 275
PKG3 35 665

2️⃣ Delivery Time Estimation

Vehicles (N) deliver packages at constant speed (S).

Each vehicle has a max carriable weight (L).

Vehicles are reused after they return to the source.

Shipments maximize vehicle load; heavier shipments are prioritized.

Example Input

100 5
PKG1 50 30 OFR001
PKG2 75 125 OFFR0008
PKG3 175 100 OFFR003
PKG4 110 60 OFR002
PKG5 155 95 NA
2 70 200


Example Output

PKG1 0 750 3.98
PKG2 0 1475 1.78
PKG3 0 2350 1.42
PKG4 105 1395 0.85
PKG5 0 2125 4.19

🏗️ Project Structure
src/main/java/com/everesteng/courier
│── CourierApplication.java      # Main Spring Boot app (CLI)
│
├── builder/                     # Builder Pattern
│   ├── ShipmentBuilder.java
│   └── VehicleBuilder.java
│
├── exception/
│   └── CourierServiceException.java
│
├── model/                       # Core domain models
│   ├── Package.java
│   ├── Shipment.java
│   └── Vehicle.java
│
├── service/                     # Business services
│   ├── CourierService.java
│   ├── DiscountService.java
│   ├── ShipmentService.java
│   ├── DeliveryTimeService.java
│   └── VehicleService.java
│
└── strategy/                    # Strategy Pattern for discounts
    ├── DiscountStrategy.java
    ├── NoDiscount.java
    ├── OFR001Discount.java
    ├── OFR002Discount.java
    └── OFR003Discount.java

🧪 Testing

Unit tests cover:

Builders: ShipmentBuilderTest, VehicleBuilderTest

Models: PackageTest, ShipmentTest, VehicleTest

Services: CourierServiceTest, DeliveryTimeServiceTest, ShipmentServiceTest, VehicleServiceTest, DiscountServiceTest

Strategies: DiscountStrategyTest

Run all tests:

mvn test

▶️ Running the Application

Build the project:

mvn clean install


Run with Spring Boot:

mvn spring-boot:run


Choose mode when prompted:

1 → Delivery Cost Estimation

2 → Delivery Time Estimation

⚙️ Design Patterns Used

Builder Pattern → ShipmentBuilder, VehicleBuilder.

Strategy Pattern → DiscountStrategy and its implementations.

🏆 SOLID Principles

Single Responsibility → Each service handles one concern.

Open/Closed → Adding new discounts requires only a new strategy class.

Liskov Substitution → Strategies can replace each other.

Interface Segregation → DiscountStrategy defines only needed behavior.

Dependency Injection → Services injected via constructors (Spring Boot).

📦 Requirements

Java 17+

Maven 3.8+

Spring Boot 3.x

👩‍💻 Author

Sayantika Kandar
