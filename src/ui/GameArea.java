package ui;

import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import ui.grid.Grid;
import ui.position.Position;
import utils.AppColor;

public class GameArea extends Rectangle {
    private Rectangle gameArea;
    private Position position;

    public GameArea(double startRow, double width, double height){
        gameArea = new Rectangle(10, 10, width, height*1.3);

        gameArea.setColor(AppColor.GREEN.toColor());
        gameArea.translate(0,startRow*3.5);
        gameArea.fill();


    }
}
