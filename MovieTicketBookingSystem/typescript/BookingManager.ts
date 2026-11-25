import { randomUUID } from "crypto";
import { Booking } from "./model/Booking.js";
import { Show } from "./model/Show.js";
import { Seat } from "./model/Seat.js";
import { SeatLockManager } from "./SeatLockManager.js";
import { PaymentType } from "./enums/PaymentType.js";
import { PaymentFactory } from "./factory/PaymentFactory.js";
import { PaymentStatus } from "./enums/PaymentStatus.js";
import { SeatStatus } from "./enums/SeatStatus.js";

export class BookingManager {
  private readonly bookings = new Map<string, Booking>();
  private readonly seatLockManager = new SeatLockManager();

  public createBooking(
    show: Show,
    seats: Seat[],
    userId: string,
  ): Booking | null {
    // try to acquire lock over the seats
    if (!this.seatLockManager.lockSeats(show, seats, userId)) return null;

    // Create and return booking
    const bookingId = randomUUID();
    const booking = new Booking(bookingId, show, seats, userId);
    this.bookings.set(bookingId, booking);
    return booking;
  }

  public confirmBooking(bookingId: string, paymentType: PaymentType): void {
    const booking = this.bookings.get(bookingId);
    if (!booking) return;
    const show = booking.show;

    // check if the seat is still acquired by the same user
    for (const seat of booking.seats) {
      if (!this.seatLockManager.isLockedBy(show, seat, booking.userId)) {
        console.log(`Seat ${seat.id} is not available to book now.`);
        return;
      }
    }

    // calculate the total price of all the seats
    let totalAmount = 0;
    for (const seat of booking.seats) {
      totalAmount += show.getPriceForSeatType(seat.type)!;
    }

    // make payment
    const paymentStrategy = PaymentFactory.getPaymentStrategy(paymentType);
    const payment = paymentStrategy.pay(totalAmount);
    booking.setPayment(payment);

    if (payment.status !== PaymentStatus.SUCCESS) {
      console.log("Failed to confirm the booking.");
      return; // Seats remain locked; user can retry within the lock window
    }

    // mark the seats as booked
    for (const seat of booking.seats) {
      show.setSeatStatus(seat, SeatStatus.BOOKED);
    }

    // release the lock on the seats
    this.seatLockManager.unlockSeats(show, booking.seats, booking.userId);
  }
}
