package game.characters;

import game.PlayerEnum;
import game.spells.Spell;
import ui.character.CharacterUI;
import ui.position.Position;

public class PlayerOneCharacter extends Character {

	private CharacterUI characterHead;
	private Position position;
	private PlayerEnum playerNumber;
	

	public PlayerOneCharacter(int column, int row) {
		playerNumber = PlayerEnum.Player_1;
		position = new Position(column, row);
		characterHead = new CharacterUI(column, row);
	}

	public Position getPosition() {
		return position;
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
		int currentRow = position.getRow();
		int curremtColumn = position.getCol();
		throw new UnsupportedOperationException("Unimplemented method 'castSpell'");
	}

}
