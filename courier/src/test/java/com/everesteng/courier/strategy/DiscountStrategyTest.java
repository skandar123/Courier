/*
 * Name: Sayantika Kandar
 * Purpose: Unit tests for different DiscountStrategy implementations.
 *
 * - Verifies discount calculation logic for valid and invalid cases of
 *   OFR001, OFR002, OFR003, and NoDiscount strategies.
 * - Ensures that discounts are applied only when package conditions
 *   (weight, distance, offer code) meet eligibility rules.
 * - Confirms fallback behavior when no discount is applicable.
 */

package com.everesteng.courier.strategy;

import com.everesteng.courier.model.Package;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountStrategyTest {

    /**
     * ✅ Test case: OFR001 should apply 10% discount
     * when conditions are satisfied (weight & distance).
     */
    @Test
    void testOFR001ValidDiscount() {
        Package pkg = new Package("PKG1", 100, 150, "OFR001");
        DiscountStrategy strategy = new OFR001Discount();

        int discount = strategy.calculate(pkg, 1000);
        assertEquals(100, discount); // 10% of 1000
    }

    /**
     * ✅ Test case: OFR001 should give no discount
     * when distance exceeds eligible limit.
     */
    @Test
    void testOFR001NoDiscount() {
        Package pkg = new Package("PKG1", 50, 250, "OFR001");
        DiscountStrategy strategy = new OFR001Discount();

        int discount = strategy.calculate(pkg, 1000);
        assertEquals(0, discount);
    }

    /**
     * ✅ Test case: OFR002 should apply 7% discount
     * when conditions are satisfied.
     */
    @Test
    void testOFR002ValidDiscount() {
        Package pkg = new Package("PKG2", 150, 100, "OFR002");
        DiscountStrategy strategy = new OFR002Discount();

        int discount = strategy.calculate(pkg, 1000);
        assertEquals(70, discount); // 7% of 1000
    }

    /**
     * ✅ Test case: OFR002 should give no discount
     * when package is not eligible (distance/weight out of range).
     */
    @Test
    void testOFR002NoDiscount() {
        Package pkg = new Package("PKG2", 50, 200, "OFR002");
        DiscountStrategy strategy = new OFR002Discount();

        int discount = strategy.calculate(pkg, 1000);
        assertEquals(0, discount);
    }

    /**
     * ✅ Test case: OFR003 should apply 5% discount
     * when conditions are satisfied.
     */
    @Test
    void testOFR003ValidDiscount() {
        Package pkg = new Package("PKG3", 100, 200, "OFR003");
        DiscountStrategy strategy = new OFR003Discount();

        int discount = strategy.calculate(pkg, 1000);
        assertEquals(50, discount); // 5% of 1000
    }

    /**
     * ✅ Test case: OFR003 should give no discount
     * when conditions are not met.
     */
    @Test
    void testOFR003NoDiscount() {
        Package pkg = new Package("PKG3", 200, 300, "OFR003");
        DiscountStrategy strategy = new OFR003Discount();

        int discount = strategy.calculate(pkg, 1000);
        assertEquals(0, discount);
    }

    /**
     * ✅ Test case: NoDiscount strategy should always return 0,
     * regardless of package data.
     */
    @Test
    void testNoDiscountAlwaysZero() {
        Package pkg = new Package("PKG4", 50, 100, "NA");
        DiscountStrategy strategy = new NoDiscount();

        int discount = strategy.calculate(pkg, 1000);
        assertEquals(0, discount);
    }
}