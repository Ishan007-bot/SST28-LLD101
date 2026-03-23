public class CapStrategy implements OpenCloseBehaviour {
    @Override
    public void open() {
        System.out.println("Removing the cap.");
    }

    @Override
    public void close() {
        System.out.println("Putting the cap back on.");
    }
}