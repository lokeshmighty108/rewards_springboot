package com.example.rewards.dto;

import java.util.Map;

/**
 * Response model for monthly and total rewards for a customer.
 */
public class CustomerRewardsResponse {

    private final String customerId;
    private final String customerName;
    private final Map<String, Long> monthlyRewards;
    private final long totalRewards;

    /**
     * Creates a customer rewards response.
     *
     * @param customerId customer identifier
     * @param customerName customer name
     * @param monthlyRewards map of Year-Month to points
     * @param totalRewards total points over the selected period
     */
    public CustomerRewardsResponse(String customerId, String customerName, Map<String, Long> monthlyRewards, long totalRewards) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.monthlyRewards = monthlyRewards;
        this.totalRewards = totalRewards;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Map<String, Long> getMonthlyRewards() {
        return monthlyRewards;
    }

    public long getTotalRewards() {
        return totalRewards;
    }
}
