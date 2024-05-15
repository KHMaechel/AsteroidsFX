package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import javafx.scene.layout.Pane;

public interface IUIProcessingService {

    void generate(GameData gameData, Pane gameWindow);
    void process(GameData gameData, Pane gameWindow);
}
