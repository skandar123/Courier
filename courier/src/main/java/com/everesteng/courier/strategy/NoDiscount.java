/*
 * Name: Sayantika Kandar
 * Purpose: Implementation of the DiscountStrategy interface
 * that applies no discount to the package.
 *
 * - This class is part of the Strategy Design Pattern.
 * - It represents the "default" discount strategy when no offer code is applied.
 * - Always returns 0 as the discount value, meaning the package
 *   is charged the full delivery cost.
 */

package com.everesteng.courier.strategy;

import com.everesteng.courier.model.Package;
import org.springframework.stereotype.Component;

@Component  // Registers this strategy as a Spring-managed bean
public class NoDiscount implements DiscountStrategy {

    /**
     * Always returns zero discount regardless of the package details.
     *
     * @param pkg        the package (not used here since no discount applies)
     * @param totalCost  the original delivery cost
     * @return always 0, since no discount is given
     */
    @Override
    public int calculate(Package pkg, int totalCost) {
        return 0; // No discount applied
    }
}