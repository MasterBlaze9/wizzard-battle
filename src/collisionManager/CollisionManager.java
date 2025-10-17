package collisionManager;

import ui.grid.Grid;
import game.characters.Character;
import game.spells.Spell;
import game.characters.PlayerOneCharacter;
import game.characters.PlayerTwoCharacter;
import game.PlayerEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages collision checks between spells, characters and registered powerups.
 */
public class CollisionManager {

	private Character character;
	private Grid grid;

	// registry of characters in the game for spell collision checks
	private static final List<Character> registeredCharacters = new ArrayList<>();

	// registry of active powerups (use fully-qualified name since implementations
	// may extend graphic primitives from other packages)
	private static final List<game.powerUps.PowerUp> registeredPowerUps = new ArrayList<>();

	public CollisionManager(Character character) {
		this.character = character;
		// register character for global collision checks
		registerCharacter(character);
	}

	/**
	 * Create a CollisionManager that can validate moves against the provided
	 * grid/game area.
	 */
	public CollisionManager(Character character, Grid grid) {
		this(character);
		this.grid = grid;
	}

	/**
	 * Register a character for global collision checks. Safe to call multiple
	 * times.
	 */
	public static void registerCharacter(Character c) {
		if (c == null)
			return;
		if (!registeredCharacters.contains(c)) {
			registeredCharacters.add(c);
		}
	}

	/**
	 * Register a powerup for global lookup.
	 */
	public static void registerPowerUp(game.powerUps.PowerUp p) {
		if (p == null) {
			return;
		}
		if (!registeredPowerUps.contains(p)) {
			registeredPowerUps.add(p);
		}
	}

	/**
	 * Unregister a powerup.
	 */
	public static void unregisterPowerUp(game.powerUps.PowerUp p) {
		if (p == null) {
			return;
		}
		registeredPowerUps.remove(p);
	}

	/**
	 * Return a powerup occupying the given cell, or null if none.
	 */
	public static game.powerUps.PowerUp getPowerUpAt(int col, int row) {
		for (game.powerUps.PowerUp p : registeredPowerUps) {
			if (p == null) {
				continue;
			}
			if (p.getCol() == col && p.getRow() == row) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Checks whether the given spell collides with any registered character.
	 * Returns the character hit, or null if none.
	 */
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

			// ignore same-player hits
			PlayerEnum charPlayer = null;
			if (c instanceof PlayerOneCharacter) {
				charPlayer = PlayerEnum.Player_1;
			} else if (c instanceof PlayerTwoCharacter) {
				charPlayer = PlayerEnum.Player_2;
			}

			if (charPlayer != null && charPlayer == spell.getPlayerEnum()) {
				continue;
			}

			// simple cell-equality collision
			if (sCol == cCol && sRow == cRow) {
				return c;
			}
		}

		return null;
	}

	/**
	 * Checks whether the given spell would collide with any registered character
	 * when moving from fromCol to toCol (inclusive). This handles fast spells
	 * that move more than one cell per tick (prevents tunneling).
	 */
	public static Character getCollidingCharacterAlongPath(Spell spell, int fromCol, int toCol) {
		if (spell == null) {
			return null;
		}

		// convert spell path (fromCol..toCol) into a swept pixel rectangle
		int cell = Grid.CELL_SIZE;
		int spellW = spell.getWidth();

		int startX = Grid.PADDING + fromCol * cell + (cell - spellW) / 2;
		int endX = Grid.PADDING + toCol * cell + (cell - spellW) / 2;

		int sweptX = Math.min(startX, endX);
		int sweptW = Math.abs(endX - startX) + spellW;
		int sweptY = spell.getY();
		int sweptH = spell.getHeight();

		for (Character character : registeredCharacters) {
			if (character == null) {
				continue;
			}

			// ignore same-player hits
			PlayerEnum charPlayer = null;
			if (character instanceof PlayerOneCharacter) {
				charPlayer = PlayerEnum.Player_1;
			} else if (character instanceof PlayerTwoCharacter) {
				charPlayer = PlayerEnum.Player_2;
			}

			if (charPlayer != null && charPlayer == spell.getPlayerEnum()) {
				continue;
			}

			// compute character UI bounding box using CharacterUI centering logic
			int cCol = character.getPosition().getCol();
			int cRow = character.getPosition().getRow();
			int growPadding = Math.max(2, cell / 4);
			int charSize = cell + 2 * growPadding;
			int charX = Grid.PADDING + cCol * cell + (cell - charSize) / 2;
			int charY = Grid.PADDING + cRow * cell + (cell - charSize) / 2;

			// rectangle intersection test between swept rect and character bounding box
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
