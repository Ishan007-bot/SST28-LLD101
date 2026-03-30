import java.util.*;

class SeatLockService {
    private final Map<ShowSeat, Long> locks = new HashMap<>();
    private final long LOCK_TIME = 5000; // 5 sec

    public synchronized boolean lockSeats(List<ShowSeat> seats, User user) {
        long now = System.currentTimeMillis();

        for (ShowSeat s : seats) {

            // If already booked → reject
            if (s.status == SeatStatus.BOOKED)
                return false;

            // If locked → check expiry
            if (s.status == SeatStatus.LOCKED) {
                long lockTime = locks.getOrDefault(s, 0L);

                if (now - lockTime >= LOCK_TIME) {
                    // EXPIRE LOCK
                    s.status = SeatStatus.AVAILABLE;
                    s.lockedBy = null;
                    locks.remove(s);
                } else {
                    return false;
                }
            }
        }

        // Lock all seats
        for (ShowSeat s : seats) {
            s.status = SeatStatus.LOCKED;
            s.lockedBy = user;
            s.lockTime = now;
            locks.put(s, now);
        }

        return true;
    }

    public synchronized void unlockSeats(List<ShowSeat> seats) {
        for (ShowSeat s : seats) {
            s.status = SeatStatus.AVAILABLE;
            s.lockedBy = null;
            locks.remove(s);
        }
    }

    public synchronized void confirmSeats(List<ShowSeat> seats) {
        for (ShowSeat s : seats) {
            s.status = SeatStatus.BOOKED;
            s.lockedBy = null;
            locks.remove(s);
        }
    }
}