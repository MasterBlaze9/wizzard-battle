package game.powerups;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Powerup {

    private Rectangle powerUp;

    public Powerup(int x, int y) {
        powerUp = new Rectangle(x, y, 25, 25);
        powerUp.draw();
    }

}
