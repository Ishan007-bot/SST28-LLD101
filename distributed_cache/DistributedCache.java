import java.util.*;

class DistributedCache {
    private List<CacheNode> nodes = new ArrayList<>();
    private IDistributionPolicy distributionPolicy;
    private Database database;
    private RequestCollapser collapser;
    private IPrefetcher prefetcher;
    private long ttl = 5000;

    public DistributedCache(int nodeCount, int capacityPerNode, long windowMillis) {
        this.distributionPolicy = new ModDistribution();
        this.database = new Database();
        this.collapser = new RequestCollapser(windowMillis);
        this.prefetcher = new RangePrefetcher(2);

        for (int i = 0; i < nodeCount; i++) {
            nodes.add(new CacheNode(capacityPerNode));
        }
    }

    public IValue get(IKey key) {
        return get(key, true);
    }

    private IValue get(IKey key, boolean allowPrefetch) {
        int index = distributionPolicy.getNodeIndex(key, nodes.size());
        CacheNode node = nodes.get(index);

        IValue val = node.get(key);
        if (val != null)
            return val;

        if (!collapser.shouldAllow(key)) {
            System.out.println("Collapsed request for key: " + key.hashCode());
            return null;
        }

        val = database.fetch(key);
        node.put(key, val, ttl);

        if (allowPrefetch) {
            prefetcher.prefetch(key, this);
        }

        return val;
    }

    public IValue getInternal(IKey key) {
        return get(key, false);
    }

    public void addNode() {
        nodes.add(new CacheNode(3));
        redistribute();
    }

    public void removeNode() {
        if (nodes.size() == 0)
            return;
        nodes.remove(nodes.size() - 1);
        redistribute();
    }

    private void redistribute() {
        List<CacheEntry> allEntries = new ArrayList<>();

        for (CacheNode node : nodes) {
            allEntries.addAll(node.getAllEntries().values());
        }

        for (CacheNode node : nodes) {
            node.clear();
        }

        for (CacheEntry entry : allEntries) {
            int index = distributionPolicy.getNodeIndex(entry.key, nodes.size());
            nodes.get(index).put(entry.key, entry.value, ttl);
        }

        System.out.println("Redistribution done.");
    }
}