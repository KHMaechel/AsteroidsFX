import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;
import dk.sdu.mmmi.cbse.weapon.WeaponControlSystem;

module Weapon {
    requires Common;
    requires CommonBullet;
    requires CommonWeapon;
    requires Player;
    requires Enemy;
    uses BulletSPI;
    provides WeaponSPI with WeaponControlSystem;
}