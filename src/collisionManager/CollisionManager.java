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
		
		for (PowerUp powerUp : registeredPowerUps) {
			if (powerUp == null) {
				continue;
			}

			int pCol = powerUp.getCol();
			int pRow = powerUp.getRow();

			if (Math.abs(pCol - col) <= radius && Math.abs(pRow - row) <= radius) {
			
				return powerUp;
			}
		}
		return null;
	}

	
	public static PowerUp getPowerUpAlongPath(int fromCol, int fromRow, int toCol, int toRow) {
		
		if (fromCol == toCol && fromRow == toRow) {
			return getPowerUpAt(toCol, toRow);
		}

		int dCol = toCol - fromCol;
		int dRow = toRow - fromRow;
		int steps = Math.max(Math.abs(dCol), Math.abs(dRow));
		int stepCol = Integer.signum(dCol);
		int stepRow = Integer.signum(dRow);

	
		for (int i = 1; i <= steps; i++) {
			int c = fromCol + stepCol * i;
			int r = fromRow + stepRow * i;
			
			PowerUp p = getPowerUpAt(c, r);
			if (p != null) {
			
				return p;
			}
		}

		return null;
	}

	public static PowerUp getPowerUpOverlappingCharacter(Character character) {
		if (character == null) {
			return null;
		}

		int cX = character.getPixelX();
		int cY = character.getPixelY();
		int cW = character.getPixelWidth();
		int cH = character.getPixelHeight();

		

		for (PowerUp p : registeredPowerUps) {
			if (p == null)
				continue;

			int pX = p.getPixelX();
			int pY = p.getPixelY();
			int pW = p.getPixelWidth();
			int pH = p.getPixelHeight();

			if (pX < cX + cW && pX + pW > cX && pY < cY + cH && pY + pH > cY) {
			
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
	
			int prevX = spell.getPrevX();
			int currX = spell.getX();
			int sweptX = Math.min(prevX, currX);
			int sweptW = Math.abs(currX - prevX) + spellW;
			int sweptY = spell.getY();
			int sweptH = spell.getHeight();

		

			
			int charX = character.getPixelX();
			int charY = character.getPixelY();
			int charSizeW = character.getPixelWidth();
			int charSizeH = character.getPixelHeight();

			
			int spellExtra = Grid.EXTRA_HIT_BOX_PADDING_SPELL_PIXELS;
	
			int verticalCellPad = Math.max(1, cell / 2);
			int sX = sweptX - spellExtra;
			int sY = sweptY - spellExtra - verticalCellPad;
			int sW = sweptW + 2 * spellExtra;
			int sH = sweptH + 2 * spellExtra + verticalCellPad * 2;

			if (sX < charX + charSizeW && sX + sW > charX && sY < charY + charSizeH && sY + sH > charY) {
			
				return character;
			} 
		}

		return null;
	}

	public boolean checkGameAreaCollision(int newCol, int newRow) {

		int totalCols = Grid.getCols();
		int totalRows = Grid.getRows();

		int colsPerPlayer = grid.getMaxColsPerPlayer();
		int rowsPerPlayer = grid.getMaxRowsPerPlayer();

		
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

				int adjustedAreaTop = areaPixelTop - Math.max(0, charExtra / 2);
				int adjustedAreaBottom = areaPixelBottom + Math.max(0, charExtra / 2);

				
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
		// Debug method - intentionally empty after log removal
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
