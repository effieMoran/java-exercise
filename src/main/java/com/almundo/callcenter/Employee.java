package com.almundo.callcenter;

import com.almundo.callcenter.enums.Position;
import com.almundo.callcenter.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * Created by Effie on 6/4/2019.
 */
public class Employee implements Comparable<Employee>{

    private String employeeName;

    private Status status = Status.AVAILABLE;

    private Position position = Position.OPERATOR;

    private Call currentCall;

    private static final Logger logger = LoggerFactory.getLogger(Employee.class);

    public Employee(String employeeName) {
        this.employeeName = employeeName;
    }

    public Employee(String employeeName, Status status, Position position) {
        this.employeeName = employeeName;
        this.status = status;
        this.position = position;
    }

    public Call getCurrentCall() {
        return currentCall;
    }

    public void setCurrentCall(Call currentCall) {
        this.currentCall = currentCall;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Position getPosition() {
        return position;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public int hashCode() {
        return position != null ? position.hashCode() : 0;
    }

    public int compareTo(Employee employee) {
        return  this.getPosition().compareTo(employee.getPosition());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeName='" + employeeName + '\'' +
                ", status=" + status +
                ", position=" + position +
                '}';
    }

    public boolean isAvailable() {
        if (null != currentCall) {
            return currentCall.isFinished() || currentCall.isInterrupted();
        }
        return true;
    }
}
