package com.company.organalysis;

import com.company.organalysis.model.Employee;
import com.company.organalysis.service.EmployeeParser;
import com.company.organalysis.service.OrgAnalyzer;
import com.company.organalysis.service.ReportPrinter;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IntegrationTest {

    @Test
    void testEndToEnd() throws Exception {
        Path path = Path.of("sample-data/employees.csv");

        Map<Integer, Employee> employees = new EmployeeParser().parse(path);
        assertNotNull(employees);

        OrgAnalyzer analyzer = new OrgAnalyzer(employees);
        new ReportPrinter().print(
                analyzer.findUnderpaidManagers(),
                analyzer.findOverpaidManagers(),
                analyzer.findEmployeesWithTooLongChain()
        );
    }
}