package com.company.organalysis;

import com.company.organalysis.model.Employee;
import com.company.organalysis.service.EmployeeParser;
import com.company.organalysis.service.OrgAnalyzer;
import com.company.organalysis.service.ReportPrinter;

import java.nio.file.Path;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar org-analysis.jar <path-to-csv>");
            System.exit(1);
        }

        Path path = Path.of(args[0]);
        try {
            Map<Integer, Employee> employees = new EmployeeParser().parse(path);
            OrgAnalyzer analyzer = new OrgAnalyzer(employees);

            new ReportPrinter().print(
                    analyzer.findUnderpaidManagers(),
                    analyzer.findOverpaidManagers(),
                    analyzer.findEmployeesWithTooLongChain()
            );
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(2);
        }
    }
}