package ui.screens;

import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import game.PlayerEnum;

/**
 * Simple game over screen. Shows a background and a message indicating the
 * winner.
 * Pressing space will start a new game by calling GameController.startGame().
 */
public class GameOverScreen extends HomeScreen implements KeyboardHandler {

	private PlayerEnum winner;
	private Picture winnerPic;
	private Keyboard kb;

	public GameOverScreen(PlayerEnum winner) {
		super(); // HomeScreen constructor draws the background
		this.winner = winner;

		// draw winner overlay
		try {
			String winnerImg = winner == PlayerEnum.Player_1 ? "resources/Faces/Carolina.png"
					: "resources/Faces/Pascoa.png";
			winnerPic = new Picture(50, 50, winnerImg);
			winnerPic.draw();
		} catch (Exception ignored) {
		}

		// register space listener to restart
		kb = new Keyboard(this);
		KeyboardEvent restart = new KeyboardEvent();
		restart.setKey(KeyboardEvent.KEY_SPACE);
		restart.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
		kb.addEventListener(restart);
	}

	@Override
	public void keyPressed(KeyboardEvent keyboardEvent) {
		if (keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE) {
			// hide this screen and start a new game
			try {
				if (winnerPic != null) {
					winnerPic.delete();
				}
			} catch (Exception ignored) {
			}
			super.hide();
			game.GameController.startGame();
		}
	}

	@Override
	public void keyReleased(KeyboardEvent keyboardEvent) {
		// no-op
	}
}
