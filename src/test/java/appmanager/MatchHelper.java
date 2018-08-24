package appmanager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import model.GameData;
import model.TeamData;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MatchHelper extends BaseHelpers {
  PointOption tap_point = new PointOption()
          .withCoordinates(100,100);

  public MatchHelper(AppiumDriver driver) {
    super(driver);
  }

  public void verifyMatchfeedTitle() {
    Assert.assertEquals(formStaticTexts.get(1).getAttribute("name"), "Matchfeed");
  }

  public boolean verifyLocalDate() {
    String current = currentLocalDateUpper();
    for (MobileElement t: formStaticTexts){
      try {
        if (t.getText().equals(current)){
          System.out.println("Date is found");
          return true;
        }
      } catch (Exception e) {
        System.out.println("Date isn't found yet.");
      }
    }
    return false;
  }

  private MobileElement gameExists(GameData game) {
    boolean game_is_found = false;
    if (games.size() > 2) {
      while (!game_is_found) {
        for (int i = 1; i < games.size() - 1; i++) {
          List<MobileElement> children = games.get(i).findElements(By.xpath(".//XCUIElementTypeStaticText"));
          if (children.get(0).getText().equals(game.getTeam1().getShortName()))
            if (children.get(1).getText().equals(game.getTeam2().getShortName())) {
              Assert.assertEquals(children.get(2).getText(), game.getTeam1().getName());
              Assert.assertEquals(children.get(3).getText(), game.getTeam2().getName());
              if (children.get(4).getText().equals(game.getTime())) {
                return games.get(i);
              }
            }
        }
        boolean end = false; //Is this the end of the game list?
        for (MobileElement t : formStaticTexts) {
          if (!t.getText().equals("More games coming soon. Stay tuned")) {
            end = false;
          } else {
            end = true;
            game_is_found = true;
            System.out.println("No such game");
            break;
          }
        }
        if (!end) {
          swipe("Up");
        }
      }
    } else System.out.println("No games");
    return null;
  }

  public boolean gameHasLabel(GameData game, String label) {
    if (games.size() > 2) {
      int limit = games.size() - 1;
      for (int i = 1; i < limit; i++) {
        List<MobileElement> children = games.get(i).findElements(By.xpath(".//XCUIElementTypeStaticText"));
        List<MobileElement> labels = games.get(i).findElements(By.xpath(".//XCUIElementTypeImage"));
        if (children.get(0).getText().equals(game.getTeam1().getShortName()))
          if (children.get(1).getText().equals(game.getTeam2().getShortName())) {
            Assert.assertEquals(children.get(2).getText(), game.getTeam1().getName());
            Assert.assertEquals(children.get(3).getText(), game.getTeam2().getName());
            if (children.get(4).getText().equals(game.getTime())) {
              if (labels.get(1).getAttribute("name").equals("gamestatus-" + label))
                return true;
            }
          }
      }
    } else System.out.println("No games");
    return false;
  }

  public void tapGame(GameData game) {
    MobileElement game_slot = gameExists(game);
    if (game_slot!=null)
      game_slot.click();
    else
      Assert.assertNotNull(game_slot);
  }

  public void startMatchCopyrights(GameData game) {
    //Check shortname of Team1
    Assert.assertEquals(formStaticTexts.get(0).getText(), game.getTeam1().getShortName());
    //Check shortname of Team2
    Assert.assertEquals(formStaticTexts.get(2).getText(), game.getTeam2().getShortName());
    //Check game time
    Assert.assertEquals(formStaticTexts.get(1).getText(), game.getTime());
    //Check full teams' names
    Assert.assertEquals(formStaticTexts.get(3).getText(), game.getTeam1().getName() + " vs " + game.getTeam2().getName());
    //Check game credo
    Assert.assertEquals(formStaticTexts.get(5).getText(), "Highest Score wins!!");
    //Check game LocalDate (at title)
    Assert.assertEquals(formStaticTexts.get(6).getText(), currentLocalDate());
    //Check draft is available
    Assert.assertEquals(formStaticTexts.get(7).getText(), "Draft is available");
    //Check Join button is clickable
    //wait.until(ExpectedConditions.elementToBeClickable(formButtons.get(1)));
    new WebDriverWait(driver, 180)
            .until(ExpectedConditions.elementToBeClickable(formButtons.get(1)));
  }

  public void clickToJoin() {
    //WebElement button = wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAccessibilityId("Join the Game")));
    //button.click();
    new WebDriverWait(driver, 180)
            .until(ExpectedConditions.elementToBeClickable(driver.findElementByAccessibilityId("Join the Game"))).click();
    //new TouchAction(driver).tap(formButtons.get(1)).perform();
  }

  public void checkDraftisLocked() {
    //wait.until(ExpectedConditions.attributeContains(By.xpath("//XCUIElementTypeStaticText"), "value", "to Draft unlocks"));
    new WebDriverWait(driver, 180)
            .until(ExpectedConditions.attributeContains(By.xpath("//XCUIElementTypeStaticText"), "value", "to Draft unlocks"));
    Assert.assertFalse(driver.findElementByXPath("//XCUIElementTypeButton[@name='Join the Game']").isDisplayed());
  }

  public void allowNotifications() {
    //Wait for first tip appearing
    //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Allow"))).click();
    new WebDriverWait(driver, 180)
            .until(ExpectedConditions.visibilityOfElementLocated(By.id("Allow"))).click();
  }

  public void login(String country, String phone) throws InterruptedException {
    tapSignInButton();
    proceedPhone(phone, country);
    enterCode("198412");
    NextBtn.click();
    allowNotifications();
  }

  public void selectGame(GameData game) {
    tapGame(game);
    clickToJoin();
  }

  public void commonMatchCopyrights(GameData game){
    checkTeamsInGame(game);
    checkGameIsLive();
    checkCommonWardsCopyrights();
  }

  private void checkGameIsLive() {
    Assert.assertNotNull(driver.findElementByXPath("//XCUIElementTypeStaticText[@value='LIVE']"));
  }
  private void checkTeamsInGame(GameData game) {
    Assert.assertNotNull(driver.findElementByAccessibilityId(game.getTeam1().getShortName()));
    Assert.assertNotNull(driver.findElementByAccessibilityId(game.getTeam2().getShortName()));
  }
  private void checkCommonWardsCopyrights() {
    Assert.assertNotNull(driver.findElementByAccessibilityId("Ward now and get"));
    Assert.assertNotNull(driver.findElementByAccessibilityId("Ward it!"));
  }

  public void firstBloodCopyrights(){
    //Title "Who will split first blood?"
    Assert.assertNotNull(driver.findElementByAccessibilityId("Who will spill first blood?"));
    //Arrows left and right
    Assert.assertEquals(driver.findElementByAccessibilityId("arrow left").getAttribute("enabled"),"false");
    Assert.assertEquals(driver.findElementByAccessibilityId("arrow right").getAttribute("enabled"),"true");
  }

  public void tapWardIt() {
    driver.findElementByAccessibilityId("Who will spill first blood?").click();
  }

  public void firstBloodWardCopy() {
    //Title "FIRST BLOOD WARD"
    Assert.assertNotNull(driver.findElementByAccessibilityId("FIRST BLOOD WARD"));
    //Title "Who will spill first blood?"
    Assert.assertNotNull(driver.findElementByAccessibilityId("Who will spill first blood?"));
    //Ward It button is not enabled
    Assert.assertEquals(driver.findElementByAccessibilityId("Ward it!").getAttribute("enabled"),"false");
  }

  public void closeFirstBlood() {
    //Click close button
    //wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAccessibilityId("Close"))).click();
    new WebDriverWait(driver, 180)
            .until(ExpectedConditions.elementToBeClickable(driver.findElementByAccessibilityId("Close"))).click();
  }

  public void checkTeamsInFirstBloodWard(TeamData team1, TeamData team2) {
    //Parent collection
    WebElement parent = collections.get(2);
    //Assert teams in collection
    Assert.assertNotNull(parent.findElement(By.id(toUpperCase(team1.getShortName()))));
    Assert.assertNotNull(parent.findElement(MobileBy.ByAccessibilityId.AccessibilityId(toUpperCase(team2.getShortName()))));
    List<WebElement> players = parent.findElements(By.className("XCUIElementTypeCell"));
    String player_name;
    String player_role;
    int j = -1;
      for (int i = 2; i < players.size(); i++) {
        if (i % 2 == 0){
          j++;
          player_name = toUpperCase(team1.getTeam().get(j).getName());
          player_role = toUpperCase(team1.getTeam().get(j).getRole());
        } else {
          player_name = toUpperCase(team2.getTeam().get(j).getName());
          player_role = toUpperCase(team2.getTeam().get(j).getRole());
        }
        System.out.println(player_name+" "+player_role);
        List<WebElement> player_attributes = players.get(i).findElements(By.className("XCUIElementTypeStaticText"));
        Assert.assertEquals(player_role, player_attributes.get(0).getText());
        Assert.assertEquals(player_name, player_attributes.get(1).getText());
      }
  }

  public void selectFirstBloodChampion(TeamData team, int player_num) {
    //Parent collection
    WebElement parent = collections.get(2);
    //Assert teams in collection
    List<WebElement> players = parent.findElements(By.className("XCUIElementTypeCell"));
    //Calculate index of player
    int index = 2 * player_num;
    //Select player with correct index
    if (team.getIndex()==1)
      players.get(index).click();
    else
      players.get(index + 1).click();
  }

  public void wardIt(){
    //Press "Ward it!" button
    //wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAccessibilityId("Ward it!"))).click();
    new WebDriverWait(driver, 180)
            .until(ExpectedConditions.elementToBeClickable(driver.findElementByAccessibilityId("Ward it!"))).click();
  }
  public void tapPickLine(String line) {
    //Assert copyrights
    Assert.assertNotNull(driver.findElementsByAccessibilityId("BONUS WARD"));
    Assert.assertNotNull(driver.findElementsByAccessibilityId("Which line?"));
    //Check that button is disabled
    Assert.assertEquals(driver.findElementByAccessibilityId("Ward it!").getAttribute("enabled"),"false");
    switch (line){
      case "mid":
        //Tap MID
        tap_point.withCoordinates(driver.manage().window().getSize().width / 2, driver.manage().window().getSize().height / 2);
        new TouchAction(driver).tap(tap_point).perform();
        break;
      case "jungle":
        //Tap JUNGLE
        tap_point.withCoordinates(driver.manage().window().getSize().width / 2 - 50, driver.manage().window().getSize().height / 2 - 50);
        new TouchAction(driver).tap(tap_point).perform();
        break;
    }
    /*
    //Tap TOP (45% from the top of the screen)
    int y1 = (int)Math.round(driver.manage().window().getSize().height * 0.4);
    tap_point.withCoordinates(driver.manage().window().getSize().width / 2 , y1);
    new TouchAction(driver).tap(tap_point).perform();
    //Tap BOTTOM (55% from the top of the screen)
    int y2 = (int)Math.round(driver.manage().window().getSize().height * 0.6);
    tap_point.withCoordinates(driver.manage().window().getSize().width / 2 , y2);
    new TouchAction(driver).tap(tap_point).perform();*/
    //Tap "Ward It!"
    //wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByAccessibilityId("Ward it!"))).click();
    new WebDriverWait(driver, 180)
            .until(ExpectedConditions.elementToBeClickable(driver.findElementByAccessibilityId("Ward it!"))).click();
  }

  public void verifyPicksOnScreen() {
    //Assert image of main pick
    Assert.assertNotNull(driver.findElementByAccessibilityId("lol-stand-warded"));
    //Assert image of additional pick (MID line)
    Assert.assertNotNull(driver.findElementByAccessibilityId("line_pick_thumb_mid"));
  }

  public void tapArrow(String direction) {
    driver.findElementByAccessibilityId("arrow "+direction).click();
  }

  public void wardIsBlocked() {
    //Assert lock splash
    Assert.assertNotNull(driver.findElementByAccessibilityId("lock-splash"));
    //Assert lock timer
    Assert.assertNotNull(driver.findElementByAccessibilityId("UNLOCKS IN"));
  }

  public void copywrites (String reason){
    String copywrites = null;
    switch (reason){
      case "Tower":
        copywrites = "Which team will destroy the first tower?";
        break;
      case "Dragon":
        copywrites = "Which team will kill the first dragon?";
        break;
      case "Herald":
        copywrites = "Will the Rift Herald be killed?";
        break;
      case "Baron":
        copywrites = "Which team will get the Baron first?";
        break;
      case "Pentakill":
        copywrites = "Will there be a Pentakill?";
        break;
      case "ACE":
        copywrites = "Will there be a Ace?";
        break;
      case "Inhibitor":
        copywrites = "Which team will destroy the Inhibitor first??";
        break;
      case "WinGame":
        copywrites = "Who will win this game?";
        break;
    }
    if (copywrites!=null){
      new WebDriverWait(driver, 180)
              .until(ExpectedConditions.visibilityOfElementLocated(By.id(copywrites)));
    } else {
      System.out.println("Copywrite parameter is invalid.");
    }
  }

  public void waitPush (String reason){
    String pushText = null;
    switch (reason){
      case "Tower":
        pushText = "Which team will destroy the first tower?";
        break;
      case "Dragon":
        pushText = "Which team will kill the first dragon?";
        break;
      case "Herald":
        pushText = "Will the Rift Herald be killed?";
        break;
      case "Baron":
        pushText = "Which team will get the Baron first?";
        break;
      case "Pentakill":
        pushText = "Will there be a Pentakill?";
        break;
      case "ACE":
        pushText = "Will there be a Ace?";
        break;
      case "Inhibitor":
        pushText = "Which team will destroy the Inhibitor first??";
        break;
      case "WinGame":
        pushText = "Who will win this game?";
        break;
    }
    if (pushText!=null){
      new WebDriverWait(driver, 560)
              .until(ExpectedConditions.visibilityOfElementLocated(By.id("Ward unlocked")));
      new WebDriverWait(driver, 560)
              .until(ExpectedConditions.visibilityOfElementLocated(By.id(pushText))).click();
      new WebDriverWait(driver, 560)
              .until(ExpectedConditions.invisibilityOfElementLocated(By.id("Ward unlocked")));
      System.out.println(reason+" push");
    } else {
      System.out.println("Incorrect push names.");
    }
  }
}
