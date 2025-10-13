package ui.grid;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import ui.GameArea;
import ui.position.Line;
import utils.AppColor;

public class Grid {

    public static final int PADDING = 10;
    
    public static final int DEFAULT_CELL_SIZE = 5;

    public static int CELL_SIZE = DEFAULT_CELL_SIZE;

    private static double CHARACTER_SCALE = 0.9;
    private int cols;
    private int rows;

    private int targetWidth = 0;
    private int targetHeight = 0;

    private int cellSize = 0;

    private Rectangle canvas;
    private GameArea gameArea;
    private Line line;

   
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

        // compute maximum possible cell size that fits both dimensions
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
     
        int areaX = gameArea.getAreaX();
        int areaY = gameArea.getAreaY();
        int areaW = gameArea.getAreaWidth();
        int areaH = gameArea.getAreaHeight();

        int lineWidth = 10; // pixels
        int lineX = areaX + (areaW - lineWidth) / 2;
        int lineY = areaY;
        line = new Line(lineX, lineY, lineWidth, areaH);

      
        gameArea.translate(0, 0);
        line.translate(0, 0);
    }

    public int getCellSize() {
        return cellSize > 0 ? cellSize : DEFAULT_CELL_SIZE;
    }

    public static double getCharacterScale() {
        return CHARACTER_SCALE;
    }

    public static void setCharacterScale(double scale) {
        if (scale <= 0 || scale > 1) {
            throw new IllegalArgumentException("scale must be > 0 and <= 1");
        }
        CHARACTER_SCALE = scale;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
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
