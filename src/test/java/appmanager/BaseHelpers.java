package appmanager;

import cucumber.api.java.ca.Cal;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import io.appium.java_client.touch.WaitOptions;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class BaseHelpers extends LocatorsBase{
  protected AppiumDriver driver;

  public BaseHelpers(AppiumDriver driver) {
    super(driver);
    this.driver = driver;
  }

  protected String toUpperCase(String string){
    char[] str = string.toCharArray();
    for (int i=0;i<str.length;i++){
      str[i] = Character.toUpperCase(str[i]);
    }
    return String.valueOf(str);
  }
  protected String currentLocalDateUpper() {
    Calendar cal = Calendar.getInstance();
    String day_of_week = toUpperCase(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
    String month = toUpperCase(cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US));
    String date = toUpperCase(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
    return day_of_week+", "+month+" "+date;
  }
  protected String currentLocalDate() {
    Calendar cal = Calendar.getInstance();
    String day_of_week = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);
    String month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
    String date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    return day_of_week + ", " + month + " " + date;
  }
 /* protected String timeForGame(String time) {
    Calendar cal = Calendar.getInstance();
    //String day_of_week = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
    String month = String.valueOf(cal.get(Calendar.MONTH));
    String year = String.valueOf(cal.get(Calendar.YEAR));
    String date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

    return  date+month+year+time;
  }*/

  public void swipe (String direction){
    Dimension size = driver.manage().window().getSize();
    // PointOption point = new PointOption();
    WaitOptions wait = new WaitOptions().withDuration(Duration.ofMillis(1000));
    // JavascriptExecutor js = (JavascriptExecutor) driver;
    HashMap<String, String> swipeElement = new HashMap<String, String>();

    switch (direction) {
      case "Up":
        swipeElement.put("duration",String.valueOf(1.0));
        swipeElement.put("fromX", String.valueOf(size.width / 2));
        swipeElement.put("fromY", String.valueOf(size.height));
        swipeElement.put("toX", String.valueOf(size.width / 2));
        swipeElement.put("toY", String.valueOf(size.height / 2 - size.height / 4));
        break;
      case "Down":
        swipeElement.put("duration",String.valueOf(1.0));
        swipeElement.put("fromX", String.valueOf(size.width / 2));
        swipeElement.put("fromY", String.valueOf(size.height / 2));
        swipeElement.put("toX", String.valueOf(size.width / 2));
        swipeElement.put("toY", String.valueOf(size.height));
        break;
      case "Left":
        swipeElement.put("duration",String.valueOf(1.0));
        swipeElement.put("fromX", String.valueOf(size.width / 2 + size.width / 3));
        swipeElement.put("fromY", String.valueOf(size.height / 2));
        swipeElement.put("toX", String.valueOf(size.width / 10));
        swipeElement.put("toY", String.valueOf(size.height / 2));
        break;
      case "Right":
        swipeElement.put("duration",String.valueOf(1.0));
        swipeElement.put("fromX", String.valueOf(size.width / 2 - size.width / 3));
        swipeElement.put("fromY", String.valueOf(size.height / 2));
        swipeElement.put("toX", String.valueOf(size.width));
        swipeElement.put("toY", String.valueOf(size.height / 2));
        break;
    }

    if (driver.getCapabilities().getPlatform().toString().equals("MAC")){
      //driver.getPlatformName().toString().equals("android")
      //swipeElement.put("direction", move);
      //swipeElement.put("element", element);
      //js.executeScript("mobile: swipe", swipeElement);
      for (int i = 0; i < 3; i++) {
        driver.executeScript("mobile: dragFromToForDuration", swipeElement);
      }

    } /*else {
      new TouchAction(driver)
              .press(new PointOption().withCoordinates(startX,startY))
              .waitAction(wait)
              .moveTo(new PointOption().withCoordinates(endX,endY))
              .release()
              .perform();
    }*/

  }

  public void swipe (String direction, WebElement element) {
    // JavascriptExecutor js = (JavascriptExecutor) driver;
    HashMap<String, String> swipeElement = new HashMap<String, String>();
    String move = null;

    switch (direction) {
      case "Up":
        move = "up";
        break;
      case "Down":
        move = "down";
        break;
      case "Left":
        swipeElement.put("duration",String.valueOf(1.0));
        swipeElement.put("element", element.getAttribute("name"));
        swipeElement.put("fromX", String.valueOf(element.getLocation().x));
        swipeElement.put("fromY", String.valueOf(element.getLocation().y));
        swipeElement.put("toX", String.valueOf(10));
        swipeElement.put("toY", String.valueOf(element.getLocation().y));
        break;
      case "Right":
        move = "right";
        break;
    }

    //swipeElement.put("direction", move);
    //js.executeScript("mobile: swipe", swipeElement);
    //driver.executeScript("mobile: swipe", swipeElement);
    driver.executeScript("mobile: dragFromToForDuration", swipeElement);
  }

  public void tapSignInButton(){
             new WebDriverWait(driver, 10)
            .until(ExpectedConditions.elementToBeClickable(loginButton))
            .click();
    //loginButton.click();
  }
  public void proceedPhone(String phone, String country) throws InterruptedException {
    Thread.sleep(1000);
    clickCountryField();
    selectCountry(country);
    enterPhone(phone);
    NextBtn.click();
  }
  public void clickCountryField() {
    //formStaticTexts.get(1).click();
    phoneCountryCode.click();
  }
  public void selectCountry(String country) {
    phoneCountrySearchIcon.click();
    phoneCountrySearchField.sendKeys(country);
    phoneCountryGermany.get(1).click();
  }
  public void enterPhone(String number) {
    textFields.get(0).clear();
    textFields.get(0).sendKeys(number);
  }
  public void enterCode(String code) throws InterruptedException {
    Thread.sleep(1000);
    //new TouchAction(driver).tap(tapOptions().withElement(element(textFields.get(0)))).perform();
    textFields.get(0).sendKeys(code);
  }

  public void makeScreenshot(String pathname) throws IOException, InterruptedException {
    Thread.sleep(1000);
    File srcFile = driver.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(srcFile, new File(pathname));
  }

  public void backToMatchfeedFromMatch() {
    formButtons.get(0).click();
    //System.out.println(driver.getPageSource());
    //WebElement back_button = driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Back to matchfeed']"));
    formButtons.get(3).click();
  }
  }
