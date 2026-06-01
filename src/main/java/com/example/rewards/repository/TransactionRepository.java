package com.example.rewards.repository;

import com.example.rewards.model.Transaction;
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
}
