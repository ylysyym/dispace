package com.lrdwhyt.dispace;

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
  public boolean containsPoint(Coords point) {
    if (point.getX() < 0 || point.getY() < 0 || point.getX() >= getWidth() || point.getY() >= getHeight()) {
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
  public Coords getPointInDirection(int x, int y, Direction direction) {
    switch (direction) {
      case NORTH:
        if (y > 0) {
          return new Coords(x, y - 1);
        } else {
          return null;
        }
      case NORTHEAST:
        if (y > 0 && x < getWidth()) {
          return new Coords(x + 1, y - 1);
        } else {
          return null;
        }
      case EAST:
        if (x < getWidth()) {
          return new Coords(x + 1, y);
        } else {
          return null;
        }
      case SOUTHEAST:
        if (y < getHeight() && x < getWidth()) {
          return new Coords(x + 1, y + 1);
        } else {
          return null;
        }
      case SOUTH:
        if (y < getHeight()) {
          return new Coords(x, y + 1);
        } else {
          return null;
        }
      case SOUTHWEST:
        if (y < getHeight() && x > 0) {
          return new Coords(x - 1, y + 1);
        } else {
          return null;
        }
      case WEST:
        if (x > 0) {
          return new Coords(x - 1, y);
        } else {
          return null;
        }
      case NORTHWEST:
        if (y > 0 && x > 0) {
          return new Coords(x - 1, y - 1);
        } else {
          return null;
        }
    }
    return null;
  }

}
