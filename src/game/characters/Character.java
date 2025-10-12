package game.characters;

import game.PlayerEnum;
import game.spells.Spell;

public abstract class Character {

	private int column;
	private int row;

	public Character(int column, int row, PlayerEnum playerNumber) {
		this.column = column;
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public abstract void moveUp();
	public abstract void moveDown();
	public abstract void moveLeft();
	public abstract void moveRight();

	public abstract void castSpell(Spell spellToCast);

}
