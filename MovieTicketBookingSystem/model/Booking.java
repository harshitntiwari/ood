package MovieTicketBookingSystem.model;

import java.util.List;
import java.util.UUID;

public class Booking {
    private UUID id;
    private Show show;
    private List<Seat> seats;
    private String userId;
    private Payment payment;

    public Booking(UUID id, Show show, List<Seat> seats, String userId) {
        this.id = id;
        this.show = show;
        this.seats = seats;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public String getUserId() {
        return userId;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Show getShow() {
        return show;
    }
}