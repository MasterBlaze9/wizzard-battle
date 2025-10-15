package game.powerups;

import utils.AppColor;

public class PowerUpSpellSpeed extends PowerUp {

    public PowerUpSpellSpeed(int x, int y) {
        super(x, y);
        super.setColor(AppColor.BROWN.toColor());
        super.fill();
    }
}
