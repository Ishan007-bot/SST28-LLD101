import java.util.*;

class Dispatcher {

    private IElevatorStrategy strategy;

    public Dispatcher(IElevatorStrategy strategy) {
        this.strategy = strategy;
    }

    public Elevator assignElevator(Request request, List<Elevator> elevators) {

        Elevator e = strategy.selectElevator(request, elevators);

        strategy.scheduleRequest(e, request);

        System.out.println("Assigned Elevator: " + e.getId());
        return e;
    }
}