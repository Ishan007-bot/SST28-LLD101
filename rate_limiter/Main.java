import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Double> requests = Arrays.asList(1.1, 2.1, 1.2, 1.3, 2.2, 1.4, 1.5, 2.3);

        System.out.println("===== Sliding Window Test =====");
        processQueue(new LinkedList<>(requests), new SlidingWindowRule(3, 4));

        System.out.println("\n\n===== Fixed Window Test =====");
        processQueue(new LinkedList<>(requests), new FixedWindowRule(2, 4));
    }

    private static void processQueue(Queue<Double> queue, IRuleStrategy strategy) {
        RemoteResourceProxy proxy = new RemoteResourceProxy(strategy);
        int step = 1;

        while (!queue.isEmpty()) {
            System.out.println("\nStep " + step++);
            System.out.println("Queue: " + queue);

            double req = queue.poll();

            try {
                proxy.getResponse(req);
            } catch (Exception e) {
                System.out.println("Re-adding " + req + " to queue");
                queue.add(req);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nAll requests processed");
    }
}