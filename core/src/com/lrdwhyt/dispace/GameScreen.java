package com.lrdwhyt.dispace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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
  private InputMultiplexer inputHandler;
  private GestureDetector touchHandler;
  private Dispace game;

  public GameScreen(Dispace g) {
    this.game = g;
    world = new World(WORLD_WIDTH, WORLD_HEIGHT);
    world.generate(1);
    drawMapFromWorld();
    camera = new OrthographicCamera();
    calibrateCamera();
    centerScreenOn(0, 0);
    inputHandler = new InputMultiplexer();
    touchHandler = new GestureDetector(new GestureDetector.GestureListener() {
      @Override
      public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
      }

      @Override
      public boolean tap(float x, float y, int count, int button) {
        Coords destinationPoint = getSpaceAtPosition(x, y);
        if (destinationPoint != null) {
          centerScreenOn(destinationPoint);
        }
        return false;
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
    InputProcessor backHandler = new InputAdapter() {
      @Override
      public boolean keyUp(int keyCode) {
        if (keyCode == Input.Keys.BACK) {
          dispose();
          game.setScreen(new MainMenuScreen(game));
          return true;
        }
        return false;
      }
    };
    inputHandler.addProcessor(touchHandler);
    inputHandler.addProcessor(backHandler);
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
    Gdx.input.setCatchBackKey(true);
    Gdx.input.setInputProcessor(inputHandler);
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
    Vector3 positionTap = camera.unproject(new Vector3(x, y, 0));
    int xSpace = (int) Math.floor(positionTap.x / SPACE_SIZE);
    int ySpace = (int) Math.floor(positionTap.y / SPACE_SIZE);
    if (world.containsPoint(xSpace, ySpace)) {
      return new Coords(xSpace, ySpace);
    } else {
      return null;
    }
  }

  public void centerScreenOn(int x, int y) {
    int xCamera = x * SPACE_SIZE + SPACE_SIZE / 2;
    int yCamera = y * SPACE_SIZE + SPACE_SIZE / 2;
    camera.position.set(xCamera, yCamera, 0);
    camera.update();

  }

  public void centerScreenOn(Coords position) {
    centerScreenOn(position.getX(), position.getY());
  }

}
