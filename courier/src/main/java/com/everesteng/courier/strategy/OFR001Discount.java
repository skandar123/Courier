/*
 * Name: Sayantika Kandar
 * Purpose: Implementation of the DiscountStrategy interface
 * that applies a specific discount rule for offer code "OFR001".
 *
 * - This is part of the Strategy Design Pattern.
 * - Defines discount eligibility conditions based on package distance and weight.
 * - If conditions are met, applies a 10% discount on the total delivery cost.
 * - Otherwise, no discount is given.
 */

package com.everesteng.courier.strategy;

import com.everesteng.courier.model.Package;
import org.springframework.stereotype.Component;

@Component("OFR001")  // Registers this strategy in Spring with the name "OFR001"
public class OFR001Discount implements DiscountStrategy {

    /**
     * Calculates discount for packages eligible under "OFR001".
     *
     * Conditions:
     *   - Distance must be less than 200 km
     *   - Weight must be between 70 and 200 (inclusive)
     *
     * If both conditions are satisfied → 10% discount on totalCost.
     * Otherwise → no discount.
     *
     * @param pkg        the package to check eligibility
     * @param totalCost  the original delivery cost before discount
     * @return discount amount (10% of cost if eligible, otherwise 0)
     */
    @Override
    public int calculate(Package pkg, int totalCost) {
        // Check eligibility criteria for OFR001
        if (pkg.getDistance() < 200 && pkg.getWeight() >= 70 && pkg.getWeight() <= 200) {
            return (int) Math.round(totalCost * 0.10); // 10% discount
        }
        return 0; // Not eligible → no discount
    }
}