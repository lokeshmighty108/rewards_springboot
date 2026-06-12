package com.example.rewards.repository;

import com.example.rewards.exception.InvalidTransactionException;
import com.example.rewards.model.Transaction;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * In-memory repository backed by seeded transaction data.
 */
@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

    private final List<Transaction> transactions;

    /**
     * Creates repository with seeded transaction data.
     *
     * @param sampleTransactions initial list of transactions
     * @param validator bean validator used to protect repository data integrity
     */
    public InMemoryTransactionRepository(List<Transaction> sampleTransactions, Validator validator) {
        validateTransactions(sampleTransactions, validator);
        this.transactions = new ArrayList<>(sampleTransactions);
    }

    @Override
    public List<Transaction> findAll() {
        return List.copyOf(transactions);
    }

    @Override
    public List<Transaction> findByCustomerId(String customerId) {
        return transactions.stream()
                .filter(transaction -> transaction.getCustomerId().equals(customerId))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(transaction -> isWithinDateRange(transaction, startDate, endDate))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Transaction> findByCustomerIdAndDateRange(String customerId, LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(transaction -> transaction.getCustomerId().equals(customerId))
                .filter(transaction -> isWithinDateRange(transaction, startDate, endDate))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean existsByCustomerId(String customerId) {
        return transactions.stream().anyMatch(transaction -> transaction.getCustomerId().equals(customerId));
    }

    private boolean isWithinDateRange(Transaction transaction, LocalDate startDate, LocalDate endDate) {
        LocalDate transactionDate = transaction.getTransactionDate();
        boolean afterStart = startDate == null || !transactionDate.isBefore(startDate);
        boolean beforeEnd = endDate == null || !transactionDate.isAfter(endDate);
        return afterStart && beforeEnd;
    }

    private void validateTransactions(List<Transaction> transactionsToValidate, Validator validator) {
        for (Transaction transaction : transactionsToValidate) {
            Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
            if (!violations.isEmpty()) {
                String message = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(" "));
                throw new InvalidTransactionException(message);
            }
        }
    }
}
