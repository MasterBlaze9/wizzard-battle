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

	// HomeScreen homeScreen = new HomeScreen();
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
		if (collisionManager.checkGameAreaColision(newCol, newRow)) {
			characterHead.move(0, -Grid.CELL_SIZE * moveCells);
			position.setRow(newRow);

			PowerUp p = CollisionManager.getPowerUpAt(newCol, newRow);
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
		if (collisionManager.checkGameAreaColision(newCol, newRow)) {
			characterHead.move(0, Grid.CELL_SIZE * moveCells);
			position.setRow(newRow);

			PowerUp p = CollisionManager.getPowerUpAt(newCol, newRow);
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
		if (collisionManager.checkGameAreaColision(newCol, newRow)) {
			characterHead.move(-Grid.CELL_SIZE * moveCells, 0);
			position.setCol(newCol);

			PowerUp p = CollisionManager.getPowerUpAt(newCol, newRow);
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
		if (collisionManager.checkGameAreaColision(newCol, newRow)) {
			characterHead.move(Grid.CELL_SIZE * moveCells, 0);
			position.setCol(newCol);

			PowerUp p = CollisionManager.getPowerUpAt(newCol, newRow);
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

	@Override
	public void takeDamage(int damage) {
		healthBar.removeLifePoints(damage);
	}

	@Override
	public void addLifePoints() {
		healthBar.addLifePoints();
	}

}
