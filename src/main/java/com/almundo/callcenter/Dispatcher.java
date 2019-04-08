package com.almundo.callcenter;

import com.almundo.callcenter.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;


/**
 * Created by Effie on 6/4/2019.
 */
public class Dispatcher implements Runnable {

    private final int MAX_CALLS = 10;

    private PriorityBlockingQueue<Employee> employees;

    private BlockingQueue<Runnable> currentCalls = new ArrayBlockingQueue<>(50);

    private ConcurrentLinkedQueue<Call> onHoldCalls;

    private ExecutorService executorService;

    private boolean callServiceWorking = true;

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    public Dispatcher(PriorityBlockingQueue<Employee> employees) {
        logger.info("Creating dispatcher. ");
        this.onHoldCalls = new ConcurrentLinkedQueue<>();
        this.employees = employees;
        this.executorService = Executors.newFixedThreadPool(MAX_CALLS);
    }

    public void produceCall(Call call) {
        currentCalls.add(call);
    }

    public void dispatchCall(Call call){
        Optional<Employee> employee = getCallAttender();
        employee.ifPresent(
                 attender -> {

                     attender.setStatus(Status.BUSY);
                     //employees.remove(attender);
                     logger.info(attender.getEmployeeName() + " has taken a call.");

                     executorService.execute(call);
                     attender.setStatus(Status.AVAILABLE);

                     //employees.add(attender);
                     logger.info(attender.getEmployeeName() + " has finished a call.");
                 }
        );
        //TODO: Add solution when there is no employee available.

    }

    public void setCallServiceWorking(boolean callServiceWorking) {
        this.callServiceWorking = callServiceWorking;
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
        logger.info("Stated dispatcher");
        while (callServiceWorking || !currentCalls.isEmpty()) {
            if (!currentCalls.isEmpty()) {
                Call call = (Call) currentCalls.poll();
                dispatchCall(call);

            }
            else {
                //TODO: Check what to do if the list is empty.
            }
        }
        logger.info("Work day finished");
    }
}
