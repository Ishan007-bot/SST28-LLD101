public class UpiPayment implements IPaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Payment done via UPI: " + amount);
        return true;
    }
}