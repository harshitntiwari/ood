package movieticketbookingsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    private String id;
    private List<Seat> seats;

    public Screen(String id) {
        this.id = id;
        seats = new ArrayList<>();
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }
}