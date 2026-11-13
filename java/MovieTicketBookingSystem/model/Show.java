package MovieTicketBookingSystem.model;

import MovieTicketBookingSystem.enums.SeatStatus;
import MovieTicketBookingSystem.enums.SeatType;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Show {
    private String id;
    private Movie movie;
    private Theater theater;
    private Screen screen;
    private LocalDateTime startTime;
    private Map<SeatType, Double> seatPricing;
    private Map<String, SeatStatus>  seatStatusMap = new ConcurrentHashMap<>();

    public Show(String id, Movie movie, Theater theater, Screen screen, LocalDateTime startTime) {
        this.id = id;
        this.movie = movie;
        this.theater = theater;
        this.screen = screen;
        this.startTime = startTime;
        this.seatPricing = new HashMap<>();

        // initially mark all seats for this show as available
        for(Seat seat:screen.getSeats()) {
            seatStatusMap.put(seat.getId(), SeatStatus.AVAILABLE);
        }
    }

    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public Double getPriceForSeatType(SeatType seatType) {
        return seatPricing.get(seatType);
    }

    public void setSeatPricing(SeatType seatType, double Price) {
        this.seatPricing.put(seatType, Price);
    }

    public SeatStatus getSeatStatus(Seat seat) {
        return seatStatusMap.get(seat.getId());
    }

    public void setSeatStatus(Seat seat, SeatStatus seatStatus) {
        seatStatusMap.put(seat.getId(), seatStatus);
    }
}