package com.example.rewards.controller;

import com.example.rewards.dto.RewardsSummaryResponse;
import com.example.rewards.service.RewardService;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing reward calculation endpoints.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService rewardService;

    /**
     * Creates reward controller.
     *
     * @param rewardService rewards business service
     */
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * Returns reward points grouped by customer and month with totals.
     *
     * @param customerId optional customer identifier to scope the response
     * @param startDate optional start date in ISO format (yyyy-MM-dd)
     * @param endDate optional end date in ISO format (yyyy-MM-dd)
     * @return rewards summary response
     */
    @GetMapping
    public RewardsSummaryResponse getRewards(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return rewardService.getRewardsSummary(customerId, startDate, endDate);
    }
}
