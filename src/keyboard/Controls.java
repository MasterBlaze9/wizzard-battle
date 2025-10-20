package keyboard;

import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;

import game.PlayerEnum;

public class Controls {

	KeyboardEvent moveUp = new KeyboardEvent();
	KeyboardEvent moveDown = new KeyboardEvent();
	KeyboardEvent moveLeft = new KeyboardEvent();
	KeyboardEvent moveRight = new KeyboardEvent();
	KeyboardEvent attack = new KeyboardEvent();

	// Player control configurations
	private static class ControlConfig {
		int up, down, left, right, attack;

		ControlConfig(int up, int down, int left, int right, int attack) {
			this.up = up;
			this.down = down;
			this.left = left;
			this.right = right;
			this.attack = attack;
		}
	}

	private static final ControlConfig PLAYER1_CONFIG = new ControlConfig(
			KeyboardEvent.KEY_W,
			KeyboardEvent.KEY_S,
			KeyboardEvent.KEY_A,
			KeyboardEvent.KEY_D,
			KeyboardEvent.KEY_T);

	private static final ControlConfig PLAYER2_CONFIG = new ControlConfig(
			KeyboardEvent.KEY_I,
			KeyboardEvent.KEY_K,
			KeyboardEvent.KEY_J,
			KeyboardEvent.KEY_L,
			KeyboardEvent.KEY_P);

	public Controls(PlayerEnum playerNumber) {
		ControlConfig config = playerNumber.equals(PlayerEnum.Player_1) ? PLAYER1_CONFIG : PLAYER2_CONFIG;

		setupKeyEvent(moveUp, config.up);
		setupKeyEvent(moveDown, config.down);
		setupKeyEvent(moveLeft, config.left);
		setupKeyEvent(moveRight, config.right);
		setupKeyEvent(attack, config.attack);
	}

	private void setupKeyEvent(KeyboardEvent event, int key) {
		event.setKey(key);
		event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
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
