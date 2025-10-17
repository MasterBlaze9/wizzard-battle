package game.characters;

import ui.position.Position;

public abstract class Character {

	public abstract Position getPosition();

	// Pixel bounds of the character's rendered picture
	public abstract int getPixelX();
	public abstract int getPixelY();
	public abstract int getPixelWidth();
	public abstract int getPixelHeight();

	public abstract void moveUp();

	public abstract void moveDown();

	public abstract void moveLeft();

	public abstract void moveRight();

	public abstract void castSpell();

	public abstract void takeDamage(int damage);

	public abstract void addLifePoints();

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
		if (extraDamage <= 0 || durationSeconds <= 0) {
			return;
		}
		synchronized (this) {
			spellDamageModifier += extraDamage;
		}
		new Thread(() -> {
			try {
				Thread.sleep(durationSeconds * 1000L);
			} catch (InterruptedException ignored) {
				Thread.currentThread().interrupt();
			}
			synchronized (this) {
				spellDamageModifier = Math.max(0, spellDamageModifier - extraDamage);
			}
		}).start();
	}

	public void applySpeedBuff(int extraSpeed, int durationSeconds) {
		if (extraSpeed <= 0 || durationSeconds <= 0) {
			return;
		}
		synchronized (this) {
			spellSpeedModifier += extraSpeed;
		}
		new Thread(() -> {
			try {
				Thread.sleep(durationSeconds * 1000L);
			} catch (InterruptedException ignored) {
				Thread.currentThread().interrupt();
			}
			synchronized (this) {
				spellSpeedModifier = Math.max(0, spellSpeedModifier - extraSpeed);
			}
		}).start();
	}

	public void applyMovementBuff(int extraMovement, int durationSeconds) {
		if (extraMovement <= 0 || durationSeconds <= 0) {
			return;
		}
		synchronized (this) {
			movementSpeedModifier += extraMovement;
		}
		new Thread(() -> {
			try {
				Thread.sleep(durationSeconds * 1000L);
			} catch (InterruptedException ignored) {
				Thread.currentThread().interrupt();
			}
			synchronized (this) {
				movementSpeedModifier = Math.max(0, movementSpeedModifier - extraMovement);
			}
		}).start();
	}

}
