package ui.screens;

import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import game.PlayerEnum;


public class GameOver extends HomeScreen implements KeyboardHandler {

	private PlayerEnum winner;
	private Picture winnerPic;
	private Keyboard kb;

	public GameOver(PlayerEnum winner) {
		super();
		this.winner = winner;

		try {
			String winnerImg = winner == PlayerEnum.Player_1 ? "resources/Faces/Carolina.png"
					: "resources/Faces/Pascoa.png";
			winnerPic = new Picture(60, 60, winnerImg);
			winnerPic.draw();
		} catch (Exception ignored) {
		}

		kb = new Keyboard(this);
		KeyboardEvent restart = new KeyboardEvent();
		restart.setKey(KeyboardEvent.KEY_SPACE);
		restart.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
		kb.addEventListener(restart);
	}

	@Override
	public void keyPressed(KeyboardEvent keyboardEvent) {
		if (keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE) {
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

	}
}
