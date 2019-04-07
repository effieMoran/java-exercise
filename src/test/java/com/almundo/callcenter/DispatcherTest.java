package com.almundo.callcenter;

import com.almundo.callcenter.enums.Position;
import com.almundo.callcenter.enums.Status;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by User on 7/4/2019.
 */
public class DispatcherTest {

    @Test
    public void freeEmployee() {
        PriorityQueue<Employee> employees = new PriorityQueue<>();
        Employee employee1 = new Employee("Homer Simpson", Status.AVAILABLE, Position.MANAGER);
        Employee employee2 = new Employee("John Doe",Status.AVAILABLE, Position.SUPERVISOR);
        Employee employee3 =new Employee("John Snow",Status.AVAILABLE, Position.OPERATOR);

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        Dispatcher dispatcher = new Dispatcher(employees);

        Optional<Employee> attendant = dispatcher.getCallAttender();
        Assert.assertEquals(employee3, attendant.get());
        employees.poll();

        attendant = dispatcher.getCallAttender();
        Assert.assertEquals(employee2, attendant.get());
        employees.poll();

        attendant = dispatcher.getCallAttender();
        Assert.assertEquals(employee1, attendant.get());
    }
}
