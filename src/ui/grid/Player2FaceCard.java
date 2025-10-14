package ui.grid;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import utils.AppColor;

public class Player2FaceCard extends Rectangle {

    private Rectangle card2;

    public Player2FaceCard(int x, int y, int width, int height) {
        card2 = new Rectangle(x, y, width, height);
     //   card2.setColor(AppColor.BLUE.toColor());
        card2.draw();
    }

}
