import { PaymentType } from "./enums/PaymentType.js";
import { SeatType } from "./enums/SeatType.js";
import { MovieBookingService } from "./MovieBookingService.js";
import { Booking } from "./model/Booking.js";
import { Movie } from "./model/Movie.js";
import { Screen } from "./model/Screen.js";
import { Seat } from "./model/Seat.js";
import { Show } from "./model/Show.js";
import { Theater } from "./model/Theater.js";

function main(): void {
  const service = new MovieBookingService();

  // create movie
  const movie1 = new Movie("m1", "Inception", 120);

  // create screen and add seats to the screen
  const screen1 = new Screen("sc1");
  screen1.addSeat(new Seat("s1", SeatType.REGULAR));
  screen1.addSeat(new Seat("s2", SeatType.REGULAR));
  screen1.addSeat(new Seat("s3", SeatType.PREMIUM));
  screen1.addSeat(new Seat("s4", SeatType.PREMIUM));

  // create theater and add screen to the theater
  const theater1 = new Theater("t1", "PVR Orion");
  theater1.addScreen(screen1);

  // create and add a show
  const show = new Show(
    "sh1",
    movie1,
    theater1,
    screen1,
    new Date(Date.now() + 2 * 60 * 60 * 1000),
  );
  show.setSeatPricing(SeatType.REGULAR, 100);
  show.setSeatPricing(SeatType.PREMIUM, 250);
  service.addShow(show);

  // find available shows for a movie
  const movieName = "Inception";
  const availableShows = service.findShows(movieName);
  if (availableShows.length === 0) {
    console.log(`No shows found for ${movieName}`);
    return;
  }

  // select a show and find available seats
  const selectedShow = availableShows[0];
  const availableSeats = service.findAvailableSeats(selectedShow);
  if (availableSeats.length === 0) {
    console.log("No available seats found for this show");
    return;
  }

  // select seats and create booking
  const selectedSeats: Seat[] = [availableSeats[0], availableSeats[2]];

  const booking: Booking | null = service.createBooking(
    selectedShow,
    selectedSeats,
    "user1",
  );
  if (booking === null) {
    console.log("Failed to create booking. Please try again.");
    return;
  }

  // confirm booking by making payment
  service.confirmBooking(booking.id, PaymentType.UPI);
}

main();
