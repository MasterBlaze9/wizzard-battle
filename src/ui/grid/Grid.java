package ui.grid;

import game.powerups.PowerUp;
import game.powerups.PowerUpDamage;
import game.powerups.PowerUpHealth;
import game.powerups.PowerUpSpellSpeed;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import ui.character.FaceCard.Player1FaceCard;
import ui.character.FaceCard.Player2FaceCard;
import ui.character.HealthBar.HealthBar;

import utils.AppColor;

import java.util.Random;

public class Grid {

    public static final int PADDING = 10;
    public static final int DEFAULT_CELL_SIZE = 5;
    public static int CELL_SIZE = DEFAULT_CELL_SIZE;

    private static int cols;
    private static int rows;
    private int targetWidth = 0;
    private int targetHeight = 0;

    private int cellSize = 0;

    private static Rectangle canvas;
    private GameArea gameArea;
    private Line line;
    private PowerUpDamage powerUpDamageLeft;
    private PowerUpHealth powerUpHealthLeft;
    private PowerUpSpellSpeed powerUpSpellSpeedLeft;
    private PowerUpDamage powerUpDamageRight;
    private PowerUpHealth powerUpHealthRight;
    private PowerUpSpellSpeed powerUpSpellSpeedRight;
    private Player1FaceCard card1;
    private Player2FaceCard card2;

    private int dividerWidth = 10;

    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        this.cellSize = DEFAULT_CELL_SIZE;
    }

    public Grid(int cols, int rows, int targetWidth, int targetHeight) {
        this.cols = cols;
        this.rows = rows;
        this.targetWidth = Math.max(0, targetWidth - 2 * PADDING);
        this.targetHeight = Math.max(0, targetHeight - 2 * PADDING);

        int sizeByWidth = this.targetWidth / Math.max(1, cols);
        int sizeByHeight = this.targetHeight / Math.max(1, rows);
        this.cellSize = Math.max(1, Math.min(sizeByWidth, sizeByHeight));
    }

    public void init() {
        int usedCellSize = cellSize > 0 ? cellSize : DEFAULT_CELL_SIZE;

        CELL_SIZE = usedCellSize;
        canvas = new Rectangle(PADDING, PADDING, cols * usedCellSize, rows * usedCellSize);
        canvas.setColor(AppColor.BROWN.toColor());
        canvas.fill();

        gameArea = new GameArea(canvas.getX(), canvas.getY(), canvas.getWidth(), canvas.getHeight());


        int areaW = gameArea.getAreaWidth();
        int areaH = gameArea.getAreaHeight();

        int lineX = (areaW - dividerWidth) / 2;
        int lineY = gameArea.getAreaY();
        line = new Line(lineX, lineY, dividerWidth, areaH);

        gameArea.translate(0, 0);
        line.translate(0, 0);


        card1 = new Player1FaceCard("resources/testeCarinha.png", PADDING *2 + PADDING / 2
                , PADDING*2 + PADDING, canvas.getWidth() / 8, canvas.getHeight() / 4);

        card2 = new Player2FaceCard(canvas.getWidth() - (canvas.getWidth() / 8 - PADDING), PADDING, canvas.getWidth() / 8, canvas.getHeight() / 4);



        // Calculate divider and area bounds
        //int line2X = gameArea.getAreaX() + (gameArea.getAreaWidth() - dividerWidth) / 2;
        int leftMinX = gameArea.getAreaX();
        int leftMaxX = lineX;
        int rightMinX = lineX + dividerWidth;
        int rightMaxX = gameArea.getAreaX() + gameArea.getAreaWidth();
        int minY = gameArea.getAreaY();
        int maxY = gameArea.getAreaY() + gameArea.getAreaHeight();
        int powerUpSize = 25;
        Random random = new Random();

// Left side power-ups
        PowerUpHealth powerUpHealthLeft = new PowerUpHealth(
                random.nextInt(leftMaxX - leftMinX - powerUpSize) + leftMinX,
                random.nextInt(maxY - minY - powerUpSize) + minY
        );
        PowerUpDamage powerUpDamageLeft = new PowerUpDamage(
                random.nextInt(leftMaxX - leftMinX - powerUpSize) + leftMinX,
                random.nextInt(maxY - minY - powerUpSize) + minY
        );
        PowerUpSpellSpeed powerUpSpellSpeedLeft = new PowerUpSpellSpeed(
                random.nextInt(leftMaxX - leftMinX - powerUpSize) + leftMinX,
                random.nextInt(maxY - minY - powerUpSize) + minY
        );

// Right side power-ups
        PowerUpHealth powerUpHealthRight = new PowerUpHealth(
                random.nextInt(rightMaxX - rightMinX - powerUpSize) + rightMinX,
                random.nextInt(maxY - minY - powerUpSize) + minY
        );
        PowerUpDamage powerUpDamageRight = new PowerUpDamage(
                random.nextInt(rightMaxX - rightMinX - powerUpSize) + rightMinX,
                random.nextInt(maxY - minY - powerUpSize) + minY
        );
        PowerUpSpellSpeed powerUpSpellSpeedRight = new PowerUpSpellSpeed(
                random.nextInt(rightMaxX - rightMinX - powerUpSize) + rightMinX,
                random.nextInt(maxY - minY - powerUpSize) + minY
        );
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
