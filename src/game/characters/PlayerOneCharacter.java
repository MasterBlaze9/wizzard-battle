package game.characters;

import game.PlayerEnum;
import game.spells.Spell;
import keyboard.AppKeyboard;
import ui.character.CharacterUI;
import ui.grid.Grid;
import ui.position.Position;
import collisionManager.CollisionManager;

public class PlayerOneCharacter extends Character {

	private CharacterUI characterHead;
	private Position position;
	private PlayerEnum playerNumber;
	private AppKeyboard appKeyboard;
	private CollisionManager collisionManager;

	public PlayerOneCharacter(Grid grid, int column, int row) {
		playerNumber = PlayerEnum.Player_1;
		position = new Position(column, row);
		characterHead = new CharacterUI(column, row);
		appKeyboard = new AppKeyboard(PlayerEnum.Player_1, this);
		collisionManager = new CollisionManager(this, grid);
	}

	@Override
	public void moveUp() {
		int newRow = position.getRow() - 1;
		int newCol = position.getCol();

		if (collisionManager.checkColision(newCol, newRow)) {
			// apply visual move and update logical position
			characterHead.move(0, -Grid.CELL_SIZE);
			position.setRow(newRow);
		}
	}

	@Override
	public void moveDown() {
		int newRow = position.getRow() + 1;
		int newCol = position.getCol();
		if (collisionManager.checkColision(newCol, newRow)) {
			characterHead.move(0, Grid.CELL_SIZE);
			position.setRow(newRow);
		}
	}

	@Override
	public void moveLeft() {
		int newCol = position.getCol() - 1;
		int newRow = position.getRow();
		if (collisionManager.checkColision(newCol, newRow)) {
			characterHead.move(-Grid.CELL_SIZE, 0);
			position.setCol(newCol);
		}
	}

	@Override
	public void moveRight() {
		int newCol = position.getCol() + 1;
		int newRow = position.getRow();
		if (collisionManager.checkColision(newCol, newRow)) {
			characterHead.move(Grid.CELL_SIZE, 0);
			position.setCol(newCol);
		}
	}

	@Override
	public void castSpell(Spell spellToCast) {
		int currentRow = position.getRow();
		int curremtColumn = position.getCol();
		// include position in the exception message so the local vars are used
		throw new UnsupportedOperationException(
				"Unimplemented method 'castSpell' at " + curremtColumn + "," + currentRow);
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

}
