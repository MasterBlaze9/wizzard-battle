package game.characters;

import game.PlayerEnum;
import game.spells.Spell;
import ui.character.CharacterUI;
import ui.position.Position;

public class PlayerTwoCharacter extends Character {

	private CharacterUI characterHead;
	private Position position;
	private PlayerEnum playerNumber;

	public PlayerTwoCharacter(int column, int row) {
		playerNumber = PlayerEnum.Player_2;
		position = new Position(column, row);
		characterHead = new CharacterUI(column, row);
	}

	@Override
	public void moveUp() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'moveUp'");
	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'moveDown'");
	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'moveLeft'");
	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'moveRight'");
	}

	@Override
	public void castSpell(Spell spellToCast) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'castSpell'");
	}

}
