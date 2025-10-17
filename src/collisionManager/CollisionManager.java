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
			int spellExtra = Grid.EXTRA_HITBOX_PADDING_SPELL_PIXELS;
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
