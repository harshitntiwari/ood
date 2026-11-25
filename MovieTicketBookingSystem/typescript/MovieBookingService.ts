import { PaymentType } from "./enums/PaymentType.js";
import { SeatStatus } from "./enums/SeatStatus.js";
import { Booking } from "./model/Booking.js";
import { Seat } from "./model/Seat.js";
import { Show } from "./model/Show.js";
import { BookingManager } from "./BookingManager.js";

export class MovieBookingService {
  private readonly shows = new Map<string, Show>();
  private readonly bookingManager = new BookingManager();

  addShow(show: Show): void {
    this.shows.set(show.id, show);
  }

  findShows(movieName: string): Show[] {
    const result: Show[] = [];
    for (const show of this.shows.values()) {
      if (show.movie.name.toLowerCase() === movieName.toLowerCase()) {
        result.push(show);
      }
    }
    return result;
  }

  findAvailableSeats(show: Show): Seat[] {
    const result: Seat[] = [];
    for (const seat of show.screen.seats) {
      if (show.getSeatStatus(seat) === SeatStatus.AVAILABLE) {
        result.push(seat);
      }
    }
    return result;
  }

  createBooking(show: Show, seats: Seat[], userId: string): Booking | null {
    return this.bookingManager.createBooking(show, seats, userId);
  }

  confirmBooking(bookingId: string, paymentType: PaymentType): void {
    this.bookingManager.confirmBooking(bookingId, paymentType);
  }
}
