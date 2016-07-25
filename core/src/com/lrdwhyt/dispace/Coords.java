package com.lrdwhyt.dispace;

/**
 * Object for storing (x, y) pairs
 */
public class Coords {

  final private int x;
  final private int y;

  public Coords(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Coords(int index, int mapWidth, boolean createFromIndex) {
    x = index % mapWidth;
    y = index / mapWidth;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getIndex(int mapWidth) {
    return getY() * mapWidth + getX();
  }

  /**
   * Returns whether a point is due <direction> of this point
   */
  public boolean isDueDirection(Coords point, Direction direction) {
    if (equals(point)) {
      return false;
    }
    if (direction == Direction.NORTH || direction == Direction.SOUTH) {
      if (getX() != point.getX()) {
        return false;
      } else {
        if (direction == Direction.NORTH && getY() > point.getY()) {
          return true;
        } else if (direction == Direction.SOUTH && getY() < point.getY()) {
          return true;
        } else {
          return false;
        }
      }
    } else if (direction == Direction.EAST || direction == Direction.WEST) {
      if (getY() != point.getY()) {
        return false;
      } else {
        if (direction == Direction.EAST && getX() > point.getX()) {
          return true;
        } else if (direction == Direction.WEST && getX() < point.getX()) {
          return true;
        } else {
          return false;
        }
      }
    } else {
      if (Math.abs(point.getX() - getX()) == Math.abs(point.getY() - getY())) {
        if (direction == Direction.NORTHEAST && getY() > point.getY() && getX() < point.getX()) {
          return true;
        } else if (direction == Direction.SOUTHEAST && getY() < point.getY() && getX() < point.getX()) {
          return true;
        } else if (direction == Direction.SOUTHWEST && getY() < point.getY() && getX() > point.getX()) {
          return true;
        } else if (direction == Direction.NORTHWEST && getY() > point.getY() && getX() > point.getX()) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
  }

  /**
   * Returns direction from this to other, with the 4 cardinal directions only returned if they are due direction.
   */
  public Direction getSlantDir(Coords other) {
    if (this.equals(other)) {
      return null;
    } else {
      if (getX() == other.getX()) {
        if (getY() > other.getY()) {
          return Direction.NORTH;
        } else {
          return Direction.SOUTH;
        }
      } else if (getY() == other.getY()) {
        if (getX() > other.getX()) {
          return Direction.WEST;
        } else {
          return Direction.EAST;
        }
      } else {
        if (getY() > other.getY()) {
          if (getX() > other.getX()) {
            return Direction.NORTHWEST;
          } else {
            return Direction.NORTHEAST;
          }
        } else {
          if (getX() > other.getX()) {
            return Direction.SOUTHWEST;
          } else {
            return Direction.SOUTHEAST;
          }
        }
      }
    }
  }

  public Coords getPointInDirection(Direction dir) {
    switch (dir) {
      case NORTH:
        return new Coords(getX(), getY() - 1);
      case NORTHEAST:
        return new Coords(getX() + 1, getY() - 1);
      case EAST:
        return new Coords(getX() + 1, getY());
      case SOUTHEAST:
        return new Coords(getX() + 1, getY() + 1);
      case SOUTH:
        return new Coords(getX(), getY() + 1);
      case SOUTHWEST:
        return new Coords(getX() - 1, getY() + 1);
      case WEST:
        return new Coords(getX() - 1, getY());
      case NORTHWEST:
        return new Coords(getX() - 1, getY() - 1);
    }
    return null;
  }

  public boolean equals(Coords coords) {
    if (getX() == coords.getX() && getY() == coords.getY()) {
      return true;
    }
    return false;
  }

}