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
    private int speed;
    private int damage;
    private PlayerEnum playerEnum;

    public Spell(int row, int col, PlayerEnum playerEnum) {
        if (playerEnum.equals(PlayerEnum.Player_1)) {
            spell = new Picture(
                    Grid.PADDING + col * Grid.CELL_SIZE + (Grid.CELL_SIZE - Math.max(6, Grid.CELL_SIZE * 2)) / 2,
                    Grid.PADDING + row * Grid.CELL_SIZE + (Grid.CELL_SIZE - Math.max(3, Grid.CELL_SIZE / 2)) / 2
                            + Grid.CELL_SIZE * 2,
                    "resources/Spells/fire.png");
            spell.draw();
        } else {
            spell = new Picture(
                    Grid.PADDING + col * Grid.CELL_SIZE + (Grid.CELL_SIZE - Math.max(6, Grid.CELL_SIZE * 2)) / 2,
                    Grid.PADDING + row * Grid.CELL_SIZE + (Grid.CELL_SIZE - Math.max(3, Grid.CELL_SIZE / 2)) / 2
                            + Grid.CELL_SIZE * 2,
                    "resources/Spells/fire1.png");
            spell.draw();
        }

        speed = 2;
        damage = 1;
        this.playerEnum = playerEnum;

        position = new Position(col, row);

        final int dir = playerEnum.equals(PlayerEnum.Player_1) ? 1 : -1;

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

                        try {
                            delete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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

                        try {
                            delete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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
                            try {
                                delete();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            break;
                        }
                        position.setCol(desiredNext);
                        translate(dir * Grid.CELL_SIZE * speed, 0);
                    } catch (Exception e) {

                        e.printStackTrace();
                        try {
                            delete();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
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

    public void translate(int col, int row) {
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
}
