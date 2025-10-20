// src/ui/character/FaceCard/playerOneFaceImagePathCard.java
package ui.faceCard;

import game.PlayerEnum;
import org.academiadecodigo.simplegraphics.pictures.Picture;

// no collection utilities required in this class after refactor

public class PlayerFaceCard {

    private Picture playerFace;

    public PlayerFaceCard(PlayerEnum player, String facePath, int x, int y, int width, int height) {

        if (facePath == null) {
            throw new IllegalArgumentException("facePath cannot be null for player: " + player);
        }

        playerFace = new Picture(x, y, facePath);
        playerFace.grow(width / 4, height / 4);
        playerFace.draw();
    }

    public void hide() {
        if (playerFace != null) {
            try {
                playerFace.delete();
            } catch (Exception ignored) {
            }
        }
    }

    // face assignment is performed by the caller and injected into the constructor

    // face retrieval is now the caller's responsibility; this class only displays
    // the provided face

}
