public class TripleRoomPricing implements RoomPricingPolicy{
     public Money basePrice() { 
        return new Money(12000.0); 
    }
}