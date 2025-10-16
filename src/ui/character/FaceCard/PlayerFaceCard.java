// src/ui/character/FaceCard/Player1FaceCard.java
package ui.character.FaceCard;

import game.PlayerEnum;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerFaceCard {
    private static String player1Face;
    private static String player2Face;
    private Picture card1;

    private static final String[] FACE_IMAGES = {
            "resources/Carolina.png",
            "resources/Pascoa.png",
            "resources/Rolo.png"
    };


    public PlayerFaceCard(PlayerEnum player, int x, int y, int width, int height) {

        if (player1Face == null || player2Face == null) {
            FaceCardAssigner();
        }
        String facePath = getFace(player);
        if (facePath == null) {
            throw new IllegalStateException("no face assigned for player: " + player);
        }

        card1 = new Picture(x, y, facePath);
        card1.grow(width / 8.0, height / 8.0);
        card1.draw();

    }

    public void FaceCardAssigner() {
        List<String> images = new ArrayList<>(Arrays.asList(FACE_IMAGES));
        Collections.shuffle(images);

        player1Face = images.get(0);
        player2Face = images.get(1);
    }

    public String getFace(PlayerEnum player) {
        if (player == PlayerEnum.Player_1) {
            return player1Face;
        } else if (player == PlayerEnum.Player_2) {
            return player2Face;
        }
        return null;
    }


}
