import java.util.*;

class LRUEviction implements IEvictionPolicy {
    private LinkedHashMap<IKey, Boolean> map;

    public LRUEviction() {
        this.map = new LinkedHashMap<>(16, 0.75f, true);
    }

    @Override
    public void onGet(IKey key) {
        map.get(key);
    }

    @Override
    public void onPut(IKey key) {
        map.put(key, true);
    }

    @Override
    public IKey evict() {
        Iterator<IKey> it = map.keySet().iterator();
        IKey lru = it.next();
        it.remove();
        return lru;
    }
}