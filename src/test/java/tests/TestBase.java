package tests;

import appmanager.AppManager;
import io.appium.java_client.remote.MobilePlatform;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import java.net.MalformedURLException;


public class TestBase {

  protected static final AppManager app = new AppManager(MobilePlatform.IOS);
  //protected static final AppManager app = new AppManager(BrowserType.FIREFOX);
  //protected static final AppManager app = new AppManager(MobilePlatform.ANDROID);
 // public WebDriver browser;

  @Before
  public void startDriver() throws MalformedURLException {
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
  @After
  public void stopDriver() {
    app.stop();
   // browser.quit();
  }

}
