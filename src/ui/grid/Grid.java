package ui.grid;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Grid  {

	public static final int PADDING = 10;

    private int cellSize = 15;
    private int cols;
    private int rows;

    private Rectangle field;

    /**
     * Simple graphics grid constructor with a certain number of rows and columns
     *
     * @param cols number of the columns
     * @param rows number of rows
     */
    public Grid(int cols, int rows){
        this.cols = cols;
        this.rows = rows;
    }

    /**
     * Initializes the field simple graphics rectangle and draws it
     */

    public void init() {
        this.field = new Rectangle(PADDING,PADDING, cols * cellSize, rows * cellSize);
        this.field.draw();
    }

    public int getCellSize() {
        return cellSize;
    }


    public int getCols() {
        return this.cols;
    }

   
    public int getRows() {
        return this.rows;
    }

    public int getWidth() {
        return this.field.getWidth();
    }

    public int getHeight() {
        return this.field.getHeight();
    }

    public int getX() {
        return this.field.getX();
    }

    public int getY() {
        return this.field.getY();
    }

   

}
