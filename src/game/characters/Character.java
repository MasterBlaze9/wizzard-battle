package game.characters;

import game.PlayerEnum;
import game.spells.Spell;
import ui.position.Position;

public abstract class Character {


	

	public abstract void moveUp();
	public abstract void moveDown();
	public abstract void moveLeft();
	public abstract void moveRight();
	public abstract void castSpell(Spell spellToCast);

}
