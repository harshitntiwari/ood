package ElevatorSystem.states;

import ElevatorSystem.enums.Direction;
import ElevatorSystem.enums.RequestType;
import ElevatorSystem.model.Elevator;
import ElevatorSystem.model.Request;

public class IdleState implements ElevatorState {

    @Override
    public void move(Elevator elevator){
        if(elevator.getRequests().isEmpty()) return;

        // deciding which direction to set the elevator's state into, by finding the nearest request

        Request nearestReq = null;
        int minDis = Integer.MAX_VALUE;
        for(Request request: elevator.getRequests()) {
            int dis = Math.abs(request.getFloor() - elevator.getFloor());
            if(dis < minDis || (dis == minDis && request.getFloor() < nearestReq.getFloor())) {
                minDis = dis;
                nearestReq = request;
            }
        }

        if(
            nearestReq.getFloor() < elevator.getFloor() || 
            (nearestReq.getFloor() == elevator.getFloor() && nearestReq.getRequestType() == RequestType.CALL_DOWN)
        ) {
            elevator.setState(new MovingDownState());
        }
        else {
            elevator.setState(new MovingUpState());
        }
    }

    @Override
    public Direction getDirection(){
        return Direction.IDLE;
    }
}