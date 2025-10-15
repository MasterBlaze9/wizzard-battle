package game.powerUps;

import utils.AppColor;

public class PowerUpSpellSpeed extends PowerUp {

    public PowerUpSpellSpeed(int col, int row) {
        super(col, row);
        super.setColor(AppColor.BROWN.toColor());
        super.fill();
    }
}
