package game.powerUps;

import org.academiadecodigo.simplegraphics.pictures.Picture;

import ui.grid.Grid;
import collisionManager.CollisionManager;

public class PowerUp {

    private final int col;
    private final int row;
    private Picture powerUpSquare;
    // Track instances so we can cleanup any leftover images on game-over
    private static final java.util.List<PowerUp> ACTIVE = new java.util.ArrayList<>();

    public PowerUp(int col, int row, String imagePath) {

        this.row = row;
        this.col = col;

        powerUpSquare = new Picture(Grid.PADDING + col * Grid.CELL_SIZE + (Grid.CELL_SIZE - getDefaultSize()) / 2,
                Grid.PADDING + row * Grid.CELL_SIZE + (Grid.CELL_SIZE - getDefaultSize()) / 2, imagePath);
        // draw first so we can read pixel bounds
        powerUpSquare.draw();
        CollisionManager.registerPowerUp(this);
        synchronized (ACTIVE) {
            ACTIVE.add(this);
        }
        try {
            System.out
                    .println(String.format("[COLLIDE DEBUG] PowerUp created logical=(%d,%d) pixel=(%d,%d) size=(%d,%d)",
                            col, row, powerUpSquare.getX(), powerUpSquare.getY(), powerUpSquare.getWidth(),
                            powerUpSquare.getHeight()));
        } catch (Exception ignored) {
        }
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

    public int getPixelX() {
        try {
            return powerUpSquare.getX();
        } catch (Exception e) {
            return Grid.PADDING + col * Grid.CELL_SIZE;
        }
    }

    public int getPixelY() {
        try {
            return powerUpSquare.getY();
        } catch (Exception e) {
            return Grid.PADDING + row * Grid.CELL_SIZE;
        }
    }

    public int getPixelWidth() {
        try {
            return powerUpSquare.getWidth();
        } catch (Exception e) {
            return getDefaultSize();
        }
    }

    public int getPixelHeight() {
        try {
            return powerUpSquare.getHeight();
        } catch (Exception e) {
            return getDefaultSize();
        }
    }

    public static void cleanupAll() {
        java.util.List<PowerUp> snapshot;
        synchronized (ACTIVE) {
            snapshot = new java.util.ArrayList<>(ACTIVE);
        }
        for (PowerUp p : snapshot) {
            if (p != null) {
                try {
                    p.delete();
                } catch (Exception ignored) {
                }
            }
        }
        synchronized (ACTIVE) {
            ACTIVE.clear();
        }
    }
}