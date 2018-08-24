package appmanager;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FormHelper extends BaseHelpers
{

  public FormHelper(AppiumDriver driver) {
    super(driver);
  }
/**  public void selectByValue (String value){
    new Select(driver.findElement(By.id("de.aureum.card.dev:id/design_bottom_sheet"))).selectByVisibleText(value);
  }*/

  public void enterName(String name){
    //fillInTheField(By.id("de.aureum.card.dev:id/name"), name);
    //nameField.sendKeys(name);
  }
  public void enterLastName(String name){
    //fillInTheField(By.id("de.aureum.card.dev:id/lastName"), name);
    //lastNameField.sendKeys(name);
  }

  public void isNumKeyboardOpen() throws InterruptedException {
    Thread.sleep(2000);
    if (driver.getCapabilities().getPlatform().toString().equals("LINUX")){
      AndroidDriver android = (AndroidDriver) driver;
      Assert.assertTrue(android.isKeyboardShown());
    } else {
      assertThat("1",driver.findElementByAccessibilityId("1").isEnabled());
      assertThat("0",driver.findElementByAccessibilityId("0").isEnabled());
      //Assert.assertFalse(driver.findElements(MobileBy.ByAccessibilityId.AccessibilityId("Shift")).isEmpty());
      //Assert.assertFalse(driver.findElements(MobileBy.ByAccessibilityId.AccessibilityId("Delete")).isEmpty());
    }
  }
  public void isNumKeyboardClosed() {
    if (driver.getCapabilities().getPlatform().toString().equals("LINUX")){
      AndroidDriver android = (AndroidDriver) driver;
      Assert.assertFalse(android.isKeyboardShown());
    } else {
      try {
        driver.findElementByAccessibilityId("0").isEnabled();
        driver.findElementByAccessibilityId("1").isEnabled();
        Assert.assertTrue(false);
      } catch (NoSuchElementException e) {
        Assert.assertTrue(true);
      }
      Assert.assertTrue(driver.findElements(MobileBy.ByAccessibilityId.AccessibilityId("Shift")).isEmpty());
      Assert.assertTrue(driver.findElements(MobileBy.ByAccessibilityId.AccessibilityId("Delete")).isEmpty());
    }
  }
  public void isKeyboardOpen() {
    //String str = driver.findElement(MobileBy.ByAccessibilityId.AccessibilityId("Return")).toString();
    //boolean btn = driver.findElements(MobileBy.ByAccessibilityId.AccessibilityId("Return")).isEmpty();
    if (driver.getCapabilities().getPlatform().toString().equals("LINUX")){
      AndroidDriver android = (AndroidDriver) driver;
      Assert.assertTrue(android.isKeyboardShown());
    } else {
      Assert.assertFalse(driver.findElements(MobileBy.ByAccessibilityId.AccessibilityId("Return")).isEmpty());
      Assert.assertFalse(driver.findElements(MobileBy.ByAccessibilityId.AccessibilityId("space")).isEmpty());
    }
  }
  public void isKeyboardClosed() {
    if (driver.getCapabilities().getPlatform().toString().equals("LINUX")){
      AndroidDriver android = (AndroidDriver) driver;
      Assert.assertFalse(android.isKeyboardShown());
    } else {
      Assert.assertTrue(driver.findElements(MobileBy.ByAccessibilityId.AccessibilityId("Return")).isEmpty());
      Assert.assertTrue(driver.findElements(MobileBy.ByAccessibilityId.AccessibilityId("space")).isEmpty());
    }
  }

  public void isNextDisabled() {
    Assert.assertTrue(!NextBtn.isEnabled());
  }
  public void isNextEnabled() {
    Assert.assertTrue(NextBtn.isEnabled());
  }
  public void goBack() {
    formButtons.get(4).click();
  }

  public void verifyCodeTitle(String phone, String country_code) {
    if (driver.getCapabilities().getPlatform().toString().equals("LINUX")){
      Assert.assertThat(formStaticTexts.get(0).getAttribute("text"), containsString(country_code +" "+phone));
    } else {
      //String full_title = "We’ve sent the code to the "+ country_code +" "+phone;
      //Assert.assertEquals(formStaticTexts.get(0).getAttribute("value"),full_title);
      Assert.assertThat(formStaticTexts.get(3).getAttribute("value"), containsString(country_code +" "+phone));
    }
  }
  public void resendCode() throws InterruptedException {
    codeHaventReceived.click();
    Thread.sleep(33000);
    codeResend.click();
    Thread.sleep(2000);
  }
  public void verifyCodeError() {
    if(driver.getCapabilities().getPlatform().toString().equals("LINUX")){
      Assert.assertEquals(wrongCodeError.getAttribute("text"),"Wrong code");
    } else {
      Assert.assertEquals(wrongCodeError.getAttribute("value"),"Wrong code");
    }
  }

  public void waitForCode() {
    FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
            .withTimeout(2, TimeUnit.MINUTES)
            .pollingEvery(10,TimeUnit.SECONDS)
            .ignoring(NoSuchElementException.class)
            .withMessage("Timeout waiting the code");
    wait.until(ExpectedConditions.elementToBeClickable(formButtons.get(2)));
  }

  public void chooseAvatar(int number) {
    avatars.get(number).click();
  }

}
