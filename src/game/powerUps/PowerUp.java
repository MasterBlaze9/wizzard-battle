package game.powerUps;

import org.academiadecodigo.simplegraphics.pictures.Picture;

import ui.grid.Grid;
import collisionManager.CollisionManager;

public class PowerUp {

    private final int col;
    private final int row;
    private Picture powerUpSquare;

    public PowerUp(int col, int row,String imagePath) {

        this.row = row;
        this.col = col;

        powerUpSquare = new Picture(Grid.PADDING + col * Grid.CELL_SIZE + (Grid.CELL_SIZE - getDefaultSize()) / 2,
                Grid.PADDING + row * Grid.CELL_SIZE + (Grid.CELL_SIZE - getDefaultSize()) / 2,imagePath);
        CollisionManager.registerPowerUp(this);
        powerUpSquare.draw();
    }

    private static int getDefaultSize() {

        return Grid.CELL_SIZE;
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


}