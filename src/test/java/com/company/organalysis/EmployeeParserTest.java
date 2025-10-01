package com.company.organalysis;

import com.company.organalysis.model.Employee;
import com.company.organalysis.service.EmployeeParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeParserTest {

    @Test
    void parseAndLinkHierarchy() throws IOException {
        String csv = String.join("\n",
                "Id,firstName,lastName,salary,managerId",
                "1,CEO,Root,100000,",
                "2,John,Doe,50000,1",
                "3,Jane,Roe,60000,1"
        );

        Path tmp = Files.createTempFile("emp", ".csv");
        Files.writeString(tmp, csv);

        Map<Integer, Employee> map = new EmployeeParser().parse(tmp);

        assertEquals(3, map.size());

        Employee ceo = map.get(1);
        assertNull(ceo.getManagerId());
        assertEquals(2, ceo.getSubordinates().size());
    }
}