import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        // ------------------ SEATS ------------------
        Seat s1 = new Seat("S1", SeatType.GOLD, 200);
        Seat s2 = new Seat("S2", SeatType.GOLD, 200);

        ShowSeat ss1 = new ShowSeat(s1);
        ShowSeat ss2 = new ShowSeat(s2);

        List<ShowSeat> seats = new ArrayList<>(Arrays.asList(ss1, ss2));

        // ------------------ MOVIE ------------------
        Movie movie = new Movie("M1", "Avengers");

        // ------------------ PRICING ------------------
        IPricingStrategy pricing = new DemandPricingStrategy();

        // ------------------ SHOW ------------------
        long duration = 2 * 60 * 60 * 1000; // 2 hours

        Show show = new Show(
                "SH1",
                movie,
                seats,
                System.currentTimeMillis(),
                duration,
                "SCREEN_1",
                pricing);

        // ------------------ SERVICES ------------------
        SeatLockService lockService = new SeatLockService();
        BookingService bookingService = new BookingService(lockService);

        // ------------------ USERS ------------------
        Customer user1 = new Customer("U1", "Adam");
        Customer user2 = new Customer("U2", "Aryan");

        // ------------------ BOOKING TEST ------------------

        System.out.println("\n=== USER1 BOOKING ===");
        Booking b1 = bookingService.createBooking(user1, show, Arrays.asList(ss1));

        System.out.println("\n=== USER2 TRY SAME SEAT (SHOULD FAIL) ===");
        Booking b2 = bookingService.createBooking(user2, show, Arrays.asList(ss1));

        // ------------------ PAYMENT ------------------
        if (b1 != null) {
            System.out.println("\n=== USER1 PAYMENT ===");
            bookingService.confirmBooking(b1, new UpiPayment());
        }

        System.out.println("\n=== USER2 TRY AFTER BOOKED (SHOULD FAIL) ===");
        Booking b3 = bookingService.createBooking(user2, show, Arrays.asList(ss1));

        // ------------------ LOCK EXPIRY TEST ------------------
        System.out.println("\n=== LOCK EXPIRY TEST ===");

        ShowSeat ss3 = new ShowSeat(new Seat("S3", SeatType.GOLD, 200));
        show.showSeats.add(ss3);

        Booking tempBooking = bookingService.createBooking(user2, show, Arrays.asList(ss3));

        System.out.println("Waiting for lock to expire...");
        Thread.sleep(6000); // wait > 5 sec

        System.out.println("Trying again after expiry (SHOULD SUCCEED)");
        Booking afterExpiry = bookingService.createBooking(user1, show, Arrays.asList(ss3));

        // ------------------ ADMIN TEST ------------------
        System.out.println("\n=== ADMIN SHOW TEST ===");

        AdminService adminService = new AdminService();
        List<Show> shows = new ArrayList<>();

        // Add first show
        adminService.addShow(shows, show);

        // Overlapping show (same screen, overlapping time → FAIL)
        Show overlappingShow = new Show(
                "SH2",
                movie,
                seats,
                show.startTime + 30 * 60 * 1000, // +30 mins
                duration,
                "SCREEN_1",
                pricing);

        adminService.addShow(shows, overlappingShow);

        // Non-overlapping show (different time → SUCCESS)
        Show nonOverlapShow = new Show(
                "SH3",
                movie,
                seats,
                show.startTime + 3 * 60 * 60 * 1000, // +3 hours
                duration,
                "SCREEN_1",
                pricing);

        adminService.addShow(shows, nonOverlapShow);

        // Same time but DIFFERENT screen → SUCCESS
        Show differentScreenShow = new Show(
                "SH4",
                movie,
                seats,
                show.startTime,
                duration,
                "SCREEN_2",
                pricing);

        adminService.addShow(shows, differentScreenShow);
    }
}