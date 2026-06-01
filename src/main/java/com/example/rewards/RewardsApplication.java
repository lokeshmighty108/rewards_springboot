package com.example.rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Rewards Spring Boot application.
 */
@SpringBootApplication
public class RewardsApplication {

    /**
     * Starts the Rewards API application.
     *
     * @param args startup arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(RewardsApplication.class, args);
    }
}
