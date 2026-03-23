public class Main {
    public static void main(String[] args) {
        Pen myGelPen = PenFactory.createPen(PenType.GEL, "Blue", MechanismType.CLICK);
        Pen myFountainPen = PenFactory.createPen(PenType.INK, "Black", MechanismType.CAP);

        try {
            myGelPen.write();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            myGelPen.start();
            myGelPen.write();
            myGelPen.close();

            System.out.println("-------------------");

            myFountainPen.start();
            myFountainPen.write();
            myFountainPen.refill("Red");
            myFountainPen.write();
            myFountainPen.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}