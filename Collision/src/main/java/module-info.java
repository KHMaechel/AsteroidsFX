import dk.sdu.mmmi.cbse.SplitClass;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.collisionsystem.Collision;

module Collision {
    requires Common;
    requires Asteroid;
    requires CommonBullet;
    requires Player;
    requires Enemy;
    provides IPostEntityProcessingService with Collision;
    provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService with SplitClass;
}