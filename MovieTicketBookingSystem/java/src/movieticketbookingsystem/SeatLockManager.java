package movieticketbookingsystem;

import movieticketbookingsystem.enums.SeatStatus;
import movieticketbookingsystem.model.Seat;
import movieticketbookingsystem.model.Show;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SeatLockManager {
    private final Map<Show, Map<Seat, String>> seatLock = new ConcurrentHashMap<>(); // lock is per seat per show
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static final long LOCK_TIMEOUT_MS = 10 * 60 * 1000; // 10 mins

    public boolean lockSeats(Show show, List<Seat> seats, String userId) {
        synchronized (show) {
            // check availability of seats
            for(Seat seat: seats) {
                if(show.getSeatStatus(seat) != SeatStatus.AVAILABLE) {
                    System.out.println("Seat " + seat.getId() + " is not available.");
                    return false;
                }
            }

            // mark seats as locked in the show
            for(Seat seat: seats) {
                show.setSeatStatus(seat, SeatStatus.BLOCKED);
            }

            // acquire the lock on seats
            seatLock.computeIfAbsent(show, v -> new ConcurrentHashMap<>());
            for(Seat seat : seats) {
                seatLock.get(show).put(seat, userId);
            }

            // schedule a task to unlock seats after the timeout
            scheduler.schedule(() -> unlockSeats(show, seats, userId), LOCK_TIMEOUT_MS, TimeUnit.MILLISECONDS);
            return true;
        }
    }

    public void unlockSeats(Show show, List<Seat> seats, String userId) {
        synchronized (show) {
            Map<Seat, String> locks = seatLock.get(show);
            if(locks != null) {
                for(Seat seat: seats) {
                    // check if the lock is acquired by the user who's trying to release it
                    if(locks.containsKey(seat) && locks.get(seat).equals(userId)) {
                        locks.remove(seat);
                        if(show.getSeatStatus(seat) == SeatStatus.BLOCKED) {
                            show.setSeatStatus(seat, SeatStatus.AVAILABLE);
                        }
                    }
                }
                // if locks from all the seats in this show has been released, remove the show
                if(locks.isEmpty()) {
                    seatLock.remove(show);
                }
            }
        }
    }

    public boolean isLockedBy(Show show, Seat seat, String userId) {
        if (seatLock.get(show) == null || seatLock.get(show).get(seat) == null) return false;
        return seatLock.get(show).get(seat).equals(userId);
    }
}