package dk.sdu.mmmi.cbse.common.weapon;


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface WeaponSPI {
    void fire(Entity owner, GameData gameData, World world);
    int getNumberOfBulletsIdentifier();
}

