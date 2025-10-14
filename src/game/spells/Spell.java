package game.spells;

import game.PlayerEnum;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import ui.position.Position;
import utils.AppColor;

public class Spell extends Rectangle {

    private Position position;
    private int speed;
    private int damage;
    private PlayerEnum playerEnum;
    private AppColor color;

    public Spell(int row, int col, PlayerEnum playerEnum) {
        position = new Position(col, row);
        speed = 1;
        damage = 1;
        this.playerEnum = playerEnum;
        if (playerEnum.equals(PlayerEnum.Player_1)) {
            color = AppColor.RED;
        } else {
            color = AppColor.YELLOW;
        }

//        Rectangle spell = new Rectangle(position.getCol(), position.getRow(), 20,5);
//        spell.setColor(color.toColor());
//        spell.draw();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Position getPosition() {
        return position;
    }
}


