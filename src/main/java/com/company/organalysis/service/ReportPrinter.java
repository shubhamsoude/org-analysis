package com.company.organalysis.service;

import com.company.organalysis.model.Employee;

import java.math.BigDecimal;
import java.util.Map;

public class ReportPrinter {

    public void print(Map<Employee, BigDecimal> underpaid,
                      Map<Employee, BigDecimal> overpaid,
                      Map<Employee, Integer> tooLong) {
        System.out.println("=== Underpaid Managers ===");
        if (underpaid.isEmpty()) {
            System.out.println("(none)");
        } else {
            underpaid.forEach((emp, diff) ->
                    System.out.println(emp.fullName() + " (" + emp.getId() + ") underpaid by " + diff));
        }
        System.out.println();

        System.out.println("=== Overpaid Managers ===");
        if (overpaid.isEmpty()) {
            System.out.println("(none)");
        } else {
            overpaid.forEach((emp, diff) ->
                    System.out.println(emp.fullName() + " (" + emp.getId() + ") overpaid by " + diff));
        }
        System.out.println();

        System.out.println("=== Employees with Too Long Reporting Line ===");
        if (tooLong.isEmpty()) {
            System.out.println("(none)");
        } else {
            tooLong.forEach((emp, extra) ->
                    System.out.println(emp.fullName() + " (" + emp.getId() + ") exceeds chain length by " + extra));
        }
    }
}