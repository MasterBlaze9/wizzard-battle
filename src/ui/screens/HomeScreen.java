package ui.screens;

import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class HomeScreen {

    private Picture homeScreenBackground;
    private KeyboardEvent startGame;
    private boolean visible = false;

    public HomeScreen() {
        homeScreenBackground = new Picture(0, 0, "resources/backgroundStart.png");
        homeScreenBackground.draw();
        // constructor draws the background; mark visible so show()/hide() behave correctly
        visible = true;

    }

    public void hide() {
        if (!visible) {
            return;
        }
        if (homeScreenBackground != null) {
            homeScreenBackground.delete();
        }
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void show() {
        if (visible) {
            return;
        }
        homeScreenBackground = new Picture(0, 0, "resources/backgroundStart.png");
        homeScreenBackground.draw();
        visible = true;
    }

    public KeyboardEvent getStartGameScreen() {
        return startGame;
    }
}
