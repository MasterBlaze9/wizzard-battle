package ui.healthBar;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import game.PlayerEnum;

import org.academiadecodigo.simplegraphics.pictures.Picture;
import ui.grid.Grid;

public class HealthBar {

    private Rectangle playerOneHealthBar;
    private Rectangle playerTwoHealthBar;
    private Life[] lifeCounter;
    private PlayerEnum playerNumber;

    private int numberOfLives;
    private static final int DEFAULT_LIVES = 3;

    public HealthBar(PlayerEnum playerNumber) {
        this.playerNumber = playerNumber;
        if (numberOfLives <= 0) {
            numberOfLives = DEFAULT_LIVES;
        }

        if (playerNumber.equals(PlayerEnum.Player_1)) {

            playerOneHealthBar = new Rectangle(Grid.PADDING + Grid.getWidth() / 8, Grid.PADDING, Grid.getWidth() / 4,
                    Grid.PADDING * 5);
            playerOneHealthBar.draw();

            lifeCounter = new Life[numberOfLives];
            for (int i = 0; i < numberOfLives; i++) {
                lifeCounter[i] = new Life(i, true);
            }

        } else {
            playerTwoHealthBar = new Rectangle(Grid.getWidth() / 2 + (Grid.getWidth() / 8 + Grid.PADDING), Grid.PADDING,
                    Grid.getWidth() / 4, Grid.PADDING * 5);
            playerTwoHealthBar.draw();

            lifeCounter = new Life[numberOfLives];
            for (int i = 0; i < numberOfLives; i++) {
                lifeCounter[i] = new Life(i, false);
            }

        }

    }

    public void removeLifePoints(int pointsToRemove) {
        // defensive: nothing to do for non-positive removal
        if (pointsToRemove <= 0) {
            return;
        }
        int removed = 0;

        if (playerNumber.equals(PlayerEnum.Player_1)) {
            // Player 1: remove from rightmost to leftmost
            for (int i = lifeCounter.length - 1; i >= 0 && removed < pointsToRemove; i--) {
                if (lifeCounter[i] != null) {
                    lifeCounter[i].remove(); // deletes the ellipse from the screen
                    lifeCounter[i] = null;
                    removed++;
                }
            }
        } else {
            // Player 2: remove from leftmost to rightmost
            for (int i = 0; i < lifeCounter.length && removed < pointsToRemove; i++) {
                if (lifeCounter[i] != null) {
                    lifeCounter[i].remove(); // deletes the ellipse from the screen
                    lifeCounter[i] = null;
                    removed++;
                }
            }
        }
    }

    public void addLifePoints() {
        // Restore a single life according to the player's direction
        if (playerNumber.equals(PlayerEnum.Player_1)) {
            // Player 1: restore rightmost empty slot first
            for (int i = lifeCounter.length - 1; i >= 0; i--) {
                if (lifeCounter[i] == null) {
                    lifeCounter[i] = new Life(i, true);
                    break;
                }
            }
        } else {
            // Player 2: restore leftmost empty slot first
            for (int i = 0; i < lifeCounter.length; i++) {
                if (lifeCounter[i] == null) {
                    lifeCounter[i] = new Life(i, false);
                    break;
                }
            }
        }
    }

    class Life {

        private Picture life;

        public Life(int index, boolean playerOne) {

            int diameter = Grid.CELL_SIZE * 3;
            int spacing = Grid.CELL_SIZE;

            int barX;
            int barY = Grid.PADDING;
            int barHeight = Grid.PADDING * 5;
            int barWidth = Grid.getWidth() / 4;

            if (playerOne) {
                // match the x used when creating playerOneHealthBar
                barX = Grid.PADDING + Grid.getWidth() / 8;
            } else {
                barX = Grid.getWidth() / 2 + (Grid.getWidth() / 8 + Grid.PADDING + 15);
            }

            // center vertically inside the health bar
            int ellipseY = barY + (barHeight - diameter) / 2;
            ellipseY += 15;

            // compute total width of all life ellipses and spacing
            int totalWidth = numberOfLives * diameter + Math.max(0, numberOfLives - 1) * spacing;

            // startX: player one should start at the leftmost inset; player two centered
            int startX;
            if (playerOne) {
                startX = barX + Grid.PADDING;
            } else {
                startX = barX + (barWidth - totalWidth) - Grid.PADDING;

            }

            int ellipseX = startX + index * (diameter + spacing);

            life = new Picture(ellipseX, ellipseY, "resources/PowerUps/health.png");
            life.grow(10, 10);
            life.draw();

        }

        /**
         * Remove the visual ellipse from the screen and mark this Life as removed.
         */
        public void remove() {
            if (life != null) {
                try {
                    life.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                life = null;
            }
        }

    }

}
