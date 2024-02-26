package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.asteroidsystem.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.enemy.Enemy;
import dk.sdu.mmmi.cbse.playersystem.Player;

public class Collision implements IPostEntityProcessingService {

    public Collision() {
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // If the two entities are identical
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                // Asteroids should not collide with asteroids
                if (entity1.getClass().equals(Asteroid.class) && entity2.getClass().equals(Asteroid.class)) {
                    continue;
                }
                // Bullets should not collide with bullets
                if (entity1.getClass().equals(Bullet.class) && entity2.getClass().equals(Bullet.class)) {
                    continue;
                }


                // If Player collides with Enemy or Asteroid: destroy both
                if ((entity1.getClass().equals(Player.class) && (entity2.getClass().equals(Asteroid.class) || entity2.getClass().equals(Enemy.class)))
                        ||(entity2.getClass().equals(Player.class) && (entity1.getClass().equals(Asteroid.class) || entity1.getClass().equals(Enemy.class))) ) {
                    if (this.collides(entity1, entity2)) {
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                    }
                }

                // If spaceship or asteroid collides with bullet: - 1 hp:
                if (entity1.getClass().equals(Bullet.class) && (entity2.getClass().equals(Player.class) || entity2.getClass().equals(Enemy.class) || entity2.getClass().equals(Asteroid.class)) ) {
                    if (this.collides(entity1, entity2)) {
                        world.removeEntity(entity1);
                        entity2.setHp(entity2.getHp()-1);
                        // remove player and enemy. Asteroid removal is handled in the Asteroid module, since they might split
                        if (entity2.getHp() <= 0 && (entity2.getClass().equals(Player.class) || entity2.getClass().equals(Enemy.class)) ) {
                            world.removeEntity(entity2);
                        }
                    }
                }


            }
        }

    }
    // CircularCollision (Should use Pythagoras)
    public Boolean collides(Entity entity1, Entity entity2) {
        float deltaX = (float) entity1.getX() - (float) entity2.getX();
        float deltaY = (float) entity1.getY() - (float) entity2.getY();

        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

}