import java.util.*;

class Booking {
    String bookingId;
    User user;
    Show show;
    List<ShowSeat> seats;
    BookingStatus status;

    public Booking(String id, User user, Show show, List<ShowSeat> seats) {
        this.bookingId = id;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.status = BookingStatus.PENDING;
    }

    public double getTotalAmount() {
        return seats.stream().mapToDouble(s -> s.price).sum();
    }
}