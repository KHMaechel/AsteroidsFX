import dk.sdu.mmmi.cbse.common.services.IUIProcessingService;
import dk.sdu.mmmi.cbse.life.*;

module UI {
    exports dk.sdu.mmmi.cbse.life;

    requires Common;
    requires javafx.graphics;
    requires CommonPlayer;
    provides IUIProcessingService with PlayerLife, Score, Level, GameOver, Pause;
}