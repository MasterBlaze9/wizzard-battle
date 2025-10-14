package ui.grid;

import org.academiadecodigo.simplegraphics.graphics.Ellipse;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import utils.AppColor;

public class Grid {

    public static final int PADDING = 10;

    public static final int DEFAULT_CELL_SIZE = 5;

    public static int CELL_SIZE = DEFAULT_CELL_SIZE;

    private static double CHARACTER_SCALE = 0.9;
    private static int cols;
    private static int rows;

    private int targetWidth = 0;
    private int targetHeight = 0;

    private int cellSize = 0;

    private Rectangle canvas;
    private GameArea gameArea;
    private Line line;
    private HealthBarAppearancePlayer1 healthBar1;
    private HealthBarAppearancePlayer2 healthBar2;
    private Player1FaceCard card1;
    private Player2FaceCard card2;
    private Life life;
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

        healthBar1 = new HealthBarAppearancePlayer1(PADDING+canvas.getWidth() / 8, PADDING, canvas.getWidth()/ 4 ,dividerWidth * 5);

        healthBar2 = new HealthBarAppearancePlayer2(canvas.getWidth() /2 + (canvas.getWidth()/ 8 + PADDING), PADDING, canvas.getWidth()/4 , dividerWidth * 5) ;


        card1 = new Player1FaceCard(PADDING, PADDING, canvas.getWidth() / 8, canvas.getHeight() / 4);


        card2 = new Player2FaceCard(canvas.getWidth() - (canvas.getWidth()/8 -PADDING), PADDING, canvas.getWidth() / 8, canvas.getHeight() / 4);

        life = new Life(PADDING+canvas.getWidth() / 8, PADDING, CELL_SIZE * 2 ,CELL_SIZE * 2);
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

    public int getWidth() {
        return canvas.getWidth();
    }

    public int getHeight() {
        return canvas.getHeight();
    }

    public int getX() {
        return canvas.getX();
    }

    public int getY() {
        return canvas.getY();
    }

}
