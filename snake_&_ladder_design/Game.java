import java.util.Queue;

public class Game {
    private Board board;
    private Queue<Player> players;
    private Dice dice;
    private MakeMoveStrategy moveStrategy;

    public Game(Board board, Queue<Player> players, Dice dice, MakeMoveStrategy moveStrategy) {
        this.board = board;
        this.players = players;
        this.dice = dice;
        this.moveStrategy = moveStrategy;
    }

    public void startGame() {
        boolean gameWon = false;

        while (!gameWon && !players.isEmpty()) {
            Player currentPlayer = players.poll();

            gameWon = moveStrategy.executeTurn(currentPlayer, board, dice);

            if (gameWon) {
                System.out.println(currentPlayer.getName() + " wins the game!");
            } else {
                players.offer(currentPlayer);
            }
        }
    }
}