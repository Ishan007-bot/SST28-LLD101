import java.util.*;

interface IElevatorStrategy {
    Elevator selectElevator(Request request, List<Elevator> elevators);

    void scheduleRequest(Elevator elevator, Request request);
}