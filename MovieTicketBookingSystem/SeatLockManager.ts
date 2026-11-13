import { Show } from "./model/Show.js";
import { Seat } from "./model/Seat.js";
import { SeatStatus } from "./enums/SeatStatus.js";

export class SeatLockManager {
  private readonly seatLock = new Map<Show, Map<Seat, string>>();

  private static readonly LOCK_TIMEOUT_MS = 10 * 60 * 1000; // 10 mins

  public lockSeats(show: Show, seats: Seat[], userId: string) {
    // check availability of seats
    for (const seat of seats) {
      if (show.getSeatStatus(seat) !== SeatStatus.AVAILABLE) {
        console.log(`Seat ${seat.id} is not available.`);
        return false;
      }
    }

    // mark seats as locked in the show
    for (const seat of seats) {
      show.setSeatStatus(seat, SeatStatus.BLOCKED);
    }

    // acquire the lock on seats
    if (!this.seatLock.get(show)) {
      this.seatLock.set(show, new Map<Seat, string>());
    }
    const showLocks = this.seatLock.get(show)!;
    for (const seat of seats) {
      showLocks.set(seat, userId);
    }

    const timer = setTimeout(() => {
      this.unlockSeats(show, seats, userId);
    }, SeatLockManager.LOCK_TIMEOUT_MS);

    // Store timer reference (unblocking Node process exit if needed)
    timer.unref?.();
    return true;
  }

  public unlockSeats(show: Show, seats: Seat[], userId: string) {
    const locks = this.seatLock.get(show);
    if (!locks) return;

    for (const seat of seats) {
      // check if the lock is acquired by the user who's trying to release it
      if (locks.get(seat) === userId) {
        locks.delete(seat);
        if (show.getSeatStatus(seat) === SeatStatus.BLOCKED) {
          show.setSeatStatus(seat, SeatStatus.AVAILABLE);
        }
      }
    }

    // if locks from all the seats in this show has been released, remove the show
    if (locks.size === 0) {
      this.seatLock.delete(show);
    }
  }

  public isLockedBy(show: Show, seat: Seat, userId: string): boolean {
    return this.seatLock.get(show)?.get(seat) === userId;
  }
}
