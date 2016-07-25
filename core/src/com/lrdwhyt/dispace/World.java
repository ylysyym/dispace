package com.lrdwhyt.dispace;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class World extends Map {

  private SpaceSet spaceSet;

  public World(int width, int height) {
    super(width, height);
    spaceSet = new SpaceSet(1);
  }

  public void generate(int seed) {
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        int type = (int) (Math.random() * spaceSet.getLength());
        setAt(x, y, type);
      }
    }
  }

  public TextureRegion getSpace(int type) {
    return spaceSet.getSpaceType(type);
  }

}
