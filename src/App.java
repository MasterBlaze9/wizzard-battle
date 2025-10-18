import collisionManager.CollisionManager;
import game.characters.PlayerOneCharacter;
import game.characters.PlayerTwoCharacter;
import ui.grid.Grid;
import ui.screens.HomeScreen;
import keyboard.AppKeyboard;

public class App {

    private static HomeScreen home;

    public static void main(String[] args) {

        home = new HomeScreen();

        game.GameController.setStartAction(() -> startGame());
        AppKeyboard.addStartListener(home, () -> game.GameController.startGame());

    }

    private static void startGame() {
        // Ensure home screen is hidden
        if (home != null && home.isVisible()) {
            home.hide();
        }
        
        CollisionManager.clearAll();
        
        Grid canvas = new Grid(128, 72, 1920, 1080);
        canvas.init();

        int quarterCellCol = Grid.getCols() / 4;

        int gameAreaTop = canvas.getGameAreaTopRow();
        int rowsPerPlayer = canvas.getMaxRowsPerPlayer();
        int startRow = gameAreaTop + rowsPerPlayer / 2;

        PlayerOneCharacter playerOneCharacter = new PlayerOneCharacter(canvas, quarterCellCol, startRow);
        PlayerTwoCharacter playerTwoCharacter = new PlayerTwoCharacter(canvas, quarterCellCol * 3, startRow);
       
        CollisionManager.dumpState();
    }
}
