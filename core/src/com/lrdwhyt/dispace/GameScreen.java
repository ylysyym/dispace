package com.lrdwhyt.dispace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
  private TiledMap map;
  private TiledMapRenderer mapRenderer;
  private OrthographicCamera camera;
  private final int SPACE_SIZE = 32;
  private final int MIN_NUMBER_OF_SPACES = 9;
  private final int WORLD_WIDTH = 100;
  private final int WORLD_HEIGHT = 100;
  private World world;
  private float cameraOffsetX;
  private float cameraOffsetY;
  private GestureDetector touchHandler;

  public GameScreen(Game game) {
    Gdx.graphics.setContinuousRendering(false);
    camera = new OrthographicCamera();
    calibrateCamera();
    world = new World(WORLD_WIDTH, WORLD_HEIGHT);
    world.generate(1);
    drawMapFromWorld();
    touchHandler = new GestureDetector(new TouchHandler(camera, SPACE_SIZE));
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();
    mapRenderer.setView(camera);
    mapRenderer.render();
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(touchHandler);
    Gdx.graphics.requestRendering();
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    map.dispose();
  }

  public void calibrateCamera() {
    float screenWidth = Gdx.app.getGraphics().getWidth();
    float screenHeight = Gdx.app.getGraphics().getHeight();
    if (screenWidth > screenHeight) {
      //Screen is landscape
      camera.setToOrtho(false, screenWidth / screenHeight * MIN_NUMBER_OF_SPACES * SPACE_SIZE, MIN_NUMBER_OF_SPACES * SPACE_SIZE);
    } else {
      //Screen is portrait
      camera.setToOrtho(false, MIN_NUMBER_OF_SPACES * SPACE_SIZE, screenHeight / screenWidth * MIN_NUMBER_OF_SPACES * SPACE_SIZE);
    }
    cameraOffsetX = ((camera.position.x * 2 - SPACE_SIZE) % (2 * SPACE_SIZE)) / 2;
    cameraOffsetY = ((camera.position.y * 2 - SPACE_SIZE) % (2 * SPACE_SIZE)) / 2;
    camera.translate(new Vector2(-cameraOffsetX + SPACE_SIZE, -cameraOffsetY + SPACE_SIZE));
    camera.update();
  }

  public void drawMapFromWorld() {
    map = new TiledMap();
    MapLayers mapLayers = map.getLayers();
    TiledMapTileLayer baseLayer = new TiledMapTileLayer(world.getWidth(), world.getHeight(), SPACE_SIZE, SPACE_SIZE);
    for (int x = 0; x < world.getWidth(); x++) {
      for (int y = 0; y < world.getHeight(); y++) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(world.getSpace(world.getAt(x, y))));
        baseLayer.setCell(x, y, cell);
      }
    }
    mapLayers.add(baseLayer);
    mapRenderer = new OrthogonalTiledMapRenderer(map);
  }

}
