package com.lrdwhyt.dispace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dispace extends Game {

  public SpriteBatch spriteBatch;
  public BitmapFont bitmapFont;

  @Override
  public void create() {
    spriteBatch = new SpriteBatch();
    bitmapFont = new BitmapFont();
    this.setScreen(new GameScreen(this));
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
