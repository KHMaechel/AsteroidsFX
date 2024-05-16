package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IUIProcessingService;
import dk.sdu.mmmi.cbse.common.util.SPILocator;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.util.stream.Collectors.toList;

public class Main extends Application {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {

        //Pane gameWindow = new Pane();
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
            if (event.getCode().equals(KeyCode.ENTER)) {
                gameData.getKeys().setKey(GameKeys.ENTER, true);
            }
            if (event.getCode().equals(KeyCode.P)) {
                gameData.getKeys().setKey(GameKeys.P, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
            if (event.getCode().equals(KeyCode.ENTER)) {
                gameData.getKeys().setKey(GameKeys.ENTER, false);
            }
            if (event.getCode().equals(KeyCode.P)) {
                gameData.getKeys().setKey(GameKeys.P, false);
            }

        });

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }

        for (IUIProcessingService uiProcessingService : getUIProcessingServices()) {
            uiProcessingService.generate(gameData, gameWindow);
        }

        render();

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();

    }

    private void render() {
        new AnimationTimer() {
            private long then = 0;

            @Override
            public void handle(long now) {
                update();
                draw();
                removeMissingEntities();
                gameData.getKeys().update();
            }

        }.start();
    }

    private void update() {

        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
        for (IUIProcessingService uiProcessingService : getUIProcessingServices()) {
            uiProcessingService.process(gameData, gameWindow);
        }

        if (gameData.isGameOver()) {
            for (IGamePluginService iGamePluginService : getPluginServices()) {
                iGamePluginService.stop(gameData, world);
            }
        }

        if (gameData.isNewGame()) {
            gameData.setNewGame(false);
            for (IGamePluginService iGamePlugin : getPluginServices()) {
                iGamePlugin.start(gameData, world);
            }
        }
        // has to be executed after the iGamePlugin.start loop
        if (!gameData.isGamePaused() && !gameData.isGameOver()) {
            for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
                entityProcessorService.process(gameData, world);
            }
        }

    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }
    }

    // Removes entities from JavaFX gameWindow and polygons map, if they are not present in the 'world'
    private void removeMissingEntities() {
        for (Entity entity : polygons.keySet()) {
            if (!world.getEntities().contains(entity)) {
                gameWindow.getChildren().remove(polygons.get(entity));
                polygons.remove(entity);
            }
        }
    }





    private Collection<? extends IGamePluginService> getPluginServices() {
        return SPILocator.getInstance().locateAllSPIs(IGamePluginService.class);
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.getInstance().locateAllSPIs(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return SPILocator.getInstance().locateAllSPIs(IPostEntityProcessingService.class);
    }
    private Collection<? extends IUIProcessingService> getUIProcessingServices() {
        return ServiceLoader.load(IUIProcessingService.class).stream().map(ServiceLoader.Provider::get)
                .collect(toList());
    }
}
