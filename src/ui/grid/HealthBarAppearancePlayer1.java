package ui.grid;


import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import utils.AppColor;

public class HealthBarAppearancePlayer1 extends Rectangle {

    private Rectangle healthBar1;

 public HealthBarAppearancePlayer1(int x, int y, int width, int height){
     healthBar1 = new Rectangle(x, y, width, height);
//     healthBar1.setColor(AppColor.BLUE.toColor());
     healthBar1.draw();

 }

}

