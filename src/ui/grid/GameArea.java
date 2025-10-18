package ui.grid;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class GameArea {
    private Rectangle gameArea;
    private int areaX;
    private int areaY;
    private int areaWidth;
    private int areaHeight;
    private Picture GameArea;

    public GameArea(String imagePath, int canvasX, int canvasY, int canvasWidth, int canvasHeight) {

        int areaWidth = canvasWidth;
        int areaHeight = canvasHeight / 2;

        int areaX = canvasX;
        // Calculate area Y position to ensure equal top and bottom margins
        int topMargin = (canvasHeight - areaHeight) / 2;
        int areaY = canvasY + topMargin;

        gameArea = new Rectangle(areaX, areaY, areaWidth, areaHeight);

        this.areaX = areaX;
        this.areaY = areaY;
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        this.GameArea = new Picture(areaX, areaY, imagePath);
        GameArea.draw();

    }

    public void translate(int col, int row) {
        gameArea.translate(col, row);
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
