public class Main {
    public static void main(String[] args) {
        System.out.println("=== Transport Booking ===");
        TripRequest req = new TripRequest("23BCS1010", new GeoPoint(12.97, 77.59), new GeoPoint(12.93, 77.62));

        DistanceCalculator dist = new DistanceCalculator();
        DriverAllocator alloc = new DriverAllocator();
        PaymentGateway pay = new PaymentGateway();

        TransportBookingService svc = new TransportBookingService(dist, alloc, pay);
        svc.book(req);
    }
}