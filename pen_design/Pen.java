public class Pen {
    private String color;
    private boolean isOpen;
    private WriteBehaviour writeBehaviour;
    private RefillBehaviour refillBehaviour;
    private OpenCloseBehaviour openCloseBehaviour;

    public Pen(String color, WriteBehaviour writeBehaviour, RefillBehaviour refillBehaviour,
            OpenCloseBehaviour openCloseBehaviour) {
        this.color = color;
        this.writeBehaviour = writeBehaviour;
        this.refillBehaviour = refillBehaviour;
        this.openCloseBehaviour = openCloseBehaviour;
        this.isOpen = false;
    }

    public void start() {
        openCloseBehaviour.open();
        this.isOpen = true;
    }

    public void close() {
        openCloseBehaviour.close();
        this.isOpen = false;
    }

    public void write() throws Exception {
        if (!isOpen) {
            throw new Exception("Cannot write! The pen is closed. Please start() the pen first.");
        }
        System.out.print("[" + color.toUpperCase() + "] ");
        writeBehaviour.write();
    }

    public void refill(String newColor) {
        refillBehaviour.refill();
        this.color = newColor;
        System.out.println("Pen has been refilled with " + newColor + " ink.");
    }
}