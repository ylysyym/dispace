package com.lrdwhyt.dispace;

public enum Item {

  WOOD(1, "Wood", true);

  int id;
  String name;
  boolean isUnique;

  Item(int id, String name, boolean isUnique) {
    this.id = id;
    this.name = name;
    this.isUnique = isUnique;
  }

  int getId() {
    return id;
  }

  Item getById(int id) {
    for (Item item : Item.values()) {
      if (item.getId() == id) {
        return item;
      }
    }
    return null;
  }

}
