package com.example.rewards.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a purchase transaction for a customer.
 */
public class Transaction {

    @NotBlank(message = "Transaction id must not be blank.")
    private final String transactionId;

    @NotBlank(message = "Customer id must not be blank.")
    private final String customerId;

    @NotBlank(message = "Customer name must not be blank.")
    private final String customerName;

    @NotNull(message = "Transaction amount must not be null.")
    @DecimalMin(value = "0.00", message = "Transaction amount must not be negative.")
    private final BigDecimal amount;

    @NotNull(message = "Transaction date must not be null.")
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
