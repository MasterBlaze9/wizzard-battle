package ui.character.HealthBar;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class HealthBarAppearancePlayer2 extends Rectangle {


        private Rectangle healthBar2;

        public HealthBarAppearancePlayer2(int x, int y, int width, int height){
            healthBar2 = new Rectangle(x, y, width, height);
//     healthBar1.setColor(AppColor.BLUE.toColor());
            healthBar2.draw();

        }
}
