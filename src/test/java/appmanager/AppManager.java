package appmanager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import model.UserData;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppManager {

  private DraftHelper draft;
  private MatchHelper match;
  private GameHelper gameHelper;
  private LocatorsBase locators;
  private NavHelper navigation;
  private FormHelper form;
  public AppiumDriver driver;
  private String agent;
  public WebDriver browser;

  public AppManager(String agent) {
    this.agent = agent;
  }

  public void init() throws MalformedURLException {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    if (agent.equals(MobilePlatform.ANDROID)) {
      capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
      capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "041604a1e6563902");
      capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.0");
      capabilities.setCapability(MobileCapabilityType.APP, "C:\\Testing\\apk\\Wardit-staging-release-0.0.348.apk");
      capabilities.setCapability("autoGrantPermissions", true);
      capabilities.setCapability("noReset", true);
      driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
    } else if (agent.equals(MobilePlatform.IOS)) {
      capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
      capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone X");
      capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.4");
      capabilities.setCapability(MobileCapabilityType.APP, "/Users/valery/Desktop/Ward.app");
      capabilities.setCapability("autoGrantPermissions", true);
      capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
      capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,180);
      driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
    }
      ChromeOptions options = new ChromeOptions();
      options.addArguments("disable-infobars");
      options.addArguments("--start-maximized");
      options.addArguments("--disable-extensions");
      browser = new ChromeDriver(options);
      browser.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
      browser.get("https://admin-staging.wardapp.xyz/auth");
      login();

    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    form = new FormHelper(driver);
    navigation = new NavHelper(driver);
    locators = new LocatorsBase(driver);
    gameHelper = new GameHelper(browser);
    match = new MatchHelper(driver);
    draft = new DraftHelper(driver);
  }

  private void login() {
    WebElement auth_field = browser.findElement(By.xpath("//input[@name='token']"));
    auth_field.click();
    auth_field.sendKeys("secret");
    browser.findElement(By.xpath("//button[@type='submit']")).click();
  }

  public void stop() {
    driver.quit();
    browser.quit();
  }

  public void proceedPhone(String number){
    form.enterPhone(number);
    locators.loginButton.click();
    //form.clickElement(By.id("de.aureum.card.dev:id/login"));
  }

  public void checkText(By locator, String text) {
    WebElement title = driver.findElement(locator);
    String titleText = title.getText();
    Assert.assertEquals(titleText, text);
  }

  public void verifyScreen(String screen_name) {
    boolean is_found = false; // validation parameter
    if (screen_name.equals("Onboarding Name")) {
      checkText(By.id("de.aureum.preference.dev:id/description"),"Please enter your name");
      is_found = true;
    }
    else if (screen_name.equals("Phone input")) {
      checkText(By.className("android.widget.TextView"), "Use your phone number to login or create a new account");
      is_found = true;
    }
    else if (screen_name.equals("Code 1")) {
      checkText(By.id("de.aureum.preference.dev:id/title"), "Setup your password");
      is_found = true;
    }
    else if (screen_name.equals("Code 2")) {
      checkText(By.id("de.aureum.preference.dev:id/title"), "Confirm your password");
      is_found = true;
    }
    else if (screen_name.equals("Code login")) {
      checkText(By.id("de.aureum.preference.dev:id/title"), "Enter your password");
      is_found = true;
    }
    else if (screen_name.equals("Enable camera")) {
      checkText(By.id("de.aureum.preference.dev:id/description"), "First we need to know how you look, so we can recognise you anywhere and anytime you want");
      is_found = true;
    }
    else if (screen_name.equals("Photo screen")) {
      checkText(By.className("android.widget.TextView"), "First we need to know how you look, so we can recognise you anywhere and anytime you want");
      is_found = true;
    }
    Assert.assertEquals(is_found,true);
  }

  public FormHelper form() {
    return form;
  }

  public NavHelper navigation() {
    return navigation;
  }

  public LocatorsBase locators() {
    return locators;
  }

  public GameHelper getGameHelper() {
    return gameHelper;
  }

  public MatchHelper match() {
    return match;
  }

  public DraftHelper draft() {
    return draft;
  }

  /*  public void keyboardIsOpen(){
    Assert.assertEquals(driver.isKeyboardShown(),true);
  }
  public void keyboardIsClosed(){
    Assert.assertEquals(driver.isKeyboardShown(),false);
  }
*/
}
