package tests;

import model.GameData;
import model.TeamData;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.awt.*;
import java.io.IOException;

public class MatchWardFirstBlood extends TestBase {
  @Test
  @DisplayName("1. Test Enter Match. Check FIRST BLOOD copyrights")
  public void testEnterMatch() throws InterruptedException, AWTException, IOException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game = app.getGameHelper().createGame("lol",team1,team2,4);
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
    //Finish game
    app.getGameHelper().finishGame(game,team1);
  }

  @Test
  @DisplayName("2. Test Enter Match, Exit match and Return to the game.")
  public void testReEnterToMatch() throws InterruptedException, AWTException, IOException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game = app.getGameHelper().createGame("lol",team1,team2,3);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().selectGame(game);
    //Confirm draft in monitor
    app.getGameHelper().setSomeDraft(game);
    //Wait game time and proceed with empty draft
    app.draft().waitAndProceedEmptyDraft();
    //Start game in monitor
    app.getGameHelper().startGame(game);
    //Enter game and go back
    app.draft().enterMatch();
    app.match().backToMatchfeedFromMatch();
    //Join the game
    app.match().selectGame(game);
    //Land on the First Blood screen
    app.match().firstBloodCopyrights();
    //System.out.println(app.driver.getPageSource());
    //Finish game
    app.getGameHelper().finishGame(game,team1);
  }

  @Test
  @DisplayName("3. Test First Blood Ward Screen #1. Check teams to select.")
  public void testTeamInFirstBlood() throws InterruptedException, AWTException, IOException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game = app.getGameHelper().createGame("lol",team1,team2,3);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().selectGame(game);
    //Confirm draft in monitor
    app.getGameHelper().setSomeDraft(game);
    //Wait game time and proceed with empty draft
    app.draft().waitAndProceedEmptyDraft();
    //Start game in monitor
    app.getGameHelper().startGame(game);
    //Enter game and go back
    app.draft().enterMatch();
    //Go to Ward selection
    app.match().tapWardIt();
    app.match().closeFirstBlood();
    app.match().tapWardIt();
    //Check First Blood 1 screen
    app.match().firstBloodWardCopy();
    app.match().checkTeamsInFirstBloodWard(team1,team2);
    //System.out.println(app.driver.getPageSource());
    //Finish game
    app.getGameHelper().finishGame(game,team1);
  }

  @Test
  @DisplayName("4. Test First Blood Ward Screen #1. Select FB champion. Select Line (MID)")
  public void testHeroSelectionInFirstBlood() throws InterruptedException, AWTException, IOException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game = app.getGameHelper().createGame("lol",team1,team2,3);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().selectGame(game);
    //Confirm draft in monitor
    app.getGameHelper().setSomeDraft(game);
    //Wait game time and proceed with empty draft
    app.draft().waitAndProceedEmptyDraft();
    //Start game in monitor
    app.getGameHelper().startGame(game);
    //Enter game and go back
    app.draft().enterMatch();
    //Go to Ward selection
    app.match().tapWardIt();
    //Check First Blood 1 screen
    app.match().selectFirstBloodChampion(team1,1);
    //Unselect this player
    app.match().selectFirstBloodChampion(team1,1);
    //Select another player
    app.match().selectFirstBloodChampion(team2,4);
    app.match().wardIt();
    app.match().tapPickLine("mid");
    app.match().verifyPicksOnScreen();
    System.out.println(app.driver.getPageSource());
    //Finish game
    app.getGameHelper().finishGame(game,team1);
  }

  @Test
  @DisplayName("5. Test First Blood Ward Screen #1. Success ward.")
  public void testFirstBloodSuccess() throws InterruptedException, AWTException, IOException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game = app.getGameHelper().createGame("lol",team1,team2,2);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().selectGame(game);
    //Confirm draft in monitor
    app.getGameHelper().setSomeDraft(game);
    //Wait game time and proceed with empty draft
    app.draft().waitAndProceedEmptyDraft();
    //Start game in monitor
    app.getGameHelper().startGame(game);
    //Enter game and go back
    app.draft().enterMatch();
    //Go to Ward selection
    app.match().tapWardIt();
    //Check First Blood 1 screen
    app.match().selectFirstBloodChampion(team1,1);
    app.match().tapPickLine("mid");
    //System.out.println(app.driver.getPageSource());
    //Finish game
    app.getGameHelper().finishGame(game,team1);
  }

  @Test
  @DisplayName("6. First Blood Ward is Missed by Timeout. ")
  public void testFirstBloodMissedByTimeout() throws InterruptedException, AWTException, IOException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game = app.getGameHelper().createGame("lol",team1,team2,2);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().selectGame(game);
    //Confirm draft in monitor
    app.getGameHelper().setSomeDraft(game);
    //Wait game time and proceed with empty draft
    app.draft().waitAndProceedEmptyDraft();
    //Start game in monitor
    app.getGameHelper().startGame(game);
    //Enter game and go back
    app.draft().enterMatch();
    app.getGameHelper().setFirstBloodWard(team1,1,"mid");
    //Go to Ward selection
    app.match().tapWardIt();
    //Check First Blood 1 screen
    app.match().selectFirstBloodChampion(team1,1);
    app.match().tapPickLine("mid");
    //System.out.println(app.driver.getPageSource());
    //Finish game
    app.getGameHelper().finishGame(game,team1);
  }
}
