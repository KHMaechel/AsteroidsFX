import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Enemy {
    exports dk.sdu.mmmi.cbse.enemy;
    requires Common;
    requires CommonBullet;
    requires CommonWeapon;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    uses dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;
    provides IGamePluginService with dk.sdu.mmmi.cbse.enemy.EnemyPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.enemy.EnemyControlSystem;

}