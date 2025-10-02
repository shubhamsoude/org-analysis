package com.company.organalysis;

import com.company.organalysis.model.Employee;
import com.company.organalysis.service.EmployeeParser;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeParserEdgeCaseTest {

    @Test
    void emptyCsvShouldFail() throws Exception {
        Path tmp = Files.createTempFile("emp-empty", ".csv");
        Files.writeString(tmp, ""); // no header
        EmployeeParser parser = new EmployeeParser();
        assertThrows(IllegalStateException.class, () -> parser.parse(tmp));
    }

    @Test
    void headerOnlyShouldFail() throws Exception {
        Path tmp = Files.createTempFile("emp-header", ".csv");
        Files.writeString(tmp, "Id,firstName,lastName,salary,managerId\n");
        EmployeeParser parser = new EmployeeParser();
        assertThrows(IllegalStateException.class, () -> parser.parse(tmp));
    }

    @Test
    void duplicateIdsShouldFail() throws Exception {
        String csv = String.join("\n",
                "Id,firstName,lastName,salary,managerId",
                "1,Alice,Smith,50000,",
                "1,Bob,Jones,40000,"  // duplicate id
        );
        Path tmp = Files.createTempFile("emp-dupes", ".csv");
        Files.writeString(tmp, csv);

        EmployeeParser parser = new EmployeeParser();
        assertThrows(IllegalStateException.class, () -> parser.parse(tmp));
    }

    @Test
    void tooLongChainShouldFlagEmployee() throws Exception {
        // Create chain: CEO -> M1 -> M2 -> M3 -> M4 -> M5 -> Worker
        String csv = String.join("\n",
                "Id,firstName,lastName,salary,managerId",
                "1,CEO,Root,100000,",
                "2,M1,Mgr,60000,1",
                "3,M2,Mgr,50000,2",
                "4,M3,Mgr,50000,3",
                "5,M4,Mgr,50000,4",
                "6,M5,Mgr,50000,5",
                "7,Worker,Emp,40000,6"
        );
        Path tmp = Files.createTempFile("emp-chain", ".csv");
        Files.writeString(tmp, csv);

        Map<Integer, Employee> map = new EmployeeParser().parse(tmp);
        var analyzer = new com.company.organalysis.service.OrgAnalyzer(map);

        assertFalse(analyzer.findEmployeesWithTooLongChain().isEmpty(),
                "Worker should be flagged for exceeding chain length");
    }
}