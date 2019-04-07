package com.almundo.callcenter;


import com.almundo.callcenter.enums.Position;
import com.almundo.callcenter.enums.Status;
import org.junit.Test;

import java.util.concurrent.PriorityBlockingQueue;

import static org.junit.Assert.assertEquals;

/**
 * Created by Effie on 6/4/2019.
 */
public class EmployeeTest {

    @Test
    public void employeePriority(){
        PriorityBlockingQueue<Employee> employees = new PriorityBlockingQueue<>();
        employees.add(new Employee("John Doe",Status.AVAILABLE, Position.MANAGER));
        employees.add(new Employee("John Doe",Status.AVAILABLE, Position.SUPERVISOR));
        employees.add(new Employee("John Doe",Status.AVAILABLE, Position.OPERATOR));
        assertEquals(Position.OPERATOR, employees.poll().getPosition());
        assertEquals(Position.SUPERVISOR, employees.poll().getPosition());
        assertEquals(Position.MANAGER, employees.poll().getPosition());
    }
}