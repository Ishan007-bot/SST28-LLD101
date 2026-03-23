import java.util.List;

public class Board {
    private int size;
    private List<Snake> snakes;
    private List<Ladder> ladders;

    public Board(int size, List<Snake> snakes, List<Ladder> ladders) {
        this.size = size;
        this.snakes = snakes;
        this.ladders = ladders;
    }

    public int getSize() {
        return size;
    }

    public int resolveSnakeAndLadder(int currentPosition) {
        for (Snake snake : snakes) {
            if (snake.getStart() == currentPosition) {
                return snake.getEnd();
            }
        }

        for (Ladder ladder : ladders) {
            if (ladder.getStart() == currentPosition) {
                return ladder.getEnd();
            }
        }

        return currentPosition;
    }
}