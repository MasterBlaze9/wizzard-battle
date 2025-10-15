// src/ui/character/FaceCard/Player1FaceCard.java
package ui.character.FaceCard;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Player1FaceCard {

    private Picture card1;

    public Player1FaceCard(String imagePath, int x, int y, int width, int height) {
        this.card1 = new Picture(x, y, imagePath);
        card1.draw();
    }
}
