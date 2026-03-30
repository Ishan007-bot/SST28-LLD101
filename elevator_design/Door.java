class Door {
    private DoorState state = DoorState.CLOSED;

    public void open() {
        state = DoorState.OPEN;
        System.out.println("Door opened");
    }

    public void close() {
        state = DoorState.CLOSED;
        System.out.println("Door closed");
    }
}