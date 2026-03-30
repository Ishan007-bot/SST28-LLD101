public class CreditCardPayment implements IPaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Payment done via Credit Card: " + amount);
        return true;
    }
}