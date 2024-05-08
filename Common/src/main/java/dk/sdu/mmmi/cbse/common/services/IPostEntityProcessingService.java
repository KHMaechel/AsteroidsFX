package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Service interface used for post-processing entities, e.g. collision detection.
 */
public interface IPostEntityProcessingService {

    /**
     * @param gameData Game data includes settings, screen dimensions and state.
     * @param world The game world contains entities to be post-processed.
     *
     * Precondition: gameData and world must not be null.
     * Postcondition: Entities in the world may be updated (collision, removing entities).
     */
    void process(GameData gameData, World world);
}
