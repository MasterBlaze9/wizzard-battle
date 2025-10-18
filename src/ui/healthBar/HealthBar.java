package ui.healthBar;



import game.PlayerEnum;

import org.academiadecodigo.simplegraphics.pictures.Picture;
import ui.grid.Grid;

public class HealthBar {

    private Life[] lifeCounter;
    private PlayerEnum playerNumber;

    private int numberOfLives;
    private static final int DEFAULT_LIVES = 10;

   
    private static java.util.List<HealthBar> INSTANCES = new java.util.ArrayList<>();

    public HealthBar(PlayerEnum playerNumber) {
        this.playerNumber = playerNumber;
        if (numberOfLives <= 0) {
            numberOfLives = DEFAULT_LIVES;
        }

        if (playerNumber.equals(PlayerEnum.Player_1)) {



            lifeCounter = new Life[numberOfLives];
            for (int i = 0; i < numberOfLives; i++) {
                lifeCounter[i] = new Life(i, true);
            }

        } else {


            lifeCounter = new Life[numberOfLives];
            for (int i = 0; i < numberOfLives; i++) {
                lifeCounter[i] = new Life(i, false);
            }

        }

        // Register this instance for global cleanup
        INSTANCES.add(this);

    }

    public void removeLifePoints(int pointsToRemove) {

        if (pointsToRemove <= 0) {
            return;
        }
        int removed = 0;

        if (playerNumber.equals(PlayerEnum.Player_1)) {

            for (int i = lifeCounter.length - 1; i >= 0 && removed < pointsToRemove; i--) {
                if (lifeCounter[i] != null) {
                    lifeCounter[i].remove();
                    lifeCounter[i] = null;
                    removed++;
                }
            }
        } else {

            for (int i = 0; i < lifeCounter.length && removed < pointsToRemove; i++) {
                if (lifeCounter[i] != null) {
                    lifeCounter[i].remove();
                    lifeCounter[i] = null;
                    removed++;
                }
            }
        }
    }

    public boolean isAlive() {
        for (int i = 0; i < lifeCounter.length; i++) {
            if (lifeCounter[i] != null) {
                return true;
            }
        }
        return false;
    }

    public void addLifePoints() {

        int len = lifeCounter.length;

        int minFilled = Integer.MAX_VALUE;
        int maxFilled = Integer.MIN_VALUE;
        int filledCount = 0;

        for (int i = 0; i < len; i++) {
            if (lifeCounter[i] != null) {
                filledCount++;
                if (i < minFilled) {
                    minFilled = i;
                }
                if (i > maxFilled) {
                    maxFilled = i;
                }
            }
        }

        java.util.function.IntConsumer placeAt = (int idx) -> {
            if (idx >= 0 && idx < len && lifeCounter[idx] == null) {
                if (playerNumber.equals(PlayerEnum.Player_1)) {
                    lifeCounter[idx] = new Life(idx, true);
                } else {
                    lifeCounter[idx] = new Life(idx, false);
                }
            }
        };

        if (filledCount == 0) {

            if (playerNumber.equals(PlayerEnum.Player_1)) {
                placeAt.accept(0);
            } else {
                placeAt.accept(len - 1);
            }
            return;
        }

        if (playerNumber.equals(PlayerEnum.Player_1)) {

            for (int i = maxFilled + 1; i < len; i++) {
                if (lifeCounter[i] == null) {
                    placeAt.accept(i);
                    return;
                }
            }

            for (int i = maxFilled - 1; i >= 0; i--) {
                if (lifeCounter[i] == null) {
                    placeAt.accept(i);
                    return;
                }
            }
        } else {

            for (int i = minFilled - 1; i >= 0; i--) {
                if (lifeCounter[i] == null) {
                    placeAt.accept(i);
                    return;
                }
            }

            for (int i = minFilled + 1; i < len; i++) {
                if (lifeCounter[i] == null) {
                    placeAt.accept(i);
                    return;
                }
            }
        }

        for (int i = 0; i < len; i++) {
            if (lifeCounter[i] == null) {
                placeAt.accept(i);
                return;
            }
        }
    }

    public void cleanup() {
        // Remove all life counters
        if (lifeCounter != null) {
            for (int i = 0; i < lifeCounter.length; i++) {
                if (lifeCounter[i] != null) {
                    lifeCounter[i].remove();
                    lifeCounter[i] = null;
                }
            }
        }

     
    }

    // Static helper to cleanup all health bars
    public static void cleanupAll() {
        try {
            for (HealthBar hb : new java.util.ArrayList<>(INSTANCES)) {
                if (hb != null) {
                    hb.cleanup();
                }
            }
        } finally {
            INSTANCES.clear();
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

                barX = Grid.PADDING + Grid.getWidth() / 8;
            } else {
                barX = Grid.getWidth() / 2 + (Grid.getWidth() / 8 + Grid.PADDING + 15);
            }

            int ellipseY = barY + (barHeight - diameter) / 2;
            ellipseY += 15;

            int totalWidth = numberOfLives * diameter + Math.max(0, numberOfLives - 1) * spacing;

            int startX;
            if (playerOne) {
                startX = barX + Grid.PADDING;
            } else {
                startX = barX + (barWidth - totalWidth) - Grid.PADDING;

            }

            int ellipseX = startX + index * (diameter + spacing);

            life = new Picture(ellipseX, ellipseY * 2, "resources/PowerUps/health.png");
            life.grow(10, 10);
            life.draw();

        }

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
