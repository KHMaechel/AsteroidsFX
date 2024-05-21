package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        player = createPlayerShip(gameData);
        ((Player) player).setLife(3);
        world.addEntity(player);
        gameData.setPlayer(player);
    }

    private Entity createPlayerShip(GameData gameData) {

        Entity playerShip = new Player();
        playerShip.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        playerShip.setX(gameData.getDisplayHeight() / 2);
        playerShip.setY(gameData.getDisplayWidth() / 2);
        playerShip.setRadius(8);
        playerShip.setType(Entity.EntityType.PLAYER);
        playerShip.setNumberOfWeapons(1);
        playerShip.setHp(3);
        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (dk.sdu.mmmi.cbse.common.data.Entity entity : world.getEntities()) {
            if (entity.getType() == Entity.EntityType.PLAYER) {
                world.removeEntity(entity);
            }
        }
    }

}
