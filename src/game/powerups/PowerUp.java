package game.powerUps;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import ui.grid.Grid;
import collisionManager.CollisionManager;

public class PowerUp {

    private final int col;
    private final int row;

    private Rectangle powerUpSquare;

    public PowerUp(int col, int row) {
        this.col = col;
        this.row = row;
        powerUpSquare = new Rectangle(col, row, getDefaultSize(), getDefaultSize());
        CollisionManager.registerPowerUp(this);
    }

    private static int getDefaultSize() {

        return  Grid.CELL_SIZE;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void removeFromGame() {
        try {
            delete();
        } catch (Exception ignored) {
        }
        CollisionManager.unregisterPowerUp(this);

        try {
            Grid.onPowerUpCollected(this);
        } catch (Exception ignored) {
        }
    }

    public void delete() {
        powerUpSquare.delete();
    }

    public void setColor(Color color) {
        powerUpSquare.setColor(color);
    }

    public void fill() {
        powerUpSquare.fill();
    }

}
