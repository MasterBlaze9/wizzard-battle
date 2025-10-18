package game.spells;

import game.PlayerEnum;

import org.academiadecodigo.simplegraphics.pictures.Picture;

import ui.position.Position;
import ui.grid.Grid;

import game.characters.Character;

import collisionManager.CollisionManager;

public class Spell {

    private Picture spell;
    private Position position;
    // track previous picture X to compute exact swept rectangle during movement
    private int prevX;
    private int speed;
    private int damage;
    private PlayerEnum playerEnum;

    // Track all created spells so we can cleanup on game-over
    private static final java.util.List<Spell> ACTIVE = new java.util.ArrayList<>();

    public Spell(int row, int col, PlayerEnum playerEnum) {
        int baseX = Grid.PADDING + col * Grid.CELL_SIZE
                + (Grid.CELL_SIZE - Math.max(6, Grid.CELL_SIZE * 2)) / 2;

        int baseY = Grid.PADDING + row * Grid.CELL_SIZE
                + (Grid.CELL_SIZE - Math.max(3, Grid.CELL_SIZE / 2)) / 2
                - Grid.EXTRA_HIT_BOX_PADDING_CHAR_PIXELS - Grid.SPELL_VERTICAL_OFFSET_BASE;

        // Per-player vertical tuning: allow a different offset for Player 2
        if (playerEnum.equals(PlayerEnum.Player_2)) {
            baseY = Grid.PADDING + row * Grid.CELL_SIZE
                    + (Grid.CELL_SIZE - Math.max(3, Grid.CELL_SIZE / 2)) / 2
                    - Grid.EXTRA_HIT_BOX_PADDING_CHAR_PIXELS + Grid.SPELL_VERTICAL_OFFSET_P2;
        }

        // adjust horizontal spawn so each player fires from their (opposite) hand
        int handOffset = Grid.CELL_SIZE / 2 + Grid.SPELL_HAND_TUNING; // tuning offset: half a cell plus a few pixels
        // Player 1 fires from the right-hand side, Player 2 from the left-hand side
        int spawnX = baseX + (playerEnum.equals(PlayerEnum.Player_1) ? handOffset : -handOffset);

        if (playerEnum.equals(PlayerEnum.Player_1)) {
            spell = new Picture(spawnX, baseY, "resources/Spells/spell.png");
        } else {
            spell = new Picture(spawnX, baseY, "resources/Spells/spell2.png");
        }
        spell.draw();
        prevX = spell.getX();

        speed = 2;
        damage = 1;
        this.playerEnum = playerEnum;

        position = new Position(col, row);

        final int dir = playerEnum.equals(PlayerEnum.Player_1) ? 1 : -1;

        synchronized (ACTIVE) {
            ACTIVE.add(this);
        }

        new Thread(() -> {
            try {
                while (true) {

                    int currentCol = position.getCol();
                    int desiredNext = currentCol + dir * speed;

                    if (dir > 0 && desiredNext >= Grid.getCols()) {
                        int cellsToEdge = Math.max(0, Grid.getCols() - 1 - currentCol);
                        if (cellsToEdge > 0) {
                            try {
                                translate(dir * Grid.CELL_SIZE * cellsToEdge, 0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException ignored) {
                            Thread.currentThread().interrupt();
                        }

                        safeDelete();

                        break;
                    }

                    if (dir < 0 && desiredNext < 0) {
                        int cellsToEdge = Math.max(0, currentCol);
                        if (cellsToEdge > 0) {
                            try {
                                translate(dir * Grid.CELL_SIZE * cellsToEdge, 0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException ignored) {
                            Thread.currentThread().interrupt();
                        }

                        safeDelete();

                        break;
                    }

                    try {

                        Character hit = CollisionManager.getCollidingCharacterAlongPath(this, currentCol + dir,
                                desiredNext);
                        if (hit != null) {

                            int hitCol = hit.getPosition().getCol();
                            int cellsToHit = Math.abs(hitCol - currentCol);
                            if (cellsToHit > 0) {
                                try {
                                    translate(dir * Grid.CELL_SIZE * cellsToHit, 0);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                position.setCol(hitCol);
                            }
                            hit.takeDamage(damage);
                            System.out.println("Spell hit character: " + hit.getClass().getSimpleName());
                            safeDelete();
                            break;
                        }
                        position.setCol(desiredNext);
                        translate(dir * Grid.CELL_SIZE * speed, 0);
                    } catch (Exception e) {

                        e.printStackTrace();
                        safeDelete();
                        break;
                    }

                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            } catch (Exception e) {
                // Log unexpected exceptions so we can see why a spell stopped
                e.printStackTrace();
            } finally {
                synchronized (ACTIVE) {
                    ACTIVE.remove(this);
                }
            }
        }).start();
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

    public PlayerEnum getPlayerEnum() {
        return playerEnum;
    }

    public void delete() {
        spell.delete();
    }

    private void safeDelete() {
        try {
            delete();
        } catch (Exception ignore) {
        }
    }

    public void translate(int col, int row) {
        // record previous X before moving so collision manager can compute the swept
        // area exactly
        prevX = spell.getX();
        spell.translate(col, row);
    }

    public int getWidth() {
        return spell.getWidth();
    }

    public int getHeight() {
        return spell.getHeight();
    }

    public int getY() {
        return spell.getY();
    }

    public int getX() {
        return spell.getX();
    }

    public int getPrevX() {
        return prevX;
    }

    // Remove all active spells' pictures
    public static void cleanupAll() {
        java.util.List<Spell> snapshot;
        synchronized (ACTIVE) {
            snapshot = new java.util.ArrayList<>(ACTIVE);
        }
        for (Spell s : snapshot) {
            if (s != null) {
                s.safeDelete();
            }
        }
        synchronized (ACTIVE) {
            ACTIVE.clear();
        }
    }
}
