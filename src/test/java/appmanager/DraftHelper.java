package appmanager;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import model.DraftData;
import model.TeamData;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DraftHelper extends BaseHelpers{
  public DraftHelper(AppiumDriver driver) {
    super(driver);
  }

  public void firstDraftTip() {
    //Wait for first tip appearing
    //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Predict the Picks")));
    new WebDriverWait(driver, 240)
            .until(ExpectedConditions.visibilityOfElementLocated(By.id("Predict the Picks")));
    //Check tip content
    Assert.assertNotNull(driver.findElementByAccessibilityId("The more Champions you predict, the more points you can earn"));
    //Check visibility of OK button
    WebElement ok_button = driver.findElementByAccessibilityId("OK");
    Assert.assertTrue(ok_button.isDisplayed());
    //Click on OK button
    ok_button.click();
  }

  public void secondDraftTip() {
    //Wait for second tip appearing
    //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@value=\"Cool! You chose your first champion.\"]")));
    //Check tip content
    //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@value='Predict all the champions to earn more points']")));
    new WebDriverWait(driver, 240)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@value='Predict all the champions to earn more points']")));
    //Check visibility of OK button
    //WebElement ok_button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeButton[@name='OK']")));
    //Click on OK button
    new WebDriverWait(driver, 240)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeButton[@name='OK']")))
            .click();
    //ok_button.click();
  }

  public void finalDraftTip(int players_amount) {
    //Wait for first tip appearing
    //wait_timer.until(ExpectedConditions.visibilityOfElementLocated(By.id("Time is over")));
    new WebDriverWait(driver, 240)
            .until(ExpectedConditions.visibilityOfElementLocated(By.id("Time is over")));
    //Check tip content
    String message;
    if (players_amount!=0){
      message = "You selected "+players_amount+" of 10 champions and can get up to "+players_amount*10+" 000 wp";
    } else {
      message = "You selected 0 of 10 champions and can get up to 0 wp";
    }
    Assert.assertNotNull(driver.findElementByAccessibilityId(message));
    //Check visibility of OK button
    //System.out.println(driver.getPageSource());
    List<WebElement> ok_button = driver.findElementsByAccessibilityId("Ward it!");
    System.out.println(ok_button.size());
    Assert.assertTrue(ok_button.get(1).isDisplayed());
    //Click on OK button
    ok_button.get(1).click();
    //new TouchAction(driver).tap(button).perform();
  }

  public void acceptFinalDraftTip() {
    //Wait for final tip appearing
    //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='“Ward it” means you are done with Draft']")));
    new WebDriverWait(driver, 240)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='“Ward it” means you are done with Draft']")));
    //Check visibility of OK button
    //WebElement button = wait.until(ExpectedConditions.elementToBeClickable(formButtons.get(4)));
    WebElement button = new WebDriverWait(driver, 240)
            .until(ExpectedConditions.elementToBeClickable(formButtons.get(4)));
    Assert.assertEquals(button.getText(), "OK, I’m done");
    //Click on OK button
    new TouchAction(driver).tap(tapOptions().withElement(element(button))).perform();
    //ok_button.click();
  }

  public void keepPredicting() {
    //Wait for first tip appearing
    //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='“Ward it” means you are done with Draft']")));
    new WebDriverWait(driver, 240)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='“Ward it” means you are done with Draft']")));
    //Check visibility of OK button
    WebElement cancel_button = driver.findElementByXPath("//XCUIElementTypeButton[@name='Keep predicting']");
    Assert.assertTrue(cancel_button.isDisplayed());
    //Click on OK button
    cancel_button.click();
  }

  public void checkTeamInDraft(TeamData team, DraftData heroes) {
    Assert.assertNotNull(driver.findElementByXPath("//XCUIElementTypeStaticText[@name='"+toUpperCase(team.getName())+"']"));
    WebElement team_collection = getTeamCollection(team,heroes,"Draft");
    verifyTeamList(team, team_collection, heroes, "Draft");
  }

  public void checkTeamInResults(TeamData team, DraftData heroes) {
    Assert.assertNotNull(driver.findElementByXPath("//XCUIElementTypeStaticText[@name='"+toUpperCase(team.getName())+"']"));
    WebElement team_collection = getResultsTeamCollection(team,heroes);
    verifyTeamList(team, team_collection, heroes, "Results");
  }

  private WebElement getTeamCollection(TeamData team,DraftData heroes, String screen) {
    //heroes != null when there is Draft results screen
    WebElement team_collection;
    if (team.getIndex()==1) {
      if (heroes == null || screen.equals("Draft"))
        team_collection = collections.get(1);
      else
        team_collection = collections.get(0);
    }
      else {
      if (heroes == null || screen.equals("Draft"))
        team_collection = collections.get(2);
      else
        team_collection = collections.get(1);
    }
    return team_collection;
  }

  private WebElement getResultsTeamCollection(TeamData team,DraftData heroes) {
    WebElement team_collection;
    if (team.getIndex()==1)
        team_collection = collections.get(0);
    else
        team_collection = collections.get(1);
    return team_collection;
  }

  private void verifyTeamList(TeamData team, WebElement team_collection, DraftData heroes, String screen) {
    List<WebElement> players = team_collection.findElements(By.xpath("//XCUIElementTypeCell"));
    //Check names and roles of team members
    for (int i = 0; i < players.size(); i++) {
      String player_name = toUpperCase(team.getTeam().get(i).getName());
      String player_role = toUpperCase(team.getTeam().get(i).getRole());
      if(player_role.equals("SUPPORT")){
        player_role = "SUP";
      }
      List<WebElement> player_attributes = players.get(i).findElements(By.xpath(".//XCUIElementTypeStaticText"));
       Assert.assertEquals(player_name, player_attributes.get(0).getText());
      String current_hero = null;
      if (heroes!=null){
        current_hero = heroes.getDraft().get(i);
      }
      if (current_hero!=null){
        Assert.assertEquals(current_hero, player_attributes.get(1).getText());
      } else {
        Assert.assertEquals(player_role, player_attributes.get(1).getText());
      }
    }
  }

  public void findHeroAndSelect(String hero) {
    //Get current list of heroes
    WebElement heroes_collection = collections.get(0);
    //Find required hero
    boolean hero_is_found = false;
    char[] capital_letter = hero.toCharArray();
    driver.findElement(MobileBy.ByAccessibilityId.AccessibilityId(String.valueOf(capital_letter[0]))).click();
    while (!hero_is_found) {
      List<WebElement> heroes = heroes_collection.findElements(By.xpath(".//XCUIElementTypeCell"));
      try {
        //WebElement h_name = h.findElements(By.xpath(".//XCUIElementTypeStaticText")).get(0).getText();
        String up_hero = toUpperCase(hero);
        driver.findElement(By.xpath("//XCUIElementTypeStaticText[@value=\""+up_hero+"\"][2]")).click();
        hero_is_found = true;
      } catch (NoSuchElementException e){
        System.out.println("I'm going to swipe "+heroes.get(2).findElement(By.xpath(".//XCUIElementTypeStaticText")).getText());
        swipe("Left",heroes.get(2).findElements(By.xpath(".//XCUIElementTypeStaticText")).get(1));
      }
    }
  }

  public void setHeroToPlayer(TeamData team, int player_num, String hero) {
    WebElement team_collection = getTeamCollection(team,null,"Draft");
    List<WebElement> players = team_collection.findElements(By.xpath(".//XCUIElementTypeCell"));
    //System.out.println(players.get(player_num-1).findElements(By.xpath(".//XCUIElementTypeStaticText")).get(1).getText());
    findHeroAndSelect(hero);
    players.get(player_num-1).click();
    //Check hero name in player's slot
    Assert.assertEquals(players.get(player_num-1).findElements(By.xpath(".//XCUIElementTypeStaticText")).get(1).getText(),hero);
  }

  public void backToMatchfeedFromDraft() {
    formButtons.get(1).click();
    WebElement back_button = driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Back to matchfeed']"));
    back_button.click();
  }

  public void backToMatchfeedFromDraftResults() {
    formButtons.get(0).click();
    WebElement back_button = driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Back to matchfeed']"));
    back_button.click();
  }

  public void setHeroesToTeam(TeamData team1, DraftData heroes1) {
    int limit = heroes1.getDraft().size();
    for (int i = 0; i < limit; i++) {
      if (heroes1.getDraft().get(i)!=null){
        setHeroToPlayer(team1,i+1,heroes1.getDraft().get(i));
      }
    }
  }

  public void removeHeroFromPlayer(TeamData team, int player_num) {
    WebElement team_collection = getTeamCollection(team,null,"Draft");
    List<WebElement> players = team_collection.findElements(By.xpath("//XCUIElementTypeCell"));
    players.get(player_num-1).click();
    driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='Tap here to remove champion']")).click();
    List<WebElement> p = players.get(player_num-1).findElements(By.xpath(".//XCUIElementTypeStaticText"));
    Assert.assertEquals(p.get(1).getText(),toUpperCase(team.getTeam().get(0).getRole()));
  }

  public void moveHeroFromTo(TeamData team_from, int player_from, TeamData team_to, int player_to) {
    WebElement team_from_collection = getTeamCollection(team_from,null,"Draft");
    WebElement team_to_collection = getTeamCollection(team_to,null,"Draft");
    List<WebElement> players_from = team_from_collection.findElements(By.xpath("//XCUIElementTypeCell"));
    List<WebElement> players_to = team_to_collection.findElements(By.xpath("//XCUIElementTypeCell"));
    String hero = players_from.get(player_from-1).findElements(By.xpath(".//XCUIElementTypeStaticText")).get(1).getText();
    WebElement from_slot = players_from.get(player_from-1);
    WebElement to_slot = players_to.get(player_to-1);
    from_slot.click();
    to_slot.click();
    //Check that FROM slot is empty
    String expected_role = team_from.getTeam().get(player_from-1).getRole();
    if (expected_role.equals("Support")){
      expected_role = "Sup";
    }
    Assert.assertEquals(from_slot.findElements(By.xpath(".//XCUIElementTypeStaticText")).get(1).getText(),toUpperCase(expected_role));
    //Check that TO slot contains this hero
    Assert.assertEquals(to_slot.findElements(By.xpath(".//XCUIElementTypeStaticText")).get(1).getText(),hero);
  }

  public void tapWardIt() {
    driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Ward it!']")).click();
  }

  public void waitTimeIsOverTip() {
    //Check that timer shows 00:00 (28)
    //wait_timer.until(ExpectedConditions.attributeContains(formStaticTexts.get(28),"value","00:05 left"));
    new WebDriverWait(driver, 240)
            .until(ExpectedConditions.attributeContains(formStaticTexts.get(28),"value","00:05 left"));
    System.out.println("Time is over");
  }

  public void waitGoToButton() {
    //wait_timer.until(ExpectedConditions.attributeContains(formStaticTexts.get(28),"value","190:00 left"));
    //wait_timer.until(ExpectedConditions.elementToBeClickable(By.xpath("//XCUIElementTypeButton[@name='Go to Summoner's Rift']")));
    //WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeButton[@name=\"Go to Summoner's Rift\"]")));
    WebElement button = new WebDriverWait(driver, 240)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeButton[@name='Go to Summoner\'s Rift']")));
    Assert.assertNotNull(button);
  }

  private void openLeaderboardInResults() {
    //WebElement arrow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeImage[@name='arrow-top']")));
    //arrow.click();
    driver.findElement(By.xpath("//XCUIElementTypeImage[@name='arrow-top']/..")).click();
  }

  private void closeLeaderboard() {
    //WebElement arrow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeImage[@name='arrow-top']")));
    //arrow.click();
    driver.findElement(By.name("Close")).click();
  }

  public void checkScoreOfDraft(int sum) {
    openLeaderboardInResults();
    closeLeaderboard();
  }

  public void checkFinalDraftCopyrights() {
    Assert.assertTrue(driver.findElementByXPath("//XCUIElementTypeStaticText[@value='The game will start soon']").isDisplayed());
    Assert.assertTrue(driver.findElementByXPath("//XCUIElementTypeStaticText[@value='Keep watching the broadcast. We will notify you, when teams will ready to play']").isDisplayed());
  }

  public void checkEndDraftPushResults(int winner_amount) throws InterruptedException {
    if (winner_amount==0){
      //wait_push.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@value='Oooops!']")));
      new WebDriverWait(driver, 180)
              .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@value='Oooops!']")));
      //wait_push.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@value='You did not guess any of the 10 champions :-(']")));
      new WebDriverWait(driver, 180)
              .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@value='You did not guess any of the 10 champions :-(']")));
    } else{
      //wait_push.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@value='You got "+winner_amount+"0 000 wp']")));
      new WebDriverWait(driver, 180)
              .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@value='You got "+winner_amount+"0 000 wp']")));
    }
    //wait_push.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@value='"+winner_amount+"/10']")));
    new WebDriverWait(driver, 180)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@value='"+winner_amount+"/10']")));
  }

  public void waitAndProceedEmptyDraft() {
    firstDraftTip();
    //Wait when time is over and we can start the game
    finalDraftTip(0);
  }

  public void enterMatch() {
    //WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeButton[@name=\"Go to Summoner's Rift\"]")));
    //button.click();
    new WebDriverWait(driver, 240)
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeButton[@name='Go to Summoner\'s Rift']")))
            .click();
    //Thread.sleep(2000);
    //makeScreenshot("/Users/valery/Desktop/fb.png");
  }

}
