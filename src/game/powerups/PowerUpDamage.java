package game.powerUps;

import utils.AppColor;

public class PowerUpDamage extends PowerUp {

    public PowerUpDamage(int col, int row) {
        super(col, row);
        super.setColor(AppColor.YELLOW.toColor());
        super.fill();
    }
}
