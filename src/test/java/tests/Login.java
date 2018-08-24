package tests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class Login extends TestBase {

  @Test
  @DisplayName("Full Walkthrough. Check every page by texts.")
  public void testFullWalkthrough() throws InterruptedException {
    app.navigation().validateScreen("Welcome");
    app.navigation().swipe("Left", 1);
    app.navigation().validateScreen("WalkPage2");
    app.navigation().swipe("Left", 1);
    app.navigation().validateScreen("WalkPage3");
    app.navigation().swipe("Left", 1);
    app.navigation().validateScreen("WalkPage4");
    app.navigation().swipe("Left", 1);
    app.navigation().validateScreen("WalkPage5");
    app.navigation().swipe("Left", 1);
    app.navigation().validateScreen("WalkPage6");
    app.navigation().swipe("Left", 1);
    app.navigation().validateScreen("WalkPage7");
    app.navigation().swipe("Left", 1);
    /*app.navigation().swipe("Right",1);
    app.navigation().validateScreen("WalkPage6");
    app.navigation().swipe("Left",1);*/
    app.navigation().validateScreen("WalkPage7");
    app.navigation().tapSignInButton();
  }

  @Test
  @DisplayName("Test Agreement. Test Keyboard on the Phone screen.")
  public void testShortWalkthrough() throws InterruptedException {
    app.navigation().swipe("Left", 6);
    app.navigation().tapSignInButton();
    app.navigation().closeAgreement();
    app.navigation().tapSignInButton();
    app.navigation().discardAgreement();
    app.navigation().tapSignInButton();
    app.navigation().acceptAgreement();
    app.form().isNumKeyboardOpen();
    app.navigation().tapBackButton();
    app.form().isNumKeyboardClosed();
  }

  @Test
  @DisplayName("Select country. Enter phone and proceed to code")
  public void testPhoneScreen() throws InterruptedException {
    app.navigation().goToPhoneScreen();
    app.form().isNextDisabled();
    app.form().isKeyboardOpen();
    app.form().clickCountryField();
    app.form().selectCountry("Germany");
    app.form().enterPhone("123456789");
    app.form().isKeyboardOpen();
    app.navigation().NextBtn.click();
  }

  @Test
  @DisplayName("Test code screen + Back to Phone screen + Resend code + Incorrect code")
  public void testLogin() throws InterruptedException {
    app.navigation().goToPhoneScreen();
    //Go to code screen
    app.form().proceedPhone("123456789", "Germany");
    app.form().isNumKeyboardOpen();
    app.form().verifyCodeTitle("123456789", "+49");
    //Go back from code to phone screen
    app.form().goBack();
    app.form().isNumKeyboardOpen();
    //Go to code screen again
    app.form().proceedPhone("987654321", "Germany");
    app.form().isNumKeyboardOpen();
    app.form().verifyCodeTitle("987654321", "+49");
    //Resend code
    app.form().resendCode();
    app.form().isNextDisabled();
    //Enter incorrect code and verify an error
    app.form().enterCode("1111");
    app.form().NextBtn.click();
    app.form().verifyCodeError();
  }
}
