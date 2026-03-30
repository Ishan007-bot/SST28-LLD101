class Request {
    int floor;
    Direction direction;
    RequestType type;

    public Request(int floor, Direction direction, RequestType type) {
        this.floor = floor;
        this.direction = direction;
        this.type = type;
    }
}