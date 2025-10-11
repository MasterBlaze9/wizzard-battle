package game;

import game.characters.Character;
import game.characters.PlayerOneCharacter;
import game.characters.PlayerTwoCharacter;
import keyboard.AppKeyboard;

public class Player {

	private PlayerEnum playerNumber;
	private AppKeyboard playerKeyboard;
	private Character character;

	public Player(PlayerEnum playerNumber) {
		this.playerNumber = playerNumber;
		
		if (playerNumber == PlayerEnum.Player_1) {
			character = new PlayerOneCharacter();
		} else {
			character = new PlayerTwoCharacter();
		}

		playerKeyboard = new AppKeyboard(this, character);
	}

	public Character getCharacter() {
		return character;
	}

	public PlayerEnum getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(PlayerEnum playerNumber) {
		this.playerNumber = playerNumber;
	}


}
