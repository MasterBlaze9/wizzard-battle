package ui.grid;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import utils.AppColor;

public class Player1FaceCard extends Rectangle {

    private Rectangle card1;

    public Player1FaceCard(int x, int y, int width, int height) {
        card1 = new Rectangle(x, y, width, height);
//        card1.setColor(AppColor.BLUE.toColor());
        card1.draw();
    }

}
