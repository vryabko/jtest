package appmanager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LocatorsBase {

  public LocatorsBase(AppiumDriver driver) {
    Duration timeout = Duration.ofSeconds(15);
    PageFactory.initElements(new AppiumFieldDecorator(driver, timeout), this);
  }

  @AndroidFindBy(id = "button_sign_in_phone")
  @iOSFindBy(accessibility = "Sign in via Phone")
  public MobileElement loginButton;

  @AndroidFindBy(xpath= "//*[@text='Welcome to WARD, the real-time fantasy e-sport game']")
  @iOSFindBy(xpath = "//XCUIElementTypePageIndicator[@name=\"page 1 of 7\"]")
  public MobileElement walkPage1;

  @AndroidFindBy(xpath= "//*[@text='Ward is a fantasy eSport game']")
  @iOSFindBy(xpath = "//XCUIElementTypePageIndicator[@name=\"page 2 of 7\"]")
  public MobileElement walkPage2;

  @AndroidFindBy(xpath= "//*[@text='WHO WILL GET THE FIRST KILL?']")
  @iOSFindBy(xpath = "//XCUIElementTypePageIndicator[@name=\"page 3 of 7\"]")
  public MobileElement walkPage3;

  @AndroidFindBy(xpath= "//*[@text='During the match you will have 6 wards to predict']")
  @iOSFindBy(xpath = "//XCUIElementTypePageIndicator[@name=\"page 4 of 7\"]")
  public MobileElement walkPage4;

  @AndroidFindBy(id= "wardAvailableWardit")
  @iOSFindBy(xpath = "//XCUIElementTypePageIndicator[@name=\"page 5 of 7\"]")
  public MobileElement walkPage5;

  @AndroidFindBy(xpath= "//*[@text='We will notify you, when new Ward is available']")
  @iOSFindBy(accessibility = "Ward it!")
  public MobileElement walkPage5_wardIt;

  @AndroidFindBy(xpath= "//*[@text='We will notify you, when new Ward is available']")
  @iOSFindBy(xpath = "//XCUIElementTypePageIndicator[@name=\"page 6 of 7\"]")
  public MobileElement walkPage6;

  @AndroidFindBy(xpath= "//*[@text='Get the highest score and win awesome prizes!']")
  @iOSFindBy(xpath = "//XCUIElementTypePageIndicator[@name=\"page 7 of 7\"]")
  public MobileElement walkPage7;

  //Agreement elements

  @AndroidFindBy(id= "world.ward.wardit.staging:id/image")
  @iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"￼ ￼ \"]")
  public MobileElement closeAgreement;

  @AndroidFindBy(id= "agree")
  @iOSFindBy(accessibility = "Yes, I Agree")
  public MobileElement agree;

  @AndroidFindBy(id= "world.ward.wardit.staging:id/disagree")
  @iOSFindBy(accessibility = "No, I don't")
  public MobileElement disagree;

  // Common elements

  @AndroidFindBy(className = "android.widget.TextView")
  @iOSFindBy(className = "XCUIElementTypeStaticText")
  public List<MobileElement> formStaticTexts;

  @AndroidFindBy(className = "android.widget.EditText")
  @iOSFindBy(className = "XCUIElementTypeTextField")
  public List<MobileElement> textFields;

  @AndroidFindBy(className = "android.widget.ImageButton")
  @iOSFindBy(className = "XCUIElementTypeButton")
  public List<MobileElement> formButtons;

  // Login Phone screen

  @AndroidFindBy(id = "close")
  @iOSFindBy(accessibility = "Cancel")
  public MobileElement phoneCancelBtn;

  @AndroidFindBy(id = "de.aureum.card.dev:id/login")
  @iOSFindBy(accessibility = "Terms & Conditions")
  public MobileElement phoneTerms;

  @AndroidFindBy(id = "searchToggle")
  @iOSFindBy(accessibility = "+1")
  public MobileElement phoneCountryCode;

  @AndroidFindBy(id = "searchToggle")
  @iOSFindBy(accessibility = "search icon")
  public MobileElement phoneCountrySearchIcon;

  @AndroidFindBy(id = "de.aureum.card.dev:id/login")
  @iOSFindBy(accessibility = "Done")
  public MobileElement phoneCloseCountryList;

  @AndroidFindBy(id = "searchField")
  @iOSFindBy(xpath = "//XCUIElementTypeTextField[@value=\"Search\"]")
  public MobileElement phoneCountrySearchField;

  @AndroidFindBy(className = "android.widget.TextView")
  @iOSFindBy(accessibility = "Germany")
  public List<MobileElement> phoneCountryGermany;

  @AndroidFindBy(id = "next")
  @iOSFindBy(accessibility = "Next")
  public MobileElement NextBtn;

  //Login Code screen

  @AndroidFindBy(id = "confirm_code")
  @iOSFindBy(xpath = "//XCUIElementTypeTextField[@value=\"Code from sms\"]")
  public MobileElement codeField;

  @AndroidFindBy(id = "resend")
  @iOSFindBy(accessibility = "Haven't received the code?")
  public MobileElement codeHaventReceived;

  @AndroidFindBy(id = "error_text")
  @iOSFindBy(accessibility = "New SMS could be queried in 24 seconds")
  public MobileElement codeTimerMessage;

  @AndroidFindBy(id = "resend")
  @iOSFindBy(accessibility = "Resend the code")
  public MobileElement codeResend;

  @AndroidFindBy(id = "error_text")
  @iOSFindBy(accessibility = "Wrong code")
  public MobileElement wrongCodeError;

  //Register Name screen

  //1. Back button is formButtons.get(0)
  //2. Title is formStaticTexts.get(0) value="Ok, Let’s know yeach other. What is your full name?"
  //3. Name is textFields.get(0) value="Name"
  //4. Second Name is textFields.get(1) value="Second Name"
  //5. Next is formButtons.get(1) value="Next"

  //Register Avatar screen

  //1. Back button is formButtons.get(0)
  //2. Title is formStaticTexts.get(1) value="Now choose your avatar"
  //3. Next is formButtons.get(1) value="Next"
  @AndroidFindBy(id = "de.aureum.card.dev:id/login")
  @iOSFindBy(xpath = "//XCUIElementTypeCell")
  public List<MobileElement> avatars;

  //Matchfeed screen

  //1. Settings button is formButton.get(0) name="settings icon"
  //2. Title is formStaticTexts.get(0) value="Matchfeed"
  //3. Date "FRIDAY, MAR 16" is one of formStaticTexts, maybe formStaticTexts.get(1)
  //4. Parent for one match record is XCUIElementTypeCell. First record is XCUIElementTypeCell.get(1)
  @AndroidFindBy(id = "de.aureum.card.dev:id/login")
  @iOSFindBy(xpath = "//XCUIElementTypeCell")
  public List<MobileElement> games;
  //5. Child inside Parent is XCUIElementTypeStaticText
  //6. First team short record is child 0 value="ABC"
  //7. Second team short record is child 1 value="CBA"
  //8. First team full record is child 2 value="UNICORN"
  //9. Second team full record is child 3 value="FAITH"
  //10. Time of match is child 4 value="2:35 PM"

  //Enter Match screen

  //1. Back button is formButtons.get(0)
  //2. "Join the Game" button is formButtons.get(1) name="Join the Game"
  //3. First team short record is formStaticTexts.get(0) value="TSM"
  //4. Second team short record is formStaticTexts.get(2) value="FNC"
  //5. First+Second teams full records is formStaticTexts.get(3) value="TSM vs FNATIC"
  //6. Time of match is formStaticTexts.get(1) value="2:35 PM"
  //7. Draft notification is formStaticTexts.get(7) value="Draft is available"

  //Draft screen
  @iOSFindBy(xpath = "//XCUIElementTypeCollectionView")
  public List<WebElement> collections;

}
