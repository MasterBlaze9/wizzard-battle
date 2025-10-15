package game.powerups;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import utils.AppColor;

import java.util.Random;

public class PowerUp extends Rectangle{


    private Rectangle powerUp;

    public PowerUp(int x, int y) {
        super(x, y, 25, 25);
       setColor(AppColor.RED.toColor());
       fill();
    }

    Random random = new Random();






}
