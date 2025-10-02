# Assumptions

- **CEO**: The CEO is the only employee with no `managerId`. Exactly one CEO must exist in the data.
- **Direct reports only**: Salary band calculations use the *average of direct reports only*, not all descendants.
- **Salary rules**:
    - Underpaid if manager earns less than **20% above** average of direct reports.
    - Overpaid if manager earns more than **50% above** average of direct reports.
- **Employees with long chain**: Flagged if there are more than 4 managers between them and the CEO.
- **Input validation**:
    - Duplicate employee IDs → not allowed (throws exception).
    - Multiple or zero CEOs → not allowed.
    - Cycles in hierarchy (direct or indirect) → not allowed.
    - Unknown `managerId` values → tolerated, but a warning is logged.
- **Salaries**: Treated as `BigDecimal`. Negative or zero salaries are considered invalid business input but not explicitly blocked.
- **Scale**: Monetary differences are displayed to 2 decimal places.
- **Size**: Input is assumed to be at most ~1000 employees. Data is stored fully in memory.
