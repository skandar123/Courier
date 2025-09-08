/*
 * Name: Sayantika Kandar
 * Purpose: Service class to handle discount calculation for packages.
 * Implements the Strategy Design Pattern by delegating discount calculation
 * to specific discount strategies (e.g., percentage-based, weight-based).
 * If no valid offer code is found, a default NoDiscount strategy is used.
 */

package com.everesteng.courier.service;

import com.everesteng.courier.model.Package;
import com.everesteng.courier.strategy.DiscountStrategy;
import com.everesteng.courier.strategy.NoDiscount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service  // Marks this as a Spring-managed service
public class DiscountService {

    // Holds all discount strategies, mapped by their offer code
    private final Map<String, DiscountStrategy> strategies;

    /**
     * Constructor that initializes discount strategies.
     *
     * Converts the list of available DiscountStrategy implementations into a map
     * where the key is derived from the class name (e.g., "OFR001" from "OFR001Discount").
     *
     * @param strategyList list of discount strategy implementations
     */
    public DiscountService(List<DiscountStrategy> strategyList) {
        this.strategies = strategyList.stream()
                // Key = class name without "Discount", Value = strategy instance
                .collect(Collectors.toMap(
                        s -> s.getClass().getSimpleName().replace("Discount", ""),
                        s -> s
                ));
    }

    /**
     * Calculates the discount for a package based on its offer code.
     *
     * If the offer code matches a known strategy, that strategy is applied.
     * Otherwise, NoDiscount strategy is used (returns zero discount).
     *
     * @param pkg       the package containing the offer code
     * @param totalCost the pre-discount cost of the package
     * @return discount amount to be subtracted from total cost
     */
    public int calculateDiscount(Package pkg, int totalCost) {
        return strategies
                .getOrDefault(pkg.getOfferCode(), new NoDiscount())
                .calculate(pkg, totalCost);
    }
}