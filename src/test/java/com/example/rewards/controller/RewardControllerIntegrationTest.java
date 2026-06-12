package com.example.rewards.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for rewards REST API.
 */
@SpringBootTest
@AutoConfigureMockMvc
class RewardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Verifies endpoint returns rewards for all customers from the seeded dataset.
     */
    @Test
    void shouldReturnRewardsForAllCustomers() throws Exception {
        mockMvc.perform(get("/api/rewards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers.length()").value(3))
                .andExpect(jsonPath("$.customers[0].customerId").value("C001"))
                .andExpect(jsonPath("$.customers[0].monthlyRewards['2026-01']").value(115))
                .andExpect(jsonPath("$.customers[0].monthlyRewards['2026-02']").value(130))
                .andExpect(jsonPath("$.customers[0].monthlyRewards['2026-03']").value(0))
                .andExpect(jsonPath("$.customers[0].totalRewards").value(245))
                .andExpect(jsonPath("$.customers[1].customerId").value("C002"))
                .andExpect(jsonPath("$.customers[1].totalRewards").value(403))
                .andExpect(jsonPath("$.customers[2].customerId").value("C003"))
                .andExpect(jsonPath("$.customers[2].totalRewards").value(99));
    }

    /**
     * Verifies endpoint can filter by customer id and date range.
     */
    @Test
    void shouldReturnRewardsForSingleCustomerInDateRange() throws Exception {
        mockMvc.perform(get("/api/rewards")
                        .param("customerId", "C001")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-01-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers.length()").value(1))
                .andExpect(jsonPath("$.customers[0].customerId").value("C001"))
                .andExpect(jsonPath("$.customers[0].monthlyRewards['2026-01']").value(115))
                .andExpect(jsonPath("$.customers[0].totalRewards").value(115));
    }

    /**
     * Verifies unknown customer id returns not found.
     */
    @Test
    void shouldReturnNotFoundForUnknownCustomer() throws Exception {
        mockMvc.perform(get("/api/rewards").param("customerId", "UNKNOWN"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Customer id UNKNOWN was not found."));
    }

    /**
     * Verifies invalid date range returns bad request.
     */
    @Test
    void shouldReturnBadRequestWhenStartDateAfterEndDate() throws Exception {
        mockMvc.perform(get("/api/rewards")
                        .param("startDate", "2026-03-31")
                        .param("endDate", "2026-03-01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("startDate must be before or equal to endDate."));
    }

    /**
     * Verifies invalid date format returns bad request.
     */
    @Test
    void shouldReturnBadRequestForInvalidDateFormat() throws Exception {
        mockMvc.perform(get("/api/rewards").param("startDate", "03-01-2026"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    /**
     * Verifies blank customer id returns bad request.
     */
    @Test
    void shouldReturnBadRequestForBlankCustomerId() throws Exception {
        mockMvc.perform(get("/api/rewards").param("customerId", "   "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("customerId must not be blank."));
    }
}
