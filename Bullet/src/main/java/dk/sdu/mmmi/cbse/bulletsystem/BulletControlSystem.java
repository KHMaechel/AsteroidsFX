package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {

            int speed = bullet.getSpeed();
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
    public Entity createBullet(Entity shooter, GameData gameData, Placement placement) {


        Entity bullet = new Bullet();
        System.out.println("bullet created");
        bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);
        bullet.setRotation(shooter.getRotation());
        bullet.setSpeed(3);
        double changeX;
        double changeY;

        // place the bullet to the right, left or in front of the ship
        switch(placement) {
            case CENTER:
                changeX = 8 * Math.cos(Math.toRadians(bullet.getRotation()));
                changeY = 8 * Math.sin(Math.toRadians(bullet.getRotation()));
                break;
            case LEFT:
                changeX = 10 * Math.cos(Math.toRadians(bullet.getRotation()-25));
                changeY = 10 * Math.sin(Math.toRadians(bullet.getRotation()-25));
                break;
            case RIGHT:
                changeX = 10 * Math.cos(Math.toRadians(bullet.getRotation()+25));
                changeY = 10 * Math.sin(Math.toRadians(bullet.getRotation()+25));
                break;
            default:
                changeX = 8 * Math.cos(Math.toRadians(bullet.getRotation()));
                changeY = 8 * Math.sin(Math.toRadians(bullet.getRotation()));
        }



        bullet.setX(shooter.getX() + changeX);
        bullet.setY(shooter.getY() + changeY);

        bullet.setRadius(2);
        return bullet;


    }

}
