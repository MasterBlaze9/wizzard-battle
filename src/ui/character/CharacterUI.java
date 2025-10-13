package ui.character;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Ellipse;

import ui.position.Position;
import ui.grid.Grid;

public class CharacterUI {

	Ellipse characterHead;
	Position position;

	public CharacterUI(int column, int row) {
		// Treat constructor args as cell indices. Convert to pixel coordinates so the UI aligns to the grid.
		int pixelX = Grid.PADDING + column * Grid.CELL_SIZE;
		int pixelY = Grid.PADDING + row * Grid.CELL_SIZE;
		int size = Grid.CELL_SIZE; // ellipse size equals cell size
		characterHead = new Ellipse(pixelX, pixelY, size, size);
		characterHead.grow(10, 10);
		position = new Position(column, row);
		characterHead.setColor(Color.BLACK);
		characterHead.fill();
	}
}
