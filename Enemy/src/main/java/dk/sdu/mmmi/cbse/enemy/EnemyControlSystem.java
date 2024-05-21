package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {


        // Add enemy if number of enemies < max number of allowed enemies
        int maxEnemies = gameData.getLevel();
        int numberOfEnemies = world.getEntities(Enemy.class).size();
        if( numberOfEnemies < maxEnemies && Math.random()*1000 > 990) {
            EnemyPlugin ep = new EnemyPlugin();
            Entity enemy = ep.createEnemyShip(gameData);
            world.addEntity(enemy);
        }

        // Enemy movement
        for (Entity enemy : world.getEntities(Enemy.class)) {

            // Move forward (Same as player)
            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);

            // Rotate enemy randomly
            int nextRotation;
            if (Math.random() * 100 > 90) {
                // Determine the next rotation direction
                if (Math.random() * 100 < 50) {
                    nextRotation = 5;
                } else {
                    nextRotation = -5;
                    // Apply the rotation to the enemy
                    enemy.setRotation(enemy.getRotation() + nextRotation);
                }
            }

            // Rotate if close the edge of the map
            double displayWidth = gameData.getDisplayWidth();
            double displayHeight = gameData.getDisplayHeight();
            if(enemy.getX() < 5) {
                enemy.setRotation(enemy.getRotation() + 20);
            }
            if(enemy.getX() > displayWidth-5) {
                enemy.setRotation(enemy.getRotation() - 20);
            }
            if(enemy.getY() < 5) {
                enemy.setRotation(enemy.getRotation() + 20);
            }
            if(enemy.getY() > displayHeight-5) {
                enemy.setRotation(enemy.getRotation() - 20);
            }


            // Shoot enemy bullet
            if(Math.random()*1000 > 990) {
                getWeaponSPIs().stream()
                        .filter(spi -> enemy.getNumberOfWeapons() == (spi.getNumberOfBulletsIdentifier()))
                        .findFirst()
                        .ifPresent(spi -> {
                            spi.fire(enemy, gameData, world);
                        });
            }



        }

    }

    private Collection<? extends WeaponSPI> getWeaponSPIs() {
        return ServiceLoader.load(WeaponSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}
