interface IPrefetcher {
    void prefetch(IKey key, DistributedCache cache);
}