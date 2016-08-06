package com.lrdwhyt.dispace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
  private TiledMap map;
  private TiledMapRenderer mapRenderer;
  private OrthographicCamera camera;
  private final int SPACE_SIZE = 32;
  private final int MIN_NUMBER_OF_SPACES = 9;
  private final int WORLD_WIDTH = 100;
  private final int WORLD_HEIGHT = 100;
  private World world;
  private GestureDetector touchHandler;
  private Dispace game;
  private Stage hudStage;
  private Table hudTable;
  private Table tooltipTable;
  private Label tooltip;

  public GameScreen(Dispace g) {
    this.game = g;
    hudStage = new Stage(new ScreenViewport());
    hudTable = new Table();
    tooltipTable = new Table();
    tooltipTable.setBackground(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("pixel.png")))).tint(new Color(0.3f, 0.3f, 0.3f, 0.8f)));
    Label.LabelStyle tooltipStyle = new Label.LabelStyle();
    tooltipStyle.font = game.robotoThin;
    tooltipStyle.font.getData().setScale(0.25f);
    tooltipStyle.fontColor = new Color(0.8f, 0.5f, 0, 1);
    tooltip = new Label("", tooltipStyle);
    tooltipTable.center().top();
    tooltipTable.add(tooltip);
    hudTable.center().top();
    hudTable.add(tooltipTable).height(120f).width(Gdx.graphics.getWidth());
    hudTable.setFillParent(true);
    hudStage.addActor(hudTable);
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
        Vector2 destinationPoint = getSpaceAtPosition(x, y);
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
  }

  @Override
  public void render(float delta) {
    if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
      game.setScreen(new MainMenuScreen(game));
    }
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();
    mapRenderer.setView(camera);
    mapRenderer.render();
    hudStage.draw();
  }

  @Override
  public void show() {
    Gdx.input.setCatchBackKey(true);
    Gdx.input.setInputProcessor(touchHandler);
    Gdx.graphics.requestRendering();
  }

  @Override
  public void resize(int width, int height) {
    hudStage.getViewport().update(width, height, true);
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
  public Vector2 getSpaceAtPosition(float x, float y) {
    Vector3 positionTap = camera.unproject(new Vector3(x, y, 0));
    int xSpace = (int) Math.floor(positionTap.x / SPACE_SIZE);
    int ySpace = (int) Math.floor(positionTap.y / SPACE_SIZE);
    if (world.containsPoint(xSpace, ySpace)) {
      return new Vector2(xSpace, ySpace);
    } else {
      return null;
    }
  }

  public void centerScreenOn(float x, float y) {
    float xCamera = x * SPACE_SIZE + SPACE_SIZE / 2;
    float yCamera = y * SPACE_SIZE + SPACE_SIZE / 2;
    camera.position.set(xCamera, yCamera, 0);
    tooltip.setText("(" + x + ", " + y + ")");
    camera.update();

  }

  public void centerScreenOn(Vector2 position) {
    centerScreenOn(position.x, position.y);
  }

}
