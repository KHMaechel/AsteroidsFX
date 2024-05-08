package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
/**
 * Interface used to handle the start and stop conditions of the game plugins.
*/

public interface IGamePluginService {

    /**
     * Initializes the game components.
     *
     * @param gameData Game data that includes settings, screen dimensions and state.
     * @param world The game world in which entities will be registered and managed.
     * Precondition: gameData and world must not be null and initialized.
     * Postcondition: All necessary game entities and components are added to the world.
     */
    void start(GameData gameData, World world);

    /**
     * Cleans up components and entities when the game stops.
     *
     * @param gameData The current game data.
     * @param world    The game world containing entities to be removed.
     * Precondition - gameData and world must contain data previously added
     * Postcondition - All entities associated with the plugin are removed from the game world.
     */
    void stop(GameData gameData, World world);
}
