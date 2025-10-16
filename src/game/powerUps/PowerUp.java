package game.powerUps;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import ui.grid.Grid;
import utils.AppColor;
import collisionManager.CollisionManager;

/**
 * PowerUp positioned in logical grid cells (col,row).
 * Subclasses should use the (col,row) constructor.
 */
public class PowerUp extends Rectangle {

    private final int col;
    private final int row;

    public PowerUp(int col, int row) {
        super(
                Grid.PADDING + col * Grid.CELL_SIZE + (Grid.CELL_SIZE - getDefaultSize()) / 2,
                Grid.PADDING + row * Grid.CELL_SIZE + (Grid.CELL_SIZE - getDefaultSize()) / 2,
                getDefaultSize(),
                getDefaultSize());

        this.col = col;
        this.row = row;

        setColor(AppColor.RED.toColor());
        fill();

        // register for global lookup
        CollisionManager.registerPowerUp(this);
    }

    private static int getDefaultSize() {
        // make powerup visually at least the cell size or 12 px
        return Math.max(12, Grid.CELL_SIZE);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    /**
     * Remove visual and unregister from collision manager.
     */
    public void removeFromGame() {
        try {
            this.delete();
        } catch (Exception ignored) {
        }
        CollisionManager.unregisterPowerUp(this);
        // notify grid that this powerup is collected/removed so it can clear references
        try {
            Grid.onPowerUpCollected(this);
        } catch (Exception ignored) {
        }
    }

}
