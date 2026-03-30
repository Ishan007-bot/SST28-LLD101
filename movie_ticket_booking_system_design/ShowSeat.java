class ShowSeat {
    Seat seat;
    double price;
    SeatStatus status = SeatStatus.AVAILABLE;
    User lockedBy;
    long lockTime;

    public ShowSeat(Seat seat) {
        this.seat = seat;
    }
}