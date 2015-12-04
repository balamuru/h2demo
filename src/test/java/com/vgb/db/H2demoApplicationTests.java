package com.vgb.db;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = H2demoApplication.class)
@WebAppConfiguration
public class H2demoApplicationTests {

    @Autowired
    EmployeeService employeeService;

	@Test
	public void contextLoads() {
	}


    @Before
    public void setup() throws Exception {
        employeeService.deleteAll();
    }

    @Test
    public void createEmployees() throws Exception {

        final List<Long> runTimes = new ArrayList<>();
        final int numIterations = 20;
        final int numEmployeesToCreate = 100000;
        for (int i = 1; i <= numIterations; i++) {
            System.out.println("Starting iteration " + i);
            createEmployees(runTimes, numEmployeesToCreate);
        }

        for (Long runTime : runTimes) {
            System.out.println("Run time : " + runTime);
        }


    }

    private void createEmployees(List<Long> runTimes, int numEmployeesToCreate) throws Exception {

        final BitSet bitset = new BitSet(64*1024);
        final int bitsToPopulate = 200;
//        final int bitsToPopulate = bitset.size();
        for (int i = 0; i < bitsToPopulate; i ++) {
            bitset.set(i, true);
        }
        Long lastId = null;
        long t1 = System.currentTimeMillis();
        for (int i = 1 ; i <= numEmployeesToCreate; i++) {

            final Employee employee = new Employee(null, "John Doe" + 1, i + ", Main Street, Sometown, USA, Earth, Solar System, Milky Way, Universe, Blah Blah", bitset);
            employeeService.create(employee);

            if (i % 10000 == 0) {
            System.out.println("#### " + i + " : " + employee);
//            assertNotNull(employee.getId());
            }
            lastId = employee.getId();
        }
        long t2 = System.currentTimeMillis();
        System.out.println("Time taken: " + (t2 - t1) + " with last id " + lastId);
        runTimes.add(t2-t1);
    }


}
