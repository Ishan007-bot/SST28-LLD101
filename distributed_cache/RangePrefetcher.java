class RangePrefetcher implements IPrefetcher {
    private int range;

    public RangePrefetcher(int range) {
        this.range = range;
    }

    @Override
    public void prefetch(IKey key, DistributedCache cache) {
        int base = key.hashCode();

        for (int i = base - range; i <= base + range; i++) {
            IKey k = new SimpleKey(i);
            cache.getInternal(k);
        }
    }
}