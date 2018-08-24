package model;

import java.util.ArrayList;
import java.util.List;

public class DraftData {
  private List<String> draft = new ArrayList<>();

  public DraftData(String first, String second, String third, String fourth, String fifth) {
    this.draft.add(first);
    this.draft.add(second);
    this.draft.add(third);
    this.draft.add(fourth);
    this.draft.add(fifth);
  }
  public List<String> getDraft() {
    return draft;
  }

  public void setHero(int position, String name){
    this.draft.set(position-1,name);
  }
}
