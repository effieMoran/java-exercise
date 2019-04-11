package com.almundo.callcenter;

import com.almundo.callcenter.Constants.OperatorMessagges;
import com.almundo.callcenter.enums.Status;
import org.apache.commons.collections4.list.TreeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by Effie on 6/4/2019.
 */
public class Dispatcher implements Runnable {

    private final int MAX_CALLS = 10;

    List<Employee> availableEmployees = Collections.synchronizedList(new ArrayList<Employee>());

    private PriorityBlockingQueue<Employee> busyEmployees;

    private int MAX_PENDING_CALLS = 100;

    private BlockingQueue<Runnable> currentCalls = new ArrayBlockingQueue<>(MAX_PENDING_CALLS);

    private ThreadPoolExecutor threadPoolExecutor;

    private boolean callServiceWorking = true;

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    public Dispatcher(TreeList<Employee> availableEmployees) {
        logger.info("Creating dispatcher. ");
        this.availableEmployees.addAll(availableEmployees);
        this.availableEmployees = availableEmployees;
        busyEmployees = new PriorityBlockingQueue<>();
        this.threadPoolExecutor = new ThreadPoolExecutor(MAX_CALLS,
                MAX_CALLS, 5, TimeUnit.SECONDS, currentCalls);
        threadPoolExecutor.setRejectedExecutionHandler(new RejectedExecutionHandlerCustom());
    }

    public void produceCall(Call call) {

        try {
            currentCalls.add(call);
        } catch (IllegalStateException e) {
            logger.info(OperatorMessagges.HIGH_CALL_VOLUME);
        }
    }

    public void dispatchCall(Call call) {

        synchronized (availableEmployees) {
            if (!availableEmployees.isEmpty()) {

                Optional<Employee> optionalEmployee =findEmployee();
                if (optionalEmployee.isPresent()) {
                    Employee employee = optionalEmployee.get();
                    availableEmployees.remove(employee);
                    if (employee.isAvailable() || null != employee.getCurrentCall()) {
                        employee.setCurrentCall(call);
                        employee.setStatus(Status.BUSY);
                        busyEmployees.add(employee);

                        logger.info(employee.getEmployeeName() + " has taken a call.");

                        threadPoolExecutor.execute(call);
                    }
                } else {
                    logger.info(OperatorMessagges.EMPLOYEES_UNAVAILABLE);
                    currentCalls.add(call);
                }
            } else if (MAX_PENDING_CALLS == availableEmployees.size()){
                logger.info(OperatorMessagges.HIGH_CALL_VOLUME);
            } else {
                logger.info(OperatorMessagges.EMPLOYEES_STILL_UNAVAILABLE);
                currentCalls.add(call);
            }
        }
    }

    /**
     * Find the first employee available and related to the position.
     *
     * @return Optional of Employee
     */
    private Optional<Employee> findEmployee() {
        synchronized (availableEmployees) {
            return availableEmployees.stream().min(Comparator.comparing(Employee::getPosition));
        }
    }

    public void setCallServiceWorking(boolean callServiceWorking) {
        this.callServiceWorking = callServiceWorking;
    }

    @Override
    public void run() {
        logger.info("Stated dispatcher");
        while (callServiceWorking || !currentCalls.isEmpty()) {
            synchronized (availableEmployees) {
                if (!currentCalls.isEmpty()) {
                    Call call = (Call) currentCalls.poll();
                    dispatchCall(call);
                } else {
                    //TODO: Check what to do if the list is empty.
                }

                if (!busyEmployees.isEmpty()) {
                    Iterator<Employee> iterator = busyEmployees.iterator();

                    while (iterator.hasNext()) {
                        Employee e = iterator.next();
                        if (e.isAvailable()) {
                            busyEmployees.remove(e);
                            availableEmployees.add(e);
                        }
                    }
                }
            }
        }
        logger.info("Work day finished");
    }
}
