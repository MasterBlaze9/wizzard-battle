package ui.grid;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import ui.GameArea;
import ui.position.Line;
import utils.AppColor;

public class Grid {

    public static final int PADDING = 10;
    // Single shared cell size so all "pixels" (grid cells) are the same size.
    public static final int CELL_SIZE = 5;
    /**
     * Scale (0..1) used by character UI to determine how large a character is
     * relative to a single grid cell. Exposed here so it can be tuned project-wide.
     */
    private static double CHARACTER_SCALE = 0.9;
    private int cols;
    private int rows;

    private Rectangle canvas;

    /**
     * Simple graphics grid constructor with a certain number of rows and columns
     *
     * @param cols number of the columns
     * @param rows number of rows
     */
    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
    }

    /**
     * Initializes the canvas simple graphics rectangle and draws it
     */
    public void init() {
        canvas = new Rectangle(PADDING, PADDING, cols * CELL_SIZE, rows * CELL_SIZE);
        canvas.setColor(AppColor.BROWN.toColor());
        canvas.fill();
        GameArea gameArea = new GameArea( (double) getRows() /4, getWidth(), (double) getHeight() /2 );
        Line line = new Line(0 , 10, getHeight() /2);
    }

    public int getCellSize() {
        return CELL_SIZE;
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
