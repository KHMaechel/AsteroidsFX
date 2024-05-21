package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class WeaponTwoBullets implements  WeaponSPI {


    @Override
    public void fire(Entity shooter, GameData gameData, World world) {
        getBulletSPIs()
                .forEach(bulletSPI -> world.addEntity(bulletSPI.createBullet(shooter, gameData, BulletSPI.Placement.LEFT)));
        getBulletSPIs()
                .forEach(bulletSPI -> world.addEntity(bulletSPI.createBullet(shooter, gameData, BulletSPI.Placement.RIGHT)));

    }

    @Override
    public int getNumberOfBulletsIdentifier() {
        return 2;
    }


    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}