import com.almundo.callcenter.Call;
import com.almundo.callcenter.Dispatcher;
import com.almundo.callcenter.Employee;
import com.almundo.callcenter.enums.Position;
import com.almundo.callcenter.enums.Status;
import org.apache.commons.collections4.list.TreeList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Effie on 6/4/2019.
 */
public class Main {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        TreeList<Employee> employeeList = new TreeList<>();

        employeeList.add(new Employee("NINE", Status.AVAILABLE, Position.SUPERVISOR));
        employeeList.add(new Employee("EIGHT", Status.AVAILABLE, Position.SUPERVISOR));
        employeeList.add(new Employee("SEVEN", Status.AVAILABLE, Position.SUPERVISOR));
        employeeList.add(new Employee("ONE", Status.AVAILABLE, Position.MANAGER));
        employeeList.add(new Employee("TWO", Status.AVAILABLE, Position.MANAGER));
        employeeList.add(new Employee("THREE", Status.AVAILABLE, Position.OPERATOR));
        employeeList.add(new Employee("FOUR", Status.AVAILABLE, Position.OPERATOR));
        employeeList.add(new Employee("FIVE", Status.AVAILABLE, Position.OPERATOR));
        employeeList.add(new Employee("SIX", Status.AVAILABLE, Position.OPERATOR));
        Dispatcher dispatcher = new Dispatcher(employeeList);

        executorService.execute(dispatcher);

        for(int i = 0; i<5 ; i++) {

            Call call = new Call(3);
            dispatcher.produceCall(call);
        }

        dispatcher.setCallServiceWorking(false);
        executorService.shutdown();
        dispatcher.setCallServiceWorking(false);
        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("FINISHED!!!!!");
    }
}
