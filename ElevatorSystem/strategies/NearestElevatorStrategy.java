package ElevatorSystem.strategies;

import ElevatorSystem.enums.Direction;
import ElevatorSystem.enums.RequestType;
import ElevatorSystem.model.Elevator;
import ElevatorSystem.model.Request;
import java.util.List;

public class NearestElevatorStrategy implements SelectionStrategy {

    @Override
    public Elevator selectBestElevator(List<Elevator> elevators, Request request) {
        Elevator bestElevator = null; Elevator nearestElevator = null;
        int minOptimalDis = Integer.MAX_VALUE; int minOverallDis = Integer.MAX_VALUE;

        for(Elevator elevator: elevators) {
            int dis = Math.abs(request.getFloor() - elevator.getFloor());
            if(dis < minOverallDis) {
                minOverallDis = dis;
                nearestElevator = elevator;
            }
            if(isOptimal(elevator, request) && dis < minOptimalDis) {
                minOptimalDis = dis;
                bestElevator = elevator;    
            }
        }

        // if bestElevator is null, fallback to the nearestElevator
        return (bestElevator != null ) ? bestElevator : nearestElevator;
    }

    private boolean isOptimal(Elevator elevator, Request request) {
        if(elevator.getDirection() == Direction.IDLE) return true;

        Direction reqDirection = (request.getRequestType() == RequestType.CALL_UP) ? Direction.UP : Direction.DOWN;

        if(elevator.getDirection() == reqDirection) { // only select an elevator if it is going in the same direction as the user wants to go
            if(reqDirection == Direction.UP && elevator.getFloor() <= request.getFloor()) return true;
            else if (reqDirection == Direction.DOWN && elevator.getFloor() >= request.getFloor()) return  true;
        }
        return false;
    }
}