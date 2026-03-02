public class PaymentGateway implements PaymentGatewayInt {
    public String charge(String studentId, double amount) {
        // fake deterministic txn
        return "TXN-9001";
    }
}