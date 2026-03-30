import java.util.*;

class ElevatorSystem {

    private List<Elevator> elevators;
    private Dispatcher dispatcher;

    public ElevatorSystem(List<Elevator> elevators) {
        this.elevators = elevators;
        this.dispatcher = new Dispatcher(new FcfsStrategy());
    }

    // External → via dispatcher
    public void requestElevator(int floor, Direction dir) {
        dispatcher.assignElevator(
                new Request(floor, dir, RequestType.EXTERNAL),
                elevators);
    }

    // Internal → directly to elevator
    public void submitInternalRequest(int elevatorId, int floor) {

        Elevator e = elevators.get(elevatorId);

        new FcfsStrategy().scheduleRequest(
                e,
                new Request(floor, Direction.IDLE, RequestType.INTERNAL));
    }
}