package com.company.organalysis;

import com.company.organalysis.model.Employee;
import com.company.organalysis.service.EmployeeParser;
import com.company.organalysis.service.OrgAnalyzer;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrgAnalyzerTest {

    @Test
    void testSalaryChecksAndChainFromCsv() throws Exception {
        String csv = String.join("\n",
                "Id,firstName,lastName,salary,managerId",
                "1,CEO,Root,100000,",     // CEO
                "2,Manager,One,60000,1",  // Manager
                "3,Sub,A,40000,2",
                "4,Sub,B,50000,2"
        );
        Path tmp = Files.createTempFile("emp", ".csv");
        Files.writeString(tmp, csv);

        Map<Integer, Employee> map = new EmployeeParser().parse(tmp);
        OrgAnalyzer analyzer = new OrgAnalyzer(map);

        // check manager not flagged
        assertFalse(analyzer.findUnderpaidManagers().containsKey(map.get(2)), "Manager should not be underpaid");
        assertFalse(analyzer.findOverpaidManagers().containsKey(map.get(2)), "Manager should not be overpaid");

        // CEO IS flagged as overpaid (100k > 90k)
        assertTrue(analyzer.findOverpaidManagers().containsKey(map.get(1)), "CEO should be overpaid");

        // chain checks
        assertTrue(analyzer.findEmployeesWithTooLongChain().isEmpty());
    }
}