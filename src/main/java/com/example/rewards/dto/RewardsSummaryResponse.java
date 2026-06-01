package com.example.rewards.dto;

import java.util.List;

/**
 * Top-level response for rewards summary request.
 */
public class RewardsSummaryResponse {

    private final List<CustomerRewardsResponse> customers;

    /**
     * Creates a rewards summary response.
     *
     * @param customers list of customer-level rewards summaries
     */
    public RewardsSummaryResponse(List<CustomerRewardsResponse> customers) {
        this.customers = customers;
    }

    public List<CustomerRewardsResponse> getCustomers() {
        return customers;
    }
}
