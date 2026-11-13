package ElevatorSystem;

import ElevatorSystem.enums.RequestType;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Main {
    public static void main(String[] args) throws InterruptedException {
        ElevatorController eleController = new ElevatorController(2, 10);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        scheduler.scheduleAtFixedRate(eleController::move, 0, 500, TimeUnit.MILLISECONDS);

        eleController.requestElevator(5, RequestType.CALL_UP);
        Thread.sleep(1000);
        eleController.requestElevator(2, RequestType.CALL_DOWN);

        Thread.sleep(6000);
        scheduler.shutdown();
    }
}