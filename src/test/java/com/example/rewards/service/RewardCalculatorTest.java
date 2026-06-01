package com.example.rewards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.rewards.exception.InvalidTransactionException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for reward point calculation rules.
 */
class RewardCalculatorTest {

    private final RewardCalculator rewardCalculator = new RewardCalculator();

    /**
     * Verifies the example from the assignment prompt.
     */
    @Test
    void shouldCalculatePointsForAmountAboveHundred() {
        assertEquals(90, rewardCalculator.calculatePoints(BigDecimal.valueOf(120)));
    }

    /**
     * Verifies no points are awarded at or below fifty dollars.
     */
    @Test
    void shouldReturnZeroForFiftyOrLess() {
        assertEquals(0, rewardCalculator.calculatePoints(BigDecimal.valueOf(50)));
        assertEquals(0, rewardCalculator.calculatePoints(BigDecimal.valueOf(49.99)));
    }

    /**
     * Verifies the first reward slab between fifty and one hundred dollars.
     */
    @Test
    void shouldCalculatePointsBetweenFiftyOneAndHundred() {
        assertEquals(1, rewardCalculator.calculatePoints(BigDecimal.valueOf(51)));
        assertEquals(50, rewardCalculator.calculatePoints(BigDecimal.valueOf(100)));
    }

    /**
     * Verifies decimal values use whole dollars for points.
     */
    @Test
    void shouldUseWholeDollarPartForDecimalAmounts() {
        assertEquals(49, rewardCalculator.calculatePoints(BigDecimal.valueOf(99.99)));
    }

    /**
     * Verifies negative amounts are rejected.
     */
    @Test
    void shouldThrowForNegativeAmount() {
        assertThrows(InvalidTransactionException.class, () -> rewardCalculator.calculatePoints(BigDecimal.valueOf(-1)));
    }

    /**
     * Verifies null amounts are rejected.
     */
    @Test
    void shouldThrowForNullAmount() {
        assertThrows(InvalidTransactionException.class, () -> rewardCalculator.calculatePoints(null));
    }
}
