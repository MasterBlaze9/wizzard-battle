package game.characters;

import game.PlayerEnum;
import game.spells.Spell;
import keyboard.AppKeyboard;

import ui.character.CharacterUI;
import ui.character.HealthBar.HealthBar;
import ui.grid.Grid;
import ui.position.Position;
import collisionManager.CollisionManager;


public class PlayerTwoCharacter extends Character {

	private CharacterUI characterHead;
	private Position position;
	private PlayerEnum playerNumber;
	private AppKeyboard appKeyboard;
	private CollisionManager collisionManager;
	private HealthBar healthBar;

	public PlayerTwoCharacter(Grid grid, int column, int row) {
		playerNumber = PlayerEnum.Player_2;
		position = new Position(column, row);
		characterHead = new CharacterUI(column, row);
		appKeyboard = new AppKeyboard(PlayerEnum.Player_2, this);
		collisionManager = new CollisionManager(this, grid);
		healthBar = new HealthBar(PlayerEnum.Player_2);
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
		int newRow = position.getRow() - 1;
		int newCol = position.getCol();
		if (collisionManager.checkGameAreaColision(newCol, newRow)) {
			characterHead.move(0, -Grid.CELL_SIZE);
			position.setRow(newRow);
		}
	}

	@Override
	public void moveDown() {
		int newRow = position.getRow() + 1;
		int newCol = position.getCol();
		if (collisionManager.checkGameAreaColision(newCol, newRow)) {
			characterHead.move(0, Grid.CELL_SIZE);
			position.setRow(newRow);
		}
	}

	@Override
	public void moveLeft() {
		int newCol = position.getCol() - 1;
		int newRow = position.getRow();
		if (collisionManager.checkGameAreaColision(newCol, newRow)) {
			characterHead.move(-Grid.CELL_SIZE, 0);
			position.setCol(newCol);
		}
	}

	@Override
	public void moveRight() {
		int newCol = position.getCol() + 1;
		int newRow = position.getRow();
		if (collisionManager.checkGameAreaColision(newCol, newRow)) {
			characterHead.move(Grid.CELL_SIZE, 0);
			position.setCol(newCol);
		}
	}

	@Override
	public void castSpell() {

		new Spell(position.getRow(), position.getCol(), playerNumber);
	}

	@Override
	public void takeDamage(int damage) {
		healthBar.removeLifePoints(damage);
	}

	@Override
	public void addLifePoints() {
		healthBar.addLifePoints();
	}

}
