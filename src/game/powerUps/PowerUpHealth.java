package game.powerUps;

import utils.AppColor;

public class PowerUpHealth extends PowerUp {

    public int healthToAdd = 1;

    public PowerUpHealth(int col, int row) {
        super(col, row,"resources/PowerUps/health.png");
    }

}
