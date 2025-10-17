package ui.grid;

import game.PlayerEnum;
import game.powerUps.PowerUp;
import game.powerUps.PowerUpDamage;
import game.powerUps.PowerUpHealth;
import game.powerUps.PowerUpSpellSpeed;
import org.academiadecodigo.simplegraphics.pictures.Picture;
import ui.faceCard.PlayerFaceCard;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import utils.AppColor;

import java.util.Random;

public class Grid {

    public static final int PADDING = 10;
    public static final int DEFAULT_CELL_SIZE = 5;
    public static int CELL_SIZE;

    public static final int POWERUP_SPAWN_DELAY_SECONDS = 8;
    public static final int POWERUP_BUFF_DURATION_SECONDS = 10;

    public static final int POWERUP_PICKUP_RADIUS_CELLS = 1;

    private static int cols;
    private static int rows;
    private int targetWidth = 0;
    private int targetHeight = 0;

    private int cellSize = 0;

    private static Picture canvas;
    private GameArea gameArea;
    private Line line;

    private static PowerUp leftPowerUp;
    private static PowerUp rightPowerUp;

    private PlayerFaceCard card1;
    private PlayerFaceCard card2;

    private static Grid activeGrid;

    private int dividerWidth = 10;

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

        // keep the static CELL_SIZE in sync with the grid's computed cell size
        CELL_SIZE = usedCellSize;

        canvas = new Picture(PADDING, PADDING, "resources/backgroun2.png");
        canvas.draw();

        gameArea = new GameArea("resources/gameArea.png", canvas.getX(), canvas.getY(), canvas.getWidth(),canvas.getHeight());


        int areaW = gameArea.getAreaWidth();
        int areaH = gameArea.getAreaHeight();

        int lineX = (areaW - dividerWidth) / 2;
        int lineY = gameArea.getAreaY();
        line = new Line(lineX, lineY, dividerWidth, areaH);

        gameArea.translate(0, 0);
        line.translate(0, 0);

        card1 = new PlayerFaceCard(PlayerEnum.Player_1, 85, 70,
                canvas.getWidth() / 8, canvas.getHeight() / 4);

        card2 = new PlayerFaceCard(PlayerEnum.Player_2,
                canvas.getWidth() - 135, 70,
                canvas.getWidth() / 8, canvas.getHeight() / 4);

        activeGrid = this;

        new Thread(() -> {
            try {
                Thread.sleep(POWERUP_SPAWN_DELAY_SECONDS * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            spawnPowerUpsOnce();
        }).start();

    }

    private void spawnPowerUpsOnce() {
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

        // spawn left random powerup
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

        // spawn right random powerup
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

    /**
     * Called by a powerup when it is collected/removed so Grid can forget
     * references.
     */
    public static void onPowerUpCollected(PowerUp powerUp) {
        if (powerUp == null) {
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
                Thread.sleep(POWERUP_SPAWN_DELAY_SECONDS * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
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

            return Math.max(0, gameArea.getAreaHeight() / usedCellSize);
        }

        return Math.max(0, rows / 2);
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
            return Math.max(0, (rows - rowsInArea) / 2);
        }

        int rowsInArea = Math.max(0, rows / 2);
        return Math.max(0, (rows - rowsInArea) / 2);
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

}
