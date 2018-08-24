package tests.sanityTests;

import appmanager.AppManager;
import io.appium.java_client.remote.MobilePlatform;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.net.MalformedURLException;

public class SanityTestBase {

  protected static final AppManager app = new AppManager(MobilePlatform.IOS);
  //protected static final AppManager app = new AppManager(BrowserType.FIREFOX);
  //protected static final AppManager app = new AppManager(MobilePlatform.ANDROID);
  // public WebDriver browser;

  @BeforeClass
  public static void startDriver() throws MalformedURLException {
    app.init();

/*    ChromeOptions options = new ChromeOptions();
    options.addArguments("disable-infobars");
    options.addArguments("--start-maximized");
    options.addArguments("--disable-extensions");
    //options.addPreference("security.sandbox.content.level",5);
    browser = new ChromeDriver(options);
    browser.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    browser.get("https://admin-staging.wardapp.xyz/");*/
  }
  @AfterClass
  public static void stopDriver() {
    app.stop();
    // browser.quit();
  }

}
