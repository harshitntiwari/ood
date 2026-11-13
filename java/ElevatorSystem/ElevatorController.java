package ElevatorSystem;

import ElevatorSystem.enums.RequestType;
import ElevatorSystem.model.Elevator;
import ElevatorSystem.model.Request;
import ElevatorSystem.strategies.NearestElevatorStrategy;
import ElevatorSystem.strategies.SelectionStrategy;
import java.util.ArrayList;
import java.util.List;

class ElevatorController {
    private final List<Elevator> elevators;
    private final int maxFloors;
    private final SelectionStrategy strategy;

    public ElevatorController(int numElevators, int maxFloors) {
        elevators = new ArrayList<>();
        for(int i = 0; i < numElevators; i++) {
            elevators.add(new Elevator(i));
        }
        this.maxFloors = maxFloors;
        this.strategy = new NearestElevatorStrategy();
    }

    public void  requestElevator(int floor, RequestType type) {
        if(floor < 0 || floor > maxFloors) {
            throw new IllegalArgumentException("Invalid floor");
        }

        if(type == RequestType.DESTINATION) {
            throw new IllegalArgumentException("Invalid request type");
        }
        
        Request request = new Request(floor, type);
        Elevator bestElevator = strategy.selectBestElevator(elevators, request);
        bestElevator.addRequest(request);
    }

    public void requestDestination(int elevatorId, int floor) {
        if(floor < 0 || floor > maxFloors) {
            throw new IllegalArgumentException("Invalid floor");
        }

        Elevator elevator = elevators.stream().filter(e -> e.getId() == elevatorId).findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid elevator id"));
        Request request = new Request(floor, RequestType.DESTINATION);
        elevator.addRequest(request);
    }

    public void move() {
        for (Elevator e: elevators) e.move();
    }
}