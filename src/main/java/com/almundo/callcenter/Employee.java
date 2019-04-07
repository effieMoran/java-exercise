package com.almundo.callcenter;

import com.almundo.callcenter.enums.Position;
import com.almundo.callcenter.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Created by Effie on 6/4/2019.
 */
public class Employee implements Comparable<Employee>, Runnable{

    private String employeeName;

    private Status status = Status.AVAILABLE;

    private Position position = Position.OPERATOR;

    private LinkedList<Call> pendingCalls = new LinkedList<>();

    private static final Logger logger = LoggerFactory.getLogger(Employee.class);

    public Employee(String employeeName) {
        this.employeeName = employeeName;
    }

    public Employee(String employeeName, Status status, Position position) {
        this.employeeName = employeeName;
        this.status = status;
        this.position = position;
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

    public void takeCall(Call call) {
        pendingCalls.add(call);
//        status = Status.BUSY;
//        try {
//            Thread.sleep(call.getDuration() * 1000);
//            logger.info(employeeName + " has taken a call");
//        } catch (InterruptedException e){
//            Thread.currentThread().interrupt();
//            logger.error("There's been an issue with the call. Please, try again later.");
//        }
//        status = Status.AVAILABLE;
    }

    @Override
    public int hashCode() {
        return position != null ? position.hashCode() : 0;
    }

    public int compareTo(Employee employee) {
        if (this.position == employee.getPosition()) {
            return 0;
        }
        else if ( this.position.getOrder() > employee.getPosition().getOrder()) {
            return 1;
        }
        return -1;
        //return  this.getPosition().compareTo(employee.getPosition());
    }

    @Override
    public void run() {
        //logger.info("The " + position + " " + employeeName + " starts to work.");
        if (!pendingCalls.isEmpty()) {
            Call call = pendingCalls.poll();
            status = Status.BUSY;
            try {
                Thread.sleep(call.getDuration() * 1000);
                logger.info(position + " " + employeeName + " has taken a call of " + call.getDuration());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("There's been an issue with the call. Please, try again later.");
            }
            logger.info("The call of " + call.getDuration() + " attended by " + employeeName+ " has finished");
            status = Status.AVAILABLE;
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeName='" + employeeName + '\'' +
                ", status=" + status +
                ", position=" + position +
                '}';
    }
}
