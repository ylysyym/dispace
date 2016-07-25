package com.lrdwhyt.dispace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpaceSet {

  private int type;
  private final int length;
  private Texture texture;
  private TextureRegion[][] textureRegion;

  public SpaceSet(int type) {
    this.type = type;
    switch (type) {
      case 1:
        texture = new Texture(Gdx.files.internal("spaces2.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        textureRegion = TextureRegion.split(texture, 32, 32);
        length = textureRegion[0].length;
        break;
      default:
        length = 0;
    }
  }

  public TextureRegion getSpaceType(int type) {
    return textureRegion[0][type];
  }

  public int getLength() {
    return length;
  }

}
