import dk.sdu.mmmi.cbse.common.services.IUIProcessingService;
import dk.sdu.mmmi.cbse.life.GameOver;
import dk.sdu.mmmi.cbse.life.PlayerLife;
import dk.sdu.mmmi.cbse.life.Score;
import dk.sdu.mmmi.cbse.life.Level;

module UI {
    exports dk.sdu.mmmi.cbse.life;

    requires Common;
    requires javafx.graphics;
    requires Player;
    provides IUIProcessingService with PlayerLife, Score, Level, GameOver;
}