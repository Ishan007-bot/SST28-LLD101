import java.util.*;

public class Main {
    public static void main(String[] args) {

        // Create Elevators
        Elevator e1 = new Elevator(0);
        Elevator e2 = new Elevator(1);

        List<Elevator> elevators = Arrays.asList(e1, e2);

        // Create System
        ElevatorSystem system = new ElevatorSystem(elevators);

        System.out.println("\n=== External Request (Nearest Elevator) ===");

        system.requestElevator(3, Direction.UP); // nearest elevator should be assigned

        System.out.println("\n=== Internal FCFS (4 -> 1) ===");

        system.submitInternalRequest(0, 4);
        system.submitInternalRequest(0, 1);

        System.out.println("\n--- Movement ---");
        e1.move();

        System.out.println("\n=== Mixed Scenario (4 -> 5 -> 1) ===");

        // Reset elevators
        Elevator e3 = new Elevator(2);
        Elevator e4 = new Elevator(3);

        List<Elevator> elevators2 = Arrays.asList(e3, e4);
        ElevatorSystem system2 = new ElevatorSystem(elevators2);

        // Internal first
        system2.submitInternalRequest(0, 4);

        // External (nearest logic)
        system2.requestElevator(5, Direction.DOWN);

        // Internal again
        system2.submitInternalRequest(0, 1);

        System.out.println("\n--- Movement ---");
        e3.move();
        e4.move();

        System.out.println("\n=== Alarm ===");

        e3.pressAlarm();

        System.out.println("\n=== Overweight ===");

        e3.setWeight(750); // >700 triggers observer

        System.out.println("\n=== Another External (Load balancing check) ===");

        system2.requestElevator(9, Direction.UP);

        System.out.println("\n--- Final Movement ---");
        e3.move();
        e4.move();
    }
}