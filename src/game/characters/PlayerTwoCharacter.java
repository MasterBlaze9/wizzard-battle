package game.characters;

import game.PlayerEnum;
import game.spells.Spell;
import keyboard.AppKeyboard;
// Controls import removed because it's not used here
import ui.character.CharacterUI;
import ui.grid.Grid;
import ui.position.Position;

public class PlayerTwoCharacter extends Character {

	private CharacterUI characterHead;
	private Position position;
	private PlayerEnum playerNumber;
	private AppKeyboard appKeyboard;

	public PlayerTwoCharacter(int column, int row) {
		playerNumber = PlayerEnum.Player_2;
		position = new Position(column, row);
		characterHead = new CharacterUI(column, row);
		appKeyboard = new AppKeyboard(PlayerEnum.Player_2, this);
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
		characterHead.move(0, -Grid.CELL_SIZE);
	}

	@Override
	public void moveDown() {
		characterHead.move(0, Grid.CELL_SIZE);
	}

	@Override
	public void moveLeft() {
		characterHead.move(-Grid.CELL_SIZE, 0);
	}

	@Override
	public void moveRight() {
		characterHead.move(Grid.CELL_SIZE, 0);
	}

	@Override
	public void castSpell(Spell spellToCast) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'castSpell'");
	}

}
