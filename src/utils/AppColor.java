package utils;

import org.academiadecodigo.simplegraphics.graphics.Color;

public enum AppColor {
	RED(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue()),
	BLUE(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue()),
	GREEN(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue()),
	YELLOW(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue()),
	BROWN(210, 105, 30);

	private final int red;
	private final int green;
	private final int blue;

	AppColor(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public Color toColor() {
		return new Color(red, green, blue);
	}
}
