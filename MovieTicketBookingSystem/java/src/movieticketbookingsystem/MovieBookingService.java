package movieticketbookingsystem;

import movieticketbookingsystem.enums.PaymentType;
import movieticketbookingsystem.enums.SeatStatus;
import movieticketbookingsystem.model.Booking;
import movieticketbookingsystem.model.Seat;
import movieticketbookingsystem.model.Show;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MovieBookingService {
    private final Map<String, Show> shows;
    private final BookingManager bookingManager;

    public MovieBookingService() {
        shows = new ConcurrentHashMap<>();
        this.bookingManager = new BookingManager();
    }

    public void addShow(Show show) {
        shows.put(show.getId(), show);
    }

    public List<Show> findShows(String movieName) {
        List<Show> result = shows.values().stream()
        .filter(show -> show.getMovie().getName().equalsIgnoreCase(movieName))
        .toList();

        return result;
    }

    public List<Seat> findAvailableSeats(Show show) {
        List<Seat> result = show.getScreen().getSeats().stream()
        .filter(seat -> show.getSeatStatus(seat) == SeatStatus.AVAILABLE)
        .toList();

        return result;
    }

    public Optional<Booking> createBooking(Show show, List<Seat> seats, String userId) {
        return bookingManager.createBooking(show, seats, userId);
    }

    public void confirmBooking(UUID bookingId, PaymentType paymentType) {
        bookingManager.confirmBooking(bookingId, paymentType);
    }

}