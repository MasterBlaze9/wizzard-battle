package keyboard;

import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;

import game.PlayerEnum;

public class Controls {

	KeyboardEvent moveUp = new KeyboardEvent();
	KeyboardEvent moveDown = new KeyboardEvent();
	KeyboardEvent moveLeft = new KeyboardEvent();
	KeyboardEvent moveRight = new KeyboardEvent();
	KeyboardEvent attack = new KeyboardEvent(); // TODO Define the attack key

	public Controls(PlayerEnum playerNumber) {
		if (playerNumber.equals(PlayerEnum.Player_1)) {
			moveUp.setKey(KeyboardEvent.KEY_W);
			moveDown.setKey(KeyboardEvent.KEY_S);
			moveLeft.setKey(KeyboardEvent.KEY_A);
			moveRight.setKey(KeyboardEvent.KEY_D);
			attack.setKey(KeyboardEvent.KEY_T);

			moveUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			moveDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			moveLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			moveRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			attack.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

		} else {
			moveUp.setKey(KeyboardEvent.KEY_I);
			moveDown.setKey(KeyboardEvent.KEY_K);
			moveLeft.setKey(KeyboardEvent.KEY_J);
			moveRight.setKey(KeyboardEvent.KEY_L);
			attack.setKey(KeyboardEvent.KEY_P);

			moveUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			moveDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			moveLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			moveRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			attack.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
		}
	}

	public KeyboardEvent getMoveUpEvent() {
		return moveUp;
	}

	public KeyboardEvent getMoveDownEvent() {
		return moveDown;
	}

	public KeyboardEvent getMoveLeftEvent() {
		return moveLeft;
	}

	public KeyboardEvent getMoveRightEvent() {
		return moveRight;
	}

	public KeyboardEvent getAttackEvent() {
		return attack;
	}



}
