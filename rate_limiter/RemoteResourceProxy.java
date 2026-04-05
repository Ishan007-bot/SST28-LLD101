public class RemoteResourceProxy implements IRateLimiter {
    private IRuleStrategy strategy;
    private RemoteResource resource;

    public RemoteResourceProxy(IRuleStrategy strategy) {
        this.strategy = strategy;
        this.resource = new RemoteResource();
    }

    @Override
    public void getResponse(double req) {
        if (strategy.allowRequest()) {
            System.out.println("Processing request: " + req);
            resource.getResponse(req);
        } else {
            System.out.println("Request " + req + " REJECTED -> sending back to queue");
            throw new RuntimeException("RATE_LIMIT_EXCEEDED");
        }
    }
}