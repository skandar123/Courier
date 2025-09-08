/*
 * Name: Sayantika Kandar
 * Purpose: Implementation of the DiscountStrategy interface
 * that applies a specific discount rule for offer code "OFR002".
 *
 * - This is part of the Strategy Design Pattern.
 * - Defines discount eligibility conditions based on package distance and weight.
 * - If conditions are met, applies a 7% discount on the total delivery cost.
 * - Otherwise, no discount is given.
 */

package com.everesteng.courier.strategy;

import com.everesteng.courier.model.Package;
import org.springframework.stereotype.Component;

@Component("OFR002")  // Registers this strategy in Spring with the name "OFR002"
public class OFR002Discount implements DiscountStrategy {

    /**
     * Calculates discount for packages eligible under "OFR002".
     *
     * Conditions:
     *   - Distance must be between 50 km and 150 km (inclusive)
     *   - Weight must be between 100 and 250 (inclusive)
     *
     * If both conditions are satisfied → 7% discount on totalCost.
     * Otherwise → no discount.
     *
     * @param pkg        the package to check eligibility
     * @param totalCost  the original delivery cost before discount
     * @return discount amount (7% of cost if eligible, otherwise 0)
     */
    @Override
    public int calculate(Package pkg, int totalCost) {
        // Check eligibility criteria for OFR002
        if (pkg.getDistance() >= 50 && pkg.getDistance() <= 150 &&
                pkg.getWeight() >= 100 && pkg.getWeight() <= 250) {
            return (int) Math.round(totalCost * 0.07); // 7% discount
        }
        return 0; // Not eligible → no discount
    }
}