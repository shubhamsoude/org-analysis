# Employee Org Analysis

A Java SE console application to analyze an organization’s structure from a CSV file.

## Features
- Identify **underpaid managers** (earn < 20% above average of direct reports).
- Identify **overpaid managers** (earn > 50% above average of direct reports).
- Identify **employees with too long reporting line** (> 4 managers between them and CEO).

## Requirements
- Java 17+
- Maven 3.6+
- JUnit 5 (for tests)

## Example Output
Using the provided sample file (sample-data/employees.csv), the program prints:

=== Underpaid Managers ===
- Martin Chekov (124) underpaid by 15000.00

=== Overpaid Managers ===
- (none)

=== Employees with Too Long Reporting Line ===
- (none)


## Build
```bash
mvn clean package