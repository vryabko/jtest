package tests;

import model.DraftData;
import model.GameData;
import model.TeamData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.awt.*;

public class Matchfeed extends TestBase {
  @Test
  @DisplayName("1. Test Matchscreen. That particular game exists in the list and has 'draft' label")
  public void testMatchscreen () throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("FlyQuest",1);
    TeamData team2 = new TeamData("GIANTS",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,5);
    //Login as registered user
    app.navigation().tapSignInButton();
    app.form().proceedPhone("1745778393", "Germany");
    //app.form().waitForCode();
    app.form().enterCode("198412");
    app.form().NextBtn.click();
    //Land on Matchscreen
    app.match().allowNotifications();
    app.match().verifyMatchfeedTitle();
    app.match().verifyLocalDate();
    Assert.assertTrue(app.match().gameHasLabel(game1, "draft"));
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("2. Test Enter game screen. Enter the game.")
  public void testEnterGamescreen () throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("FlyQuest",1);
    TeamData team2 = new TeamData("GIANTS",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,5);
    //Login as registered user
    app.navigation().tapSignInButton();
    app.form().proceedPhone("1745778393", "Germany");
    //app.form().waitForCode();
    app.form().enterCode("198412");
    app.form().NextBtn.click();
    //Confirm alert
    app.match().allowNotifications();
    //Select the game in Matchlist
    app.match().tapGame(game1);
    app.form().goBack();
    app.match().tapGame(game1);
    //Check game start screen and join the game
    app.match().startMatchCopyrights(game1);
    app.match().clickToJoin();
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("3. Test basic Draft. Select drafts for 1 slot to the team1 and 1 slot to the team2.")
  public void testBasicDraft () throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("FlyQuest",1);
    TeamData team2 = new TeamData("GIANTS",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,7);
    DraftData heroes1 = new DraftData(null,null,null,null,null);
    DraftData heroes2 = new DraftData(null,null,null,null,null);
    //Login as registered user
    app.navigation().tapSignInButton();
    app.form().proceedPhone("1745778393", "Germany");
    //app.form().waitForCode();
    app.form().enterCode("198412");
    app.form().NextBtn.click();
    app.match().allowNotifications();
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Land on Draft screen
    //Wait first tip and verify it
    app.draft().firstDraftTip();
    //Verify content of each team
    app.draft().checkTeamInDraft(team1,heroes1);
    app.draft().checkTeamInDraft(team2,heroes2);
    //Set hero to mentioned player
    app.draft().setHeroToPlayer(team1,1,"Vel'Koz");
    //Wait second tip and verify it
    app.draft().secondDraftTip();
    //Set heroes in the draft
    app.draft().setHeroToPlayer(team2,2,"Galio");
    app.draft().setHeroToPlayer(team1,2,"Vi");
    //Add heroes to draft list
    heroes1.setHero(1,"Vel'Koz");
    heroes2.setHero(2,"Galio");
    heroes1.setHero(2,"Vi");
    //Validate that heroes are set
    app.draft().checkTeamInDraft(team1,heroes1);
    app.draft().checkTeamInDraft(team2,heroes2);
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("4. Test full Draft. Enter the game with this draft.")
  public void testFullDraft() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("FlyQuest",1);
    TeamData team2 = new TeamData("GIANTS",2);
    //DraftData heroes1 = new DraftData("Ahri","Braum","Corki","Dr. Mundo","Warwick");
    DraftData heroes1 = new DraftData(null,"Yasuo","Taric","Sion","Riven");
    DraftData heroes2 = new DraftData("Xerath","Braum","Zyra","Dr. Mundo","Vladimir");
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,15);
    //Login as registered user
    app.navigation().tapSignInButton();
    app.form().proceedPhone("1745778393", "Germany");
    app.form().enterCode("198412");
    //app.form().waitForCode();
    app.form().NextBtn.click();
    app.match().allowNotifications();
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Land on Draft screen
    //Wait first tip and verify it
    app.draft().firstDraftTip();
    //Set hero to mentioned player
    app.draft().setHeroToPlayer(team1,1,"Ahri");
    app.draft().secondDraftTip();
    //Select full set of heroes to the team
    app.draft().setHeroesToTeam(team1,heroes1);
    app.draft().setHeroesToTeam(team2,heroes2);
    //Add heroes to draft list
    heroes1.setHero(1,"Ahri");
    //Validate that heroes are set
    app.draft().checkTeamInDraft(team1,heroes1);
    app.draft().checkTeamInDraft(team2,heroes2);
    //Proceed with this draft
    app.draft().tapWardIt();
    app.draft().keepPredicting();
    app.draft().tapWardIt();
    app.draft().checkFinalDraftCopyrights();
  }

  @Test
  @DisplayName("5. Test Draft. Move heroes between slots. Remove hero from slot. Keep predicting.")
  public void testDraftMoveHeroes() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("FlyQuest",1);
    TeamData team2 = new TeamData("GIANTS",2);
    DraftData heroes1 = new DraftData(null,null,"Taliyah",null,"Rammus");
    DraftData heroes2 = new DraftData(null,null,null,null,null);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,30);
    //Login as registered user
    app.navigation().tapSignInButton();
    app.form().proceedPhone("1745778393", "Germany");
    app.form().enterCode("198412");
    //app.form().waitForCode();
    app.form().NextBtn.click();
    app.match().allowNotifications();
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Land on Draft screen
    //Wait first tip and verify it
    app.draft().firstDraftTip();
    //Set hero to mentioned player
    app.draft().setHeroToPlayer(team1,1,"Ahri");
    app.draft().secondDraftTip();
    app.draft().setHeroToPlayer(team2,4,"Braum");
    //Select full set of heroes to the theam
    app.draft().setHeroesToTeam(team1,heroes1);
    //Move hero from one player to another
    app.draft().moveHeroFromTo(team1,1,team2,1);
    app.draft().moveHeroFromTo(team1,3,team2,3);
    //Check keeping of predicting
    app.draft().tapWardIt();
    app.draft().keepPredicting();
    app.draft().moveHeroFromTo(team1,5,team2,5);
    //Check removing of hero from player
    app.draft().removeHeroFromPlayer(team2,1);
    //Add heroes to draft list
    heroes1.setHero(1,null);
    heroes1.setHero(3,null);
    heroes1.setHero(5,null);
    heroes2.setHero(3,"Taliyah");
    heroes2.setHero(4,"Braum");
    heroes2.setHero(5,"Rammus");
    //Validate that heroes are set
    app.draft().checkTeamInDraft(team1,heroes1);
    app.draft().checkTeamInDraft(team2,heroes2);
    //Finish draft without final tip
    app.draft().tapWardIt();
    //Finish the game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("6. Test Draft Exit. 0 heroes are choosed. 2 heroes are choosed. Roaster is empty after re-enterring.")
  public void testDraftExitEmptyHeroes() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("FlyQuest",1);
    TeamData team2 = new TeamData("GIANTS",2);
    //DraftData null_heroes = new DraftData(null,null,null,null,null);
    /* GameData game1 = new GameData(team1,team2,"8:40 PM"); */
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,10);
    //Login as registered user
    app.navigation().tapSignInButton();
    app.form().proceedPhone("1745778393", "Germany");
    app.form().enterCode("198412");
    //app.form().waitForCode();
    app.form().NextBtn.click();
    app.match().allowNotifications();
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Land on Draft screen
    //Wait first tip and verify it
    app.draft().firstDraftTip();
    //Exit without selections
    app.draft().backToMatchfeedFromDraft();
    //Re-enter the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    app.draft().firstDraftTip();
    //Check if teams are clean from heroes
    app.draft().checkTeamInDraft(team1,null);
    app.draft().checkTeamInDraft(team2,null);
    //Set hero to mentioned player
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    app.draft().setHeroToPlayer(team2,4,"Brand");
    //Exit draft
    app.draft().backToMatchfeedFromDraft();
    //Re-enter the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    app.draft().firstDraftTip();
    //Check if teams are clean from heroes
    app.draft().checkTeamInDraft(team1,null);
    app.draft().checkTeamInDraft(team2,null);
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("7. Test empty Draft. Finish with empty Draft (0 of 10). By timout")
  public void testEmptyDraftEnding() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,3);
    //System.out.println(game1.getTime());
    DraftData null_heroes = new DraftData(null,null,null,null,null);
    //Login as registered user
    app.navigation().tapSignInButton();
    app.form().proceedPhone("1745778393", "Germany");
    app.form().enterCode("198412");
    //app.form().waitForCode();
    app.form().NextBtn.click();
    app.match().allowNotifications();
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    //app.draft().waitTimeIsOverTip();
    app.draft().finalDraftTip(0);
    app.draft().checkTeamInResults(team1,null_heroes);
    app.draft().checkTeamInResults(team2,null_heroes);
    app.draft().checkFinalDraftCopyrights();
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("8. Finish Draft with one Hero in each team (2 of 10). After moving to another position. By timout")
  public void testDraftEndingOneHero() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    //Create a game with time shifting from present time
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,5);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData(null,null,"Taliyah",null,null);
    DraftData heroes2 = new DraftData("Brand",null,null,null,null);
    //Login as registered user
    app.navigation().tapSignInButton();
    app.form().proceedPhone("1745778393", "Germany");
    app.form().enterCode("198412");
    //app.form().waitForCode();
    app.form().NextBtn.click();
    app.match().allowNotifications();
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    app.draft().removeHeroFromPlayer(team1,1);
    //Set final list of heroes to both teams
    app.draft().setHeroesToTeam(team1,heroes1);
    app.draft().setHeroesToTeam(team2,heroes2);
    //Check moving heroes positions
    app.draft().moveHeroFromTo(team1,3, team2, 2);
    heroes1.setHero(3,null);
    heroes2.setHero(2,"Taliyah");
    //app.draft().waitTimeIsOverTip();
    app.draft().finalDraftTip(2);
    //Check that correct heroes set to teams
    app.draft().checkTeamInResults(team1,heroes1);
    app.draft().checkTeamInResults(team2,heroes2);
    app.draft().checkFinalDraftCopyrights();
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("9. Finish Draft with all Heroes (10 of 10). By timout")
  public void testDraftEndingAllHeroes() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    //Create a game with time shifting from present time
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,6);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData(null,"Caitlyn","Taric","Diana","Evelynn");
    DraftData heroes2 = new DraftData("Braum","Fiora","Gragas","Irelia","Kalista");
    //Login as registered user
    app.navigation().tapSignInButton();
    app.form().proceedPhone("1745778393", "Germany");
    app.form().enterCode("198412");
    //app.form().waitForCode();
    app.form().NextBtn.click();
    app.match().allowNotifications();
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    //Set final list of heroes to both teams
    app.draft().setHeroesToTeam(team1,heroes1);
    app.draft().setHeroesToTeam(team2,heroes2);
    heroes1.setHero(1,"Aatrox");
    //app.draft().waitTimeIsOverTip();
    app.draft().finalDraftTip(10);
    //Check that correct heroes set to teams
    app.draft().checkTeamInResults(team1,heroes1);
    app.draft().checkTeamInResults(team2,heroes2);
    app.draft().checkFinalDraftCopyrights();
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("10. Test empty Draft. Finish with empty Draft (0 of 10). Before timeout")
  public void testEmptyDraftEndingManual() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,5);
    System.out.println(game1.getTime());
    DraftData null_heroes = new DraftData(null,null,null,null,null);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    //app.draft().waitTimeIsOverTip();
    //Click to finish draft manually
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Check selected heroes
    app.draft().checkTeamInResults(team1,null_heroes);
    app.draft().checkTeamInResults(team2,null_heroes);
    app.draft().checkFinalDraftCopyrights();
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("11. Finish Draft with one Hero in each team (2 of 10). Before timout")
  public void testDraftEndingOneHeroManual() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    //Create a game with time shifting from present time
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,7);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData(null,null,"Taliyah",null,null);
    DraftData heroes2 = new DraftData("Brand",null,null,null,null);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    app.draft().removeHeroFromPlayer(team1,1);
    //Set final list of heroes to both teams
    app.draft().setHeroesToTeam(team1,heroes1);
    app.draft().setHeroesToTeam(team2,heroes2);
    //app.draft().waitTimeIsOverTip();
    //Click to finish draft manually
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Check that correct heroes set to teams
    app.draft().checkTeamInResults(team1,heroes1);
    app.draft().checkTeamInResults(team2,heroes2);
    app.draft().checkFinalDraftCopyrights();
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("12. Finish Draft with all Heroes (10 of 10). Before timout")
  public void testDraftEndingAllHeroesManual() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    //Create a game with time shifting from present time
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,10);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData("Aatrox","Caitlyn","Taliyah","Diana","Evelynn");
    DraftData heroes2 = new DraftData("Braum","Fiora","Gragas","Irelia","Kalista");
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().selectGame(game1);
    //Proceed with empty draft
    app.draft().firstDraftTip();
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    app.draft().removeHeroFromPlayer(team1,1);
    //Set final list of heroes to both teams
    app.draft().setHeroesToTeam(team1,heroes1);
    app.draft().setHeroesToTeam(team2,heroes2);
    //app.draft().waitTimeIsOverTip();
    //Click to finish draft manually
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Check that correct heroes set to teams
    app.draft().checkTeamInResults(team1,heroes1);
    app.draft().checkTeamInResults(team2,heroes2);
    app.draft().checkFinalDraftCopyrights();
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("13. Test finish with empty Draft (0 of 10). Before timeout. Then enterring to the match and check selection")
  public void testEmptyDraftEndingAndExit() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,20);
    System.out.println(game1.getTime());
    DraftData null_heroes = new DraftData(null,null,null,null,null);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    //Click to finish draft manually
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Exit match
    app.draft().backToMatchfeedFromDraftResults();
    //Join the game again
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Check team selection
    app.draft().checkTeamInResults(team1,null_heroes);
    app.draft().checkTeamInResults(team2,null_heroes);
    app.draft().checkFinalDraftCopyrights();
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("14. Test finish with full Draft (10 of 10). Before timeout. Then enterring to the match and check selection")
  public void testFullDraftEndingAndExit() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,20);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData("Aatrox","Caitlyn","Taric","Diana","Evelynn");
    DraftData heroes2 = new DraftData("Braum","Fiora","Gragas","Irelia","Kled");
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    //Select heroes to teams
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    heroes1.setHero(1,null);
    app.draft().setHeroesToTeam(team1,heroes1);
    app.draft().setHeroesToTeam(team2,heroes2);
    heroes1.setHero(1,"Aatrox");
    //Click to finish draft manually
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Exit match
    app.draft().backToMatchfeedFromDraftResults();
    //Join the game again
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Check team selection
    app.draft().checkTeamInResults(team1,heroes1);
    app.draft().checkTeamInResults(team2,heroes2);
    app.draft().checkFinalDraftCopyrights();
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("15. Test substitution on Draft selection screen. Empty draft.")
  public void testSubstitutionEmptyDraft() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,10);
    System.out.println(game1.getTime());
    DraftData heroes1 = null;
    DraftData heroes2 = null;
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    //Verify teams
    app.draft().checkTeamInDraft(team1,heroes1);
    app.draft().checkTeamInDraft(team2,heroes2);
    //Make substitution of one player in monitor
    app.getGameHelper().substitutePlayer(game1,team1,1,"Allorim");
    team1.setPlayer(1,"Allorim","Top");
    //Check substitution in the app
    Thread.sleep(10000);
    app.draft().checkTeamInDraft(team1,heroes1);
    app.draft().checkTeamInDraft(team2,heroes2);
    //Finish game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("16. Test substitution of selected slot in draft.")
  public void testSubstitutionOfSelectedSlot() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,10);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData("Aatrox",null,null,null,"Fiora");
    DraftData heroes2 = new DraftData(null,null,null,"Irelia",null);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    //Verify teams
    app.draft().checkTeamInDraft(team1,null);
    app.draft().checkTeamInDraft(team2,null);
    //Select heroes to teams
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    heroes1.setHero(1,null);
    app.draft().setHeroesToTeam(team1,heroes1);
    app.draft().setHeroesToTeam(team2,heroes2);
    //Make substitution of one player in monitor
    app.getGameHelper().substitutePlayer(game1,team1,1,"Allorim");
    app.getGameHelper().substitutePlayer(game1,team1,5,"Damonte");
    app.getGameHelper().substitutePlayer(game1,team2,4,"Innaxe");
    team1.setPlayer(1,"Allorim","Top");
    team1.setPlayer(5,"Damonte","Mid");
    team2.setPlayer(4,"Innaxe","Adc");
    heroes1.setHero(1,"Aatrox");
    //Wait 10sec and Check substitution in the app
    Thread.sleep(7000);
    app.draft().checkTeamInDraft(team1,heroes1);
    app.draft().checkTeamInDraft(team2,heroes2);
    //Finish game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("17. Test substitution on Draft Results screen. Empty draft.")
  public void testSubstitutionEmptyResults() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,10);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData(null, null, null,null,null);
    DraftData heroes2 = new DraftData(null, null, null,null,null);;
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    //Verify teams
    app.draft().checkTeamInDraft(team1,heroes1);
    app.draft().checkTeamInDraft(team2,heroes2);
    //Finish with draft
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Make substitution of one player in monitor
    app.getGameHelper().substitutePlayer(game1,team1,1,"Allorim");
    team1.setPlayer(1,"Allorim","Top");
    //Check substitution in the app
    Thread.sleep(7000);
    app.draft().checkTeamInResults(team1,heroes1);
    app.draft().checkTeamInResults(team2,heroes2);
    //Finish game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("18. Test substitution of selected slot on Draft Results screen.")
  public void testSubstitutionOfSelectedSlotInResults() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,10);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData("Aatrox",null,null,null,"Fiora");
    DraftData heroes2 = new DraftData(null,null,null,"Irelia",null);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    //Verify teams
    app.draft().checkTeamInDraft(team1,null);
    app.draft().checkTeamInDraft(team2,null);
    //Select heroes to teams
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    heroes1.setHero(1,null);
    app.draft().setHeroesToTeam(team1,heroes1);
    app.draft().setHeroesToTeam(team2,heroes2);
    //Finish with draft
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Make substitution of one player in monitor
    app.getGameHelper().substitutePlayer(game1,team1,1,"Allorim");
    app.getGameHelper().substitutePlayer(game1,team1,5,"Damonte");
    app.getGameHelper().substitutePlayer(game1,team2,4,"Innaxe");
    team1.setPlayer(1,"Allorim","Top");
    team1.setPlayer(5,"Damonte","Mid");
    team2.setPlayer(4,"Innaxe","Adc");
    heroes1.setHero(1,"Aatrox");
    //Wait 10sec and Check substitution in the app
    Thread.sleep(7000);
    app.draft().checkTeamInResults(team1,heroes1);
    app.draft().checkTeamInResults(team2,heroes2);
    //Finish game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("18_1. Test substitution of selected slot on Draft Results screen. Results had already counted.")
  public void testSubstitutionOfSelectedSlotInResultsAfterConfirm() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,10);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData("Aatrox","Jax","Ahri","Diana","Fiora");
    DraftData heroes2 = new DraftData("Hecarim","Galio","Gragas","Irelia","Taric");
    DraftData heroes_app1 = new DraftData("Aatrox",null,null,null,"Fiora");
    DraftData heroes_app2 = new DraftData(null,null,null,"Irelia",null);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    //Verify teams
    app.draft().checkTeamInDraft(team1,null);
    app.draft().checkTeamInDraft(team2,null);
    //Select heroes to teams
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    app.draft().setHeroToPlayer(team1,5,"Fiora");
    app.draft().setHeroToPlayer(team2,4,"Irelia");
    heroes1.setHero(1,"Aatrox");
    heroes1.setHero(5,"Fiora");
    heroes2.setHero(4,"Irelia");
    //Finish with draft
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Make substitution of one player in monitor
    app.getGameHelper().substitutePlayer(game1,team1,1,"Allorim");
    app.getGameHelper().substitutePlayer(game1,team1,5,"Damonte");
    app.getGameHelper().substitutePlayer(game1,team2,4,"Innaxe");
    team1.setPlayer(1,"Allorim","Top");
    team1.setPlayer(5,"Damonte","Mid");
    team2.setPlayer(4,"Innaxe","Adc");
    //Select heroes in Monitor for teams
    app.getGameHelper().setHeroes(game1,team1,heroes1);
    app.getGameHelper().setHeroes(game1,team2,heroes2);
    app.getGameHelper().confirmDraft();
    //Verify popup message in the app
    app.draft().checkEndDraftPushResults(3);
    //Finish game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("19. Test Draft Results then confirm Draft in Monitor. Draft Confirm popup. Empty Draft is selected. Before Game start.")
  public void testEmptyResults() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,10);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData("Aatrox","Caitlyn","Taric","Diana","Evelynn");
    DraftData heroes2 = new DraftData("Braum","Fiora","Gragas","Irelia","Kled");
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    //Finish with draft
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Start game in monitor
    //app.getGameHelper().startGame(game1);
    //Verify game start in the app
    //app.draft().waitGoToButton();
    //app.draft().checkScoreOfDraft(0);
    //Select heroes in Monitor for teams
    app.getGameHelper().setHeroes(game1,team1,heroes1);
    app.getGameHelper().setHeroes(game1,team2,heroes2);
    app.getGameHelper().confirmDraft();
    //Verify popup message in the app
    app.draft().checkEndDraftPushResults(0);
    //Open Leaderboards
    app.draft().checkScoreOfDraft(1);
    //Finish game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("20. Test Draft Results then confirm Draft in Monitor. Draft Confirm popup. No correct heroes are selected. All are incorrect. Before Game start.")
  public void testResultsNoCorrectPicks() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,10);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData("Ahri","Caitlyn","Taric","Diana","Evelynn");
    DraftData heroes2 = new DraftData("Braum","Fiora","Gragas","Irelia","Kalista");
    DraftData heroes_app1 = new DraftData(null,"Corki","Tristana","Darius","Hecarim");
    DraftData heroes_app2 = new DraftData("Illaoi","Irelia","Jax","Jhin","Camille");
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    app.draft().setHeroesToTeam(team1,heroes_app1);
    app.draft().setHeroesToTeam(team2,heroes_app2);
    heroes_app1.setHero(1,"Aatrox");
    //Finish with draft
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Select heroes in Monitor for teams
    app.getGameHelper().setHeroes(game1,team1,heroes1);
    app.getGameHelper().setHeroes(game1,team2,heroes2);
    app.getGameHelper().confirmDraft();
    //Verify popup message in the app
    app.draft().checkEndDraftPushResults(0);
    //Open Leaderboards
    app.draft().checkScoreOfDraft(1);
    //Finish game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("21. Test Draft Results then confirm Draft in Monitor. Draft Confirm popup. Two correct heroes are selected. No incorrect heroes. Before Game start.")
  public void testResultsTwoCorrectPicks() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,10);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData("Aatrox","Caitlyn","Taric","Diana","Evelynn");
    DraftData heroes2 = new DraftData("Braum","Fiora","Gragas","Irelia","Kalista");
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    app.draft().setHeroToPlayer(team2,5,"Kalista");
    //Finish with draft
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Select heroes in Monitor for teams
    app.getGameHelper().setHeroes(game1,team1,heroes1);
    app.getGameHelper().setHeroes(game1,team2,heroes2);
    app.getGameHelper().confirmDraft();
    //Verify popup message in the app
    app.draft().checkEndDraftPushResults(2);
    //Open Leaderboards
    app.draft().checkScoreOfDraft(1);
    //Finish game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("22. Test Draft Results then confirm Draft in Monitor. Draft Confirm popup. Two correct heroes are selected. All other are incorrect heroes. Before Game start.")
  public void testResultsOnlyTwoCorrectPicks() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,10);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData("Aatrox","Caitlyn","Taric","Diana","Evelynn");
    DraftData heroes2 = new DraftData("Braum","Fiora","Gragas","Irelia","Kalista");
    DraftData heroes_app1 = new DraftData(null,"Corki","Tristana","Darius","Hecarim");
    DraftData heroes_app2 = new DraftData("Illaoi","Irelia","Jhin","Jax","Kalista");
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    app.draft().setHeroesToTeam(team1,heroes_app1);
    app.draft().setHeroesToTeam(team2,heroes_app2);
    heroes_app1.setHero(1,"Aatrox");
    //Finish with draft
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Select heroes in Monitor for teams
    app.getGameHelper().setHeroes(game1,team1,heroes1);
    app.getGameHelper().setHeroes(game1,team2,heroes2);
    app.getGameHelper().confirmDraft();
    //Verify popup message in the app
    app.draft().checkEndDraftPushResults(2);
    //Open Leaderboards
    app.draft().checkScoreOfDraft(1);
    //Finish game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("23. Test Draft Results then confirm Draft in Monitor. Draft Confirm popup. All correct heroes are selected. Before Game start.")
  public void testResultsAllCorrectPicks() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,10);
    System.out.println(game1.getTime());
    DraftData heroes1 = new DraftData(null,"Corki","Tristana","Darius","Hecarim");
    DraftData heroes2 = new DraftData("Illaoi","Irelia","Jax","Jhin","Kalista");
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    app.draft().setHeroesToTeam(team1,heroes1);
    app.draft().setHeroesToTeam(team2,heroes2);
    heroes1.setHero(1,"Aatrox");
    //Finish with draft
    app.draft().tapWardIt();
    app.draft().acceptFinalDraftTip();
    //Select heroes in Monitor for teams
    app.getGameHelper().setHeroes(game1,team1,heroes1);
    app.getGameHelper().setHeroes(game1,team2,heroes2);
    app.getGameHelper().confirmDraft();
    //Verify popup message in the app
    app.draft().checkEndDraftPushResults(10);
    //Open Leaderboards
    app.draft().checkScoreOfDraft(1);
    //Finish game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("24. Test Draft Results then confirm Draft in Monitor. Draft Confirm popup. Two correct heroes are selected. All other are incorrect heroes. By timeout.")
  public void testResultsOnlyTwoCorrectPicksByTimeout() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,8);
    DraftData heroes1 = new DraftData("Aatrox","Caitlyn","Taric","Diana","Evelynn");
    DraftData heroes2 = new DraftData("Braum","Fiora","Gragas","Irelia","Kalista");
    DraftData heroes_app1 = new DraftData(null,"Corki","Tristana","Darius","Hecarim");
    DraftData heroes_app2 = new DraftData("Illaoi","Irelia","Jax","Lee Sin","Kalista");
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    app.draft().setHeroesToTeam(team1,heroes_app1);
    app.draft().setHeroesToTeam(team2,heroes_app2);
    heroes_app1.setHero(1,"Aatrox");
    System.out.println(app.driver.getPageSource());
    //Select heroes in Monitor for teams
    app.getGameHelper().setHeroes(game1,team1,heroes1);
    app.getGameHelper().setHeroes(game1,team2,heroes2);
    app.getGameHelper().confirmDraft();
    //Wait game start time
    app.draft().finalDraftTip(10);
    //Verify popup message in the app
    app.draft().checkEndDraftPushResults(2);
    //Open Leaderboards
    app.draft().checkScoreOfDraft(1);
    //Finish game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("25. Test Draft Results then confirm Draft in Monitor. Draft Confirm popup. Two correct heroes are selected. Only 3 heroes is selected. By timeout.")
  public void testResultsFewPicksTwoCorrectPicksByTimeout() throws InterruptedException, AWTException {
    TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,7);
    DraftData heroes1 = new DraftData("Aatrox","Caitlyn","Taric","Diana","Evelynn");
    DraftData heroes2 = new DraftData("Braum","Fiora","Gragas","Irelia","Kalista");
    DraftData heroes_app1 = new DraftData(null,null,null,"Fiora",null);
    DraftData heroes_app2 = new DraftData(null,"Irelia","Gragas",null,null);
    //Login as registered user
    app.match().login("Germany","1745778393");
    //Join the game
    app.match().tapGame(game1);
    app.match().clickToJoin();
    //Proceed with empty draft
    app.draft().firstDraftTip();
    app.draft().setHeroToPlayer(team1,1,"Aatrox");
    app.draft().secondDraftTip();
    app.draft().setHeroesToTeam(team1,heroes_app1);
    app.draft().setHeroesToTeam(team2,heroes_app2);
    heroes_app1.setHero(1,"Aatrox");
    //Select heroes in Monitor for teams
    app.getGameHelper().setHeroes(game1,team1,heroes1);
    app.getGameHelper().setHeroes(game1,team2,heroes2);
    app.getGameHelper().confirmDraft();
    //Wait game start time
    app.draft().finalDraftTip(4);
    //Verify popup message in the app
    app.draft().checkEndDraftPushResults(2);
    //Open Leaderboards
    app.draft().checkScoreOfDraft(1);
    //Finish game
    app.getGameHelper().finishGame(game1,team1);
  }

  @Test
  @DisplayName("Test debugger.")
  public void test() throws AWTException, InterruptedException {
    /*TeamData team1 = new TeamData("Echo Fox",1);
    TeamData team2 = new TeamData("SPLYCE",2);
    GameData game1 = app.getGameHelper().createGame("lol",team1,team2,5);
    app.getGameHelper().finishGame(game1,team1);
    Timer t = new Timer();
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        System.out.println(driver.getPageSource());
      }
    }, 0, 500);*/
    System.out.println(app.driver.getPageSource());
  }
  }
