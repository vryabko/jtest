package tests.sanityTests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class LoginSanityCheck extends SanityTestBase {

  @Test
  @DisplayName("Login Sanity check from Walkthrough.")
  public void sanityTestLogin1() throws InterruptedException {
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
    app.navigation().tapSignInButton();
  }

  @Test
  @DisplayName("Login Sanity check Agreement.")
  public void sanityTestLogin2() throws InterruptedException {
    //app.navigation().closeAgreement(); закрыто из-за отсутствия идентификатора для крестика в iOS
    //app.navigation().tapSignInButton(); закрыто из-за отсутствия идентификатора для крестика в iOS
    app.navigation().discardAgreement();
    app.navigation().tapSignInButton();
    app.navigation().acceptAgreement();
    app.form().isNumKeyboardOpen();
  //  app.navigation().tapBackButton(); Закрыто из-за бага о неубирающейся клавиатуре в Android
   // app.form().isNumKeyboardClosed(); Закрыто из-за бага о неубирающейся клавиатуре в Android
  }

  @Test
  @DisplayName("Login Sanity check Phone screen (with country selection).")
  public void sanityTestLogin3() throws InterruptedException {
   // app.navigation().acceptAgreement(); Закрыто из-за бага о неубирающейся клавиатуре в Android
    app.form().isNextDisabled();
    app.form().clickCountryField();
    app.form().selectCountry("Germany");
    app.form().isNumKeyboardOpen();
    app.form().enterPhone("123456789");
    app.form().isNextEnabled();
    app.navigation().NextBtn.click();
  }

  @Test
  @DisplayName("Login Sanity check Code screen.")
  public void sanityTestLogin4() throws InterruptedException {
    app.form().isNumKeyboardOpen();
    app.form().verifyCodeTitle("123456789", "+49");
    //Go back from code to phone screen
    app.form().goBack();
    app.form().isNumKeyboardOpen();
    //Go to code screen again
    //app.form().proceedPhone("987654321", "Germany");
    app.form().enterPhone("987654321");
    app.form().NextBtn.click();
    //Resend code
    app.form().resendCode();
    app.form().isNextDisabled();
    //Enter incorrect code and verify an error
    app.form().enterCode("1111");
    app.form().NextBtn.click();
    app.form().verifyCodeError();
    app.form().enterCode("198412");
    app.form().NextBtn.click();
  }

}
