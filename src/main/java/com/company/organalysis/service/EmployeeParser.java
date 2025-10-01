package com.company.organalysis.service;

import com.company.organalysis.model.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class EmployeeParser {

    public Map<Integer, Employee> parse(Path csvPath) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(csvPath)) {
            String header = br.readLine();
            if (header == null) throw new IllegalStateException("Empty CSV");

            Map<Integer, Employee> byId = new HashMap<>();
            String line;
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 5) {
                    System.err.println("WARN line " + lineNo + ": expected 5 fields, got " + parts.length);
                    continue;
                }
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String fn = parts[1].trim();
                    String ln = parts[2].trim();
                    BigDecimal salary = new BigDecimal(parts[3].trim());
                    Integer managerId = parts[4].trim().isEmpty() ? null : Integer.parseInt(parts[4].trim());

                    if (byId.containsKey(id)) {
                        throw new IllegalStateException("Duplicate employee id: " + id);
                    }
                    byId.put(id, new Employee(id, fn, ln, salary, managerId));
                } catch (NumberFormatException nfe) {
                    System.err.println("WARN line " + lineNo + ": invalid number -> " + nfe.getMessage());
                }
            }

            // link subordinates
            for (Employee e : byId.values()) {
                if (e.getManagerId() != null) {
                    Employee m = byId.get(e.getManagerId());
                    if (m != null) {
                        m.getSubordinates().add(e);
                    } else {
                        System.err.println("WARN: employee " + e + " has unknown managerId " + e.getManagerId());
                    }
                }
            }

            // validate exactly one CEO
            long ceos = byId.values().stream().filter(e -> e.getManagerId() == null).count();
            if (ceos != 1) {
                throw new IllegalStateException("Expected exactly 1 CEO, found " + ceos);
            }

            detectCycles(byId);
            return byId;
        }
    }

    private void detectCycles(Map<Integer, Employee> byId) {
        Set<Integer> visiting = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        for (Employee e : byId.values()) {
            if (!visited.contains(e.getId())) {
                if (dfsCycle(e, byId, visiting, visited)) {
                    throw new IllegalStateException("Cycle detected in management chain starting at " + e);
                }
            }
        }
    }

    private boolean dfsCycle(Employee e, Map<Integer, Employee> byId, Set<Integer> visiting, Set<Integer> visited) {
        if (visiting.contains(e.getId())) return true;
        if (visited.contains(e.getId())) return false;
        visiting.add(e.getId());
        Integer mid = e.getManagerId();
        if (mid != null) {
            Employee m = byId.get(mid);
            if (m != null) {
                if (dfsCycle(m, byId, visiting, visited)) return true;
            }
        }
        visiting.remove(e.getId());
        visited.add(e.getId());
        return false;
    }
}