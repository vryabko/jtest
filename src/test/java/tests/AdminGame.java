package tests;

import model.DraftData;

import model.GameData;
import model.TeamData;
import org.junit.*;

import java.awt.*;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;


public class AdminGame extends TestBase {
//private WebDriver browser;

  @Test
  public void testGameCreation() throws Exception {
    // app.getGameHelper().createGame("lol","GIANTS","FlyQuest","16","45");
    app.getGameHelper().createGame("lol", "FlyQuest", "GIANTS", "20", "30");
    Thread.sleep(3000);
    Assert.assertTrue(app.getGameHelper().doesGameExist("04.04.2018","20:30","FlyQuest","GIANTS"));
  }

  @Test
  public void testGameMonitor() throws AWTException, InterruptedException {
   app.browser.get("https://monitor-staging.wardapp.xyz/");
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game = app.getGameHelper().createGame("lol",team1,team2,10);
    //app.getGameHelper().validateStatus("Wards disabled");
   //TeamData team1 = new TeamData("FlyQuest");
   //TeamData team2 = new TeamData("GIANTS");
   //app.getGameHelper().verifyMainTeamPlayersInDraft(team1, team2);

    DraftData heroes1 = new DraftData("Ahri","Braum","Corki","Dr. Mundo","Warwick");
    DraftData heroes2 = new DraftData("Xerath","Yasuo","Taric","Sion","Riven");

    app.getGameHelper().setHeroes(game,team1,heroes1);
    app.getGameHelper().setHeroes(game,team2,heroes2);
    System.out.println("Success!");
  }


  }
