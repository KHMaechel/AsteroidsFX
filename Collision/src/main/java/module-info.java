import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires Asteroid;
    requires CommonBullet;
    requires Player;
    requires Enemy;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.Collision;
}