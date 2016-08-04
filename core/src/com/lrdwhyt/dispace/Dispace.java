package com.lrdwhyt.dispace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dispace extends Game {

  private Game game;
  public SpriteBatch spriteBatch;
  public BitmapFont bitmapFont;

  public Dispace() {
    game = this;
  }

  @Override
  public void create() {
    spriteBatch = new SpriteBatch();
    bitmapFont = new BitmapFont();
    Gdx.graphics.setContinuousRendering(false);
    this.setScreen(new MainMenuScreen(game));
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
    spriteBatch.dispose();
    bitmapFont.dispose();
  }

}
