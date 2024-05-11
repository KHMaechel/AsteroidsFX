package dk.sdu.mmmi.cbse.collisionsystem;


import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.asteroidsystem.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.enemy.Enemy;
import dk.sdu.mmmi.cbse.playersystem.Player;

import java.util.ArrayList;
import java.util.Arrays;

class CollisionTest {

    @Mock
    private World world;
    @Mock
    private GameData gameData;

    private Player player;

    private Enemy enemy;

    private Asteroid asteroid1;

    private Asteroid asteroid2;

    private Bullet bullet1;

    private Bullet bullet2;

    private Collision collision;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        collision = new Collision();
        enemy = new Enemy();
        player = new Player();
        asteroid1 = new Asteroid();
        asteroid2 = new Asteroid();
        bullet1 = new Bullet();
        bullet2 = new Bullet();
    }

    @AfterEach
    void tearDown() {
        collision = null;
        gameData = null;
        world = null;
        enemy = null;
        player = null;
        asteroid1 = null;
        asteroid2 = null;
        bullet1 = null;
        bullet2 = null;
    }

    @Test
    void testPlayerCollidesWithEnemy() {


        player.setX(0.0);
        player.setY(0.0);
        player.setRadius(10.0F);

        enemy.setX(0.0);
        enemy.setY(0.0);
        enemy.setRadius(10.0F);


        when(world.getEntities())
                .thenReturn(new ArrayList<>(Arrays.asList(player, enemy)));

        collision.process(gameData, world);


        verify(world, atLeastOnce()).removeEntity(player);
        verify(world, atLeastOnce()).removeEntity(enemy);
    }

    @Test
    void testBulletDoesNotCollideWithBullet() {

        bullet1.setX(0.0);
        bullet1.setY(0.0);
        bullet1.setRadius(2.0F);

        bullet2.setX(0.0);
        bullet2.setY(0.0);
        bullet2.setRadius(2.0F);


        when(world.getEntities())
                .thenReturn(new ArrayList<>(Arrays.asList(bullet1, bullet2)));

        collision.process(gameData, world);


        verify(world, never()).removeEntity(bullet1);
        verify(world, never()).removeEntity(bullet2);
    }

    @Test
    void testEnemyDoesNotCollideWithAsteroid() {

        enemy.setX(0.0);
        enemy.setY(0.0);
        enemy.setRadius(10.0F);

        asteroid1.setX(0.0);
        asteroid1.setY(0.0);
        asteroid1.setRadius(15.0F);


        when(world.getEntities())
                .thenReturn(new ArrayList<>(Arrays.asList(enemy, asteroid1)));

        collision.process(gameData, world);


        verify(world, never()).removeEntity(enemy);
        verify(world, never()).removeEntity(asteroid1);
    }

    @Test
    void testAsteroidDoesNotCollideWithAsteroid() {


        asteroid1.setX(0.0);
        asteroid1.setY(0.0);
        asteroid1.setRadius(2.0F);

        asteroid2.setX(0.0);
        asteroid2.setY(0.0);
        asteroid2.setRadius(2.0F);


        when(world.getEntities())
                .thenReturn(new ArrayList<>(Arrays.asList(asteroid1, asteroid2)));

        collision.process(gameData, world);


        verify(world, never()).removeEntity(asteroid1);
        verify(world, never()).removeEntity(asteroid2);
    }


    @Test
    void testBulletHitsEnemy() {
        bullet1.setX(0.0);
        bullet1.setY(0.0);
        bullet1.setRadius(2.0F);

        enemy.setX(0.0);
        enemy.setY(0.0);
        enemy.setRadius(10.0F);
        enemy.setHp(2);

        when(world.getEntities())
                .thenReturn(new ArrayList<>(Arrays.asList(bullet1, enemy)));

        // Act
        collision.process(gameData, world);

        // Verify
        verify(world, atLeastOnce()).removeEntity(bullet1);
        verify(world, never()).removeEntity(enemy); // HP reduction doesn't kill enemy
    }

    @Test
    void testNoSelfCollision() {
        // Ensure entities do not collide with themselves
        player.setX(0.0);
        player.setY(0.0);
        player.setRadius(10.0F);

        when(world.getEntities()).thenReturn(Arrays.asList(player));

        collision.process(gameData, world);

        verify(world, never()).removeEntity(player);
    }


}
