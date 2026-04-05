public class RemoteResource implements IRateLimiter {
    public void getResponse(double req) {
        System.out.println("Request " + req + " served successfully");
    }
}