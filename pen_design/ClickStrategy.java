public class ClickStrategy implements OpenCloseBehaviour {
    @Override
    public void open() {
        System.out.println("Clicking the top button to extend the nib.");
    }

    @Override
    public void close() {
        System.out.println("Clicking the top button to retract the nib.");
    }
}