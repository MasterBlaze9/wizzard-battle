import game.characters.PlayerOneCharacter;
import game.characters.PlayerTwoCharacter;
import ui.grid.Grid;

public class App {
  
    public static void main(String[] args) {


        Grid canvas = new Grid(128, 72, 1000, 1000);
        canvas.init();

        int quarterCellCol = Grid.getCols() / 4;

       
        int gameAreaTop = canvas.getGameAreaTopRow();
        int rowsPerPlayer = canvas.getMaxRowsPerPlayer();
        int startRow = gameAreaTop + rowsPerPlayer / 2;

        PlayerOneCharacter playerOneCharacter = new PlayerOneCharacter(canvas, quarterCellCol, startRow);
        PlayerTwoCharacter playerTwoCharacter = new PlayerTwoCharacter(canvas, quarterCellCol * 3, startRow);

    }
}
