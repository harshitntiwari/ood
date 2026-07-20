package ElevatorSystem.model;

import ElevatorSystem.enums.Direction;
import ElevatorSystem.states.ElevatorState;
import ElevatorSystem.states.IdleState;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Elevator {
    private int id;
    private AtomicInteger floor;
    private ElevatorState state;
    private Set<Request> requests;

    public Elevator(int id) {
        this.id = id;
        this.floor = new AtomicInteger(0);
        this.state = new IdleState();
        this.requests = new HashSet<>();
    }

    public synchronized void addRequest(Request request) {
        requests.add(request);
    }

    public synchronized void removeRequest(Request request) {
        requests.remove(request);
    }

    public void move() {
        state.move(this);
    }

    public Direction getDirection() {
        return state.getDirection();
    }

    // ----- getters and setters for private fields ----

    public int getId() {
        return this.id;
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }

    public int getFloor() {
        return floor.get();
    }

    public void setFloor(int floor) {
        this.floor.set(floor);
    }

    public Set<Request> getRequests() {
        return requests;
    }
}