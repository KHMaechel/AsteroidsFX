import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;

module Weapon {
    requires Common;
    requires CommonBullet;
    requires CommonWeapon;
    requires Player;
    requires Enemy;
    provides IGamePluginService with dk.sdu.mmmi.cbse.weapon.WeaponPlugin;
    uses BulletSPI;
    provides WeaponSPI with dk.sdu.mmmi.cbse.weapon.WeaponPlugin;
}