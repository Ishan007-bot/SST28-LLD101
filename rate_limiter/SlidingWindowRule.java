import java.util.*;

public class SlidingWindowRule implements IRuleStrategy {
    private Queue<Long> timestamps = new LinkedList<>();
    private final long windowSize; // in ms
    private final int maxRequests;

    public SlidingWindowRule(int maxRequests, int windowSeconds) {
        this.maxRequests = maxRequests;
        this.windowSize = windowSeconds * 1000L;
    }

    @Override
    public boolean allowRequest() {
        long now = System.currentTimeMillis();

        while (!timestamps.isEmpty() && now - timestamps.peek() > windowSize) {
            timestamps.poll();
        }

        if (timestamps.size() < maxRequests) {
            timestamps.add(now);
            return true;
        }

        return false;
    }
}