import java.util.*;

class Show {
    String showId;
    Movie movie;
    List<ShowSeat> showSeats;
    long startTime;
    long duration; // in milliseconds
    String screenId;
    IPricingStrategy pricingStrategy;

    public Show(String id, Movie movie, List<ShowSeat> seats,
            long startTime, long duration, String screenId,
            IPricingStrategy strategy) {

        this.showId = id;
        this.movie = movie;
        this.showSeats = seats;
        this.startTime = startTime;
        this.duration = duration;
        this.screenId = screenId;
        this.pricingStrategy = strategy;

        // calculate price
        for (ShowSeat ss : showSeats) {
            ss.price = pricingStrategy.calculatePrice(ss.seat, this);
        }
    }
}