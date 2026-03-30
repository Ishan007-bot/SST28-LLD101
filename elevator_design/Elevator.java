import java.util.*;

class Elevator implements Observer {

    private int id;
    private int currentFloor = 0;
    private ElevatorState state = ElevatorState.IDLE;

    private TreeSet<Integer> upStops = new TreeSet<>();
    private TreeSet<Integer> downStops = new TreeSet<>(Collections.reverseOrder());

    private Door door = new Door();
    private WeightSensor weightSensor = new WeightSensor();
    private Alarm alarm = new Alarm();

    public Elevator(int id) {
        this.id = id;

        weightSensor.addObserver(this); // for overweight handling
        weightSensor.addObserver(alarm); // alarmlistens
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public TreeSet<Integer> getUpStops() {
        return upStops;
    }

    public TreeSet<Integer> getDownStops() {
        return downStops;
    }

    public void setWeight(double w) {
        weightSensor.setWeight(w);
    }

    public void pressAlarm() {
        alarm.update("ALARM");
    }

    public void move() {

        state = ElevatorState.MOVING;

        while (!upStops.isEmpty() || !downStops.isEmpty()) {

            while (!upStops.isEmpty()) {
                int next = upStops.pollFirst();
                System.out.println("Elevator " + id + " going UP to " + next);
                currentFloor = next;
            }

            while (!downStops.isEmpty()) {
                int next = downStops.pollFirst();
                System.out.println("Elevator " + id + " going DOWN to " + next);
                currentFloor = next;
            }
        }

        state = ElevatorState.IDLE;
    }

    @Override
    public void update(String event) {

        if (event.equals("OVERWEIGHT")) {
            System.out.println("Elevator " + id + " OVERWEIGHT! Door stays open.");
            door.open();
            System.out.println("Playing warning sound...");
        }
    }
}