package game;

import collisionManager.CollisionManager;
import ui.grid.Grid;
import ui.healthBar.HealthBar;
import ui.screens.GameOverScreen;
import keyboard.AppKeyboard;


public class GameStateManager {


    public static void triggerGameOver(PlayerEnum winner) {

        AppKeyboard.cleanupAll();
        HealthBar.cleanupAll();
        CollisionManager.clearAll();
        Grid.clearAll();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        new GameOverScreen(winner);
    }
}
