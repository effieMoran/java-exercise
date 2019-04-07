import com.almundo.callcenter.Call;
import com.almundo.callcenter.Dispatcher;
import com.almundo.callcenter.Employee;
import com.almundo.callcenter.enums.Position;
import com.almundo.callcenter.enums.Status;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by User on 6/4/2019.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        PriorityQueue<Employee> employeeList = new PriorityQueue<>();
        employeeList.add(new Employee("NINE", Status.AVAILABLE, Position.SUPERVISOR));
        employeeList.add(new Employee("EIGHT", Status.AVAILABLE, Position.SUPERVISOR));
        employeeList.add(new Employee("SEVEN", Status.AVAILABLE, Position.SUPERVISOR));
        employeeList.add(new Employee("ONE", Status.AVAILABLE, Position.OPERATOR));
        employeeList.add(new Employee("TWO", Status.AVAILABLE, Position.OPERATOR));
        employeeList.add(new Employee("THREE", Status.AVAILABLE, Position.OPERATOR));
        employeeList.add(new Employee("FOUR", Status.AVAILABLE, Position.OPERATOR));
        employeeList.add(new Employee("FIVE", Status.AVAILABLE, Position.OPERATOR));
        employeeList.add(new Employee("SIX", Status.AVAILABLE, Position.OPERATOR));
        Dispatcher dispatcher = new Dispatcher(employeeList);

        for(int i = 0; i<10 ; i++) {

            Call call = new Call(20);//ThreadLocalRandom.current().nextInt(5, 10 + 1));
            dispatcher.dispatchCall(call);
        }

    }
}
