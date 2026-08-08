package MovieTicketBookingSystem.model;

import MovieTicketBookingSystem.enums.SeatType;

public class Seat {
    private final String id;
    private final SeatType type;

    public Seat(String id, SeatType type) {
        this.id = id;
        this.type = type;
    } 

    public String getId() {
        return id;
    }

    public SeatType getType() {
        return type;
    }
}