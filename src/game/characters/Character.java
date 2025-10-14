package game.characters;

import game.spells.Spell;
import ui.position.Position;

public abstract class Character {

	public abstract Position getPosition();

	public abstract void moveUp();

	public abstract void moveDown();

	public abstract void moveLeft();

	public abstract void moveRight();

	public abstract void castSpell();

}
