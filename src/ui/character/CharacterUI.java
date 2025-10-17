package ui.character;

import org.academiadecodigo.simplegraphics.pictures.Picture;

import ui.position.Position;
import Grid;

public class CharacterUI {

	Picture characterHead;
	Position position;

	public CharacterUI(int column, int row, String facePath) {

		int cellSize = Grid.CELL_SIZE;

		int growPadding = Math.max(2, cellSize / 4);
		int finalSize = cellSize + 2 * growPadding;

		int pixelX = Grid.PADDING + column * cellSize + (cellSize - finalSize) / 2;
		int pixelY = Grid.PADDING + row * cellSize + (cellSize - finalSize) / 2;

		characterHead = new Picture(pixelX, pixelY, facePath);
		position = new Position(column, row);
		characterHead.draw();

	}

	public void move(int cols, int rows) {
		characterHead.translate(cols, rows);
	}

}
