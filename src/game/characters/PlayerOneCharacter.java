package game.characters;

import game.GameStateManager;
import game.PlayerEnum;
import keyboard.AppKeyboard;
import ui.character.CharacterUI;
import ui.grid.Grid;
import ui.healthBar.HealthBar;
import ui.position.Position;
import collisionManager.CollisionManager;

public class PlayerOneCharacter extends Character {

	public PlayerOneCharacter(Grid grid, int column, int row) {
		playerNumber = PlayerEnum.Player_1;
		position = new Position(column, row);
		characterHead = new CharacterUI(column, row, "resources/Characters/character.png");
		appKeyboard = new AppKeyboard(PlayerEnum.Player_1, this);
		collisionManager = new CollisionManager(this, grid);
		healthBar = new HealthBar(PlayerEnum.Player_1);
	}

	public PlayerEnum getPlayerEnum() {
		return playerNumber;
	}

	@Override
	protected PlayerEnum getOpponentPlayer() {
		return PlayerEnum.Player_2;
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public int getPixelX() {
		return characterHead.getPixelX();
	}

	@Override
	public int getPixelY() {
		return characterHead.getPixelY();
	}

	@Override
	public int getPixelWidth() {
		return characterHead.getPixelWidth();
	}

	@Override
	public int getPixelHeight() {
		return characterHead.getPixelHeight();
	}

	public PlayerEnum getPlayerNumber() {
		return playerNumber;
	}

	public AppKeyboard getAppKeyboard() {
		return appKeyboard;
	}

	@Override
	public void takeDamage(int damage) {
		healthBar.removeLifePoints(damage);
		if (!healthBar.isAlive()) {
			hideCharacter();
			GameStateManager.triggerGameOver(getOpponentPlayer());
		}
	}

    @Override
    public void cleanupOnGameOver() {
        hideCharacter();
        if (appKeyboard != null) {
            appKeyboard.cleanup();
        }
    }

	@Override
	public void addLifePoints() {
		healthBar.addLifePoints();
	}

}
