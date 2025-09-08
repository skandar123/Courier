/*
 * Name: Sayantika Kandar
 * Purpose: Implementation of the DiscountStrategy interface
 * that applies a specific discount rule for offer code "OFR003".
 *
 * - This is part of the Strategy Design Pattern.
 * - Defines discount eligibility conditions based on package distance and weight.
 * - If conditions are met, applies a 5% discount on the total delivery cost.
 * - Otherwise, no discount is given.
 */

package com.everesteng.courier.strategy;

import com.everesteng.courier.model.Package;
import org.springframework.stereotype.Component;

@Component("OFR003")  // Registers this strategy in Spring with the name "OFR003"
public class OFR003Discount implements DiscountStrategy {

    /**
     * Calculates discount for packages eligible under "OFR003".
     *
     * Conditions:
     *   - Distance must be between 50 km and 250 km (inclusive)
     *   - Weight must be between 10 and 150 (inclusive)
     *
     * If both conditions are satisfied → 5% discount on totalCost.
     * Otherwise → no discount.
     *
     * @param pkg        the package to check eligibility
     * @param totalCost  the original delivery cost before discount
     * @return discount amount (5% of cost if eligible, otherwise 0)
     */
    @Override
    public int calculate(Package pkg, int totalCost) {
        // Check eligibility criteria for OFR003
        if (pkg.getDistance() >= 50 && pkg.getDistance() <= 250 &&
                pkg.getWeight() >= 10 && pkg.getWeight() <= 150) {
            return (int) Math.round(totalCost * 0.05); // 5% discount
        }
        return 0; // Not eligible → no discount
    }
}