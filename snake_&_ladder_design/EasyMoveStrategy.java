public class EasyMoveStrategy implements MakeMoveStrategy {
    @Override
    public boolean executeTurn(Player player, Board board, Dice dice) {
        boolean keepPlaying = true;

        while (keepPlaying) {
            int roll = dice.roll();
            System.out.println(player.getName() + " rolled a " + roll);

            if (roll == 6) {
                System.out.println("Rolled a 6! Extra turn granted.");
                keepPlaying = true;
            } else {
                keepPlaying = false;
            }

            int nextPosition = player.getPosition() + roll;

            if (nextPosition > board.getSize()) {
                continue;
            }

            nextPosition = board.resolveSnakeAndLadder(nextPosition);
            player.setPosition(nextPosition);
            System.out.println(player.getName() + " moved to " + nextPosition);

            if (player.getPosition() == board.getSize()) {
                return true;
            }
        }
        return false;
    }
}