package dk.sdu.mmmi.cbse.life;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.services.IUIProcessingService;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Pause implements IUIProcessingService {

    private boolean togglePauseReady = true;

    @Override
    public void generate(GameData gameData, Pane gameWindow) {
    }

    @Override
    public void process(GameData gameData, Pane gameWindow) {
        Text gamePausedText = (Text) gameWindow.lookup("#gamePausedText");

        if (gameData.isGamePaused() && !gameData.isGameOver()) {
            if (gamePausedText != null) {
                gamePausedText.setText("Game paused!");
            } else {
                gamePausedText = new Text((gameWindow.getWidth() / 2)-160, (gameWindow.getHeight() / 2)-100, "Game paused!");
                gamePausedText.setId("gamePausedText");
                gamePausedText.setStyle("-fx-font: 50 arial;");
                gameWindow.getChildren().add(gamePausedText);
            }


             if (gameData.getKeys().isPressed(GameKeys.P) && togglePauseReady) {
                gameData.setGamePaused(false);
                togglePauseReady = false;

            }


        }


        if (!gameData.isGamePaused() && !gameData.isGameOver()) {
            if (gamePausedText != null) {
                gameWindow.getChildren().remove(gamePausedText);
            }
            if (gameData.getKeys().isPressed(GameKeys.P) && togglePauseReady) {
                gameData.setGamePaused(true);
                togglePauseReady = false;
            }

        }



        if (!gameData.getKeys().isPressed(GameKeys.P) && !gameData.isGameOver()) {
            togglePauseReady = true;
        }

    }


}
