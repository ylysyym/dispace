package com.lrdwhyt.dispace;

import com.badlogic.gdx.math.Vector2;

public class Map {

  private final int width;
  private final int height;
  private final int[][] mapArray;

  public Map(int width, int height) {
    this.width = width;
    this.height = height;
    mapArray = new int[width][height];
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int toX(int index) {
    return index % getWidth();
  }

  public int toY(int index) {
    return index / getWidth();
  }

  public int toIndex(int x, int y) {
    return y * getWidth() + x;
  }

  public int getAt(int x, int y) {
    return mapArray[x][y];
  }

  public void setAt(int x, int y, int value) {
    mapArray[x][y] = value;
  }

  /**
   * Checks if map contains point within its bounds
   */
  public boolean containsPoint(Vector2 point) {
    if (point.x < 0 || point.y < 0 || point.x >= getWidth() || point.y >= getHeight()) {
      return false;
    } else {
      return true;
    }
  }

  public boolean containsPoint(int x, int y) {
    if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Returns point that is <direction> of (x, y)
   * Returns null if calculated point is not within the map bounds
   */
  public Vector2 getPointInDirection(Vector2 point, Direction direction) {
    point.add(direction.getVector());
    if (containsPoint(point)) {
      return point;
    } else {
      return null;
    }
  }

}
