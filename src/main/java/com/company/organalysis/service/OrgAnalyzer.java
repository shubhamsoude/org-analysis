package com.company.organalysis.service;

import com.company.organalysis.model.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class OrgAnalyzer {
    private final Map<Integer, Employee> byId;

    public OrgAnalyzer(Map<Integer, Employee> byId) {
        this.byId = byId;
    }

    /** Managers underpaid if salary < 120% of avg direct subordinates */
    public Map<Employee, BigDecimal> findUnderpaidManagers() {
        Map<Employee, BigDecimal> result = new HashMap<>();
        for (Employee m : byId.values()) {
            if (!m.isManager()) continue;
            BigDecimal avg = averageDirects(m);
            if (avg == null) continue;

            BigDecimal minAllowed = avg.multiply(BigDecimal.valueOf(1.2));
            if (m.getSalary().compareTo(minAllowed) < 0) {
                result.put(m, minAllowed.subtract(m.getSalary()).setScale(2, RoundingMode.HALF_UP));
            }
        }
        return result;
    }

    /** Managers overpaid if salary > 150% of avg direct subordinates */
    public Map<Employee, BigDecimal> findOverpaidManagers() {
        Map<Employee, BigDecimal> result = new HashMap<>();
        for (Employee m : byId.values()) {
            if (!m.isManager()) continue;
            BigDecimal avg = averageDirects(m);
            if (avg == null) continue;

            BigDecimal maxAllowed = avg.multiply(BigDecimal.valueOf(1.5));
            if (m.getSalary().compareTo(maxAllowed) > 0) {
                result.put(m, m.getSalary().subtract(maxAllowed).setScale(2, RoundingMode.HALF_UP));
            }
        }
        return result;
    }

    /** Employees with >4 managers in between them and CEO */
    public Map<Employee, Integer> findEmployeesWithTooLongChain() {
        Map<Employee, Integer> result = new HashMap<>();
        for (Employee e : byId.values()) {
            int between = managersBetweenEmployeeAndCeo(e);
            if (between > 4) {
                result.put(e, between - 4);
            }
        }
        return result;
    }

    private BigDecimal averageDirects(Employee m) {
        if (m.getSubordinates().isEmpty()) return null;
        BigDecimal sum = BigDecimal.ZERO;
        for (Employee s : m.getSubordinates()) {
            sum = sum.add(s.getSalary());
        }
        return sum.divide(BigDecimal.valueOf(m.getSubordinates().size()), 6, RoundingMode.HALF_UP);
    }

    private int managersBetweenEmployeeAndCeo(Employee e) {
        int count = 0;
        Integer mid = e.getManagerId();
        while (mid != null) {
            count++;
            Employee manager = byId.get(mid);
            if (manager == null) break; // broken chain
            mid = manager.getManagerId();
        }
        // count includes the CEO link → exclude CEO
        return Math.max(0, count - 1);
    }
}