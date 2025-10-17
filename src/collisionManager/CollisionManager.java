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

	public static void registerCharacter(Character character) {
		if (character == null)
			return;
		if (!registeredCharacters.contains(character)) {
			registeredCharacters.add(character);
		}
	}

	public static void registerPowerUp(PowerUp powerUp) {
		if (powerUp == null) {
			return;
		}
		if (!registeredPowerUps.contains(powerUp)) {
			registeredPowerUps.add(powerUp);
		}
	}

	public static void unregisterPowerUp(PowerUp powerUp) {
		if (powerUp == null) {
			return;
		}
		registeredPowerUps.remove(powerUp);
	}

	public static PowerUp getPowerUpAt(int col, int row) {
		int radius = Grid.POWER_UP_PICKUP_RADIUS_CELLS;
		for (PowerUp p : registeredPowerUps) {
			if (p == null) {
				continue;
			}

			int pCol = p.getCol();
			int pRow = p.getRow();

			if (Math.abs(pCol - col) <= radius && Math.abs(pRow - row) <= radius) {
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

		for (Character character : registeredCharacters) {
			if (character == null)
				continue;

			int characterCol = character.getPosition().getCol();
			int characterRow = character.getPosition().getRow();

			PlayerEnum charPlayer = null;
			if (character instanceof PlayerOneCharacter) {
				charPlayer = PlayerEnum.Player_1;
			} else if (character instanceof PlayerTwoCharacter) {
				charPlayer = PlayerEnum.Player_2;
			}

			if (charPlayer != null && charPlayer == spell.getPlayerEnum()) {
				continue;
			}

			if (sCol == characterCol && sRow == characterRow) {
				return character;
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

			int characterCol = character.getPosition().getCol();
			int characterRow = character.getPosition().getRow();

			if (characterRow == effectiveSpellRow && characterCol >= minCol && characterCol <= maxCol) {
				return character;
			}

			int spellW = spell.getWidth();
			int startX = Grid.PADDING + fromCol * cell + (cell - spellW) / 2;
			int endX = Grid.PADDING + toCol * cell + (cell - spellW) / 2;
			int sweptX = Math.min(startX, endX);
			int sweptW = Math.abs(endX - startX) + spellW;
			int sweptY = spellPixelY;
			int sweptH = spell.getHeight();

			int growPadding = Math.max(2, cell / 4);
			int charSize = cell + 2 * growPadding;
			int charX = Grid.PADDING + characterCol * cell + (cell - charSize) / 2;
			int charY = Grid.PADDING + characterRow * cell + (cell - charSize) / 2 + Grid.CELL_SIZE + 5;

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
