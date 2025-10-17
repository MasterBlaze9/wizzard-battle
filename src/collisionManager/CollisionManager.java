package collisionManager;

import ui.grid.Grid;
import game.characters.Character;
import game.spells.Spell;
import game.characters.PlayerOneCharacter;
import game.characters.PlayerTwoCharacter;
import game.powerUps.PowerUp;
import game.PlayerEnum;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

	private Character character;
	private Grid grid;

	private static final List<Character> registeredCharacters = new ArrayList<>();
	private static final List<PowerUp> registeredPowerUps = new ArrayList<>();

	public CollisionManager(Character character) {
		this.character = character;
		registerCharacter(character);
	}

	public CollisionManager(Character character, Grid grid) {
		this(character);
		this.grid = grid;
	}

	public static void registerCharacter(Character c) {
		if (c == null)
			return;
		if (!registeredCharacters.contains(c)) {
			registeredCharacters.add(c);
		}
	}

	public static void registerPowerUp(PowerUp p) {
		if (p == null) {
			return;
		}
		if (!registeredPowerUps.contains(p)) {
			registeredPowerUps.add(p);
		}
	}

	public static void unregisterPowerUp(game.powerUps.PowerUp p) {
		if (p == null) {
			return;
		}
		registeredPowerUps.remove(p);
	}

	public static PowerUp getPowerUpAt(int col, int row) {
		for (PowerUp p : registeredPowerUps) {
			if (p == null) {
				continue;
			}
			if (p.getCol() == col && p.getRow() == row) {
				return p;
			}
		}
		return null;
	}

	public static Character getCollidingCharacter(Spell spell) {
		if (spell == null) {
			return null;
		}

		int sCol = spell.getPosition().getCol();
		int sRow = spell.getPosition().getRow();

		for (Character c : registeredCharacters) {
			if (c == null)
				continue;

			int cCol = c.getPosition().getCol();
			int cRow = c.getPosition().getRow();

		
			PlayerEnum charPlayer = null;
			if (c instanceof PlayerOneCharacter) {
				charPlayer = PlayerEnum.Player_1;
			} else if (c instanceof PlayerTwoCharacter) {
				charPlayer = PlayerEnum.Player_2;
			}

			if (charPlayer != null && charPlayer == spell.getPlayerEnum()) {
				continue;
			}

		
			if (sCol == cCol && sRow == cRow) {
				return c;
			}
		}

		return null;
	}


	public static Character getCollidingCharacterAlongPath(Spell spell, int fromCol, int toCol) {
		if (spell == null) {
			return null;
		}


		int cell = Grid.CELL_SIZE;
		int spellPixelY = spell.getY();
		int spellLogicalRow = spell.getPosition().getRow();
		int spellRowBasePixel = Grid.PADDING + spellLogicalRow * cell;

		int verticalOffsetPixels = spellPixelY - spellRowBasePixel;

		int verticalOffsetCells = Math.round((float) verticalOffsetPixels / (float) cell);
		int effectiveSpellRow = spellLogicalRow + verticalOffsetCells;

	
		int minCol = Math.min(fromCol, toCol);
		int maxCol = Math.max(fromCol, toCol);

		for (Character character : registeredCharacters) {
			if (character == null) {
				continue;
			}

		
			PlayerEnum charPlayer = null;
			if (character instanceof PlayerOneCharacter) {
				charPlayer = PlayerEnum.Player_1;
			} else if (character instanceof PlayerTwoCharacter) {
				charPlayer = PlayerEnum.Player_2;
			}

			if (charPlayer != null && charPlayer == spell.getPlayerEnum()) {
				continue;
			}

			int cCol = character.getPosition().getCol();
			int cRow = character.getPosition().getRow();

		
			if (cRow == effectiveSpellRow && cCol >= minCol && cCol <= maxCol) {
				return character;
			}

			
			int spellW = spell.getWidth();
			int startX = Grid.PADDING + fromCol * cell + (cell - spellW) / 2 ;
			int endX = Grid.PADDING + toCol * cell + (cell - spellW) / 2;
			int sweptX = Math.min(startX, endX);
			int sweptW = Math.abs(endX - startX) + spellW;
			int sweptY = spellPixelY;
			int sweptH = spell.getHeight();

			int growPadding = Math.max(2, cell / 4);
			int charSize = cell + 2 * growPadding;
			int charX = Grid.PADDING + cCol * cell + (cell - charSize) / 2;
			int charY = Grid.PADDING + cRow * cell + (cell - charSize) / 2 + Grid.CELL_SIZE + 5;

			if (sweptX < charX + charSize && sweptX + sweptW > charX && sweptY < charY + charSize
					&& sweptY + sweptH > charY) {
				return character;
			}
		}

		return null;
	}

	public boolean checkGameAreaColision(int newCol, int newRow) {

		int totalCols = Grid.getCols();
		int totalRows = Grid.getRows();

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
