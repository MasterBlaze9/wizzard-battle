package game.powerups;

import utils.AppColor;

public class PowerUpDamage extends PowerUp {

    public PowerUpDamage(int x, int y) {
        super(x, y);
        super.setColor(AppColor.YELLOW.toColor());
        super.fill();
    }
}
