package ui.character;

import org.academiadecodigo.simplegraphics.pictures.Picture;

import ui.position.Position;
import ui.grid.Grid;

public class CharacterUI {

	Picture characterHead;
	Position position;

	public CharacterUI(int column, int row, String facePath) {

		int cellSize = Grid.CELL_SIZE;

		// create picture first and draw so width/height are available
		characterHead = new Picture(0, 0, facePath);
		characterHead.draw();

		// compute target center of the logical cell in pixels
		int cellOriginX = Grid.PADDING + column * cellSize;
		int cellOriginY = Grid.PADDING + row * cellSize;

		int targetCenterX = cellOriginX + cellSize / 2;
		int targetCenterY = cellOriginY + cellSize / 2;

		// actual picture size (available after draw)
		int picW = characterHead.getWidth();
		int picH = characterHead.getHeight();

		int pixelX = targetCenterX - picW / 2;
		int pixelY = targetCenterY - picH / 2;

		// small upward nudge so characters appear a bit higher on screen
		int verticalNudge = 6; // pixels upward
		pixelY -= verticalNudge;

        try {
            // translate from initial (0,0) to desired position
            characterHead.translate(pixelX, pixelY);
            position = new Position(column, row);
        }
        catch(Exception e){
            System.out.println("Error trying to move characterHead UI: " + e.getMessage());
        }

	}

	public void move(int cols, int rows) {
        try {
            characterHead.translate(cols, rows);
        }
        catch(Exception e){
            System.out.println("Error trying to move character UI: " + e.getMessage());
        }
	}

	public int getPixelX() {
		return characterHead.getX();
	}

	public int getPixelY() {
		return characterHead.getY();
	}

	public int getPixelWidth() {
		return characterHead.getWidth();
	}

	public int getPixelHeight() {
		return characterHead.getHeight();
	}

	public void hide() {
		if (characterHead != null) {
			try {
				characterHead.delete();
			} catch (Exception ignored) {
			}
		}
	}

}
