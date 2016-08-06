package com.lrdwhyt.dispace;

import com.badlogic.gdx.math.Vector2;

/**
 * Used in movement animations
 */
public enum Direction {
  NORTH(0, 1f), NORTHEAST(1f, 1f), EAST(1f, 0), SOUTHEAST(1f, -1f), SOUTH(0, -1f), SOUTHWEST(-1f, 1f), WEST(-1f, 0), NORTHWEST(-1f, 1f);

  Vector2 vector;

  Direction(float x, float y) {
    vector = new Vector2(x, y);
  }

  Vector2 getVector() {
    return vector;
  }

}