package com.example.rewards.repository;

import com.example.rewards.model.Transaction;
import java.util.ArrayList;
import java.util.List;
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
     */
    public InMemoryTransactionRepository(List<Transaction> sampleTransactions) {
        this.transactions = new ArrayList<>(sampleTransactions);
    }

    @Override
    public List<Transaction> findAll() {
        return List.copyOf(transactions);
    }
}
