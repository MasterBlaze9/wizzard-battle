package ui.character;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Ellipse;

import ui.position.Position;
import ui.grid.Grid;

public class CharacterUI {

	Ellipse characterHead;
	Position position;

	public CharacterUI(int column, int row) {
		// Treat constructor args as cell indices. Convert to pixel coordinates so the
		// UI aligns to the grid.
		int cellSize = Grid.CELL_SIZE;
		// previous code used grow(10,10) which increases the ellipse size and can shift
		// its
		// top-left corner. To keep the ellipse centered in the logical cell, compute
		// the
		// final size explicitly and offset the pixel coordinates so the center of the
		// ellipse matches the center of the cell.
		// Make the grow padding relative to the cell size so large cells don't overflow
		int growPadding = Math.max(2, cellSize / 4); // at least a small padding
		int finalSize = cellSize + 2 * growPadding;

		int pixelX = Grid.PADDING + column * cellSize + (cellSize - finalSize) / 2;
		int pixelY = Grid.PADDING + row * cellSize + (cellSize - finalSize) / 2;

		characterHead = new Ellipse(pixelX, pixelY, finalSize, finalSize);
		position = new Position(column, row);
		characterHead.setColor(Color.BLACK);
		characterHead.fill();
	}

	public void move(int cols, int rows) {
		characterHead.translate(cols, rows);
	}

}
