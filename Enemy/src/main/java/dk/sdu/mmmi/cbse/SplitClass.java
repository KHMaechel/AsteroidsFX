package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class SplitClass implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        System.out.println("Collision package");
    }
}