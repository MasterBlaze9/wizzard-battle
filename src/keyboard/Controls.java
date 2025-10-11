package keyboard;

import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;

import game.Player;
import game.PlayerEnum;

public class Controls {

	KeyboardEvent moveUp = new KeyboardEvent();
	KeyboardEvent moveDown = new KeyboardEvent();
	KeyboardEvent moveLeft = new KeyboardEvent();
	KeyboardEvent moveRight = new KeyboardEvent();
	KeyboardEvent attack = new KeyboardEvent(); // TODO Define the attack key

	public Controls(Player player) {
		if (player.getPlayerNumber().equals(PlayerEnum.Player_1)) {
			moveUp.setKey(KeyboardEvent.KEY_W);
			moveDown.setKey(KeyboardEvent.KEY_S);
			moveLeft.setKey(KeyboardEvent.KEY_A);
			moveRight.setKey(KeyboardEvent.KEY_D);

			moveUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			moveDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			moveLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			moveRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

		} else {
			moveUp.setKey(KeyboardEvent.KEY_UP);
			moveDown.setKey(KeyboardEvent.KEY_DOWN);
			moveLeft.setKey(KeyboardEvent.KEY_LEFT);
			moveRight.setKey(KeyboardEvent.KEY_RIGHT);

			moveUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			moveDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			moveLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
			moveRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
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
