public class DriverAllocator implements DriverAllocatorInt {
    public String allocate(String studentId) {
        // fake deterministic driver
        return "DRV-17";
    }
}