import java.util.HashMap;
import java.util.Map;

public class RoomPricingRegistry {

    private static final Map<Integer, RoomPricingPolicy> policies = new HashMap<>();

    static {
        policies.put(LegacyRoomTypes.SINGLE, new SingleRoomPricing());
        policies.put(LegacyRoomTypes.DOUBLE, new DoubleRoomPricing());
        policies.put(LegacyRoomTypes.TRIPLE, new TripleRoomPricing());
        policies.put(LegacyRoomTypes.DELUXE, new DoubleRoomPricing());
    }

    public static RoomPricingPolicy get(int roomType) {
        return policies.get(roomType);
    }
}