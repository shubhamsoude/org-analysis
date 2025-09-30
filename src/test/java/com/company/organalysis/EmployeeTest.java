package com.company.organalysis;

import com.company.organalysis.model.Employee;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    void testEmployeeBasics() {
        Employee e = new Employee(1, "Alice", "Smith", new BigDecimal("50000"), null);

        assertEquals(1, e.getId());
        assertEquals("Alice", e.getFirstName());
        assertEquals("Smith", e.getLastName());
        assertEquals(new BigDecimal("50000"), e.getSalary());
        assertNull(e.getManagerId());
        assertEquals("Alice Smith", e.fullName());
        assertFalse(e.isManager());

        // add a subordinate and check manager status
        e.getSubordinates().add(new Employee(2, "Bob", "Jones", new BigDecimal("30000"), 1));
        assertTrue(e.isManager());
    }
}