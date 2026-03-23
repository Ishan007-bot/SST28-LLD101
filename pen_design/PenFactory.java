package pen;

public class PenFactory {
    public static Pen createPen(PenType type, String color, MechanismType mechanism) {
        WriteBehaviour writeBehaviour;
        RefillBehaviour refillBehaviour;
        OpenCloseBehaviour openCloseBehaviour;

        switch (type) {
            case BALLPOINT:
                writeBehaviour = new BallpointWrite();
                refillBehaviour = new TubeRefill();
                break;
            case GEL:
                writeBehaviour = new GelWrite();
                refillBehaviour = new TubeRefill();
                break;
            case INK:
                writeBehaviour = new InkWrite();
                refillBehaviour = new BottleRefill();
                break;
            default:
                throw new IllegalArgumentException("Unknown Pen Type");
        }

        switch (mechanism) {
            case CAP:
                openCloseBehaviour = new CapStrategy();
                break;
            case CLICK:
                openCloseBehaviour = new ClickStrategy();
                break;
            default:
                throw new IllegalArgumentException("Unknown Mechanism Type");
        }

        return new Pen(color, writeBehaviour, refillBehaviour, openCloseBehaviour);
    }
}