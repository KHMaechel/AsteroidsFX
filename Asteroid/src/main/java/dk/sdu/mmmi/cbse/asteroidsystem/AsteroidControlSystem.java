package dk.sdu.mmmi.cbse.asteroidsystem;

import java.lang.Math;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;
import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toList;


public class AsteroidControlSystem implements IEntityProcessingService {


    @Override
    public void process(GameData gameData, World world) {


        if(Math.random()*1000 > 985) {
        Random rnd = new Random();
        int size = rnd.nextInt(5) + 20;
        Entity asteroid = new Asteroid();
        asteroid.setPolygonCoordinates(
                size,-size,
                0,-sqrt(2*size*size),
                -size,-size,
                -sqrt(2*size*size),0,
                -size,size,
                0,sqrt(2*size*size),
                size, size,
                sqrt(2*size*size),0,
                size,-size);

        setAsteroidSpawnCoordinates(asteroid, gameData);

        world.addEntity(asteroid);

        }
        // move forward
        for (Entity entity : world.getEntities(Asteroid.class)) {
            double changeX = Math.cos(Math.toRadians(entity.getRotation()));
            double changeY = Math.sin(Math.toRadians(entity.getRotation()));
            entity.setX(entity.getX() + changeX);
            entity.setY(entity.getY() + changeY);


            // Remove asteroid when they reach the edge of the map + some more
            int maxAsteroidSizeTimeTwo = 100;
            double displayWidth = gameData.getDisplayWidth();
            double displayHeight = gameData.getDisplayHeight();
            if (entity.getX() < -maxAsteroidSizeTimeTwo || entity.getX() > displayWidth * maxAsteroidSizeTimeTwo|| entity.getY() < -maxAsteroidSizeTimeTwo || entity.getY() > displayHeight + maxAsteroidSizeTimeTwo) {
                world.removeEntity(entity);
            }
        }
    }


    private void setAsteroidSpawnCoordinates(Entity asteroid, GameData gameData){
        Random random = new Random();
        int size = 50;
        int asteroidSpawnSide = random.nextInt(0, 4);
        switch (asteroidSpawnSide) {
            case 0: // left
                asteroid.setX(-size);
                asteroid.setY(Math.random() * gameData.getDisplayHeight());
                asteroid.setRotation(random.nextInt(320, 400));
                break;
            case 1: // Right
                asteroid.setX(gameData.getDisplayWidth() + size);
                asteroid.setY(Math.random() * gameData.getDisplayHeight());
                asteroid.setRotation(random.nextInt(140, 220));
                break;
            case 2: // Top
                asteroid.setX(Math.random() * gameData.getDisplayWidth());
                asteroid.setY(-size);
                asteroid.setRotation(random.nextInt(50, 130));
                break;
            case 3: // Bottom
                asteroid.setX(Math.random() * gameData.getDisplayWidth());
                asteroid.setY(gameData.getDisplayHeight() + size);
                asteroid.setRotation(random.nextInt(230,310));
                break;
        }


    }


}