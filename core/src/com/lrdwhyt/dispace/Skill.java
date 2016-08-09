package com.lrdwhyt.dispace;

public enum Skill {

  MAPMAKING(1, "Mapmaking"), HARVESTING(2, "Harvesting");

  int id;
  String name;

  Skill(int id, String name) {
    this.id = id;
    this.name = name;
  }

  int getId() {
    return id;
  }

  Skill getById(int id) {
    for (Skill skill : Skill.values()) {
      if (skill.getId() == id) {
        return skill;
      }
    }
    return null;
  }

}
