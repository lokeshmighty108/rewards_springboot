package com.example.rewards.repository;

import com.example.rewards.model.Transaction;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository abstraction for transaction access.
 */
public interface TransactionRepository {

    /**
     * Returns all transaction records.
     *
     * @return list of transactions
     */
    List<Transaction> findAll();

    /**
     * Returns transactions for a specific customer.
     *
     * @param customerId customer identifier
     * @return matching transactions
     */
    List<Transaction> findByCustomerId(String customerId);

    /**
     * Returns transactions inside an inclusive date range.
     *
     * @param startDate optional start date, inclusive
     * @param endDate optional end date, inclusive
     * @return matching transactions
     */
    List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * Returns transactions for a customer inside an inclusive date range.
     *
     * @param customerId customer identifier
     * @param startDate optional start date, inclusive
     * @param endDate optional end date, inclusive
     * @return matching transactions
     */
    List<Transaction> findByCustomerIdAndDateRange(String customerId, LocalDate startDate, LocalDate endDate);

    /**
     * Checks whether a customer exists in the transaction data.
     *
     * @param customerId customer identifier
     * @return true when at least one transaction exists for the customer
     */
    boolean existsByCustomerId(String customerId);
}
