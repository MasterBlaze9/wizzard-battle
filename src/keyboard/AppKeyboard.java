package keyboard;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;


import game.PlayerEnum;
import game.characters.Character;

//import ui.screens.HomeScreen;


public class AppKeyboard implements KeyboardHandler {

	private Keyboard keyboard;
	private Controls playerControls;
//	private HomeScreen start;
	private Character controlledCharacter;

	KeyboardEvent startGame = new KeyboardEvent();

	public AppKeyboard(PlayerEnum playerNumber, Character controlledCharacter ){
		keyboard = new Keyboard(this);
		playerControls = new Controls(playerNumber);
		this.controlledCharacter = controlledCharacter;
//		this.start = start;

		keyboard.addEventListener(playerControls.getMoveUpEvent());
		keyboard.addEventListener(playerControls.getMoveDownEvent());
		keyboard.addEventListener(playerControls.getMoveLeftEvent());
		keyboard.addEventListener(playerControls.getMoveRightEvent());
		keyboard.addEventListener(playerControls.getAttackEvent());
//		keyboard.addEventListener(start.getStartGameScreen());

//		startGame.setKey(KeyboardEvent.KEY_SPACE);
//		startGame.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
//		keyboard.addEventListener(startGame);


	}


	@Override
	public void keyPressed(KeyboardEvent keyboardEvent) {
		int key = keyboardEvent.getKey();

//		if(start.isVisible()) {
//			if (key == startGame.getKey()){
//				start.hide();
//			}
//			return;
//		}

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

		}

	}




	@Override
	public void keyReleased(KeyboardEvent keyboardEvent) {

	}

}
