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


	public boolean checkGameAreaColision(int newCol, int newRow) {
		

		int totalCols = grid.getCols();
		int totalRows = grid.getRows();

		int colsPerPlayer = grid.getMaxColsPerPlayer();
		int rowsPerPlayer = grid.getMaxRowsPerPlayer();

		
		int topRow = (totalRows - rowsPerPlayer) / 2;
		int bottomRow = topRow + rowsPerPlayer - 1;

		int allowedColMin = 0;
		int allowedColMax = totalCols - 1;

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
			
			allowedColMin = 0;
			allowedColMax = totalCols - 1;
		}

		boolean withinCols = newCol >= allowedColMin && newCol <= allowedColMax;
		boolean withinRows = newRow >= topRow && newRow <= bottomRow;

		return withinCols && withinRows;
	}

}
