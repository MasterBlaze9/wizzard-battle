import game.characters.PlayerOneCharacter;
import ui.grid.Grid;

public class App {
    public static void main(String[] args) {

        Grid canvas = new Grid(128, 65);
        canvas.init();
       
        int quarterCellCol = canvas.getCols() / 4;
        int quarterCellRow = canvas.getRows() / 2;
        PlayerOneCharacter playerOneCharacter = new PlayerOneCharacter(quarterCellCol, quarterCellRow);

        System.out.println(playerOneCharacter.getPosition().getCol());
        System.out.println(playerOneCharacter.getPosition().getRow());
    }
}
