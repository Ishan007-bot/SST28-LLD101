public class DoubleRoomPricing implements RoomPricingPolicy{
    public Money basePrice() { 
        return new Money(15000.0); 
    }
}