package game.powerups;

import utils.AppColor;

public class PowerUpHealth extends PowerUp {
    private PowerUpHealth powerUpHealth;

    public int healthToAdd = 1;


    public PowerUpHealth(int x, int y) {
        super(x, y);

        super.setColor(AppColor.RED.toColor());

        super.fill();
    }


}
