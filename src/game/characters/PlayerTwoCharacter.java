package game.characters;

import game.PlayerEnum;
import game.powerUps.PowerUp;
import game.powerUps.PowerUpDamage;
import game.powerUps.PowerUpHealth;
import game.powerUps.PowerUpSpellSpeed;
import game.spells.Spell;
import keyboard.AppKeyboard;
import ui.character.CharacterUI;
import ui.grid.Grid;
import ui.healthBar.HealthBar;
import ui.position.Position;
import collisionManager.CollisionManager;

public class PlayerTwoCharacter extends Character {

	private CharacterUI characterHead;
	private Position position;
	private PlayerEnum playerNumber;
	private AppKeyboard appKeyboard;
	private CollisionManager collisionManager;
	private HealthBar healthBar;

	public PlayerTwoCharacter(Grid grid, int column, int row) {
		playerNumber = PlayerEnum.Player_2;
		position = new Position(column, row);
		characterHead = new CharacterUI(column, row, "resources/Characters/character2.png");
		appKeyboard = new AppKeyboard(PlayerEnum.Player_2, this);
		collisionManager = new CollisionManager(this, grid);
		healthBar = new HealthBar(PlayerEnum.Player_2);
	}

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
	public void moveUp() {
		int moveCells = 1 + Math.max(0, getMovementSpeedModifier());
		int newRow = position.getRow() - moveCells;
		int newCol = position.getCol();
		if (collisionManager.checkGameAreaCollision(newCol, newRow)) {
			characterHead.move(0, -Grid.CELL_SIZE * moveCells);
			PowerUp powerUp = CollisionManager.getPowerUpAlongPath(position.getCol(), position.getRow(), newCol,
					newRow);
			if (powerUp == null) {
				powerUp = CollisionManager.getPowerUpOverlappingCharacter(this);
			}
			position.setRow(newRow);
			if (CollisionManager.isDebugEnabled()) {
				System.out.println(String.format("[COLLIDE DEBUG] Player2 moved to logical=(%d,%d) pixel=(%d,%d)",
						position.getCol(), position.getRow(), getPixelX(), getPixelY()));
			}
			if (powerUp != null) {
				if (powerUp instanceof PowerUpHealth) {
					addLifePoints();
					powerUp.removeFromGame();
				} else if (powerUp instanceof PowerUpDamage) {
					applyDamageBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					powerUp.removeFromGame();
				} else if (powerUp instanceof PowerUpSpellSpeed) {
					applySpeedBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					applyMovementBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					powerUp.removeFromGame();
				}
			}
		}
	}

	@Override
	public void moveDown() {
		int moveCells = 1 + Math.max(0, getMovementSpeedModifier());
		int newRow = position.getRow() + moveCells;
		int newCol = position.getCol();
		if (collisionManager.checkGameAreaCollision(newCol, newRow)) {
			characterHead.move(0, Grid.CELL_SIZE * moveCells);
			PowerUp powerUp = CollisionManager.getPowerUpAlongPath(position.getCol(), position.getRow(), newCol,
					newRow);
			if (powerUp == null) {
				powerUp = CollisionManager.getPowerUpOverlappingCharacter(this);
			}
			position.setRow(newRow);
			if (CollisionManager.isDebugEnabled()) {
				System.out.println(String.format("[COLLIDE DEBUG] Player2 moved to logical=(%d,%d) pixel=(%d,%d)",
						position.getCol(), position.getRow(), getPixelX(), getPixelY()));
			}
			if (powerUp != null) {
				if (powerUp instanceof PowerUpHealth) {
					addLifePoints();
					powerUp.removeFromGame();
				} else if (powerUp instanceof PowerUpDamage) {
					applyDamageBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					powerUp.removeFromGame();
				} else if (powerUp instanceof PowerUpSpellSpeed) {
					applySpeedBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					applyMovementBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					powerUp.removeFromGame();
				}
			}
		}
	}

	@Override
	public void moveLeft() {
		int moveCells = 1 + Math.max(0, getMovementSpeedModifier());
		int newCol = position.getCol() - moveCells;
		int newRow = position.getRow();
		if (collisionManager.checkGameAreaCollision(newCol, newRow)) {
			characterHead.move(-Grid.CELL_SIZE * moveCells, 0);
			PowerUp powerUp = CollisionManager.getPowerUpAlongPath(position.getCol(), position.getRow(), newCol,
					newRow);
			if (powerUp == null) {
				powerUp = CollisionManager.getPowerUpOverlappingCharacter(this);
			}
			position.setCol(newCol);
			if (CollisionManager.isDebugEnabled()) {
				System.out.println(String.format("[COLLIDE DEBUG] Player2 moved to logical=(%d,%d) pixel=(%d,%d)",
						position.getCol(), position.getRow(), getPixelX(), getPixelY()));
			}
			if (powerUp != null) {
				if (powerUp instanceof PowerUpHealth) {
					addLifePoints();
					powerUp.removeFromGame();
				} else if (powerUp instanceof PowerUpDamage) {
					applyDamageBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					powerUp.removeFromGame();
				} else if (powerUp instanceof PowerUpSpellSpeed) {
					applySpeedBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					applyMovementBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					powerUp.removeFromGame();
				}
			}
		}
	}

	@Override
	public void moveRight() {
		int moveCells = 1 + Math.max(0, getMovementSpeedModifier());
		int newCol = position.getCol() + moveCells;
		int newRow = position.getRow();
		if (collisionManager.checkGameAreaCollision(newCol, newRow)) {
			characterHead.move(Grid.CELL_SIZE * moveCells, 0);
			PowerUp powerUp = CollisionManager.getPowerUpAlongPath(position.getCol(), position.getRow(), newCol,
					newRow);
			if (powerUp == null) {
				powerUp = CollisionManager.getPowerUpOverlappingCharacter(this);
			}
			position.setCol(newCol);
			if (CollisionManager.isDebugEnabled()) {
				System.out.println(String.format("[COLLIDE DEBUG] Player2 moved to logical=(%d,%d) pixel=(%d,%d)",
						position.getCol(), position.getRow(), getPixelX(), getPixelY()));
			}
			if (powerUp != null) {
				if (powerUp instanceof PowerUpHealth) {
					addLifePoints();
					powerUp.removeFromGame();
				} else if (powerUp instanceof PowerUpDamage) {
					applyDamageBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					powerUp.removeFromGame();
				} else if (powerUp instanceof PowerUpSpellSpeed) {
					applySpeedBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					applyMovementBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					powerUp.removeFromGame();
				}
			}
		}
	}

	@Override
	public void castSpell() {
		Spell s = new Spell(position.getRow(), position.getCol(), playerNumber);
		s.setDamage(s.getDamage() + getSpellDamageModifier());
		s.setSpeed(s.getSpeed() + getSpellSpeedModifier());
	}

	@Override
	public void takeDamage(int damage) {
		healthBar.removeLifePoints(damage);
	}

	@Override
	public void addLifePoints() {
		healthBar.addLifePoints();
	}

}
