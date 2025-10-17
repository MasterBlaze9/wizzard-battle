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
		characterHead = new CharacterUI(column, row,"resources/Characters/character.png" );
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

		if (collisionManager.checkGameAreaColision(newCol, newRow)) {
			// apply visual move and update logical position
			characterHead.move(0, -Grid.CELL_SIZE * moveCells);
			position.setRow(newRow);

			// pickup powerup if present
			PowerUp p = CollisionManager.getPowerUpAt(newCol, newRow);
			if (p != null) {
				if (p instanceof PowerUpHealth) {
					addLifePoints();
					p.removeFromGame();
				} else if (p instanceof PowerUpDamage) {
					applyDamageBuff(1, ui.grid.Grid.POWERUP_BUFF_DURATION_SECONDS);
					p.removeFromGame();
				} else if (p instanceof PowerUpSpellSpeed) {
					
					applySpeedBuff(1, ui.grid.Grid.POWERUP_BUFF_DURATION_SECONDS);
					applyMovementBuff(1, ui.grid.Grid.POWERUP_BUFF_DURATION_SECONDS);
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
		if (collisionManager.checkGameAreaColision(newCol, newRow)) {
			characterHead.move(0, Grid.CELL_SIZE * moveCells);
			position.setRow(newRow);

			PowerUp p = CollisionManager.getPowerUpAt(newCol, newRow);
			if (p != null) {
				if (p instanceof PowerUpHealth) {
					addLifePoints();
					p.removeFromGame();
				} else if (p instanceof PowerUpDamage) {
					applyDamageBuff(1, ui.grid.Grid.POWERUP_BUFF_DURATION_SECONDS);
					p.removeFromGame();
				} else if (p instanceof PowerUpSpellSpeed) {
					applySpeedBuff(1, ui.grid.Grid.POWERUP_BUFF_DURATION_SECONDS);
					applyMovementBuff(1, ui.grid.Grid.POWERUP_BUFF_DURATION_SECONDS);
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
		if (collisionManager.checkGameAreaColision(newCol, newRow)) {
			characterHead.move(-Grid.CELL_SIZE * moveCells, 0);
			position.setCol(newCol);

			PowerUp p = CollisionManager.getPowerUpAt(newCol, newRow);
			if (p != null) {
				if (p instanceof PowerUpHealth) {
					addLifePoints();
					p.removeFromGame();
				} else if (p instanceof PowerUpDamage) {
					applyDamageBuff(1, ui.grid.Grid.POWERUP_BUFF_DURATION_SECONDS);
					p.removeFromGame();
				} else if (p instanceof PowerUpSpellSpeed) {
					applySpeedBuff(1, ui.grid.Grid.POWERUP_BUFF_DURATION_SECONDS);
					applyMovementBuff(1, ui.grid.Grid.POWERUP_BUFF_DURATION_SECONDS);
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
		if (collisionManager.checkGameAreaColision(newCol, newRow)) {
			characterHead.move(Grid.CELL_SIZE * moveCells, 0);
			position.setCol(newCol);

			PowerUp p = CollisionManager.getPowerUpAt(newCol, newRow);
			if (p != null) {
				if (p instanceof PowerUpHealth) {
					addLifePoints();
					p.removeFromGame();
				} else if (p instanceof PowerUpDamage) {
					applyDamageBuff(1, ui.grid.Grid.POWERUP_BUFF_DURATION_SECONDS);
					p.removeFromGame();
				} else if (p instanceof PowerUpSpellSpeed) {
					applySpeedBuff(1, ui.grid.Grid.POWERUP_BUFF_DURATION_SECONDS);
					applyMovementBuff(1, ui.grid.Grid.POWERUP_BUFF_DURATION_SECONDS);
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

	public PlayerEnum getPlayerNumber() {
		return playerNumber;
	}

	public AppKeyboard getAppKeyboard() {
		return appKeyboard;
	}

	public void takeDamage(int damage) {
		healthBar.removeLifePoints(damage);
	}

	@Override
	public void addLifePoints() {
		healthBar.addLifePoints();
	}

}
