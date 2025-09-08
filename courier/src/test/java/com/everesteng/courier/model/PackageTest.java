/*
 * Name: Sayantika Kandar
 * Purpose: Unit tests for the Package model class.
 *
 * - Validates constructor input validation (ID, weight, distance).
 * - Ensures setter validations (delivery time).
 * - Tests equality and hashCode consistency based on package ID.
 */

package com.everesteng.courier.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PackageTest {

    /**
     * Test constructor validation for package ID.
     * - Should throw IllegalArgumentException if ID is null or empty.
     */
    @Test
    void constructor_ShouldThrow_WhenIdIsNullOrEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> new Package(null, 10, 20, "OFR001"));   // Null ID
        assertThrows(IllegalArgumentException.class,
                () -> new Package("   ", 10, 20, "OFR001")); // Empty/whitespace ID
    }

    /**
     * Test constructor validation for weight and distance.
     * - Should throw IllegalArgumentException if weight or distance is negative.
     */
    @Test
    void constructor_ShouldThrow_WhenWeightOrDistanceNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Package("PKG1", -5, 20, "OFR001"));  // Negative weight
        assertThrows(IllegalArgumentException.class,
                () -> new Package("PKG1", 10, -10, "OFR001")); // Negative distance
    }

    /**
     * Test delivery time setter validation.
     * - Should throw IllegalArgumentException if delivery time is negative.
     */
    @Test
    void setDeliveryTime_ShouldThrow_WhenNegative() {
        Package pkg = new Package("PKG1", 10, 20, "OFR001");
        assertThrows(IllegalArgumentException.class,
                () -> pkg.setDeliveryTime(-1));  // Negative delivery time
    }

    /**
     * Test equals() and hashCode() implementation.
     * - Equality is based only on package ID, ignoring weight, distance, and offer code.
     * - Ensures two packages with the same ID are equal and have the same hashCode.
     */
    @Test
    void equalsAndHashCode_ShouldWorkBasedOnId() {
        Package p1 = new Package("PKG1", 10, 20, "OFR001");
        Package p2 = new Package("PKG1", 50, 100, "OFR002");

        assertEquals(p1, p2);                 // Same ID → considered equal
        assertEquals(p1.hashCode(), p2.hashCode()); // Hash codes must also match
    }
}