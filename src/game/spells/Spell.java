package game.spells;

import game.PlayerEnum;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import ui.position.Position;
import ui.grid.Grid;
import game.characters.Character;
import collisionManager.CollisionManager;
import utils.AppColor;

public class Spell {

    private Rectangle spell;
    private Position position;
    private int speed;
    private int damage;
    private PlayerEnum playerEnum;
    private AppColor color;

    public Spell(int row, int col, PlayerEnum playerEnum) {
        spell = new Rectangle(
                Grid.PADDING + col * Grid.CELL_SIZE + (Grid.CELL_SIZE - Math.max(6, Grid.CELL_SIZE * 2)) / 2,
                Grid.PADDING + row * Grid.CELL_SIZE + (Grid.CELL_SIZE - Math.max(3, Grid.CELL_SIZE / 2)) / 2,
                Math.max(6, Grid.CELL_SIZE * 2),
                Math.max(3, Grid.CELL_SIZE / 2));

        this.speed = 2;
        this.damage = 1;
        this.playerEnum = playerEnum;

        if (playerEnum.equals(PlayerEnum.Player_1)) {
            color = AppColor.RED;
        } else {
            color = AppColor.YELLOW;
        }

        // logical position in cell coords
        position = new Position(col, row);

        // visual appearance
        setColor(color.toColor());
        fill();

        // start self-animation: player 1 goes right (+1), player 2 goes left (-1)
        final int dir = playerEnum.equals(PlayerEnum.Player_1) ? 1 : -1;

        new Thread(() -> {
            try {
                while (true) {
                    // compute desired next column by speed (cells per tick)
                    int currentCol = position.getCol();
                    int desiredNext = currentCol + dir * speed;

                    // if the desired next goes past the right edge
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

                    // if the desired next goes past the left edge
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

                    // normal movement inside bounds
                    try {
                        // First, check the whole path between currentCol and desiredNext
                        Character hit = CollisionManager.getCollidingCharacterAlongPath(this, currentCol + dir,
                                desiredNext);
                        if (hit != null) {
                            // move spell to the hit character's column and delete
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

                        // no hit along the path; apply normal movement
                        position.setCol(desiredNext);
                        translate(dir * Grid.CELL_SIZE * speed, 0);
                    } catch (Exception e) {
                        // if translate/delete throws, log and try to clean up then stop
                        e.printStackTrace();
                        try {
                            delete();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                    }

                    // pacing: shorter sleep for faster feeling
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
    
    public void setColor(Color color) {
        spell.setColor(color);
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

    public void fill() {
        spell.fill();
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
