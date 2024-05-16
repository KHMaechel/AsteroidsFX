package dk.sdu.mmmi.cbse.life;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.services.IUIProcessingService;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class Level implements IUIProcessingService{

    @Override
    public void generate(GameData gameData, Pane gameWindow){
        gameData.resetLevel();
        int level = gameData.getLevel();
        Text levelText = new Text(10, 45, "Level: " + level);
        levelText.setStyle("-fx-font: 20 arial;");
        levelText.setId("levelText");
        gameWindow.getChildren().add(levelText);
    }

    @Override
    public void process(GameData gameData, Pane gameWindow) {

        int level = gameData.getLevel();
        Text levelText = (Text) gameWindow.lookup("#levelText");
        levelText.setText("Level: " + level);

    }




}
