package com.lrdwhyt.dispace;

public class NonPlayerCharacter implements Character {

  private String name;
  private Coords position;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Coords getPosition() {
    return position;
  }

  public void setPosition(Coords position) {
    this.position = position;
  }
}
