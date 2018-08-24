package appmanager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

public class NavHelper extends BaseHelpers{

  public NavHelper(AppiumDriver driver) {
    super(driver);
  }

  public void validateScreen(String screen){
    switch (screen){
      case "Welcome":
        Assert.assertNotNull(walkPage1);
        break;
      case "WalkPage2":
        Assert.assertNotNull(walkPage2);
        break;
      case "WalkPage3":
        Assert.assertNotNull(walkPage3);
        break;
      case "WalkPage4":
        Assert.assertNotNull(walkPage4);
        break;
      case "WalkPage5":
        Assert.assertNotNull(walkPage5);
        Assert.assertNotNull(walkPage5_wardIt);
        break;
      case "WalkPage6":
        Assert.assertNotNull(walkPage6);
        break;
      case "WalkPage7":
        Assert.assertNotNull(walkPage7);
        break;
      case "Name":
        Assert.assertEquals(formStaticTexts.get(0).getAttribute("value"),"Ok, Let’s know yeach other. What is your full name?");
        break;
      case "Avatar":
        Assert.assertEquals(formStaticTexts.get(1).getAttribute("value"),"Now choose your avatar");
        break;
      case "Matchfeed":
        Assert.assertEquals(formStaticTexts.get(0).getAttribute("value"),"Matchfeed");
        break;
    }
  }

  public void swipe (String direction, int iterations) throws InterruptedException {
    Dimension size = driver.manage().window().getSize();
   // PointOption point = new PointOption();
    WaitOptions wait = new WaitOptions().withDuration(Duration.ofMillis(1000));
    //JavascriptExecutor js = (JavascriptExecutor) driver;
    HashMap<String, String> swipeElement = new HashMap<String, String>();

    int startX = 0;
    int startY = 0;
    int endX = 0;
    int endY = 0;
    String move = null;

    switch (direction) {
      case "Up":
        startX = size.width / 2;
        startY = size.height / 2;
        endX = startX;
        endY = 10;
        move = "up";
        break;
      case "Down":
        startX = size.width / 2;
        startY = size.height / 2;
        endX = startX;
        endY = size.height;
        move = "down";
        break;
      case "Left":
        startX = size.width / 2 + size.width / 3;
        startY = size.height / 2;
        endX = size.width / 10;
        endY = size.height / 2;
        move = "left";
        break;
      case "Right":
        startX = size.width / 2 - size.width / 3;
        startY = size.height / 2;
        endX = size.width;
        endY = size.height / 2;
        move = "right";
        break;
    }

   /* if (driver.getCapabilities().getPlatform().toString().equals("MAC")){
      //driver.getPlatformName().toString().equals("android")
      swipeElement.put("direction", move);
      //js.executeScript("mobile: swipe", swipeElement);
      for (int i = 0; i < iterations; i++) {
        //driver.executeScript("mobile: swipe", swipeElement);
        driver.executeScript("mobile: swipeLeft");
      }
    } else {*/
      for (int i = 0; i < iterations; i++) {
        new TouchAction(driver)
                .press(new PointOption().withCoordinates(startX, startY))
                .waitAction(wait)
                .moveTo(new PointOption().withCoordinates(endX, endY))
                .release()
                .perform();
        Thread.sleep(1000);
      }


  }

  public void goToPhoneScreen() throws InterruptedException {
    swipe("Left",6);
    tapSignInButton();
    acceptAgreement();
  }

  public void tapBackButton() {
    phoneCancelBtn.click();
  }

  public void acceptAgreement() throws InterruptedException {
    Thread.sleep(2000);
    new TouchAction(driver).tap(tapOptions().withElement(element(agree))).perform();
    Thread.sleep(2000);
  }
  public void discardAgreement() throws InterruptedException {
    Thread.sleep(2000);
    new TouchAction(driver).tap(tapOptions().withElement(element(disagree))).perform();
    Thread.sleep(2000);
  }
  public void closeAgreement() throws InterruptedException {
    Thread.sleep(2000);
    new TouchAction(driver).tap(tapOptions().withElement(element(closeAgreement))).perform();
    Thread.sleep(2000);
  }
}
