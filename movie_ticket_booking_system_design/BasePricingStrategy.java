class BasePricingStrategy implements IPricingStrategy {
    public double calculatePrice(Seat seat, Show show) {
        return seat.basePrice;
    }
}