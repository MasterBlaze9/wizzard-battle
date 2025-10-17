import game.characters.PlayerOneCharacter;
import game.characters.PlayerTwoCharacter;
import ui.grid.Grid;

public class App {

        @SuppressWarnings("unused")
        public static void main(String[] args) {

                // Create a 1080p canvas while preserving logical cols/rows
                Grid canvas = new Grid(128, 72, 1920, 1080);
                canvas.init();

                int quarterCellCol = Grid.getCols() / 4;

                int gameAreaTop = canvas.getGameAreaTopRow();
                int rowsPerPlayer = canvas.getMaxRowsPerPlayer();
                int startRow = gameAreaTop + rowsPerPlayer / 2;

                PlayerOneCharacter playerOneCharacter = new PlayerOneCharacter(canvas, quarterCellCol, startRow);
                PlayerTwoCharacter playerTwoCharacter = new PlayerTwoCharacter(canvas, quarterCellCol * 3, startRow);

        }
}
