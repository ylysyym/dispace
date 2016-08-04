package com.lrdwhyt.dispace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dispace extends Game {

  public SpriteBatch spriteBatch;
  public BitmapFont robotoThin;

  @Override
  public void create() {
    spriteBatch = new SpriteBatch();
    robotoThin = new BitmapFont(Gdx.files.internal("roboto-thin.fnt"));
    robotoThin.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    Gdx.graphics.setContinuousRendering(false);
    this.setScreen(new MainMenuScreen(this));
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
    spriteBatch.dispose();
    robotoThin.dispose();
  }

}
