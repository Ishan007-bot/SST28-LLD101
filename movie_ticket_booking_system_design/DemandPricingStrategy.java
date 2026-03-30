class DemandPricingStrategy implements IPricingStrategy {
    public double calculatePrice(Seat seat, Show show) {
        return seat.basePrice * 1.5;
    }
}