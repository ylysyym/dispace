package com.lrdwhyt.dispace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

  private Game game;
  private Stage stage;
  private Table table;

  public MainMenuScreen(Game g) {
    this.game = g;
    stage = new Stage(new ScreenViewport());
    table = new Table();
    BitmapFont robotoThin = new BitmapFont(Gdx.files.internal("roboto-thin.fnt"));
    robotoThin.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    TextButton.TextButtonStyle navigationButtonStyle = new TextButton.TextButtonStyle();
    navigationButtonStyle.font = robotoThin;
    navigationButtonStyle.fontColor = new Color(1, 1, 1, 1);
    navigationButtonStyle.downFontColor = new Color(0.6f, 0.6f, 0.6f, 0.6f);

    Label.LabelStyle gameTitleStyle = new Label.LabelStyle();
    gameTitleStyle.font = robotoThin;
    gameTitleStyle.font.getData().setScale(0.9f);

    Label gameTitle = new Label("DiSpacE", gameTitleStyle);
    gameTitle.setFontScale(1.2f);
    TextButton newGameButton = new TextButton("new game", navigationButtonStyle);
    newGameButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.setScreen(new GameScreen(game));
      }
    });
    TextButton loadGameButton = new TextButton("load game", navigationButtonStyle);
    TextButton optionsButton = new TextButton("options", navigationButtonStyle);
    TextButton aboutButton = new TextButton("about", navigationButtonStyle);
    table.add(gameTitle);
    table.row().row();
    table.add(newGameButton).padLeft(20).align(Align.left);
    table.row();
    table.add(loadGameButton).padLeft(20).align(Align.left);
    table.row();
    table.add(optionsButton).padLeft(20).align(Align.left);
    table.row();
    table.add(aboutButton).padLeft(20).align(Align.left);
    table.left().top();
    table.setFillParent(true);
    stage.addActor(table);
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0.3f, 0, 0.8f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act();
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
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
    stage.dispose();
  }
}
