package keyboard;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;


import game.PlayerEnum;
import game.characters.Character;


public class AppKeyboard implements KeyboardHandler {

	private Keyboard keyboard;
	private Controls playerControls;

	private Character controlledCharacter;

	KeyboardEvent startGame = new KeyboardEvent();

	public AppKeyboard(PlayerEnum playerNumber, Character controlledCharacter) {
		keyboard = new Keyboard(this);
		playerControls = new Controls(playerNumber);
		this.controlledCharacter = controlledCharacter;

		keyboard.addEventListener(playerControls.getMoveUpEvent());
		keyboard.addEventListener(playerControls.getMoveDownEvent());
		keyboard.addEventListener(playerControls.getMoveLeftEvent());
		keyboard.addEventListener(playerControls.getMoveRightEvent());
		keyboard.addEventListener(playerControls.getAttackEvent());
		keyboard.addEventListener(startGame);

		startGame.setKey(KeyboardEvent.KEY_SPACE);
		startGame.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);



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
		} else if (key==playerControls.getAttackEvent().getKey()) {
			controlledCharacter.castSpell();
		}else if(key == startGame.getKey()){
			//TODO Create homescreen handle
		}

	}

	@Override
	public void keyReleased(KeyboardEvent keyboardEvent) {

	}

}
