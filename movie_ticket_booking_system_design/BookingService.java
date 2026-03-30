import java.util.*;

class BookingService {
    private SeatLockService lockService;

    public BookingService(SeatLockService lockService) {
        this.lockService = lockService;
    }

    public Booking createBooking(User user, Show show, List<ShowSeat> seats) {

        boolean locked = lockService.lockSeats(seats, user);
        if (!locked) {
            System.out.println("Seats not available!");
            return null;
        }

        System.out.println("Seats locked for user: " + user.name);
        return new Booking(UUID.randomUUID().toString(), user, show, seats);
    }

    public boolean confirmBooking(Booking booking, IPaymentStrategy strategy) {

        double total = booking.getTotalAmount();

        Payment payment = new Payment(total, strategy);
        boolean success = payment.process();

        if (!success) {
            lockService.unlockSeats(booking.seats);
            booking.status = BookingStatus.CANCELLED;
            return false;
        }

        lockService.confirmSeats(booking.seats);
        booking.status = BookingStatus.CONFIRMED;

        System.out.println("Booking confirmed!");
        return true;
    }
}