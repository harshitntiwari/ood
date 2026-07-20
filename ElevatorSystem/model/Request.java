package ElevatorSystem.model;

import ElevatorSystem.enums.RequestType;
import java.util.Objects;

public class Request {
    private int floor;
    private RequestType type;

    public Request(int floor, RequestType type) {
        this.floor = floor;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request r = (Request) o;
        return floor == r.floor && type == r.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, type);
    }

    // ----- getters for private fields ----

    public int getFloor() {
        return floor;
    }

    public RequestType getRequestType() {
        return type;
    }

}