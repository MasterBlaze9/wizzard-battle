package ui.character.HealthBar;

import org.academiadecodigo.simplegraphics.graphics.Ellipse;
import utils.AppColor;

    public class Life extends Ellipse {

        private Ellipse life;

        public Life(double x, double y, double width, double height) {
            super(x,y,width,height);
            life = new Ellipse(x, y, width, height);
            life.setColor(AppColor.RED.toColor());
            life.fill();
        }

    }
