package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    int speed = 3;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {

            // Calculate change in position
            double changeX = speed * Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = speed * Math.sin(Math.toRadians(bullet.getRotation()));

            bullet.setX(bullet.getX() + changeX);
            bullet.setY(bullet.getY() + changeY);

            // Remove bullets when they reach the edge of the map
            double displayWidth = gameData.getDisplayWidth();
            double displayHeight = gameData.getDisplayHeight();
            if (bullet.getX() < 0 || bullet.getX() > displayWidth || bullet.getY() < 0 || bullet.getY() > displayHeight) {
                world.removeEntity(bullet);
            }

        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {

        Entity bullet = new Bullet();
        bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        bullet.setRotation(shooter.getRotation());
        return bullet;


    }

}
