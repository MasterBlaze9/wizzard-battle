package game.spells;

import ui.position.Position;

public class Spell {

	private Position position;

	public Spell(int row, int col) {
		position = new Position(col, row);
	}

	public Position getPosition() {
		return position;
	}

	public void updatePosition(int newCol, int newRow) {
		position.setCol(newCol);
		position.setRow(newRow);
	}

}
