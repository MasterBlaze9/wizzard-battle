package ui.grid;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import org.academiadecodigo.simplegraphics.pictures.Picture;
import utils.AppColor;


public class GameArea extends Rectangle{
    private Rectangle gameArea;
    private int areaX;
    private int areaY;
    private int areaWidth;
    private int areaHeight;
    private Picture GameArea;
    
    public GameArea(String imagePath,int canvasX, int canvasY, int canvasWidth, int canvasHeight) {
      
        int areaWidth = canvasWidth;
        int areaHeight = canvasHeight / 2;

        int areaX = canvasX;
        int areaY = canvasY + (canvasHeight - areaHeight) / 2;


        gameArea = new Rectangle(areaX, areaY, areaWidth, areaHeight);
        this.areaX = areaX;
        this.areaY = areaY;
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        this.GameArea = new Picture(areaX, areaY, imagePath);
        GameArea.draw();


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
