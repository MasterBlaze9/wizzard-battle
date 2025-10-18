package game.characters;

public class BuffManager {


    public static void applyTemporaryBuff(ModifiableValue target, int extraValue, int durationSeconds) {
        if (extraValue <= 0 || durationSeconds <= 0) {
            return;
        }
        
        target.modify(extraValue);
        
        new Thread(() -> {
            try {
                Thread.sleep(durationSeconds * 1000L);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            target.modify(-extraValue);
        }).start();
    }


    public interface ModifiableValue {
     
        void modify(int delta);
    }
}
