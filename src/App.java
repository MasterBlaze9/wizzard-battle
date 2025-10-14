import game.characters.PlayerOneCharacter;
import game.characters.PlayerTwoCharacter;
import ui.grid.Grid;

public class App {
    public static void main(String[] args) {

        // Create a 1080p canvas while preserving logical cols/rows
        Grid canvas = new Grid(128, 72, 1920, 1080);
        canvas.init();

        int quarterCellCol = canvas.getCols() / 4;
        int quarterCellRow = canvas.getRows() / 2;

        PlayerOneCharacter playerOneCharacter = new PlayerOneCharacter(quarterCellCol, quarterCellRow);
        PlayerTwoCharacter playerTwoCharacter = new PlayerTwoCharacter(quarterCellCol * 3, quarterCellRow);

        // keep references in use so they are not optimized away / flagged as unused
        System.out.println("Players created: " + playerOneCharacter.getPosition().getCol() + ","
                + playerOneCharacter.getPosition().getRow());
        System.out.println("Player two at: " + playerTwoCharacter.getPosition().getCol() + ","
                + playerTwoCharacter.getPosition().getRow());

    }
}
