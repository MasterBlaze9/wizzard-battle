package ui.grid;

import game.PlayerEnum;
import game.powerUps.PowerUp;
import game.powerUps.PowerUpDamage;
import game.powerUps.PowerUpHealth;
import game.powerUps.PowerUpSpellSpeed;
import org.academiadecodigo.simplegraphics.pictures.Picture;
import ui.faceCard.PlayerFaceCard;

import java.util.Random;

public class Grid {

    public static final int PADDING = 10;
    public static final int DEFAULT_CELL_SIZE = 5;
    public static int CELL_SIZE;
    public static final int POWER_UP_SPAWN_DELAY_SECONDS = 8;
    public static final int POWER_UP_BUFF_DURATION_SECONDS = 10;
    public static final int POWER_UP_PICKUP_RADIUS_CELLS = 1;
    public static final int EXTRA_HIT_BOX_PADDING_CHAR_PIXELS = 12;
    public static final int EXTRA_HIT_BOX_PADDING_SPELL_PIXELS = 8;
    public static final int SPELL_VERTICAL_OFFSET_BASE = 35;
    public static final int SPELL_VERTICAL_OFFSET_P2 = 40;
    public static final int SPELL_HAND_TUNING = 6;
    private static int cols;
    private static int rows;
    private int targetWidth = 0;
    private int targetHeight = 0;

    private int cellSize = 0;

    private static Picture canvas;
    private GameArea gameArea;

    private static PowerUp leftPowerUp;
    private static PowerUp rightPowerUp;

    private PlayerFaceCard card1;
    private PlayerFaceCard card2;

    private static Grid activeGrid;
    // Controls whether power-ups are allowed to spawn. True while a game is active.
    private static volatile boolean powerUpSpawningEnabled = false;

    private int dividerWidth = 10;

    private static String player1FacePath;
    private static String player2FacePath;

    public Grid(int cols, int rows) {
        Grid.cols = cols;
        Grid.rows = rows;
        this.cellSize = DEFAULT_CELL_SIZE;
    }

    public Grid(int cols, int rows, int targetWidth, int targetHeight) {
        Grid.cols = cols;
        Grid.rows = rows;
        this.targetWidth = Math.max(0, targetWidth - 2 * PADDING);
        this.targetHeight = Math.max(0, targetHeight - 2 * PADDING);

        int sizeByWidth = this.targetWidth / Math.max(1, cols);
        int sizeByHeight = this.targetHeight / Math.max(1, rows);
        this.cellSize = Math.max(1, Math.min(sizeByWidth, sizeByHeight));
    }

    public PlayerFaceCard getCard1() {
        return card1;
    }

    public PlayerFaceCard getCard2() {
        return card2;
    }

    public void init() {
        int usedCellSize = cellSize > 0 ? cellSize : DEFAULT_CELL_SIZE;

        CELL_SIZE = usedCellSize;

        canvas = new Picture(PADDING, PADDING, "resources/backgroun2.png");
        canvas.draw();

        gameArea = new GameArea("resources/GameArea.png", canvas.getX(), canvas.getY(), canvas.getWidth(),
                canvas.getHeight());

        gameArea.translate(0, 0);

        // Debug: print computed logical/pixel bounds to help verify top/bottom symmetry
        int usedCellSizeDbg = getCellSize();
        int areaHdbg = gameArea.getAreaHeight();
        int rowsInAreadbg = Math.max(0, areaHdbg / usedCellSizeDbg);
        int topRowDbg = getGameAreaTopRow();
        int bottomRowDbg = topRowDbg + getMaxRowsPerPlayer() - 1;
        int areaPixelTopDbg = PADDING + topRowDbg * usedCellSizeDbg;
        int areaPixelBottomDbg = PADDING + (bottomRowDbg + 1) * usedCellSizeDbg;
        System.out.println(String.format(
                "[GRID DEBUG] rows=%d cellSize=%d areaH=%d rowsInArea=%d topRow=%d bottomRow=%d areaPixelTop=%d areaPixelBottom=%d",
                rows, usedCellSizeDbg, areaHdbg, rowsInAreadbg, topRowDbg, bottomRowDbg, areaPixelTopDbg,
                areaPixelBottomDbg));

        // choose two distinct face images and inject into the face card constructors
        java.util.List<String> facePaths = new java.util.ArrayList<>();
        facePaths.add("resources/Faces/Carolina.png");
        facePaths.add("resources/Faces/Pascoa.png");
        facePaths.add("resources/Faces/Rolo.png");
        // remove duplicates and shuffle
        facePaths = new java.util.ArrayList<>(new java.util.LinkedHashSet<>(facePaths));
        if (facePaths.size() < 2) {
            throw new IllegalStateException("Not enough distinct face images; need at least 2 distinct faces");
        }
        java.util.Collections.shuffle(facePaths);

        String face1 = facePaths.get(0);
        String face2 = facePaths.get(1);

        player1FacePath = face1;
        player2FacePath = face2;

        card1 = new PlayerFaceCard(PlayerEnum.Player_1, face1, 85, 70,
                canvas.getWidth() / 8, canvas.getHeight() / 4);

        card2 = new PlayerFaceCard(PlayerEnum.Player_2, face2,
                canvas.getWidth() - 135, 70,
                canvas.getWidth() / 8, canvas.getHeight() / 4);

        activeGrid = this;
        powerUpSpawningEnabled = true;

        new Thread(() -> {
            try {
                Thread.sleep(POWER_UP_SPAWN_DELAY_SECONDS * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            if (powerUpSpawningEnabled) {
                spawnPowerUpsOnce();
            }
        }).start();

    }

    /**
     * Returns the current active Grid instance (set during init) or null if
     * no grid has been initialized yet.
     */
    public static Grid getActiveGrid() {
        return activeGrid;
    }

    private void spawnPowerUpsOnce() {
        if (!powerUpSpawningEnabled) {
            return;
        }
        Random random = new Random();

        int topRow = getGameAreaTopRow();
        int rowsPerPlayer = getMaxRowsPerPlayer();

        int leftCols = getMaxColsPerPlayer();
        int leftCol = 0;
        if (leftCols > 0) {
            leftCol = random.nextInt(leftCols);
        }

        int rightCols = getMaxColsPerPlayer();
        int rightStart = Math.max(0, getCols() - rightCols);
        int rightCol = rightStart;
        if (rightCols > 0) {
            rightCol = rightStart + random.nextInt(rightCols);
        }

        int leftRow = topRow;
        if (rowsPerPlayer > 0) {
            leftRow = topRow + random.nextInt(rowsPerPlayer);
        }

        int rightRow = topRow;
        if (rowsPerPlayer > 0) {
            rightRow = topRow + random.nextInt(rowsPerPlayer);
        }

        int leftType = random.nextInt(3);
        switch (leftType) {
            case 0:
                leftPowerUp = new PowerUpDamage(leftCol, leftRow);
                break;
            case 1:
                leftPowerUp = new PowerUpHealth(leftCol, leftRow);
                break;
            default:
                leftPowerUp = new PowerUpSpellSpeed(leftCol, leftRow);
                break;
        }

        int rightType = random.nextInt(3);
        switch (rightType) {
            case 0:
                rightPowerUp = new PowerUpDamage(rightCol, rightRow);
                break;
            case 1:
                rightPowerUp = new PowerUpHealth(rightCol, rightRow);
                break;
            default:
                rightPowerUp = new PowerUpSpellSpeed(rightCol, rightRow);
                break;
        }
    }

    public static void onPowerUpCollected(PowerUp powerUp) {
        if (powerUp == null) {
            return;
        }
        if (!powerUpSpawningEnabled) {
            return;
        }
        boolean wasLeft = false;
        boolean wasRight = false;
        if (powerUp == leftPowerUp) {
            leftPowerUp = null;
            wasLeft = true;
        }
        if (powerUp == rightPowerUp) {
            rightPowerUp = null;
            wasRight = true;
        }

        final boolean respawnLeft = wasLeft;
        final boolean respawnRight = wasRight;

        new Thread(() -> {
            try {
                Thread.sleep(POWER_UP_SPAWN_DELAY_SECONDS * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (!powerUpSpawningEnabled) {
                return;
            }

            if (respawnLeft) {
                spawnPowerUpForSide(true);
            }
            if (respawnRight) {
                spawnPowerUpForSide(false);
            }
        }).start();
    }

    private static void spawnPowerUpForSide(boolean left) {
        try {

            if (activeGrid == null) {
                return;
            }
            if (!powerUpSpawningEnabled) {
                return;
            }

            Random random = new Random();

            int topRow = activeGrid.getGameAreaTopRow();
            int rowsPerPlayer = activeGrid.getMaxRowsPerPlayer();

            int col = 0;
            int colsForSide = activeGrid.getMaxColsPerPlayer();
            if (colsForSide > 0) {
                if (left) {
                    col = random.nextInt(colsForSide);
                } else {
                    int rightStart = Math.max(0, getCols() - colsForSide);
                    col = rightStart + random.nextInt(colsForSide);
                }
            }

            int row = topRow;
            if (rowsPerPlayer > 0) {
                row = topRow + random.nextInt(rowsPerPlayer);
            }

            int type = random.nextInt(3);
            if (left) {
                switch (type) {
                    case 0:
                        leftPowerUp = new PowerUpDamage(col, row);
                        break;
                    case 1:
                        leftPowerUp = new PowerUpHealth(col, row);
                        break;
                    default:
                        leftPowerUp = new PowerUpSpellSpeed(col, row);
                        break;
                }
            } else {
                switch (type) {
                    case 0:
                        rightPowerUp = new PowerUpDamage(col, row);
                        break;
                    case 1:
                        rightPowerUp = new PowerUpHealth(col, row);
                        break;
                    default:
                        rightPowerUp = new PowerUpSpellSpeed(col, row);
                        break;
                }
            }
        } catch (Exception ignored) {
        }
    }

    public int getMaxRowsPerPlayer() {
        int usedCellSize = getCellSize();
        if (gameArea != null) {

            int areaH = gameArea.getAreaHeight();
            int rowsInArea = Math.max(0, areaH / usedCellSize);

            // rowsInArea should not exceed the total logical rows available
            rowsInArea = Math.min(rowsInArea, rows);

            // Ensure the remaining rows (outside the game area) split evenly
            // so the top limit equals the bottom limit. If the difference is
            // odd, reduce rowsInArea by one (if possible) to make it even.
            if ((rows - rowsInArea) % 2 != 0 && rowsInArea > 0) {
                rowsInArea--;
            }

            return Math.max(0, rowsInArea);
        }

        int rowsInArea = Math.max(0, rows / 2);
        if ((rows - rowsInArea) % 2 != 0 && rowsInArea > 0) {
            rowsInArea--;
        }
        return Math.max(0, rowsInArea);
    }

    public int getMaxColsPerPlayer() {
        int usedCellSize = getCellSize();
        if (gameArea != null) {
            int areaW = gameArea.getAreaWidth();
            int available = Math.max(0, areaW - dividerWidth);

            return Math.max(0, (available / 2) / usedCellSize);
        }

        int dividerInCells = Math.max(0, dividerWidth / Math.max(1, getCellSize()));
        return Math.max(0, (cols - dividerInCells) / 2);
    }

    public int getCellSize() {
        return cellSize > 0 ? cellSize : DEFAULT_CELL_SIZE;
    }

    public static int getCols() {
        return cols;
    }

    public static int getRows() {
        return rows;
    }

    public int getGameAreaTopRow() {
        int usedCellSize = getCellSize();
        if (gameArea != null) {
            int areaH = gameArea.getAreaHeight();
            int rowsInArea = Math.max(0, areaH / usedCellSize);

            // rowsInArea should not exceed the total logical rows available
            rowsInArea = Math.min(rowsInArea, rows);

            // Match the parity logic used in getMaxRowsPerPlayer so the top
            // and bottom margins are equal.
            if ((rows - rowsInArea) % 2 != 0 && rowsInArea > 0) {
                rowsInArea--;
            }

            return Math.max(0, (rows - rowsInArea) / 2);
        }

        int rowsInArea = Math.max(0, rows / 2);
        return Math.max(0, (rows - rowsInArea) / 2);
    }

    /**
     * Returns the number of logical rows that the game area occupies.
     * This centralizes the parity/rounding logic so callers get a consistent
     * value for both top and bottom calculations.
     */
    public int getGameAreaRows() {
        int usedCellSize = getCellSize();
        if (gameArea != null) {
            int areaH = gameArea.getAreaHeight();
            int rowsInArea = Math.max(0, areaH / usedCellSize);

            // rowsInArea should not exceed the total logical rows available
            rowsInArea = Math.min(rowsInArea, rows);

            // Ensure symmetric margins by adjusting parity if necessary
            if ((rows - rowsInArea) % 2 != 0 && rowsInArea > 0) {
                rowsInArea--;
            }

            return Math.max(0, rowsInArea);
        }

        int rowsInArea = Math.max(0, rows / 2);
        if ((rows - rowsInArea) % 2 != 0 && rowsInArea > 0) {
            rowsInArea--;
        }
        return Math.max(0, rowsInArea);
    }

    /**
     * Returns the bottom logical row index (inclusive) of the game area.
     */
    public int getGameAreaBottomRow() {
        int top = getGameAreaTopRow();
        int rowsInArea = getGameAreaRows();
        return Math.max(0, top + rowsInArea - 1);
    }

    /**
     * Prints verbose debug information about the computed game-area bounds
     * and cell sizing so callers can verify parity and placement.
     */
    public void dumpGameAreaDebug() {
        int usedCellSize = getCellSize();
        int areaH = gameArea != null ? gameArea.getAreaHeight() : 0;
        int areaW = gameArea != null ? gameArea.getAreaWidth() : 0;
        int rowsInArea = getGameAreaRows();
        int topRow = getGameAreaTopRow();
        int bottomRow = getGameAreaBottomRow();
        int colsPerPlayer = getMaxColsPerPlayer();

        System.out.println(String.format(
                "[GRID DEBUG VERBOSE] cols=%d rows=%d cell=%d areaW=%d areaH=%d rowsInArea=%d topRow=%d bottomRow=%d colsPerPlayer=%d",
                cols, rows, usedCellSize, areaW, areaH, rowsInArea, topRow, bottomRow, colsPerPlayer));
    }

    public static int getWidth() {
        return canvas.getWidth();
    }

    public static int getHeight() {
        return canvas.getHeight();
    }

    public int getX() {
        return canvas.getX();
    }

    public int getY() {
        return canvas.getY();
    }

    public static String getPlayerFacePath(PlayerEnum player) {
        if (player == PlayerEnum.Player_1) {
            return player1FacePath;
        } else {
            return player2FacePath;
        }
    }

    public static void clearAll() {
        if (activeGrid != null) {
            try {
                powerUpSpawningEnabled = false;
                if (canvas != null) {
                    canvas.delete();
                    canvas = null;
                }
                if (activeGrid.gameArea != null) {
                    if (activeGrid.gameArea.GameArea != null) {
                        activeGrid.gameArea.GameArea.delete();
                    }
                    activeGrid.gameArea = null;
                }
                if (activeGrid.card1 != null) {
                    activeGrid.card1.hide();
                    activeGrid.card1 = null;
                }
                if (activeGrid.card2 != null) {
                    activeGrid.card2.hide();
                    activeGrid.card2 = null;
                }
                if (leftPowerUp != null) {
                    leftPowerUp.removeFromGame();
                    leftPowerUp = null;
                }
                if (rightPowerUp != null) {
                    rightPowerUp.removeFromGame();
                    rightPowerUp = null;
                }
                activeGrid = null;
            } catch (Exception ignored) {
            }
        }
    }

}
