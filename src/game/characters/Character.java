package game.characters;

import collisionManager.CollisionManager;
import game.PlayerEnum;
import game.powerUps.PowerUpHandler;
import game.spells.Spell;
import ui.character.CharacterUI;
import ui.grid.Grid;
import ui.healthBar.HealthBar;
import ui.position.Position;
import keyboard.AppKeyboard;

public abstract class Character {

	protected CharacterUI characterHead;
	protected Position position;
	protected PlayerEnum playerNumber;
	protected AppKeyboard appKeyboard;
	protected CollisionManager collisionManager;
	protected HealthBar healthBar;

	public abstract Position getPosition();

	// Pixel bounds of the character's rendered picture
	public abstract int getPixelX();

	public abstract int getPixelY();

	public abstract int getPixelWidth();

	public abstract int getPixelHeight();

	
	protected abstract PlayerEnum getOpponentPlayer();

	public abstract void takeDamage(int damage);

	public abstract void addLifePoints();

	

	public void moveUp() {
		int moveCells = 1 + Math.max(0, getMovementSpeedModifier());
		int newRow = position.getRow() - moveCells;
		int newCol = position.getCol();

		if (collisionManager.checkGameAreaCollision(newCol, newRow)) {
			characterHead.move(0, -Grid.CELL_SIZE * moveCells);
			PowerUpHandler.handlePowerUpCollection(this, position.getCol(), position.getRow(), newCol, newRow);
			position.setRow(newRow);
			
		}
	}

	public void moveDown() {
		int moveCells = 1 + Math.max(0, getMovementSpeedModifier());
		int newRow = position.getRow() + moveCells;
		int newCol = position.getCol();

		if (collisionManager.checkGameAreaCollision(newCol, newRow)) {
			characterHead.move(0, Grid.CELL_SIZE * moveCells);
			PowerUpHandler.handlePowerUpCollection(this, position.getCol(), position.getRow(), newCol, newRow);
			position.setRow(newRow);
			
		}
	}

	public void moveLeft() {
		int moveCells = 1 + Math.max(0, getMovementSpeedModifier());
		int newCol = position.getCol() - moveCells;
		int newRow = position.getRow();

		if (collisionManager.checkGameAreaCollision(newCol, newRow)) {
			characterHead.move(-Grid.CELL_SIZE * moveCells, 0);
			PowerUpHandler.handlePowerUpCollection(this, position.getCol(), position.getRow(), newCol, newRow);
			position.setCol(newCol);
			
		}
	}

	public void moveRight() {
		int moveCells = 1 + Math.max(0, getMovementSpeedModifier());
		int newCol = position.getCol() + moveCells;
		int newRow = position.getRow();

		if (collisionManager.checkGameAreaCollision(newCol, newRow)) {
			characterHead.move(Grid.CELL_SIZE * moveCells, 0);
			PowerUpHandler.handlePowerUpCollection(this, position.getCol(), position.getRow(), newCol, newRow);
			position.setCol(newCol);
			
		}
	}

	public void castSpell() {
		Spell s = new Spell(position.getRow(), position.getCol(), playerNumber);
		s.setDamage(s.getDamage() + getSpellDamageModifier());
		s.setSpeed(s.getSpeed() + getSpellSpeedModifier());
	}


	protected void hideCharacter() {
		if (characterHead != null) {
			characterHead.hide();
		}
	}

	/**
	 * Called on game-over so implementations can delete any on-screen
	 * pictures (sprites) they own. This helps ensure no character image
	 * lingers after the grid/background is cleared.
	 */
	public void cleanupOnGameOver() {
		// default no-op; concrete players can override
	}

	// --- Buff state & helpers ---
	private int spellDamageModifier = 0;
	private int spellSpeedModifier = 0;
	private int movementSpeedModifier = 0;

	public synchronized int getSpellDamageModifier() {
		return spellDamageModifier;
	}

	public synchronized int getSpellSpeedModifier() {
		return spellSpeedModifier;
	}

	public synchronized int getMovementSpeedModifier() {
		return movementSpeedModifier;
	}

	public void applyDamageBuff(int extraDamage, int durationSeconds) {
		BuffManager.applyTemporaryBuff(delta -> {
			synchronized (this) {
				spellDamageModifier = Math.max(0, spellDamageModifier + delta);
			}
		}, extraDamage, durationSeconds);
	}

	public void applySpeedBuff(int extraSpeed, int durationSeconds) {
		BuffManager.applyTemporaryBuff(delta -> {
			synchronized (this) {
				spellSpeedModifier = Math.max(0, spellSpeedModifier + delta);
			}
		}, extraSpeed, durationSeconds);
	}

	public void applyMovementBuff(int extraMovement, int durationSeconds) {
		BuffManager.applyTemporaryBuff(delta -> {
			synchronized (this) {
				movementSpeedModifier = Math.max(0, movementSpeedModifier + delta);
			}
		}, extraMovement, durationSeconds);
	}

}
