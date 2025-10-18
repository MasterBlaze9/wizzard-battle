package ui.screens;

import game.PlayerEnum;
import ui.grid.Grid;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class GameOverScreen implements KeyboardHandler {

    private Picture background;
    private Picture winnerFace;
    private Text winnerText;
    private Text hintText;
    private boolean triggered = false;

    public GameOverScreen(PlayerEnum winner) {

        background = new Picture(0, 0, "resources/backgroun2.png");
        background.draw();

        String facePath = Grid.getPlayerFacePath(winner);
        if (facePath == null || facePath.isEmpty()) {

            facePath = winner == PlayerEnum.Player_1 ? "resources/Faces/Carolina.png"
                    : "resources/Faces/Pascoa.png";
        }

        int screenW = background.getWidth();
        int screenH = background.getHeight();

        winnerFace = new Picture((int) screenW / 2 - 50, (int) screenH / 2.75, facePath);
        winnerFace.draw();

        winnerFace.grow(120, 120);

        String winnerName = (winner == PlayerEnum.Player_1) ? "PLAYER 1" : "PLAYER 2";

        winnerText = new Text((int) screenW / 2 - 70, (int) (screenH / 2) + 100, winnerName + " WINS!");
        winnerText.setColor(Color.YELLOW);
        winnerText.grow(140, 48);
        winnerText.draw();

        hintText = new Text((int) screenW / 2 - 50, (int) (screenH / 2) + 200, "Press SPACE to restart");
        hintText.setColor(Color.WHITE);
        hintText.grow(90, 34);
        hintText.draw();

        Keyboard kb = new Keyboard(this);
        KeyboardEvent restart = new KeyboardEvent();
        restart.setKey(KeyboardEvent.KEY_SPACE);
        restart.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        kb.addEventListener(restart);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        if (triggered) {
            return;
        }
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE) {
            triggered = true;
            cleanup();
            game.GameController.startGame();
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
    }

    private void cleanup() {
        try {
            if (hintText != null)
                hintText.delete();
        } catch (Exception ignored) {
        }
        try {
            if (winnerText != null)
                winnerText.delete();
        } catch (Exception ignored) {
        }
        try {
            if (winnerFace != null)
                winnerFace.delete();
        } catch (Exception ignored) {
        }
        try {
            if (background != null)
                background.delete();
        } catch (Exception ignored) {
        }
    }
}
