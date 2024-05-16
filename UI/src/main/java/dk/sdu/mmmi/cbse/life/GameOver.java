package dk.sdu.mmmi.cbse.life;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.services.IUIProcessingService;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GameOver implements IUIProcessingService {


    @Override
    public void generate(GameData gameData, Pane gameWindow) {
    }

    @Override
    public void process(GameData gameData, Pane gameWindow) {
        Text gameOverText = (Text) gameWindow.lookup("#gameOverText");
        Text newGameText = (Text) gameWindow.lookup("#newGameText");

        if (gameData.isGameOver()) {
            if (gameOverText != null) {
                gameOverText.setText("Game Over!");
            } else {
                gameOverText = new javafx.scene.text.Text((gameWindow.getWidth() / 2)-130, (gameWindow.getHeight() / 2)-100, "Game Over!");
                gameOverText.setId("gameOverText");
                gameOverText.setStyle("-fx-font: 50 arial;");
                gameWindow.getChildren().add(gameOverText);
            }

            if (newGameText != null) {
                newGameText.setText("Press ENTER to start a new game");
            } else {
                newGameText = new javafx.scene.text.Text((gameWindow.getWidth() / 2)-150, (gameWindow.getHeight() / 2), "Press ENTER to start a new game");
                newGameText.setId("newGameText");
                newGameText.setStyle("-fx-font: 20 arial;");
                gameWindow.getChildren().add(newGameText);
            }

            if (gameData.getKeys().isPressed(GameKeys.ENTER)) {
                gameData.resetTotalScore();
                gameData.resetLevel();
                gameData.setGameOver(false);
                gameData.setNewGame(true);
            }

        }


        if (!gameData.isGameOver()) {
            if (gameOverText != null) {
                gameWindow.getChildren().remove(gameOverText);
            }
            if (newGameText != null) {
                gameWindow.getChildren().remove(newGameText);
            }

        }


    }


}
