package com.almundo.callcenter;

import com.almundo.callcenter.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by Effie on 6/4/2019.
 */
public class Dispatcher implements Runnable {

    private final int MAX_CALLS = 10;

    private PriorityQueue<Employee> employees;

    private ConcurrentLinkedQueue<Call> onHoldCalls;

    private ExecutorService executorService;

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    public Dispatcher(PriorityQueue<Employee> employees) {
        logger.info("Starting dispatcher. ");
        this.onHoldCalls = new ConcurrentLinkedQueue<>();
        this.employees = employees;
        this.executorService = Executors.newFixedThreadPool(MAX_CALLS);
    }

    public void dispatchCall(Call call) {

        Optional<Employee> employee = getCallAttender();
        if (!employee.isPresent()) {
            logger.info("Sorry, the line is busy, your call is going in wait queue");
            onHoldCalls.add(call);
        } else {
            Employee attendant = employee.get();
            attendant.setStatus(Status.BUSY);
            attendant.takeCall(call);
            executorService.execute(attendant);
        }

    }

    /**
     * Filters a list of employees that are not busy.
     * @return
     */
    public Optional<Employee> getCallAttender() {
        return employees.stream().filter(e -> e.getStatus() == Status.AVAILABLE).findFirst();
    }

    @Override
    public void run() {

    }
}
