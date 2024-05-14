package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    public Entity createEnemyShip(GameData gameData) {
        Entity enemy = new Enemy();
        enemy.setType(Entity.EntityType.ENEMY);
        enemy.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemy.setX(gameData.getDisplayWidth()*Math.random());
        enemy.setY(gameData.getDisplayHeight()*Math.random());
        enemy.setRotation((float) (Math.random()*360));
        enemy.setRadius(8);
        enemy.setHp(3);
        enemy.setNumberOfWeapons(1);
        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}
