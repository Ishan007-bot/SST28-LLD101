import java.util.*;

class CacheNode {
    private Map<IKey, CacheEntry> storage = new HashMap<>();
    private IEvictionPolicy evictionPolicy;
    private int capacity;

    public CacheNode(int capacity) {
        this.capacity = capacity;
        this.evictionPolicy = new LRUEviction();
    }

    public IValue get(IKey key) {
        CacheEntry entry = storage.get(key);

        if (entry == null || entry.isExpired()) {
            storage.remove(key);
            return null;
        }

        evictionPolicy.onGet(key);
        entry.lastAccessTime = System.currentTimeMillis();
        return entry.value;
    }

    public void put(IKey key, IValue value, long ttl) {
        if (storage.size() >= capacity) {
            IKey evictKey = evictionPolicy.evict();
            storage.remove(evictKey);
        }

        CacheEntry entry = new CacheEntry(key, value, ttl);
        storage.put(key, entry);
        evictionPolicy.onPut(key);
    }

    public Map<IKey, CacheEntry> getAllEntries() {
        return storage;
    }

    public void clear() {
        storage.clear();
    }
}