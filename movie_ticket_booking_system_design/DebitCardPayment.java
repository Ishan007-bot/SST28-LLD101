public class DebitCardPayment implements IPaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Payment done via Debit Card: " + amount);
        return true;
    }
}