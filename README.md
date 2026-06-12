# Rewards API - Spring Boot Assignment

This project implements the retailer rewards calculation assignment using Spring Boot.

## Problem Statement

For each transaction:

- 2 points for every dollar spent over `$100`
- 1 point for every dollar spent over `$50` and up to `$100`

Example:

- `$120` purchase = `(120 - 100) * 2 + 50 = 90 points`

The API calculates reward points per customer by month and total for a selected time range.

## Tech Stack

- Java 17
- Spring Boot 3.5
- Maven
- JUnit 5 / Spring Test / MockMvc

## Project Structure

- `com.example.rewards.controller` - REST API endpoints
- `com.example.rewards.service` - reward business logic and aggregation
- `com.example.rewards.repository` - transaction data access abstraction
- `com.example.rewards.config` - seeded sample data
- `com.example.rewards.dto` - API request/response models
- `com.example.rewards.exception` - custom exceptions and global handler
- `src/test` - unit and integration tests

## Endpoint

### Get rewards summary

`GET /api/rewards`

Optional query params:

- `customerId` (string)
- `startDate` (ISO date: `yyyy-MM-dd`)
- `endDate` (ISO date: `yyyy-MM-dd`)

### Example requests

- All customers:
  - `GET /api/rewards`
- Single customer:
  - `GET /api/rewards?customerId=C001`
- Date range filter:
  - `GET /api/rewards?startDate=2026-01-01&endDate=2026-03-31`
- Customer + date range:
  - `GET /api/rewards?customerId=C001&startDate=2026-01-01&endDate=2026-01-31`

## Sample Data

The app seeds in-memory transaction data for multiple customers and multiple months (`C001`, `C002`, `C003`) to demonstrate:

- No rewards (amount <= 50)
- First slab rewards (51..100)
- Second slab rewards (>100)
- Decimal amount behavior

## Decimal Amount Handling

Reward points are calculated with `BigDecimal` using the full transaction amount. The final calculated points are rounded down to a whole number because reward points are returned as integers.

Examples:

- `$50.99` -> `0` points
- `$99.99` -> `49` points
- `$100.50` -> `51` points
- `$120.75` -> `91` points

## How Months Are Handled

Months are **not hardcoded**. The code groups transactions dynamically using `YearMonth` from each transaction date.

## Error Handling

Global exception handling is implemented for:

- Invalid date range (`startDate > endDate`) -> `400 Bad Request`
- Unknown customer id -> `404 Not Found`
- Blank customer id -> `400 Bad Request`
- Invalid transaction amount (null/negative) -> `400 Bad Request`
- Invalid parameter format -> `400 Bad Request`

## Validation

The transaction model uses Jakarta Bean Validation annotations for required fields and non-negative transaction amounts. The in-memory repository validates seeded transaction records during initialization so invalid sample data fails fast.

## Repository Queries

The repository exposes scoped query methods instead of only `findAll()`:

- `findByCustomerId`
- `findByDateRange`
- `findByCustomerIdAndDateRange`
- `existsByCustomerId`

## Tests

### Unit tests

- `RewardCalculatorTest`
- `RewardServiceTest`

Coverage includes:

- Reward slab calculations
- Decimal and boundary handling
- Repository query methods
- Transaction model validation
- Multiple customer/month aggregation
- Date filtering
- Unknown customer scenario
- Invalid date range
- Negative transaction amount
- Blank customer id

### Integration tests

- `RewardControllerIntegrationTest`

Coverage includes:

- Full endpoint response for multiple customers
- Customer + date filter behavior
- Unknown customer (`404`)
- Invalid date range (`400`)
- Invalid date input format (`400`)

## Run Locally

From project root:

```bash
mvn clean test
mvn spring-boot:run
```

Or open the project in IntelliJ/Eclipse as a Maven project and run `RewardsApplication`.

## Notes for Submission

- `target/` and `bin/` are ignored in `.gitignore`
- Code follows Java naming/package conventions
- JavaDocs are added at class and method levels
- Tests include positive and negative scenarios
