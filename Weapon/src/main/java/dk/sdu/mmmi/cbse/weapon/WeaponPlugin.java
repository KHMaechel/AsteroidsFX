package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;


import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class WeaponPlugin implements IGamePluginService, WeaponSPI {


    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {

    }

    @Override
    public void fire(Entity shooter, GameData gameData, World world) {
        switch (shooter.getNumberOfWeapons()) {
            case 1:
                getBulletSPIs()
                        .forEach(bulletSPI -> world.addEntity(bulletSPI.createBullet(shooter, gameData, BulletSPI.Placement.CENTER)));
                break;
            case 2:
                getBulletSPIs()
                        .forEach(bulletSPI -> world.addEntity(bulletSPI.createBullet(shooter, gameData, BulletSPI.Placement.LEFT)));
                getBulletSPIs()
                        .forEach(bulletSPI -> world.addEntity(bulletSPI.createBullet(shooter, gameData, BulletSPI.Placement.RIGHT)));
                break;
            case 3:
                getBulletSPIs()
                        .forEach(bulletSPI -> world.addEntity(bulletSPI.createBullet(shooter, gameData, BulletSPI.Placement.CENTER)));
                getBulletSPIs()
                        .forEach(bulletSPI -> world.addEntity(bulletSPI.createBullet(shooter, gameData, BulletSPI.Placement.LEFT)));
                getBulletSPIs()
                        .forEach(bulletSPI -> world.addEntity(bulletSPI.createBullet(shooter, gameData, BulletSPI.Placement.RIGHT)));
                break;
            default:
                getBulletSPIs()
                        .forEach(bulletSPI -> world.addEntity(bulletSPI.createBullet(shooter, gameData, BulletSPI.Placement.CENTER)));
                break;
        }

    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}