class CacheEntry {
    IKey key;
    IValue value;
    long expiryTime;
    long lastAccessTime;

    public CacheEntry(IKey key, IValue value, long ttlMillis) {
        this.key = key;
        this.value = value;
        this.expiryTime = System.currentTimeMillis() + ttlMillis;
        this.lastAccessTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}