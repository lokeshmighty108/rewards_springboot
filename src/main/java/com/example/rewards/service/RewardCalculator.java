package com.example.rewards.service;

import com.example.rewards.exception.InvalidTransactionException;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 * Calculates reward points for a single transaction amount.
 */
@Component
public class RewardCalculator {

    private static final int LOWER_THRESHOLD = 50;
    private static final int UPPER_THRESHOLD = 100;

    /**
     * Calculates reward points from a transaction amount.
     * Logic: 2 points for each dollar over 100 and 1 point for each dollar between 51 and 100.
     *
     * @param amount transaction amount
     * @return reward points
     */
    public long calculatePoints(BigDecimal amount) {
        if (amount == null) {
            throw new InvalidTransactionException("Transaction amount must not be null.");
        }
        if (amount.signum() < 0) {
            throw new InvalidTransactionException("Transaction amount must not be negative.");
        }

        int wholeDollars = amount.intValue();
        if (wholeDollars <= LOWER_THRESHOLD) {
            return 0;
        }
        if (wholeDollars <= UPPER_THRESHOLD) {
            return wholeDollars - LOWER_THRESHOLD;
        }
        return (UPPER_THRESHOLD - LOWER_THRESHOLD) + (long) (wholeDollars - UPPER_THRESHOLD) * 2;
    }
}
