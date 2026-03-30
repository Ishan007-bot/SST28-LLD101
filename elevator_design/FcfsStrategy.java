import java.util.*;

class FcfsStrategy implements IElevatorStrategy {

    // Assign nearest elevator
    @Override
    public Elevator selectElevator(Request request, List<Elevator> elevators) {

        Elevator best = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            int distance = Math.abs(e.getCurrentFloor() - request.floor);

            if (distance < minDistance) {
                minDistance = distance;
                best = e;
            }
        }
        return best;
    }

    // Scheduling logic
    @Override
    public void scheduleRequest(Elevator e, Request r) {

        int floor = r.floor;

        if (floor > e.getCurrentFloor()) {
            e.getUpStops().add(floor);
        } else if (floor < e.getCurrentFloor()) {
            e.getDownStops().add(floor);
        }
        // if same floor → ignore
    }
}