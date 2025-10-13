package game.characters;

import game.PlayerEnum;
import game.spells.Spell;
import keyboard.AppKeyboard;
import keyboard.Controls;
import ui.character.CharacterUI;
import ui.grid.Grid;
import ui.position.Position;

public class PlayerOneCharacter extends Character {

	private CharacterUI characterHead;
	private Position position;
	private PlayerEnum playerNumber;
	private AppKeyboard appKeyboard;
	

	public PlayerOneCharacter(int column, int row) {
		playerNumber = PlayerEnum.Player_1;
		position = new Position(column, row);
		characterHead = new CharacterUI(column, row);
		appKeyboard = new AppKeyboard(PlayerEnum.Player_1,this);
	}

	public Position getPosition() {
		return position;
	}

	@Override
	public void moveUp() {
		characterHead.move(0, - Grid.CELL_SIZE);

		System.out.println(position.getCol());
		System.out.println(position.getRow());
	}

	@Override
	public void moveDown() {
		characterHead.move(0, Grid.CELL_SIZE);
	}

	@Override
	public void moveLeft() {
		characterHead.move( - Grid.CELL_SIZE, 0);
	}

	@Override
	public void moveRight() {
		characterHead.move(  Grid.CELL_SIZE, 0);
	}

	@Override
	public void castSpell(Spell spellToCast) {
		int currentRow = position.getRow();
		int curremtColumn = position.getCol();
		throw new UnsupportedOperationException("Unimplemented method 'castSpell'");
	}

}
