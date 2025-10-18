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

public class PlayerOneCharacter extends Character {

	private CharacterUI characterHead;
	private Position position;
	private PlayerEnum playerNumber;
	private AppKeyboard appKeyboard;
	private CollisionManager collisionManager;
	private HealthBar healthBar;

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
	public void moveUp() {
		int moveCells = 1 + Math.max(0, getMovementSpeedModifier());
		int newRow = position.getRow() - moveCells;
		int newCol = position.getCol();

		if (collisionManager.checkGameAreaCollision(newCol, newRow)) {

			characterHead.move(0, -Grid.CELL_SIZE * moveCells);
			// detect powerups along the movement path (handles moveCells > 1)
			PowerUp p = CollisionManager.getPowerUpAlongPath(position.getCol(), position.getRow(), newCol, newRow);
			if (p == null) {
				p = CollisionManager.getPowerUpOverlappingCharacter(this);
			}
			position.setRow(newRow);
			if (CollisionManager.isDebugEnabled()) {
				System.out.println(String.format("[COLLIDE DEBUG] Player1 moved to logical=(%d,%d) pixel=(%d,%d)",
						position.getCol(), position.getRow(), getPixelX(), getPixelY()));
			}
			if (p != null) {
				if (p instanceof PowerUpHealth) {
					addLifePoints();
					p.removeFromGame();
				} else if (p instanceof PowerUpDamage) {
					applyDamageBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					p.removeFromGame();
				} else if (p instanceof PowerUpSpellSpeed) {

					applySpeedBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					applyMovementBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					p.removeFromGame();
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
			// detect powerups along the movement path (handles moveCells > 1)
			PowerUp p = CollisionManager.getPowerUpAlongPath(position.getCol(), position.getRow(), newCol, newRow);
			if (p == null) {
				p = CollisionManager.getPowerUpOverlappingCharacter(this);
			}
			position.setRow(newRow);
			if (CollisionManager.isDebugEnabled()) {
				System.out.println(String.format("[COLLIDE DEBUG] Player1 moved to logical=(%d,%d) pixel=(%d,%d)",
						position.getCol(), position.getRow(), getPixelX(), getPixelY()));
			}
			if (p != null) {
				if (p instanceof PowerUpHealth) {
					addLifePoints();
					p.removeFromGame();
				} else if (p instanceof PowerUpDamage) {
					applyDamageBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					p.removeFromGame();
				} else if (p instanceof PowerUpSpellSpeed) {
					applySpeedBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					applyMovementBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					p.removeFromGame();
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
			PowerUp p = CollisionManager.getPowerUpAlongPath(position.getCol(), position.getRow(), newCol, newRow);
			if (p == null) {
				p = CollisionManager.getPowerUpOverlappingCharacter(this);
			}
			position.setCol(newCol);
			if (CollisionManager.isDebugEnabled()) {
				System.out.println(String.format("[COLLIDE DEBUG] Player1 moved to logical=(%d,%d) pixel=(%d,%d)",
						position.getCol(), position.getRow(), getPixelX(), getPixelY()));
			}
			if (p != null) {
				if (p instanceof PowerUpHealth) {
					addLifePoints();
					p.removeFromGame();
				} else if (p instanceof PowerUpDamage) {
					applyDamageBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					p.removeFromGame();
				} else if (p instanceof PowerUpSpellSpeed) {
					applySpeedBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					applyMovementBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					p.removeFromGame();
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
			PowerUp p = CollisionManager.getPowerUpAlongPath(position.getCol(), position.getRow(), newCol, newRow);
			if (p == null) {
				p = CollisionManager.getPowerUpOverlappingCharacter(this);
			}
			position.setCol(newCol);
			if (CollisionManager.isDebugEnabled()) {
				System.out.println(String.format("[COLLIDE DEBUG] Player1 moved to logical=(%d,%d) pixel=(%d,%d)",
						position.getCol(), position.getRow(), getPixelX(), getPixelY()));
			}
			if (p != null) {
				if (p instanceof PowerUpHealth) {
					addLifePoints();
					p.removeFromGame();
				} else if (p instanceof PowerUpDamage) {
					applyDamageBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					p.removeFromGame();
				} else if (p instanceof PowerUpSpellSpeed) {
					applySpeedBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					applyMovementBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
					p.removeFromGame();
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

	public void takeDamage(int damage) {
		healthBar.removeLifePoints(damage);
		if (!healthBar.isAlive()) {
			triggerGameOver(PlayerEnum.Player_2);
		}
	}

	private void triggerGameOver(PlayerEnum winner) {
		hideCharacter();
		// Clear all health bars from both players
		ui.healthBar.HealthBar.cleanupAll();
		CollisionManager.clearAll();
		ui.grid.Grid.clearAll();

		// Small delay to allow any running spell threads to complete cleanup
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		// Show the Game Over screen (background + winner face) and listen for SPACE
		new ui.screens.GameOverScreen(winner);
	}

	private void hideCharacter() {
		if (characterHead != null) {
			characterHead.hide();
		}
	}

	@Override
	public void cleanupOnGameOver() {
		hideCharacter();
	}

	@Override
	public void addLifePoints() {
		healthBar.addLifePoints();
	}

}
