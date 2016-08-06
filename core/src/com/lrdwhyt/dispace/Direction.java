package com.lrdwhyt.dispace;

import com.badlogic.gdx.math.Vector2;

/**
 * Used in movement animations
 */
public enum Direction {
  NORTH(0, 1), NORTHEAST(1, 1), EAST(1f, 0), SOUTHEAST(1, -1), SOUTH(0, -1), SOUTHWEST(-1, 1), WEST(-1, 0), NORTHWEST(-1, 1);

  Vector2 vector;

  Direction(float x, float y) {
    vector = new Vector2(x, y);
  }

  Vector2 getVector() {
    return vector;
  }

}