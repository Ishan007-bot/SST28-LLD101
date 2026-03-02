public interface PaymentGatewayInt {
    String charge(String studentId, double amount);
}