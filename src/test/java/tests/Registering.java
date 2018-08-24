package tests;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class Registering extends TestBase {

  @Test
  @DisplayName("New user + Name screen + Back to Phone + Go to Avatar")
  public void testNameScreen () throws InterruptedException {
    app.navigation().tapSignInButton();
    app.form().proceedPhone("15251020097", "Germany");
    //app.form().waitForCodeAndGoNext();

    //First time on Name screen
    app.navigation().validateScreen("Name");
    app.form().isKeyboardOpen();
    app.form().isNextDisabled();

    //Back to Phone screen and return to Name screen
    app.form().goBack();
    app.form().isNumKeyboardOpen();
    app.form().NextBtn.click();
    //app.form().waitForCodeAndGoNext();
    app.navigation().validateScreen("Name");

    //Check logic of Name screen
    app.form().enterName("a");
    app.form().isNextDisabled();
    app.form().enterLastName("t");
    app.form().isNextDisabled();
    app.form().enterName("Auto");
    app.form().isNextDisabled();
    app.form().enterLastName("Tester");

    //Go to Avatar screen
    app.form().NextBtn.click();
    app.form().isKeyboardClosed();
  }

  @Test
  @DisplayName("New user + Avatar screen + Back to Name + Go to Avatar")
  public void testAvatarScreen () throws InterruptedException {

    //Go to Avatar screen
    app.navigation().tapSignInButton();
    app.form().proceedPhone("15251020097", "Germany");
    //app.form().waitForCodeAndGoNext();
    app.form().enterName("Auto");
    app.form().enterLastName("Tester");
    app.form().NextBtn.click();
    app.navigation().validateScreen("Avatar");

    //Go Back to Name screen and return to Avatar screen
    app.form().isNextDisabled();
    app.form().goBack();
    app.navigation().validateScreen("Name");
    app.form().NextBtn.click();

    //Select avatar and go to Matchfeed
    app.form().chooseAvatar(0);
    app.form().isNextEnabled();
    app.form().chooseAvatar(0);
    app.form().isNextDisabled();
    app.navigation().swipe("Up");
    app.form().chooseAvatar(1);

    app.form().NextBtn.click();
    app.navigation().validateScreen("Matchfeed");
  }

}
