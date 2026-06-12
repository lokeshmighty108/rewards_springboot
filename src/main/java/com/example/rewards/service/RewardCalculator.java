package com.example.rewards.service;

import com.example.rewards.exception.InvalidTransactionException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

/**
 * Calculates reward points for a single transaction amount.
 */
@Component
public class RewardCalculator {

    private static final BigDecimal LOWER_THRESHOLD = BigDecimal.valueOf(50);
    private static final BigDecimal UPPER_THRESHOLD = BigDecimal.valueOf(100);
    private static final BigDecimal DOUBLE_POINTS_MULTIPLIER = BigDecimal.valueOf(2);

    /**
     * Calculates reward points from a transaction amount.
     * Logic: 2 points for each dollar over 100 and 1 point for each dollar over 50 up to 100.
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

        if (amount.compareTo(LOWER_THRESHOLD) <= 0) {
            return 0;
        }

        BigDecimal points;
        if (amount.compareTo(UPPER_THRESHOLD) <= 0) {
            points = amount.subtract(LOWER_THRESHOLD);
        } else {
            points = UPPER_THRESHOLD.subtract(LOWER_THRESHOLD)
                    .add(amount.subtract(UPPER_THRESHOLD).multiply(DOUBLE_POINTS_MULTIPLIER));
        }

        return points.setScale(0, RoundingMode.DOWN).longValue();
    }
}
