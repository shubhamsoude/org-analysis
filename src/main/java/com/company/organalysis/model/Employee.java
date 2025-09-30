package com.company.organalysis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final BigDecimal salary;
    private final Integer managerId; // null for CEO
    private final List<Employee> subordinates = new ArrayList<>();

    public Employee(int id, String firstName, String lastName, BigDecimal salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public BigDecimal getSalary() { return salary; }
    public Integer getManagerId() { return managerId; }
    public List<Employee> getSubordinates() { return subordinates; }

    public String fullName() {
        return firstName + " " + lastName;
    }

    public boolean isManager() {
        return !subordinates.isEmpty();
    }

    @Override
    public String toString() {
        return fullName() + " (" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}