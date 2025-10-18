package game.powerUps;

import collisionManager.CollisionManager;
import game.characters.Character;
import ui.grid.Grid;


public class PowerUpHandler {


    public static void handlePowerUpCollection(Character character, int fromCol, int fromRow, int toCol, int toRow) {
        PowerUp powerUp = CollisionManager.getPowerUpAlongPath(fromCol, fromRow, toCol, toRow);
        
        if (powerUp == null) {
            powerUp = CollisionManager.getPowerUpOverlappingCharacter(character);
        }
        
        if (powerUp != null) {
            applyPowerUpEffect(character, powerUp);
        }
    }

   
    private static void applyPowerUpEffect(Character character, PowerUp powerUp) {
        if (powerUp instanceof PowerUpHealth) {
            character.addLifePoints();
            powerUp.removeFromGame();
        } else if (powerUp instanceof PowerUpDamage) {
            character.applyDamageBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
            powerUp.removeFromGame();
        } else if (powerUp instanceof PowerUpSpellSpeed) {
            character.applySpeedBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
            character.applyMovementBuff(1, Grid.POWER_UP_BUFF_DURATION_SECONDS);
            powerUp.removeFromGame();
        }
    }
}
