class Seat {
    String seatId;
    SeatType type;
    double basePrice;

    public Seat(String id, SeatType type, double price) {
        this.seatId = id;
        this.type = type;
        this.basePrice = price;
    }
}