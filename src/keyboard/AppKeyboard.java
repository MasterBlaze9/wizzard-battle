package keyboard;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import game.PlayerEnum;
import game.characters.Character;
import ui.screens.HomeScreen;

public class AppKeyboard implements KeyboardHandler {

	private Keyboard keyboard;
	private Controls playerControls;
	private Character controlledCharacter;

	private static KeyboardEvent startGame = new KeyboardEvent();
	private static boolean startRegistered = false;

	public AppKeyboard(PlayerEnum playerNumber, Character controlledCharacter) {
		keyboard = new Keyboard(this);
		playerControls = new Controls(playerNumber);
		this.controlledCharacter = controlledCharacter;

		keyboard.addEventListener(playerControls.getMoveUpEvent());
		keyboard.addEventListener(playerControls.getMoveDownEvent());
		keyboard.addEventListener(playerControls.getMoveLeftEvent());
		keyboard.addEventListener(playerControls.getMoveRightEvent());
		keyboard.addEventListener(playerControls.getAttackEvent());
	}

	
	public static void addStartListener(HomeScreen homeScreen, Runnable startAction) {
		if (homeScreen == null || startAction == null) {
			return;
		}

		
		if (startRegistered) {
			return;
		}
		startRegistered = true;

		Keyboard kb = new Keyboard(new KeyboardHandler() {

			private boolean started = false;

			@Override
			public void keyPressed(KeyboardEvent keyboardEvent) {
				if (started) {
					return;
				}

				if (keyboardEvent.getKey() == startGame.getKey()) {
					started = true;
					homeScreen.hide();
					startAction.run();
				}
			}

			@Override
			public void keyReleased(KeyboardEvent keyboardEvent) {
				
			}
		});

		startGame.setKey(KeyboardEvent.KEY_SPACE);
		startGame.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
		kb.addEventListener(startGame);
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
		} else if (key == playerControls.getAttackEvent().getKey()) {
			controlledCharacter.castSpell();
		}
	}

	@Override
	public void keyReleased(KeyboardEvent keyboardEvent) {
		
	}
}
