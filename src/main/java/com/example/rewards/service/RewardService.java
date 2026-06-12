package com.example.rewards.service;

import com.example.rewards.dto.CustomerRewardsResponse;
import com.example.rewards.dto.RewardsSummaryResponse;
import com.example.rewards.exception.CustomerNotFoundException;
import com.example.rewards.exception.InvalidDateRangeException;
import com.example.rewards.exception.InvalidRequestException;
import com.example.rewards.model.Transaction;
import com.example.rewards.repository.TransactionRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Service that calculates rewards per customer grouped by month and total.
 */
@Service
public class RewardService {

    private final TransactionRepository transactionRepository;
    private final RewardCalculator rewardCalculator;

    /**
     * Creates reward service with required collaborators.
     *
     * @param transactionRepository transaction source
     * @param rewardCalculator reward calculation component
     */
    public RewardService(TransactionRepository transactionRepository, RewardCalculator rewardCalculator) {
        this.transactionRepository = transactionRepository;
        this.rewardCalculator = rewardCalculator;
    }

    /**
     * Returns reward summary for all customers or a single customer, optionally filtered by date range.
     *
     * @param customerId customer identifier filter
     * @param startDate optional start date filter (inclusive)
     * @param endDate optional end date filter (inclusive)
     * @return reward summary response
     */
    public RewardsSummaryResponse getRewardsSummary(String customerId, LocalDate startDate, LocalDate endDate) {
        String normalizedCustomerId = normalizeCustomerId(customerId);
        validateDateRange(startDate, endDate);

        List<Transaction> filteredTransactions = findTransactions(normalizedCustomerId, startDate, endDate);

        Map<String, List<Transaction>> transactionsByCustomer = filteredTransactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId));

        List<CustomerRewardsResponse> customerResponses = transactionsByCustomer.values().stream()
                .map(this::buildCustomerResponse)
                .sorted(Comparator.comparing(CustomerRewardsResponse::getCustomerId))
                .collect(Collectors.toList());

        return new RewardsSummaryResponse(customerResponses);
    }

    private List<Transaction> findTransactions(String customerId, LocalDate startDate, LocalDate endDate) {
        boolean hasDateRange = startDate != null || endDate != null;

        if (customerId != null) {
            if (!transactionRepository.existsByCustomerId(customerId)) {
                throw new CustomerNotFoundException("Customer id " + customerId + " was not found.");
            }
            return hasDateRange
                    ? transactionRepository.findByCustomerIdAndDateRange(customerId, startDate, endDate)
                    : transactionRepository.findByCustomerId(customerId);
        }

        return hasDateRange
                ? transactionRepository.findByDateRange(startDate, endDate)
                : transactionRepository.findAll();
    }

    private CustomerRewardsResponse buildCustomerResponse(List<Transaction> customerTransactions) {
        Transaction first = customerTransactions.get(0);

        Map<YearMonth, Long> rewardsByMonth = customerTransactions.stream()
                .collect(Collectors.groupingBy(
                        tx -> YearMonth.from(tx.getTransactionDate()),
                        Collectors.summingLong(tx -> rewardCalculator.calculatePoints(tx.getAmount()))
                ));

        Map<String, Long> orderedMonthlyRewards = rewardsByMonth.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toString(),
                        Map.Entry::getValue,
                        (left, right) -> left,
                        LinkedHashMap::new
                ));

        long totalRewards = orderedMonthlyRewards.values().stream().mapToLong(Long::longValue).sum();
        return new CustomerRewardsResponse(first.getCustomerId(), first.getCustomerName(), orderedMonthlyRewards, totalRewards);
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException("startDate must be before or equal to endDate.");
        }
    }

    private String normalizeCustomerId(String customerId) {
        if (customerId == null) {
            return null;
        }
        String trimmedCustomerId = customerId.trim();
        if (trimmedCustomerId.isEmpty()) {
            throw new InvalidRequestException("customerId must not be blank.");
        }
        return trimmedCustomerId;
    }
}
