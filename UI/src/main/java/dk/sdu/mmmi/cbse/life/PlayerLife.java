package dk.sdu.mmmi.cbse.life;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.services.IUIProcessingService;
import javafx.scene.layout.Pane;
import dk.sdu.mmmi.cbse.playersystem.Player;
import javafx.scene.text.Text;

public class PlayerLife implements IUIProcessingService{


    @Override
    public void generate(GameData gameData, Pane gameWindow){
        Player player = (Player) gameData.getPlayer();
        Text lifeText = new Text(10, 70, "Lives remaining: " + player.getLife());
        lifeText.setStyle("-fx-font: 20 arial;");
        lifeText.setId("lifeText");
        gameWindow.getChildren().add(lifeText);
    }

    @Override
    public void process(GameData gameData, Pane gameWindow) {
        Player player = (Player) gameData.getPlayer();
        int life = player.getLife();

        Text lifeText = (Text) gameWindow.lookup("#lifeText");
        lifeText.setText("Lives remaining: " + life);

    }



}
