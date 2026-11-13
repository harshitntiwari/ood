package ElevatorSystem.states;

import ElevatorSystem.enums.Direction;
import ElevatorSystem.enums.RequestType;
import ElevatorSystem.model.Elevator;
import ElevatorSystem.model.Request;

public class MovingUpState implements ElevatorState {

    @Override
    public void move(Elevator elevator){
        if(elevator.getRequests().isEmpty()) {
            elevator.setState(new IdleState());
            return; // no point going further up
        }

        // check if there is a request for the current floor
        Request hallCallRequest = new Request(elevator.getFloor(), RequestType.CALL_UP);
        Request destinationRequest = new Request(elevator.getFloor(), RequestType.DESTINATION);
        if(elevator.getRequests().contains(hallCallRequest) || elevator.getRequests().contains(destinationRequest)) {
            elevator.removeRequest(hallCallRequest);
            elevator.removeRequest(destinationRequest);
            return; // we return in this tick, simulating a stop at the current floor
        }

        // check if there are any requests for floors further up than the current floor
        boolean upRequestsExist = false;
        for(Request request: elevator.getRequests()) {
            if(
                request.getFloor() > elevator.getFloor() && 
                (request.getRequestType() == RequestType.DESTINATION || request.getRequestType() == RequestType.CALL_UP)
            ) {
                upRequestsExist = true;
                break;
            }
        }
        if (!upRequestsExist) {
            if (!elevator.getRequests().isEmpty()) {
                elevator.setState(new MovingDownState()); // reverse the direction
            } else {
                elevator.setState(new IdleState());
            }
            return; // no point going further up
        }

        elevator.setFloor(elevator.getFloor() + 1);
    }

    @Override
    public Direction getDirection(){
        return Direction.UP;
    }
}