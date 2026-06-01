package com.example.rewards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.rewards.dto.CustomerRewardsResponse;
import com.example.rewards.dto.RewardsSummaryResponse;
import com.example.rewards.exception.CustomerNotFoundException;
import com.example.rewards.exception.InvalidDateRangeException;
import com.example.rewards.exception.InvalidTransactionException;
import com.example.rewards.model.Transaction;
import com.example.rewards.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for rewards aggregation logic.
 */
@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    private RewardService rewardService;

    @BeforeEach
    void setUp() {
        rewardService = new RewardService(transactionRepository, new RewardCalculator());
    }

    /**
     * Verifies rewards are grouped by month and totaled for each customer.
     */
    @Test
    void shouldCalculateRewardsByMonthAndTotalForMultipleCustomers() {
        when(transactionRepository.findAll()).thenReturn(List.of(
                new Transaction("1", "C001", "Alice", BigDecimal.valueOf(120), LocalDate.of(2026, 1, 10)),
                new Transaction("2", "C001", "Alice", BigDecimal.valueOf(60), LocalDate.of(2026, 1, 11)),
                new Transaction("3", "C001", "Alice", BigDecimal.valueOf(140), LocalDate.of(2026, 2, 5)),
                new Transaction("4", "C002", "Bob", BigDecimal.valueOf(250), LocalDate.of(2026, 3, 7))
        ));

        RewardsSummaryResponse response = rewardService.getRewardsSummary(null, null, null);
        assertEquals(2, response.getCustomers().size());

        CustomerRewardsResponse alice = response.getCustomers().get(0);
        assertEquals("C001", alice.getCustomerId());
        assertEquals(100, alice.getMonthlyRewards().get("2026-01"));
        assertEquals(130, alice.getMonthlyRewards().get("2026-02"));
        assertEquals(230, alice.getTotalRewards());
    }

    /**
     * Verifies date filters restrict the transaction set.
     */
    @Test
    void shouldApplyDateRangeFilter() {
        when(transactionRepository.findAll()).thenReturn(List.of(
                new Transaction("1", "C001", "Alice", BigDecimal.valueOf(120), LocalDate.of(2026, 1, 10)),
                new Transaction("2", "C001", "Alice", BigDecimal.valueOf(120), LocalDate.of(2026, 2, 10)),
                new Transaction("3", "C001", "Alice", BigDecimal.valueOf(120), LocalDate.of(2026, 3, 10))
        ));

        RewardsSummaryResponse response = rewardService.getRewardsSummary(
                "C001", LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 28));

        assertEquals(1, response.getCustomers().size());
        assertEquals(90, response.getCustomers().get(0).getTotalRewards());
        assertEquals(1, response.getCustomers().get(0).getMonthlyRewards().size());
    }

    /**
     * Verifies requesting a missing customer id raises not found.
     */
    @Test
    void shouldThrowWhenCustomerDoesNotExist() {
        when(transactionRepository.findAll()).thenReturn(List.of(
                new Transaction("1", "C001", "Alice", BigDecimal.valueOf(120), LocalDate.of(2026, 1, 10))
        ));

        assertThrows(CustomerNotFoundException.class,
                () -> rewardService.getRewardsSummary("C999", null, null));
    }

    /**
     * Verifies invalid date ranges are rejected.
     */
    @Test
    void shouldThrowForInvalidDateRange() {
        assertThrows(InvalidDateRangeException.class,
                () -> rewardService.getRewardsSummary(null, LocalDate.of(2026, 3, 2), LocalDate.of(2026, 3, 1)));
    }

    /**
     * Verifies invalid transactions in the data source are rejected.
     */
    @Test
    void shouldThrowWhenTransactionAmountIsNegative() {
        when(transactionRepository.findAll()).thenReturn(List.of(
                new Transaction("1", "C001", "Alice", BigDecimal.valueOf(-10), LocalDate.of(2026, 1, 1))
        ));

        assertThrows(InvalidTransactionException.class,
                () -> rewardService.getRewardsSummary(null, null, null));
    }
}
