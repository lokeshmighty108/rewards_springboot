package com.example.rewards.repository;

import com.example.rewards.exception.InvalidTransactionException;
import com.example.rewards.model.Transaction;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for in-memory transaction repository queries and validation.
 */
class InMemoryTransactionRepositoryTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Verifies customer and date range repository methods return scoped data.
     */
    @Test
    void shouldFindTransactionsByCustomerAndDateRange() {
        InMemoryTransactionRepository repository = new InMemoryTransactionRepository(List.of(
                new Transaction("1", "C001", "Alice", BigDecimal.valueOf(120), LocalDate.of(2026, 1, 10)),
                new Transaction("2", "C001", "Alice", BigDecimal.valueOf(80), LocalDate.of(2026, 2, 10)),
                new Transaction("3", "C002", "Bob", BigDecimal.valueOf(140), LocalDate.of(2026, 2, 15))
        ), validator);

        assertEquals(2, repository.findByCustomerId("C001").size());
        assertEquals(2, repository.findByDateRange(LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 28)).size());
        assertEquals(1, repository.findByCustomerIdAndDateRange(
                "C001", LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 28)).size());
    }

    /**
     * Verifies invalid transaction data is rejected when the repository is initialized.
     */
    @Test
    void shouldRejectInvalidTransactionData() {
        List<Transaction> invalidTransactions = List.of(
                new Transaction("1", "", "Alice", BigDecimal.valueOf(120), LocalDate.of(2026, 1, 10))
        );

        assertThrows(InvalidTransactionException.class,
                () -> new InMemoryTransactionRepository(invalidTransactions, validator));
    }
}
