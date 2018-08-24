package appmanager;

import model.DraftData;
import model.GameData;
import model.PlayerData;
import model.TeamData;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class GameHelper {
  private WebDriver browser;

  public GameHelper(WebDriver browser) {
    this.browser = browser;
  }

  public void createGame(String game_type, String first_team, String second_team, String start_hours, String start_minutes) throws AWTException {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    String hash = "auto-" + timestamp.getTime();
    //Click "Create" button
    browser.findElement(By.xpath("//div[@class=\"games-controls\"]/button")).click();
    //Choose game type
    browser.findElement(By.xpath("//option[@value=\""+game_type+"\"]")).click();
    //Choose first team
    browser.findElement(By.xpath("//option[.='"+first_team+"']")).click();
    //Choose second team
    browser.findElement(By.xpath("(//option[.='"+second_team+"'])[2]")).click();
    //Set game time
    WebElement date = browser.findElement(By.name("starts_at"));
    date.click();
    date.sendKeys(Keys.ARROW_RIGHT);
    date.sendKeys(Keys.ARROW_LEFT);
    date.sendKeys(start_hours);
    date.sendKeys(Keys.ARROW_RIGHT);
    date.sendKeys(start_minutes);
    //date.sendKeys(Keys.ARROW_RIGHT,start_minutes);
    //System.out.println(timestamp.toLocalDateTime().truncatedTo(ChronoUnit.MINUTES));
    //Insert unique game ID
    browser.findElement(By.name("game_id")).click();
    browser.findElement(By.name("game_id")).clear();
    browser.findElement(By.name("game_id")).sendKeys(hash);
    //Choose game state
    //browser.findElement(By.xpath("//option[@value='pending']")).click();
    //UN-Select wards
    //browser.findElement(By.name("prepick")).click();
    //browser.findElement(By.name("tower")).click();
    //Click Save button
    browser.findElement(By.xpath("//button[@type='submit']")).click();
  }

  public void createGame(String game_type, String first_team, String second_team, int shifting_minutes) throws AWTException {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    String hash = "auto-" + timestamp.getTime();
    //Click "Create" button
    browser.findElement(By.xpath("//div[@class=\"games-controls\"]/button")).click();
    //Choose game type
    browser.findElement(By.xpath("//option[@value=\""+game_type+"\"]")).click();
    //Choose first team
    browser.findElement(By.xpath("//option[.='"+first_team+"']")).click();
    //Choose second team
    browser.findElement(By.xpath("(//option[.='"+second_team+"'])[2]")).click();
    //Set shifting time
    Calendar cal = setShiftingMinutes(shifting_minutes);
    String start_hours = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
    String start_minutes = String.valueOf(cal.get(Calendar.MINUTE));
    System.out.println(start_hours);
    System.out.println(start_minutes);
    //Set game time
    WebElement date = browser.findElement(By.name("starts_at"));
    date.click();
    date.sendKeys(Keys.ARROW_RIGHT);
    date.sendKeys(Keys.ARROW_LEFT);
    date.sendKeys(start_hours);
    date.sendKeys(Keys.ARROW_RIGHT);
    date.sendKeys(start_minutes);
    //date.sendKeys(Keys.ARROW_RIGHT,start_minutes);
    //Insert unique game ID
    browser.findElement(By.name("game_id")).click();
    browser.findElement(By.name("game_id")).clear();
    browser.findElement(By.name("game_id")).sendKeys(hash);
    //Click Save button
    browser.findElement(By.xpath("//button[@type='submit']")).click();
  }

  public GameData createGame(String game_type, TeamData first_team, TeamData second_team, int shifting_minutes) throws AWTException, InterruptedException {
    GameData game = new GameData(first_team,second_team,0,0);
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    String hash = "auto-" + timestamp.getTime();
    //Click "Create" button
    //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"games-controls\"]/button"))).click();
    new WebDriverWait(browser, 180)
            .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"games-controls\"]/button"))).click();
    //Choose game type
    browser.findElement(By.xpath("//option[@value=\""+game_type+"\"]")).click();
    //Choose first team
    browser.findElement(By.name("team_1")).sendKeys(first_team.getName());
    browser.findElement(By.cssSelector("div.autocomplete-items")).click();
    //Choose second team
    browser.findElement(By.name("team_2")).sendKeys(second_team.getName());
    browser.findElement(By.cssSelector("div.autocomplete-items")).click();
    //Set shifting time
    Calendar cal = setShiftingMinutes(shifting_minutes);
    String start_hours = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
    String start_minutes = String.valueOf(cal.get(Calendar.MINUTE));
    //Set game time
    WebElement date = browser.findElement(By.name("starts_at"));
    date.click();
    date.sendKeys(Keys.ARROW_RIGHT);
    date.sendKeys(Keys.ARROW_LEFT);
    date.sendKeys(start_hours);
    date.sendKeys(Keys.ARROW_RIGHT);
    date.sendKeys(start_minutes);
    //date.sendKeys(Keys.ARROW_RIGHT,start_minutes);
    //Insert unique game ID
    browser.findElement(By.name("game_id")).click();
    browser.findElement(By.name("game_id")).clear();
    browser.findElement(By.name("game_id")).sendKeys(hash);
    //Click Save button
    browser.findElement(By.xpath("//button[@type='submit']")).click();
    game.setTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
    return game;
  }

  public void selectGame(String data, String time, String firstT, String secondT){
    //Select list of elements in table
    List<WebElement> table = browser.findElements(By.xpath("//tr"));
    //int row_id = Integer.MAX_VALUE;
    //Go through each element in table and find the right game (!!! Game type and state are not considered)
    for (WebElement e: table) {
      try {
        Thread.sleep(3000);
        List<WebElement> game_item = e.findElements(By.xpath(".//td"));
        //Go through each parameter of game row to find necessary element
        if (game_item.get(0).getText().equals(data))
          if (game_item.get(1).getText().equals(time))
            if (game_item.get(2).getText().equals(firstT))
              if (game_item.get(3).getText().equals(secondT)) {
                e.click();
                break;
              }
      } catch (NoSuchElementException ex){
        System.out.println(ex);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
    }
    //Click on found row
    //table.get(row_id).click();
  }

  public boolean doesGameExist(String data, String time, String firstT, String secondT){
    //Select list of elements in table
    List<WebElement> table = browser.findElements(By.xpath("//tr"));
    int row_id = Integer.MAX_VALUE;
    //Go through each element in table and find the right game (!!! Game type and state are not considered)
    for (WebElement e: table) {
      try {
        List<WebElement> game_item = e.findElements(By.xpath(".//td"));
        //Go through each parameter of game row to find necessary element
        if (game_item.get(0).getText().equals(data))
          if (game_item.get(1).getText().equals(time))
            if (game_item.get(2).getText().equals(firstT))
              if (game_item.get(3).getText().equals(secondT)) {
                return true;
              }
      } catch (NoSuchElementException ex){
        System.out.println(ex);
      }
    }
    return false;
  }

  public void validateStatus(String status) {
    switch (status){
      case "Wards disabled":
      Assert.assertNotNull(browser.findElement(By.xpath("//div[@class='game-ward-disabled']")));
      break;
    }
  }

  public void verifyMainTeamPlayersInDraft(TeamData team1, TeamData team2) {

    List<WebElement> draft_columns = browser.findElements(By.xpath("//div[@class='game-column__item']"));
    List<WebElement> players_team1 = draft_columns.get(0).findElements(By.xpath(".//div[@class='draft-player']"));
    List<WebElement> players_team2 = draft_columns.get(1).findElements(By.xpath(".//div[@class='draft-player']"));

    isPlayerInTeam(team1,players_team1);
    isPlayerInTeam(team2,players_team2);
  }

  public void isPlayerInTeam(TeamData team1, List<WebElement> players) {
    for (WebElement p: players){
      List<PlayerData> team = team1.getTeam();
      String player_name = p.findElement(By.xpath(".//span[@class='draft-player__name']")).getText();
      String player_role = p.findElement(By.xpath(".//span[@class='draft-player__role']")).getText();
      Assert.assertTrue(team.stream().anyMatch(pr -> pr.getName().equals(player_name)&&pr.getRole().equals(player_role)));
    }
  }

  public void setHeroes (GameData game, TeamData team, DraftData heroes) throws InterruptedException {
    initMonitor();
    selectGame(game.getDate(),game.getFullTime(),game.getTeam1().getName(),game.getTeam2().getName());
    Thread.sleep(1000);
    //Find columns of heroes: team1 + team2
    List<WebElement> draft_columns = browser.findElements(By.cssSelector("div.game-column__item"));

    if (team.getIndex()==1) {
      //Find list of players for team1
      List<WebElement> draft_fields = draft_columns.get(0).findElements(By.xpath(".//div[@class='autocomplete']"));
      chooseHeroInField(heroes.getDraft(), draft_fields);
    } else {
      //Find list of players for team2
      List<WebElement> draft_fields = draft_columns.get(1).findElements(By.xpath(".//div[@class='autocomplete']"));
      chooseHeroInField(heroes.getDraft(), draft_fields);
    }
  }

  private void chooseHeroInField(List<String> heroes, List<WebElement> draft_fields) {
    for(int i=0; i<draft_fields.size(); i++){
      //Select dropdown of particular player
      WebElement field = draft_fields.get(i).findElement(By.xpath(".//input[@type='text']"));
      field.click();
      field.sendKeys(heroes.get(i));
      List<WebElement> dropdown_heroes = draft_fields.get(i).findElements(By.xpath(".//div[@class='autocomplete-items']/div"));
      //dropdown_heroes.stream().filter(h -> h.getText().equals(hero)).findFirst().get().click();
      dropdown_heroes.get(0).click();
    }
  }

  public void confirmDraft(){
    browser.findElement(By.cssSelector("button.btn.btn-primary")).click();
  }

  protected Calendar setShiftingMinutes(int time_shifting) {
    Calendar cal = Calendar.getInstance();
    //String day_of_week = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
    int hours = cal.get(Calendar.HOUR_OF_DAY);
    int minutes = cal.get(Calendar.MINUTE);
    //String date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    if (minutes+time_shifting > 60 ){
      minutes = minutes+time_shifting-60;
      hours = hours+1;
    } else {
      minutes = minutes+time_shifting;
    }
    cal.set(Calendar.HOUR_OF_DAY,hours);
    cal.set(Calendar.MINUTE,minutes);
    return cal;
  }

  public void finishGame(GameData game, TeamData team) throws InterruptedException {
    initMonitor();
    selectGame(game.getDate(),game.getFullTime(),game.getTeam1().getName(),game.getTeam2().getName());
    startGame(game);
    team = null; //no matter who will win
    Thread.sleep(2000);
    scrollDown();
    selectNexusWard(team);
    pressConfirmAll();
  }

  private void scrollDown() {
    JavascriptExecutor js = (JavascriptExecutor)browser;
    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
  }

  private void pressConfirmAll() {
    browser.findElement(By.ByClassName.className("btn-info")).click();
  }

  private void selectNexusWard(TeamData team) {
    List<WebElement> wards = browser.findElements(By.cssSelector("div.game-view-item-container"));
    //Check that it's Nexus ward
    for(WebElement w: wards){
      System.out.println(w.findElement(By.xpath(".//div[@class='ward-title__text']")).getText());
    }
    Assert.assertEquals(wards.get(8).findElement(By.xpath(".//div[@class='ward-title__text']")).getText(),"Nexus");
    if (team!=null){
      //select exact winner
    } else {
      //select the first winner
      wards.get(8).findElements(By.xpath(".//input[@type='checkbox']")).get(0).click();
    }
  }

  public void startGame(GameData game) throws InterruptedException {
    initMonitor();
    selectGame(game.getDate(),game.getFullTime(),game.getTeam1().getName(),game.getTeam2().getName());
    Thread.sleep(1000);
    browser.findElement(By.cssSelector("div.game-start")).click();
  }

  private void login() {
    WebElement auth_field = browser.findElement(By.xpath("//input[@name='token']"));
    auth_field.click();
    auth_field.sendKeys("secret");
    browser.findElement(By.xpath("//button[@type='submit']")).click();
  }

  public void substitutePlayer(GameData game, TeamData team, int player_num, String player_name) throws InterruptedException {
    initMonitor();
    selectGame(game.getDate(),game.getFullTime(),game.getTeam1().getName(),game.getTeam2().getName());
    Thread.sleep(2000);
    browser.findElement(By.cssSelector("div.ward-prepick-controls")).findElements(By.cssSelector("button.btn")).get(1).click();
    List<WebElement> slots = browser.findElement(By.cssSelector("div.game-view-row__item--2")).findElements(By.cssSelector("div.game-column__item")).get(team.getIndex()-1).findElements(By.cssSelector("div.substitution-item"));
    slots.get(player_num-1).findElement(By.cssSelector("select.substitution-item__select")).sendKeys(player_name);
    browser.findElement(By.cssSelector("div.ward-prepick-controls")).findElements(By.cssSelector("button.btn")).get(0).click();
  }

  private void initMonitor() {
    try {
      WebElement item = browser.findElement(By.className("game-start"));
    } catch (NoSuchElementException ex){
      browser.get("https://monitor-staging.wardapp.xyz/");
    }
    try{
      login();
    } catch (NoSuchElementException ex){
      System.out.println("Login is not needed.");
    }
  }

  public void setSomeDraft(GameData game) throws InterruptedException {
    DraftData heroes1 = new DraftData("Aatrox","Caitlyn","Taric","Diana","Evelynn");
    DraftData heroes2 = new DraftData("Braum","Fiora","Gragas","Irelia","Kalista");
    setHeroes(game,game.getTeam1(),heroes1);
    setHeroes(game,game.getTeam2(),heroes2);
    confirmDraft();
  }

  public void setFirstBloodWard(TeamData team1, int player_num, String line) {

  }
}
