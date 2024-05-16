package dk.sdu.mmmi.cbse.life;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.services.IUIProcessingService;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Score implements IUIProcessingService{

    @Override
    public void generate(GameData gameData, Pane gameWindow){
        gameData.resetTotalScore();
        int totalScore = gameData.getTotalScore();
        Text scoreText = new Text(10, 20, "Your points: " + totalScore);
        scoreText.setStyle("-fx-font: 20 arial;");

        scoreText.setId("scoreText");
        gameWindow.getChildren().add(scoreText);
    }

    @Override
    public void process(GameData gameData, Pane gameWindow) {

        int totalScore = gameData.getTotalScore();
        Text scoreText = (Text) gameWindow.lookup("#scoreText");
        scoreText.setText("Your points: " + totalScore);

    }




}
