package tests.sanityTests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import tests.sanityTests.LoginSanityCheck;

public class SanityTestRunner {

  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(LoginSanityCheck.class);
    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }
    System.out.println("Result=="+result.wasSuccessful());
  }
}
