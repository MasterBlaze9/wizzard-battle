package keyboard;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;


import game.PlayerEnum;
import game.characters.Character;
import org.w3c.dom.ls.LSOutput;

public class AppKeyboard implements KeyboardHandler {

	private Keyboard keyboard;
	private Controls playerControls;

	private Character controlledCharacter;

	public AppKeyboard(PlayerEnum playerNumber, Character controlledCharacter) {
		keyboard = new Keyboard(this);
		playerControls = new Controls(playerNumber);
		this.controlledCharacter = controlledCharacter;

		keyboard.addEventListener(playerControls.getMoveUpEvent());
		keyboard.addEventListener(playerControls.getMoveDownEvent());
		keyboard.addEventListener(playerControls.getMoveLeftEvent());
		keyboard.addEventListener(playerControls.getMoveRightEvent());
	}


	@Override
	public void keyPressed(KeyboardEvent keyboardEvent) {
		int key = keyboardEvent.getKey();

		if (key == playerControls.getMoveRightEvent().getKey()) {
			controlledCharacter.moveRight();
		} else if (key == playerControls.getMoveLeftEvent().getKey()) {
			controlledCharacter.moveLeft();
		} else if (key == playerControls.getMoveUpEvent().getKey()) {
			controlledCharacter.moveUp();
		} else if (key == playerControls.getMoveDownEvent().getKey()) {
			controlledCharacter.moveDown();
		}

	}

	@Override
	public void keyReleased(KeyboardEvent keyboardEvent) {

	}

}
