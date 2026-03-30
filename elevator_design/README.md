# Elevator control system

A small Java simulation of a multi-elevator building: external and internal calls, dispatching, doors, weight limits, and alarm handling.

## Features

Decouples request routing, elevator scheduling, and safety behaviors across focused types.

- **Multi-elevator setup** — create multiple `Elevator` instances and route work via `ElevatorSystem` + `Dispatcher`.
- **External and internal requests** — external calls are dispatched; internal calls are scheduled directly into a chosen elevator.
- **Nearest elevator selection** — `FcfsStrategy.selectElevator` picks the elevator with minimum distance to the requested floor.
- **Up/down stop scheduling** — `scheduleRequest` stores target floors in `upStops` or `downStops`, and `move()` drains them.
- **Door and weight behavior** — overweight triggers `WeightSensor` events; the elevator keeps the door open and plays a warning.
- **Alarm handling** — `pressAlarm()` triggers the `Alarm` observer output (and prints emergency behavior).

## Design patterns

Uses classic Strategy and Observer patterns to keep routing and safety logic modular.

- **Strategy** — `IElevatorStrategy` defines `selectElevator(...)` and `scheduleRequest(...)`; `Dispatcher` delegates to it.
- **Observer** — `WeightSensor` (`Subject`) notifies observers on overweight; `Elevator` reacts by opening the door, while `Alarm` listens for alarm-signals.

## System concepts

Core domain objects that model requests, dispatch assignment, and elevator movement state.

- **Elevator system** — owns the elevators and exposes `requestElevator(...)` (external) and `submitInternalRequest(...)` (per car).
- **Dispatcher** — converts a `Request` into an assignment by calling `selectElevator(...)` and then `scheduleRequest(...)`.
- **Elevator scheduling model** — `upStops` and `downStops` hold target floors; `move()` prints movement while draining those sets.
- **Elevator state** — `ElevatorState` is updated to `MOVING` during `move()` and back to `IDLE` afterwards.

## Class diagram

![alt text](<Elevator System.png>)

## Usage

Shows how to create the system, dispatch requests, schedule internal stops, and advance the simulation.

- Build an `ElevatorSystem` with a `List<Elevator>`.
- Call `requestElevator(floor, Direction.UP|DOWN)` to assign the nearest elevator for an external request.
- Call `submitInternalRequest(elevatorIndex, floor)` to schedule a target stop inside a specific elevator.
- Call `move()` on each elevator to process scheduled `upStops` / `downStops` and print movement logs.

## Running

Compile and run the demo from this directory:

```bash
javac *.java
java Main
```

## Example output

Sample console output produced by running `Main`.

```
=== External Request (Nearest Elevator) ===
Assigned Elevator: 0

=== Internal FCFS (4 -> 1) ===

--- Movement ---
Elevator 0 going UP to 1
Elevator 0 going UP to 3
Elevator 0 going UP to 4

=== Mixed Scenario (4 -> 5 -> 1) ===
Assigned Elevator: 2

--- Movement ---
Elevator 2 going UP to 1
Elevator 2 going UP to 4
Elevator 2 going UP to 5

=== Alarm ===
Alarm triggered! Playing emergency sound.

=== Overweight ===
Elevator 2 OVERWEIGHT! Door stays open.
Door opened
Playing warning sound...

=== Another External (Load balancing check) ===
Assigned Elevator: 2

--- Final Movement ---
Elevator 2 going UP to 9
```

## Key design principles

Keeps the design extensible by isolating policy and reactions behind interfaces.

- **Separation of concerns** — the system API routes requests, the dispatcher delegates policy, and the elevator owns movement/scheduling behavior.
- **Open for extension** — add new dispatch/scheduling rules by implementing `IElevatorStrategy`.
- **Loose coupling via interfaces** — `IElevatorStrategy` and `Subject` / `Observer` reduce direct dependencies between components.
- **Explicit domain model** — `Request`, `Direction`, and `ElevatorState` make intent and mode visible in the types.