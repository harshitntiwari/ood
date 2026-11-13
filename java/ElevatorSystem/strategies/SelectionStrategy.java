package ElevatorSystem.strategies;

import ElevatorSystem.model.Elevator;
import ElevatorSystem.model.Request;
import java.util.List;

public interface SelectionStrategy {
    Elevator selectBestElevator(List<Elevator> elevators, Request request);
}