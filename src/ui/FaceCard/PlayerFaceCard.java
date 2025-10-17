// src/ui/character/FaceCard/playerOneFaceImagePathCard.java
package ui.faceCard;

import game.PlayerEnum;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerFaceCard {

    //TODO Remove the possibility to have the same pic
  
    private Map<PlayerEnum, String> faceAssignments;
    private Picture playerFace;

    public PlayerFaceCard(PlayerEnum player, int x, int y, int width, int height) {
        
        faceAssignments = new HashMap<>();
        faceCardAssigner();

        String facePath = getFace(player);
        if (facePath == null) {
            throw new IllegalStateException("no face assigned for player: " + player);
        }

        playerFace = new Picture(x, y, facePath);
        playerFace.grow(width / 4, height / 4);
        playerFace.draw();
    }

    private void faceCardAssigner() {
        List<String> paths = new ArrayList<>(Arrays.asList(
                "resources/Faces/Carolina.png",
                "resources/Faces/Pascoa.png",
                "resources/Faces/Rolo.png"));

        Collections.shuffle(paths);

        String p1 = paths.size() > 0 ? paths.get(0) : null;
        String p2 = paths.size() > 1 ? paths.get(1) : null;

        faceAssignments.put(PlayerEnum.Player_1, p1);
        faceAssignments.put(PlayerEnum.Player_2, p2);
    }

    public String getFace(PlayerEnum player) {
        return faceAssignments.get(player);
    }

}
