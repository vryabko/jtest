package tests;

import model.GameData;
import model.TeamData;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.awt.*;
import java.io.IOException;

public class MatchWardsCommon extends TestBase {

  @Test
  @DisplayName("1. Test availability of all Wards.")
  public void testAllWardsAvailability() throws InterruptedException, AWTException, IOException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game = app.getGameHelper().createGame("lol",team1,team2,3);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().selectGame(game);
    //Proceed with empty draft
    app.draft().waitAndProceedEmptyDraft();
    //Confirm draft in monitor
    app.getGameHelper().setSomeDraft(game);
    app.getGameHelper().startGame(game);
    app.draft().enterMatch();
    //First ward
    app.match().commonMatchCopyrights(game);
    app.match().firstBloodCopyrights();
    //Swipe to the second ward and check it
    app.match().tapArrow("right");
    app.match().copywrites("Tower");
    app.match().wardIsBlocked();
    //Swipe to the third ward and check it
    app.match().tapArrow("right");
    app.match().copywrites("Dragon");
    app.match().wardIsBlocked();
    //Swipe to the fourth ward and check it
    app.match().tapArrow("right");
    app.match().copywrites("Herald");
    app.match().wardIsBlocked();
    //Swipe to the fifth ward and check it
    app.match().tapArrow("right");
    app.match().copywrites("Baron");
    app.match().wardIsBlocked();
    //Swipe to the sixth ward and check it
    app.match().tapArrow("right");
    app.match().copywrites("Pentakill");
    app.match().wardIsBlocked();
    //Swipe to the seven ward and check it
    app.match().tapArrow("right");
    app.match().copywrites("ACE");
    app.match().wardIsBlocked();
    //Swipe to the eight ward and check it
    app.match().tapArrow("right");
    app.match().copywrites("Inhibitor");
    app.match().wardIsBlocked();
    //Swipe to the nine ward and check it
    app.match().tapArrow("right");
    app.match().copywrites("WinGame");
    app.match().wardIsBlocked();
    //Finish game
    app.getGameHelper().finishGame(game,team1);
  }

  @Test
  @DisplayName("2. Test opening of wards. Wait for pushes and tap on them.")
  public void testOpenningOfWards() throws InterruptedException, AWTException, IOException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game = app.getGameHelper().createGame("lol",team1,team2,3);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().selectGame(game);
    //Proceed with empty draft
    app.draft().waitAndProceedEmptyDraft();
    //Confirm draft in monitor
    app.getGameHelper().setSomeDraft(game);
    app.getGameHelper().startGame(game);
    app.draft().enterMatch();
    //First ward
    app.match().commonMatchCopyrights(game);
    app.match().firstBloodCopyrights();
    //Swipe to the second ward and check it
    app.match().waitPush("Tower");
    app.match().waitPush("Dragon");
    app.match().waitPush("Herald");
    app.match().waitPush("Baron");
    app.match().waitPush("Pentakill");
    app.match().waitPush("ACE");
    app.match().waitPush("Inhibitor");
    app.match().waitPush("WinGame");
    //Finish game
    app.getGameHelper().finishGame(game,team1);
  }
}
