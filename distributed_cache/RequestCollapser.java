import java.util.concurrent.*;
import java.util.*;

class RequestCollapser {
    private Map<IKey, Long> requestTimeMap = new ConcurrentHashMap<>();
    private long windowMillis;

    public RequestCollapser(long windowMillis) {
        this.windowMillis = windowMillis;
    }

    public boolean shouldAllow(IKey key) {
        long now = System.currentTimeMillis();

        if (!requestTimeMap.containsKey(key)) {
            requestTimeMap.put(key, now);
            return true;
        }

        long last = requestTimeMap.get(key);

        if (now - last > windowMillis) {
            requestTimeMap.put(key, now);
            return true;
        }

        return false;
    }
}