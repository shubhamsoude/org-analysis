# Employee Org Analysis

This project is a console-based Java application to analyze an organization’s structure from a CSV file.

## Requirements
- Use only **Java SE** (any version).
- Use **Maven** for build.
- Use **JUnit** for tests.
- No frameworks, no GUI — only console output.

## Goals
The program should:
1. Identify managers who are **underpaid** (earn less than 20% above avg salary of their direct reports).
2. Identify managers who are **overpaid** (earn more than 50% above avg salary of their direct reports).
3. Identify employees whose reporting line to the CEO is **too long** (more than 4 managers in between).