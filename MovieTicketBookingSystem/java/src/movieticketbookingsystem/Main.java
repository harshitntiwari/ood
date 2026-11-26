package movieticketbookingsystem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import movieticketbookingsystem.enums.PaymentType;
import movieticketbookingsystem.enums.SeatType;
import movieticketbookingsystem.model.Booking;
import movieticketbookingsystem.model.Movie;
import movieticketbookingsystem.model.Screen;
import movieticketbookingsystem.model.Seat;
import movieticketbookingsystem.model.Show;
import movieticketbookingsystem.model.Theater;

public class Main {
    public static void main(String[] args) {
        
        MovieBookingService service = new MovieBookingService();

        // create movie
        Movie movie1 = new Movie("m1", "Inception", 120);

        // create screen and add seats to the screen
        Screen screen1 = new Screen("sc1");
        screen1.addSeat(new Seat("s1", SeatType.REGULAR));
        screen1.addSeat(new Seat("s2", SeatType.REGULAR));
        screen1.addSeat(new Seat("s3", SeatType.PREMIUM));
        screen1.addSeat(new Seat("s4", SeatType.PREMIUM));

        // create theater and add screen to the theater
        Theater theater1 = new Theater("t1", "PVR Orion");
        theater1.addScreen(screen1);

        // create and add a show
        Show show = new Show("sh1", movie1, theater1, screen1, LocalDateTime.now().plusHours(2));
        show.setSeatPricing(SeatType.REGULAR, 100);
        show.setSeatPricing(SeatType.PREMIUM, 250);
        service.addShow(show);

        // find available shows for a movie
        String movieName = "Inception";
        List<Show> availableShows = service.findShows(movieName);
        if (availableShows.isEmpty()) {
            System.out.println("No shows found for " + movieName);
            return;
        }

        // select a show and find available seats
        Show selectedShow = availableShows.get(0);
        List<Seat> availableSeats = service.findAvailableSeats(selectedShow);
        if(availableSeats.isEmpty()) {
            System.out.println("No available seats found for this show");
            return;
        }

        // select seats and create booking
        List<Seat> selectedSeats = List.of(availableSeats.get(0), availableSeats.get(2));

        Optional<Booking> bookingOpt = service.createBooking(selectedShow, selectedSeats, "user1");
        if(!bookingOpt.isPresent()) {
            System.out.println("Failed to create booking. Pease try again.");
            return;
        }

        Booking booking = bookingOpt.get();

        // confirm booking by making payment
        service.confirmBooking(booking.getId(), PaymentType.UPI);
    }
}