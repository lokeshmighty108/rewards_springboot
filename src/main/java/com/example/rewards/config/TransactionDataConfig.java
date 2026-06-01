package com.example.rewards.config;

import com.example.rewards.model.Transaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides sample in-memory transaction data.
 */
@Configuration
public class TransactionDataConfig {

    /**
     * Creates sample transaction data for multiple customers across multiple months.
     *
     * @return sample transaction list
     */
    @Bean
    public List<Transaction> sampleTransactions() {
        return List.of(
                new Transaction("T-1001", "C001", "Lokesh", BigDecimal.valueOf(120), LocalDate.of(2026, 1, 15)),
                new Transaction("T-1002", "C001", "Lokesh", BigDecimal.valueOf(75), LocalDate.of(2026, 1, 30)),
                new Transaction("T-1003", "C001", "Lokesh", BigDecimal.valueOf(140), LocalDate.of(2026, 2, 10)),
                new Transaction("T-1004", "C001", "Lokesh", BigDecimal.valueOf(49), LocalDate.of(2026, 3, 5)),
                new Transaction("T-2001", "C002", "Bobby", BigDecimal.valueOf(51), LocalDate.of(2026, 1, 5)),
                new Transaction("T-2002", "C002", "Bobby", BigDecimal.valueOf(101), LocalDate.of(2026, 2, 11)),
                new Transaction("T-2003", "C002", "Bobby", BigDecimal.valueOf(250), LocalDate.of(2026, 3, 20)),
                new Transaction("T-3001", "C003", "Shannu", BigDecimal.valueOf(99.99), LocalDate.of(2026, 2, 1)),
                new Transaction("T-3002", "C003", "Shannu", BigDecimal.valueOf(50), LocalDate.of(2026, 3, 1)),
                new Transaction("T-3003", "C003", "Shannu", BigDecimal.valueOf(100), LocalDate.of(2026, 3, 15))
        );
    }
}
