package collisionManager;

import ui.grid.Grid;
import ui.position.Position;
import game.characters.Character;
import game.spells.Spell;
import game.characters.PlayerOneCharacter;
import game.characters.PlayerTwoCharacter;
import game.PlayerEnum;

public class CollisionManager {

	private Position position;
	private Character character;
	private Spell spell;
	private Grid grid;

	public CollisionManager(Character character) {
		this.character = character;
		position = new Position(character.getPosition().getCol(), character.getPosition().getRow());
	}

	/**
	 * Create a CollisionManager that can validate moves against the provided
	 * grid/game area.
	 */
	public CollisionManager(Character character, Grid grid) {
		this(character);
		this.grid = grid;
	}

	public CollisionManager(Spell spell) {
		this.spell = spell;
		position = new Position(spell.getPosition().getCol(), spell.getPosition().getRow());
	}

	public void updateCollisionDetector(int newColumn, int newRow) {
		position.setCol(newColumn);
		position.setRow(newRow);
	}

	/**
	 * Check whether a proposed new column/row is inside the character's allowed
	 * half of the game area.
	 * Returns true when the move is allowed, false when it would breach the
	 * player's area limits.
	 *
	 * Requires a Grid instance to be provided at construction. If no grid is
	 * available this method
	 * will allow the move (returns true) because bounds can't be validated.
	 */
	public boolean checkColision(int newCol, int newRow) {
		if (character == null) {
			return true;
		}

		if (grid == null) {
			// No grid available to validate against — allow move by default.
			return true;
		}

		int totalCols = grid.getCols();
		int totalRows = grid.getRows();

		int colsPerPlayer = grid.getMaxColsPerPlayer();
		int rowsPerPlayer = grid.getMaxRowsPerPlayer();

		// compute vertical center area start/end in grid rows
		int topRow = (totalRows - rowsPerPlayer) / 2;
		int bottomRow = topRow + rowsPerPlayer - 1;

		int allowedColMin = 0;
		int allowedColMax = totalCols - 1;

		// decide side based on concrete character type or PlayerEnum if exposed
		PlayerEnum player = null;
		if (character instanceof PlayerOneCharacter) {
			player = PlayerEnum.Player_1;
		} else if (character instanceof PlayerTwoCharacter) {
			player = PlayerEnum.Player_2;
		}

		if (player == PlayerEnum.Player_1) {
			allowedColMin = 0;
			allowedColMax = Math.max(0, colsPerPlayer - 1);
		} else if (player == PlayerEnum.Player_2) {
			allowedColMin = Math.max(0, totalCols - colsPerPlayer);
			allowedColMax = totalCols - 1;
		} else {
			// Unknown player type; conservatively allow whole grid
			allowedColMin = 0;
			allowedColMax = totalCols - 1;
		}

		boolean withinCols = newCol >= allowedColMin && newCol <= allowedColMax;
		boolean withinRows = newRow >= topRow && newRow <= bottomRow;

		return withinCols && withinRows;
	}

}
