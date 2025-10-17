package ui.HealthBar;

import org.academiadecodigo.simplegraphics.graphics.Ellipse;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import game.PlayerEnum;

import ui.grid.Grid;
import utils.AppColor;

public class HealthBar {

    private Rectangle playerOneHealthBar;
    private Rectangle playerTwoHealthBar;
    private Life[] lifeCounter;
    private PlayerEnum playerNumber;

    private int numberOfLifes;
    private static final int DEFAULT_LIFES = 3;

    public HealthBar(PlayerEnum playerNumber) {
        this.playerNumber = playerNumber;
        if (numberOfLifes <= 0) {
            numberOfLifes = DEFAULT_LIFES;
        }

        if (playerNumber.equals(PlayerEnum.Player_1)) {

            playerOneHealthBar = new Rectangle(Grid.PADDING + Grid.getWidth() / 8, Grid.PADDING, Grid.getWidth() / 4,
                    Grid.PADDING * 5);
            playerOneHealthBar.draw();

            lifeCounter = new Life[numberOfLifes];
            for (int i = 0; i < numberOfLifes; i++) {
                lifeCounter[i] = new Life(i, true);
            }

        } else {
            playerTwoHealthBar = new Rectangle(Grid.getWidth() / 2 + (Grid.getWidth() / 8 + Grid.PADDING), Grid.PADDING, Grid.getWidth() / 4, Grid.PADDING * 5);
            playerTwoHealthBar.draw();

            lifeCounter = new Life[numberOfLifes];
            for (int i = 0; i < numberOfLifes; i++) {
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

        private Ellipse life;

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
                barX = Grid.getWidth() / 2 + (Grid.getWidth() / 8 + Grid.PADDING);
            }

            // center vertically inside the health bar
            int ellipseY = barY + (barHeight - diameter) / 2;

            // compute total width of all life ellipses and spacing
            int totalWidth = numberOfLifes * diameter + Math.max(0, numberOfLifes - 1) * spacing;

            // startX: player one should start at the leftmost inset; player two centered
            int startX;
            if (playerOne) {
                startX = barX + Grid.PADDING;
            } else {
                startX = barX + (barWidth - totalWidth) - Grid.PADDING;
                if (startX < barX + 4) {

                    startX = barX + 4;
                }
            }

            int ellipseX = startX + index * (diameter + spacing);

            life = new Ellipse(ellipseX, ellipseY, diameter, diameter);
            life.setColor(AppColor.RED.toColor());
            life.fill();

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
