package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;

import java.util.Collection;
import java.util.ServiceLoader;


import static java.util.stream.Collectors.toList;


public class PlayerControlSystem implements IEntityProcessingService {


    @Override
    public void process(GameData gameData, World world) {
        respawn( gameData, world);
        for (Entity player : world.getEntities(Player.class)) {
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 3);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 3);
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }

            if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
                getWeaponSPIs().stream()
                        .filter(spi -> player.getNumberOfWeapons() == (spi.getNumberOfBulletsIdentifier()))
                        .findFirst().ifPresent(spi -> {
                            spi.fire(player, gameData, world);
                        });
            }
            // Change weapon when you level up
            if (gameData.getLevel() == 3) {
                player.setNumberOfWeapons(2);
            }
            if (gameData.getLevel() == 6) {
                player.setNumberOfWeapons(3);
            }



            if (player.getX() < 0) {
                player.setX(1);
            }

            if (player.getX() > gameData.getDisplayWidth()) {
                player.setX(gameData.getDisplayWidth() - 1);
            }

            if (player.getY() < 0) {
                player.setY(1);
            }

            if (player.getY() > gameData.getDisplayHeight()) {
                player.setY(gameData.getDisplayHeight() - 1);
            }


        }
    }


    private Collection<? extends WeaponSPI> getWeaponSPIs() {
        return ServiceLoader.load(WeaponSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }


    private void respawn(GameData gameData, World world) {
        Player player;
        if (world.getEntities(Player.class).isEmpty()) {
            player = (Player) gameData.getPlayer();
            if (player.getLife() == 0) {
                gameData.setGameOver(true);
            }
            else {
                player.setX(gameData.getDisplayWidth() / 2);
                player.setY(gameData.getDisplayHeight() / 2);
                world.addEntity(gameData.getPlayer());
                player.setLife(player.getLife()-1);
                player.setHp(3);
                System.out.println("Life: " + player.getLife());
            }
        }
    }
}
