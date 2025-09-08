/*
 * Name: Sayantika Kandar
 * Purpose: Defines the Strategy interface for applying discounts
 * to a package's delivery cost.
 *
 * - This follows the Strategy Design Pattern.
 * - Different discount rules (e.g., Offer A, Offer B, No Discount, etc.)
 *   will implement this interface.
 * - Allows the system to apply different discount algorithms
 *   without changing the core calculation logic.
 */

package com.everesteng.courier.strategy;

import com.everesteng.courier.model.Package;

public interface DiscountStrategy {

    /**
     * Calculates the discount for a given package.
     *
     * @param pkg        the package for which discount is being calculated
     * @param totalCost  the original total delivery cost before discount
     * @return the discount amount (not the final price)
     */
    int calculate(Package pkg, int totalCost);
}