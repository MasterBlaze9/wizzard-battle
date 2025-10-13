import game.characters.PlayerOneCharacter;
import game.characters.PlayerTwoCharacter;
import ui.grid.Grid;

public class App {
    public static void main(String[] args) {

        Grid canvas = new Grid(128, 72);
        canvas.init();
       
        int quarterCellCol = canvas.getCols() / 4;
        int quarterCellRow = canvas.getRows() / 2;

        PlayerOneCharacter playerOneCharacter = new PlayerOneCharacter(quarterCellCol, quarterCellRow);
        PlayerTwoCharacter playerTwoCharacter  = new PlayerTwoCharacter(quarterCellCol * 3 ,quarterCellRow);

    }
}
