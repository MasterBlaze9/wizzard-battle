package ui.position;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import utils.AppColor;

public class Line extends Rectangle {

    private Rectangle line;
    private Position position;


    public Line(double col,double width,double height){
        line = new Rectangle(325, 73,width,height*1.3);
        line.setColor(AppColor.BLUE.toColor());
        line.fill();

    }


}
