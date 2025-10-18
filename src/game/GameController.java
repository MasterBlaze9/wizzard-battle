package game;

/**
 * Central place to register a start game action so screens in other packages
 * can trigger a new game without referencing the default-package App class.
 */
public class GameController {

	private static Runnable startAction;

	public static void setStartAction(Runnable action) {
		startAction = action;
	}

	public static void startGame() {
		if (startAction != null) {
			startAction.run();
		}
	}

}
