package model;

public class PlayerData {
  private String name;
  private String role;

  public PlayerData(String name, String role) {
    this.name = name;
    this.role = role;
  }

  public String getName() {
    return name;
  }

  public String getRole() {
    return role;
  }
}
