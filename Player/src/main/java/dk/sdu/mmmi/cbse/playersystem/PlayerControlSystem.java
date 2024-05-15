package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
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
                getWeaponSPIs().stream().findFirst().ifPresent(spi -> {
                    spi.fire(player, gameData, world);
                });
            }
            if (getLevel() == 3) {
                player.setNumberOfWeapons(2);
            }
            if (getLevel() == 6) {
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

    private int getLevel() {
        URL url;
        int score;
        try {
            url = new URL("http://localhost:8080/level");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();
            score = Integer.parseInt(String.valueOf(response));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return score;
    }

    private void respawn(GameData gameData, World world) {
        Player player = null;
        if (world.getEntities(Player.class).isEmpty()) {
            player = (Player) gameData.getPlayer();
            if (player.getLife() == 0) {
                return;
            }
            else {
                player.setX(gameData.getDisplayWidth() / 2);
                player.setY(gameData.getDisplayHeight() / 2);
                world.addEntity(gameData.getPlayer());
                player.setLife(player.getLife()-1);
                System.out.println("Life: " + player.getLife());
            }
        }
    }
}
