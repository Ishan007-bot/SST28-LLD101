public class FixedWindowRule implements IRuleStrategy {
    private final int maxRequests;
    private final long windowSize;

    private int counter = 0;
    private long windowStart;

    public FixedWindowRule(int maxRequests, int windowSeconds) {
        this.maxRequests = maxRequests;
        this.windowSize = windowSeconds * 1000L;
        this.windowStart = System.currentTimeMillis();
    }

    @Override
    public boolean allowRequest() {
        long now = System.currentTimeMillis();

        // reset window
        if (now - windowStart > windowSize) {
            windowStart = now;
            counter = 0;
        }

        if (counter < maxRequests) {
            counter++;
            return true;
        }

        return false;
    }
}