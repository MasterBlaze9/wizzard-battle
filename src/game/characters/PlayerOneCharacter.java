package game.characters;

import game.PlayerEnum;

public class PlayerOneCharacter extends Character {

	private PlayerEnum playerNumber;

	public PlayerOneCharacter() {
		playerNumber = PlayerEnum.Player_1;
	}

	@Override
	public void moveUp() {
		// TODO: implement actual movement (e.g., change sprite/position)
		System.out.println("PlayerOneCharacter: moveUp");
	}

	@Override
	public void moveDown() {
		// TODO: implement actual movement (e.g., change sprite/position)
		System.out.println("PlayerOneCharacter: moveDown");
	}

	@Override
	public void moveLeft() {
		// TODO: implement actual movement (e.g., change sprite/position)
		System.out.println("PlayerOneCharacter: moveLeft");
	}

	@Override
	public void moveRight() {
		// TODO: implement actual movement (e.g., change sprite/position)
		System.out.println("PlayerOneCharacter: moveRight");
	}

}
