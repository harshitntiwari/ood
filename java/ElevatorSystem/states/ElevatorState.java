package  ElevatorSystem.states;

import ElevatorSystem.model.Elevator;
import ElevatorSystem.enums.Direction;

public interface ElevatorState {
    void move(Elevator elevator);
    Direction getDirection();
}