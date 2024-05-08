package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;


/**
 * Service interface for processing entities in the game world.
 */
public interface IEntityProcessingService {

    /**
     *
     * @param gameData Game data includes settings, screen dimensions and state.
     * @param world The world that contains entities. This method updates these entities based on the game logic.

     * Precondition: gameData and world must not be null
     *
     * Postcondition:
     * - Entities in the world may be updated (e.g. position, health).
     */
    void process(GameData gameData, World world);
}
