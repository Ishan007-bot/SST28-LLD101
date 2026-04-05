public class Main {
    public static void main(String[] args) throws InterruptedException {
        DistributedCache cache = new DistributedCache(2, 2, 2000);

        IKey key1 = new SimpleKey(1);
        IKey key2 = new SimpleKey(2);
        IKey key3 = new SimpleKey(3);

        System.out.println("---- CACHE MISS (DB HIT) ----");
        System.out.println(cache.get(key1)); // DB hit

        System.out.println("\n---- CACHE HIT ----");
        System.out.println(cache.get(key1)); // from cache

        System.out.println("\n---- REQUEST COLLAPSING ----");
        System.out.println(cache.get(key2)); // DB hit
        System.out.println(cache.get(key2)); // collapsed

        Thread.sleep(2500);

        System.out.println("\n---- AFTER WINDOW (NO COLLAPSE) ----");
        System.out.println(cache.get(key2)); // DB hit again

        System.out.println("\n---- PREFETCH TEST ----");
        cache.get(new SimpleKey(10)); // will prefetch nearby keys

        System.out.println("\n---- LRU EVICTION TEST ----");
        cache.get(key1);
        cache.get(key2);
        cache.get(key3); // should evict LRU

        System.out.println("\n---- TTL EXPIRY TEST ----");
        Thread.sleep(6000);
        System.out.println(cache.get(key1)); // expired → DB hit

        System.out.println("\n---- SCALING TEST ----");
        cache.addNode(); // add node
        cache.removeNode(); // remove node
    }
}