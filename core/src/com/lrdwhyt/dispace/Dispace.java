package com.lrdwhyt.dispace;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Dispace extends ApplicationAdapter {
  private TiledMap map;
  private TiledMapRenderer mapRenderer;
  private OrthographicCamera camera;
  private final int TILE_SIZE = 32;
  private final int MIN_NUMBER_OF_TILES = 8;
  private final int WORLD_WIDTH = 100;
  private final int WORLD_HEIGHT = 100;
  private World world;
  private Stage stage;
  float offset_x;
  float offset_y;


  @Override
  public void create() {

    Gdx.graphics.setContinuousRendering(false);
    camera = new OrthographicCamera();
    calibrateCam();
    camera.translate(new Vector3(-offset_x + TILE_SIZE, -offset_y + TILE_SIZE, 0f));
    camera.update();

    world = new World(WORLD_WIDTH, WORLD_HEIGHT);
    world.generate(1);
    map = new TiledMap();
    MapLayers layers = map.getLayers();
    TiledMapTileLayer layer = new TiledMapTileLayer(WORLD_WIDTH, WORLD_HEIGHT, TILE_SIZE, TILE_SIZE);
    for (int x = 0; x < world.getWidth(); x++) {
      for (int y = 0; y < world.getHeight(); y++) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(world.getSpace(world.getAt(x, y))));
        layer.setCell(x, y, cell);
      }
    }
    layers.add(layer);
    mapRenderer = new OrthogonalTiledMapRenderer(map);
    InputHandler inputHandler = new InputHandler();
    Gdx.input.setInputProcessor(inputHandler);
    Gdx.graphics.requestRendering();

  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();
    //stage.act();

    /*if (Gdx.input.isTouched()) {
      camera.translate((Gdx.input.getX() - Gdx.graphics.getWidth() / 2) / TILE_SIZE, (Gdx.input.getY() - Gdx.graphics.getHeight() / 2) / -TILE_SIZE);
      System.out.println(Gdx.input.getX() + ", " + Gdx.input.getY());
    }*/

    mapRenderer.setView(camera);
    mapRenderer.render();
  }

  @Override
  public void dispose() {
    map.dispose();
  }

  public void calibrateCam() {
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    if (screenWidth > screenHeight) {
      camera.setToOrtho(false, screenWidth / screenHeight * MIN_NUMBER_OF_TILES * TILE_SIZE, MIN_NUMBER_OF_TILES * TILE_SIZE);
    } else {
      camera.setToOrtho(false, MIN_NUMBER_OF_TILES * TILE_SIZE, screenHeight / screenWidth * MIN_NUMBER_OF_TILES * TILE_SIZE);
    }

    System.out.println(camera.project(new Vector3(screenWidth, screenHeight, 0f)));

    offset_x = ((camera.position.x * 2 - TILE_SIZE) % (2 * TILE_SIZE)) / 2;
    offset_y = ((camera.position.y * 2 - TILE_SIZE) % (2 * TILE_SIZE)) / 2;
  }

}
