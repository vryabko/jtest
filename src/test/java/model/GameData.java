package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GameData {
  private TeamData team1;
  private TeamData team2;
  private String time; // format 6:12 PM
  private String full_time; // 18:12
  private int hours;
  private int minutes;
  private String date;

  public GameData(TeamData team1, TeamData team2, String time) {
    this.team1 = team1;
    this.team2 = team2;
    this.time = time;
  }

  public void setHours(int hours) {
    this.hours = hours;

  }

  public void setMinutes(int minutes) {
    this.minutes = minutes;
  }

  public GameData(TeamData team1, TeamData team2, int hours, int minutes) {
    this.team1 = team1;
    this.team2 = team2;
    this.hours = hours;
    this.minutes = minutes;
    setTime(hours,minutes);
    setFullTime(hours,minutes);
    setDate();
  }

  private void setDate() {
    Calendar cal = Calendar.getInstance();
    this.date = new SimpleDateFormat("dd.MM.yyyy").format(cal.getTime());
  }

  public void setTime(int hours,int minutes){
    this.hours = hours;
    this.minutes = minutes;
    if (hours<12 && hours>0)
      if (minutes<10)
        this.time = String.valueOf(hours)+":"+"0"+String.valueOf(minutes)+" AM";
      else
        this.time = String.valueOf(hours)+":"+String.valueOf(minutes)+" AM";
    else if (hours>12)
      if (minutes<10)
        this.time = String.valueOf(hours-12)+":"+"0"+String.valueOf(minutes)+" PM";
      else
        this.time = String.valueOf(hours-12)+":"+String.valueOf(minutes)+" PM";
      //Midday
    if (hours==12)
      if (minutes<10)
        this.time = String.valueOf(hours)+":"+"0"+String.valueOf(minutes)+" PM";
      else
        this.time = String.valueOf(hours)+":"+String.valueOf(minutes)+" PM";
      //Midnight
    if (hours==0)
      if (minutes<10)
        this.time = "12:"+"0"+String.valueOf(minutes)+" AM";
      else
        this.time = "12:"+String.valueOf(minutes)+" AM";
    setFullTime(hours,minutes);
  }

  private void setFullTime(int hours, int minutes) {
    if (minutes<10)
      this.full_time = hours+":0"+minutes;
    else this.full_time = hours+":"+minutes;
  }

  public TeamData getTeam1() {
    return team1;
  }

  public TeamData getTeam2() {
    return team2;
  }

  public String getTime() {
    return time;
  }

  public String getFullTime() {
    return full_time;
  }

  public int getHours() {
    return hours;
  }

  public int getMinutes() {
    return minutes;
  }

  public String getDate() {
    return date;
  }
}
