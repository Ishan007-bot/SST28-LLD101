import java.util.List;

public interface DiscountPolicy {
    double discount(String customerType, double subtotal, List<OrderLine> lines);
}