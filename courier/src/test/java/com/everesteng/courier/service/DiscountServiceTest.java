/*
 * Name: Sayantika Kandar
 * Purpose: Unit tests for DiscountService.
 *
 * - Ensures discounts are applied correctly based on offer codes (OFR001, OFR002, OFR003).
 * - Verifies that invalid or missing codes default to no discount.
 * - Uses JUnit 5 for structured testing.
 */

package com.everesteng.courier.service;

import com.everesteng.courier.model.Package;
import com.everesteng.courier.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DiscountServiceTest {

    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        // Initialize DiscountService with all available discount strategies
        discountService = new DiscountService(
                Arrays.asList(
                        new OFR001Discount(),
                        new OFR002Discount(),
                        new OFR003Discount(),
                        new NoDiscount() // default fallback if no rule matches
                )
        );
    }

    /**
     * Test case: Valid OFR001 code.
     * Conditions: distance < 200, weight between 70–200
     * Discount: 10% of total cost
     * Example: totalCost = 1000 → discount = 100
     */
    @Test
    void testValidOFR001() {
        Package pkg = new Package("PKG1", 100, 150, "OFR001");
        int discount = discountService.calculateDiscount(pkg, 1000);
        assertEquals(100, discount);
    }

    /**
     * Test case: Valid OFR002 code.
     * Conditions: distance 50–150, weight 100–250
     * Discount: 7% of total cost
     * Example: totalCost = 1000 → discount = 70
     */
    @Test
    void testValidOFR002() {
        Package pkg = new Package("PKG2", 150, 100, "OFR002");
        int discount = discountService.calculateDiscount(pkg, 1000);
        assertEquals(70, discount);
    }

    /**
     * Test case: Valid OFR003 code.
     * Conditions: distance 50–250, weight 10–150
     * Discount: 5% of total cost
     * Example: totalCost = 1000 → discount = 50
     */
    @Test
    void testValidOFR003() {
        Package pkg = new Package("PKG3", 100, 200, "OFR003");
        int discount = discountService.calculateDiscount(pkg, 1000);
        assertEquals(50, discount);
    }

    /**
     * Test case: Invalid offer code.
     * Expected: No discount applied → 0
     */
    @Test
    void testInvalidCodeDefaultsToNoDiscount() {
        Package pkg = new Package("PKG4", 80, 120, "INVALID");
        int discount = discountService.calculateDiscount(pkg, 1000);
        assertEquals(0, discount);
    }

    /**
     * Test case: "NA" (no offer code).
     * Expected: No discount applied → 0
     */
    @Test
    void testNAOfferCodeReturnsZero() {
        Package pkg = new Package("PKG5", 50, 100, "NA");
        int discount = discountService.calculateDiscount(pkg, 1000);
        assertEquals(0, discount);
    }
}