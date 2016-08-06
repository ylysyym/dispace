package com.lrdwhyt.dispace;

import com.badlogic.gdx.math.Vector2;

public class PlayerCharacter implements Character {

  private String name;
  private Vector2 position;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Vector2 getPosition() {
    return position;
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }
}
