import java.util.Random;

public class Dice {
    private int maxValue;
    private Random random;

    public Dice(int maxValue) {
        this.maxValue = maxValue;
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(maxValue) + 1;
    }
}