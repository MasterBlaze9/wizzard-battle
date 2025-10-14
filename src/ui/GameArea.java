package ui;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import utils.AppColor;

/**
 * Represents the in-game area that sits inside the grid canvas. The GameArea
 * will be centered vertically inside the provided canvas rectangle.
 */
public class GameArea extends Rectangle {
    private Rectangle gameArea;
    private int areaX;
    private int areaY;
    private int areaWidth;
    private int areaHeight;

    /**
     * Create a game area that fits inside the provided canvas bounds and is
     * vertically centered.
     *
     * @param canvasX      left X coordinate of the canvas
     * @param canvasY      top Y coordinate of the canvas
     * @param canvasWidth  width of the canvas in pixels
     * @param canvasHeight height of the canvas in pixels
     */
    public GameArea(int canvasX, int canvasY, int canvasWidth, int canvasHeight) {
        // We'll make the game area half the height of the canvas by default
        int areaWidth = canvasWidth;
        int areaHeight = canvasHeight / 2;

        // compute top-left so the area is vertically centered inside the canvas
        int areaX = canvasX;
        int areaY = canvasY + (canvasHeight - areaHeight) / 2;

        gameArea = new Rectangle(areaX, areaY, areaWidth, areaHeight);
        this.areaX = areaX;
        this.areaY = areaY;
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;

        gameArea.setColor(AppColor.GREEN.toColor());
        gameArea.fill();
    }

    public int getAreaX() {
        return areaX;
    }

    public int getAreaY() {
        return areaY;
    }

    public int getAreaWidth() {
        return areaWidth;
    }

    public int getAreaHeight() {
        return areaHeight;
    }
}
