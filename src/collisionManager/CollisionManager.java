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

	// Enable to print collision debug info
	private static final boolean DEBUG_COLLISIONS = true;

	public static boolean isDebugEnabled() {
		return DEBUG_COLLISIONS;
	}

	public static void registerCharacter(Character character) {
		if (character == null) {
			return;
		}
		if (!registeredCharacters.contains(character)) {
			registeredCharacters.add(character);
		}
	}

	public CollisionManager(Character character) {
		this.character = character;
		registerCharacter(character);
	}

	public CollisionManager(Character character, Grid grid) {
		this(character);
		this.grid = grid;
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
		if (DEBUG_COLLISIONS) {
			System.out.println(String.format("[COLLIDE DEBUG] getPowerUpAt query col=%d row=%d radius=%d registered=%d",
					col, row, radius, registeredPowerUps.size()));
		}
		for (PowerUp powerUp : registeredPowerUps) {
			if (powerUp == null) {
				continue;
			}

			int pCol = powerUp.getCol();
			int pRow = powerUp.getRow();

			if (Math.abs(pCol - col) <= radius && Math.abs(pRow - row) <= radius) {
				if (DEBUG_COLLISIONS) {
					System.out.println(String.format("[COLLIDE DEBUG] getPowerUpAt HIT powerUp col=%d row=%d",
							pCol, pRow));
				}
				return powerUp;
			}
		}
		return null;
	}

	/**
	 * Find the first power-up encountered along a straight grid path from
	 * (fromCol,fromRow)
	 * to (toCol,toRow). This handles multi-cell moves so characters don't pass
	 * through
	 * power-ups when their movement speed > 1.
	 *
	 * The search checks cells in movement order (from the source towards the
	 * destination)
	 * and returns the first matching power-up (respecting pickup radius).
	 */
	public static PowerUp getPowerUpAlongPath(int fromCol, int fromRow, int toCol, int toRow) {
		// simple single-cell case
		if (fromCol == toCol && fromRow == toRow) {
			return getPowerUpAt(toCol, toRow);
		}

		int dCol = toCol - fromCol;
		int dRow = toRow - fromRow;
		int steps = Math.max(Math.abs(dCol), Math.abs(dRow));
		int stepCol = Integer.signum(dCol);
		int stepRow = Integer.signum(dRow);

		if (DEBUG_COLLISIONS) {
			System.out.println(String.format("[COLLIDE DEBUG] getPowerUpAlongPath from=(%d,%d) to=(%d,%d) steps=%d",
					fromCol, fromRow, toCol, toRow, steps));
		}
		for (int i = 1; i <= steps; i++) {
			int c = fromCol + stepCol * i;
			int r = fromRow + stepRow * i;
			if (DEBUG_COLLISIONS) {
				System.out.println(String.format("[COLLIDE DEBUG] checking path cell (%d,%d)", c, r));
			}
			PowerUp p = getPowerUpAt(c, r);
			if (p != null) {
				if (DEBUG_COLLISIONS) {
					System.out.println(String.format("[COLLIDE DEBUG] getPowerUpAlongPath HIT at (%d,%d)", c, r));
				}
				return p;
			}
		}

		return null;
	}

	/**
	 * Find a power-up whose picture overlaps the character's picture bounds.
	 * This is a fallback when logical grid coords don't match (e.g., different
	 * coordinate spaces). Returns the first overlapping power-up or null.
	 */
	public static PowerUp getPowerUpOverlappingCharacter(Character character) {
		if (character == null) {
			return null;
		}

		int cX = character.getPixelX();
		int cY = character.getPixelY();
		int cW = character.getPixelWidth();
		int cH = character.getPixelHeight();

		if (DEBUG_COLLISIONS) {
			System.out.println(String.format(
					"[COLLIDE DEBUG] checking overlap char pixel=(%d,%d) size=(%d,%d) registeredPowerUps=%d",
					cX, cY, cW, cH, registeredPowerUps.size()));
		}

		for (PowerUp p : registeredPowerUps) {
			if (p == null)
				continue;

			int pX = p.getPixelX();
			int pY = p.getPixelY();
			int pW = p.getPixelWidth();
			int pH = p.getPixelHeight();

			if (pX < cX + cW && pX + pW > cX && pY < cY + cH && pY + pH > cY) {
				if (DEBUG_COLLISIONS) {
					System.out
							.println(String.format("[COLLIDE DEBUG] overlap HIT powerUp logical=(%d,%d) pixel=(%d,%d)",
									p.getCol(), p.getRow(), pX, pY));
				}
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

		if (DEBUG_COLLISIONS) {
			System.out.println(String.format(
					"[COLLIDE DEBUG] spell logicalCol=%d logicalRow=%d effectiveRow=%d pathFrom=%d pathTo=%d minCol=%d maxCol=%d",
					spell.getPosition().getCol(), spell.getPosition().getRow(), effectiveSpellRow, fromCol, toCol,
					minCol, maxCol));
		}

		for (Character character : registeredCharacters) {
			if (character == null) {
				continue;
			}

			if (DEBUG_COLLISIONS) {
				System.out.println(String.format("[COLLIDE DEBUG] checking character logicalCol=%d logicalRow=%d",
						character.getPosition().getCol(), character.getPosition().getRow()));
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
			// Use exact previous and current picture X positions to compute the swept area.
			int prevX = spell.getPrevX();
			int currX = spell.getX();
			int sweptX = Math.min(prevX, currX);
			int sweptW = Math.abs(currX - prevX) + spellW;
			int sweptY = spell.getY();
			int sweptH = spell.getHeight();

			if (DEBUG_COLLISIONS) {
				System.out.println(
						String.format("[COLLIDE DEBUG] spell prevX=%d currX=%d sweptX=%d sweptW=%d sweptY=%d sweptH=%d",
								prevX, currX, sweptX, sweptW, sweptY, sweptH));
			}

			// Use the character's actual image bounds for collision.
			int charX = character.getPixelX();
			int charY = character.getPixelY();
			int charSizeW = character.getPixelWidth();
			int charSizeH = character.getPixelHeight();

			// expand swept rectangle a bit so small spells reliably hit
			int spellExtra = Grid.EXTRA_HIT_BOX_PADDING_SPELL_PIXELS;
			// also add a vertical cell padding so spells that are 1 row off still hit
			int verticalCellPad = Math.max(1, cell / 2);
			int sX = sweptX - spellExtra;
			int sY = sweptY - spellExtra - verticalCellPad;
			int sW = sweptW + 2 * spellExtra;
			int sH = sweptH + 2 * spellExtra + verticalCellPad * 2;

			if (sX < charX + charSizeW && sX + sW > charX && sY < charY + charSizeH && sY + sH > charY) {
				if (DEBUG_COLLISIONS) {
					System.out.println(
							String.format("[COLLIDE DEBUG] HIT char col=%d row=%d charX=%d charY=%d charW=%d charH=%d",
									characterCol, characterRow, charX, charY, charSizeW, charSizeH));
				}
				return character;
			} else if (DEBUG_COLLISIONS) {
				System.out.println(
						String.format("[COLLIDE DEBUG] MISS char col=%d row=%d charX=%d charY=%d charW=%d charH=%d",
								characterCol, characterRow, charX, charY, charSizeW, charSizeH));
			}
		}

		return null;
	}

	public boolean checkGameAreaCollision(int newCol, int newRow) {

		int totalCols = Grid.getCols();
		int totalRows = Grid.getRows();

		int colsPerPlayer = grid.getMaxColsPerPlayer();
		int rowsPerPlayer = grid.getMaxRowsPerPlayer();

		// Compute logical bounds using Grid so parity/rounding stays consistent.
		// Use Grid's own helpers to ensure top/bottom and row counts match.
		int topRow = grid != null ? grid.getGameAreaTopRow() : (totalRows - rowsPerPlayer) / 2;
		int bottomRow = grid != null ? grid.getGameAreaBottomRow() : topRow + rowsPerPlayer - 1;

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

		// Additional safety: compute the character's future pixel hitbox based on
		// the same grid/game-area calculations used by `Grid` so logical and pixel
		// checks remain consistent.
		if (grid != null && character != null) {
			try {
				int cell = Grid.CELL_SIZE;
				int charH = character.getPixelHeight();
				int charW = character.getPixelWidth();

				int currentRow = character.getPosition().getRow();
				int deltaRows = newRow - currentRow;
				int currentPixelY = character.getPixelY();
				int newPixelY = currentPixelY + deltaRows * cell;
				int pixelBottom = newPixelY + charH;

				int currentCol = character.getPosition().getCol();
				int deltaCols = newCol - currentCol;
				int currentPixelX = character.getPixelX();
				int newPixelX = currentPixelX + deltaCols * cell;
				int pixelRight = newPixelX + charW;

				int areaPixelTop = Grid.PADDING + topRow * cell;
				int areaPixelBottom = Grid.PADDING + (bottomRow + 1) * cell;

				int charExtra = Grid.EXTRA_HIT_BOX_PADDING_CHAR_PIXELS;

				int allowedColMinPixel = Grid.PADDING + allowedColMin * cell;
				int allowedColMaxPixel = Grid.PADDING + (allowedColMax + 1) * cell;

				// Debug: log what the boundaries are
				if (DEBUG_COLLISIONS && (newRow == topRow || newRow == bottomRow)) {
					System.out.println(String.format(
							"[COLLIDE DEBUG] Boundary check: newRow=%d topRow=%d bottomRow=%d newPixelY=%d pixelBottom=%d areaPixelTop=%d areaPixelBottom=%d charH=%d charExtra=%d",
							newRow, topRow, bottomRow, newPixelY, pixelBottom, areaPixelTop, areaPixelBottom, charH,
							charExtra));
				}

				int adjustedAreaTop = areaPixelTop - Math.max(0, charExtra / 2);
				int adjustedAreaBottom = areaPixelBottom + Math.max(0, charExtra / 2);

				// Top edge must be >= adjusted top, Bottom edge must be <= adjusted bottom
				if (newPixelY < adjustedAreaTop || pixelBottom > adjustedAreaBottom) {
					return false;
				}

				int adjustedAllowedColMinPixel = allowedColMinPixel - Math.max(0, charExtra / 2);
				int adjustedAllowedColMaxPixel = allowedColMaxPixel + Math.max(0, charExtra / 2);

				if (newPixelX < adjustedAllowedColMinPixel || pixelRight > adjustedAllowedColMaxPixel) {
					return false;
				}
			} catch (Exception ignored) {
				// fallback to logical checks only
			}
		}

		return withinCols && withinRows;

	}

	/**
	 * Debug helper: prints registered characters and power-ups.
	 */
	public static void dumpState() {
		if (!DEBUG_COLLISIONS) {
			System.out.println("[COLLIDE DEBUG] dumpState called but DEBUG_COLLISIONS=false");
			return;
		}

		System.out.println("[COLLIDE DEBUG] --- CollisionManager STATE DUMP ---");
		System.out.println(String.format("[COLLIDE DEBUG] Registered characters: %d", registeredCharacters.size()));
		for (Character c : registeredCharacters) {
			if (c == null)
				continue;
			try {
				System.out.println(String.format("[COLLIDE DEBUG] Char pos col=%d row=%d pixel=(%d,%d) size=(%d,%d)",
						c.getPosition().getCol(), c.getPosition().getRow(), c.getPixelX(), c.getPixelY(),
						c.getPixelWidth(), c.getPixelHeight()));
			} catch (Exception ignored) {
			}
		}

		System.out.println(String.format("[COLLIDE DEBUG] Registered powerUps: %d", registeredPowerUps.size()));
		for (PowerUp p : registeredPowerUps) {
			if (p == null)
				continue;
			try {
				System.out.println(String.format("[COLLIDE DEBUG] PowerUp col=%d row=%d", p.getCol(), p.getRow()));
			} catch (Exception ignored) {
			}
		}

		// If the Grid was initialized, print its verbose debug info so we see the
		// exact cell/area/top/bottom computations in the same dump output.
		try {
			Grid g = Grid.getActiveGrid();
			if (g != null) {
				g.dumpGameAreaDebug();
			}
		} catch (Exception ignored) {
		}

		System.out.println("[COLLIDE DEBUG] --- END STATE DUMP ---");
	}

	public static void clearAll() {
		try {
			// Hide character sprites
			for (Character c : new java.util.ArrayList<>(registeredCharacters)) {
				if (c != null) {
					try {
						c.cleanupOnGameOver();
					} catch (Exception ignored) {
					}
				}
			}
			// Remove any on-screen spell pictures
			try {
				game.spells.Spell.cleanupAll();
			} catch (Exception ignored) {
			}
			// Remove any on-screen power-up pictures
			try {
				game.powerUps.PowerUp.cleanupAll();
			} catch (Exception ignored) {
			}
		} finally {
			registeredCharacters.clear();
			registeredPowerUps.clear();
		}
	}

}
