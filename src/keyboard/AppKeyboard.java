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
	
	private static Keyboard globalKeyboard;
	private static boolean listenerInitialized = false;
	private static volatile boolean armedForStart = false;
	private static HomeScreen armedHomeScreen = null;
	private static Runnable armedAction = null;

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
		// Initialize global listener once
		if (!listenerInitialized) {
			globalKeyboard = new Keyboard(new KeyboardHandler() {
				@Override
				public void keyPressed(KeyboardEvent keyboardEvent) {
					if (!armedForStart) {
						return;
					}
					if (keyboardEvent.getKey() == startGame.getKey()) {
						// Disarm immediately to avoid retriggering
						armedForStart = false;
						try {
							if (armedHomeScreen != null) {
								armedHomeScreen.hide();
							}
						} catch (Exception ignored) {
						}

						// Prefer armedAction when provided; otherwise fall back to GameController
						try {
							if (armedAction != null) {
								armedAction.run();
							} else {
								game.GameController.startGame();
							}
						} finally {
							// Clear one-shot references
							armedHomeScreen = null;
							armedAction = null;
						}
					}
				}

				@Override
				public void keyReleased(KeyboardEvent keyboardEvent) {
				}
			});

			startGame.setKey(KeyboardEvent.KEY_SPACE);
			startGame.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			globalKeyboard.addEventListener(startGame);
			listenerInitialized = true;
		}

	
		armedHomeScreen = homeScreen;
		armedAction = startAction; 
		armedForStart = true;
	}

	public static void armStartOnce(HomeScreen homeScreen) {
		addStartListener(homeScreen, null);
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
