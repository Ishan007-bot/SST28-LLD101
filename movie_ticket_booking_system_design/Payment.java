class Payment {
    double amount;
    IPaymentStrategy strategy;

    public Payment(double amount, IPaymentStrategy strategy) {
        this.amount = amount;
        this.strategy = strategy;
    }

    public boolean process() {
        return strategy.pay(amount);
    }
}