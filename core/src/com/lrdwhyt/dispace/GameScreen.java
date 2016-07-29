package com.lrdwhyt.dispace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
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
import com.badlogic.gdx.math.Vector3;

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
  private Coords centerPosition;

  public GameScreen(Game game) {
    Gdx.graphics.setContinuousRendering(false);
    world = new World(WORLD_WIDTH, WORLD_HEIGHT);
    world.generate(1);
    drawMapFromWorld();
    camera = new OrthographicCamera();
    calibrateCamera();
    centerScreenOn(0, 0);
    touchHandler = new GestureDetector(new GestureDetector.GestureListener() {
      @Override
      public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
      }

      @Override
      public boolean tap(float x, float y, int count, int button) {
        centerScreenOn(getSpaceAtPosition(x, y));
        return false;
      }

      public void animateTo(Camera camera, int x, int y, float step) {
        if (camera.position.x != x && camera.position.y != y) {
          //camera.translate(new Vector3(x * step, y * step, 0));
          //animateTo(camera, x, y, step);
          Gdx.graphics.requestRendering();
        }
      }

      @Override
      public boolean longPress(float x, float y) {
        return false;
      }

      @Override
      public boolean fling(float velocityX, float velocityY, int button) {
        return false;
      }

      @Override
      public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
      }

      @Override
      public boolean panStop(float x, float y, int pointer, int button) {
        return false;
      }

      @Override
      public boolean zoom(float initialDistance, float distance) {
        return false;
      }

      @Override
      public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
      }

      @Override
      public void pinchStop() {
      }
    });
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
    cameraOffsetX = (-((camera.position.x * 2 - SPACE_SIZE) % (2 * SPACE_SIZE)) / 2 + SPACE_SIZE) % (SPACE_SIZE);
    cameraOffsetY = (-((camera.position.y * 2 - SPACE_SIZE) % (2 * SPACE_SIZE)) / 2 + SPACE_SIZE) % (SPACE_SIZE);
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

  /**
   * Calculates the coordinates of the space at the position (x, y).
   * Coordinates are for the world and its maps while positions (x, y) are positions on the screen
   */
  public Coords getSpaceAtPosition(float x, float y) {
    Vector3 tapPosition = camera.unproject(new Vector3(x, y, 0));
    System.out.println("tap coords: " + tapPosition);
    int cameraTranslateX = (int) Math.floor(tapPosition.x / SPACE_SIZE);
    int cameraTranslateY = (int) Math.floor(tapPosition.y / SPACE_SIZE);
    return new Coords(cameraTranslateX, cameraTranslateY);
  }

  public void centerScreenOn(int x, int y) {
    System.out.println(x + ", " + y);
    float newX = cameraOffsetX + x * SPACE_SIZE;
    float newY = cameraOffsetY + y * SPACE_SIZE;
    System.out.println(newX + ", " + newY);
    centerPosition = new Coords(x, y);
    camera.position.set(newX, newY, 0);
    camera.update();

  }

  public void centerScreenOn(Coords position) {
    centerScreenOn(position.getX(), position.getY());
  }

}
