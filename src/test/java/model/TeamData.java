package model;

import java.util.ArrayList;
import java.util.List;

public class TeamData {
  private String name;
  private String short_name;
  private int index;
  List<PlayerData> team = new ArrayList<>();

  public TeamData(String name, int index){
    switch(name){
      case "FlyQuest":
        this.name = name;
        this.index = index;
        short_name = "FLY";
        addPlayer("Flame", "Top");
        addPlayer("AnDa", "Jungle");
        addPlayer("Fly", "Mid");
        addPlayer("WildTurtle", "Adc");
        addPlayer("Stunt", "Support");
        break;
      case "GIANTS":
        this.name = name;
        this.index = index;
        short_name = "GIA";
        addPlayer("Ruin", "Top");
        addPlayer("Djoko", "Jungle");
        addPlayer("Betsy", "Mid");
        addPlayer("Steeelback", "Adc");
        addPlayer("Targamas", "Support");
        break;
      case "Echo Fox":
        this.name = name;
        this.index = index;
        short_name = "FOX";
        addPlayer("Huni", "Top");
        addPlayer("Dardoch", "Jungle");
        addPlayer("Fenix", "Mid");
        addPlayer("Altec", "Adc");
        addPlayer("Adrian", "Support");
        break;
      case "SPLYCE":
        this.name = name;
        this.index = index;
        short_name = "SPY";
        addPlayer("Odoamne", "Top");
        addPlayer("Xerxe", "Jungle");
        addPlayer("Nisqy", "Mid");
        addPlayer("Kobbe", "Adc");
        addPlayer("Kasing", "Support");
        break;
      case "H2K":
        this.name = name;
        this.index = index;
        short_name = "H2K";
        addPlayer("Smittyj", "Top");
        addPlayer("Shook", "Jungle");
        addPlayer("Selfie", "Mid");
        addPlayer("Sheriff", "Adc");
        addPlayer("Promisq", "Support");
        break;
      case "FNATIC":
        this.name = name;
        this.index = index;
        short_name = "H2K";
        addPlayer("Soaz", "Top");
        addPlayer("Broxah", "Jungle");
        addPlayer("Caps", "Mid");
        addPlayer("Rekkles", "Adc");
        addPlayer("Hylissang", "Support");
        break;
      case "Cloud 9":
        this.name = name;
        this.index = index;
        short_name = "H2K";
        addPlayer("Licorice", "Top");
        addPlayer("Svenskeren", "Jungle");
        addPlayer("Jensen", "Mid");
        addPlayer("Sneaky", "Adc");
        addPlayer("Smoothie", "Support");
        break;
    }
  }

  private void addPlayer(String name, String role) {
    PlayerData player = new PlayerData(name,role);
    team.add(player);
  }

  public void setPlayer(int index, String name, String role) {
    PlayerData player = new PlayerData(name,role);
    team.set(index-1,player);
  }

  public String getName() {
    return name;
  }

  public String getShortName() {
    return short_name;
  }

  public int getIndex() {
    return index;
  }

  public List<PlayerData> getTeam() {
    return team;
  }

}
