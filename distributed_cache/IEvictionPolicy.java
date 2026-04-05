interface IEvictionPolicy {
    void onGet(IKey key);

    void onPut(IKey key);

    IKey evict();
}