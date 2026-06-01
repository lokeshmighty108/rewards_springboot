package com.example.rewards.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a purchase transaction for a customer.
 */
public class Transaction {

    private final String transactionId;
    private final String customerId;
    private final String customerName;
    private final BigDecimal amount;
    private final LocalDate transactionDate;

    /**
     * Creates a transaction record.
     *
     * @param transactionId transaction identifier
     * @param customerId customer identifier
     * @param customerName customer display name
     * @param amount purchase amount
     * @param transactionDate transaction date
     */
    public Transaction(String transactionId, String customerId, String customerName, BigDecimal amount, LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }
}
