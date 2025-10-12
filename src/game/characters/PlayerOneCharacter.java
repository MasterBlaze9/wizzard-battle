package game.characters;

import game.PlayerEnum;
import game.spells.Spell;

public class PlayerOneCharacter extends Character {



	public PlayerOneCharacter(int column, int row) {
		super(column, row, PlayerEnum.Player_1);
	}

	@Override
	public void moveUp() {
		// TODO: implement actual movement (e.g., change sprite/position)
		System.out.println("PlayerOneCharacter: moveUp");
	}

	@Override
	public void moveDown() {
		// TODO: implement actual movement (e.g., change sprite/position)
		System.out.println("PlayerOneCharacter: moveDown");
	}

	@Override
	public void moveLeft() {
		// TODO: implement actual movement (e.g., change sprite/position)
		System.out.println("PlayerOneCharacter: moveLeft");
	}

	@Override
	public void moveRight() {
		// TODO: implement actual movement (e.g., change sprite/position)
		System.out.println("PlayerOneCharacter: moveRight");
	}

	@Override
	public void castSpell(Spell spellToCast) {
		int currentRow = super.getRow();
		int curremtColumn = super.getColumn();
		throw new UnsupportedOperationException("Unimplemented method 'castSpell'");
	}

}
