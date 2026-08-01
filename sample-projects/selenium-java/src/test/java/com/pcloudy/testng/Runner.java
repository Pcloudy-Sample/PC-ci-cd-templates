package com.pcloudy.testng;

import com.pcloudy.test.TestCase;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Runner {

  private RemoteWebDriver driver;

  @Parameters({
    "os",
    "browserName",
    "userName",
    "accessKey",
    "baseUrl",
    "local",
    "autoHeal",
    "queueTimeout",
    "idleTimeout",
    "project",
    "build",
    "name",
    "tag"
  })
  @BeforeMethod
  public void prepareTest(
      String os,
      String browserName,
      String userName,
      String accessKey,
      String baseUrl,
      @Optional("false") String local,
      @Optional("false") String autoHeal,
      @Optional("None") String queueTimeout,
      @Optional("None") String idleTimeout,
      String project,
      String build,
      String name,
      String tag)
      throws Exception {

    Driver driverObj = new Driver();
    driver =
        driverObj.createDriver(
            os,
            browserName,
            userName,
            accessKey,
            baseUrl,
            local,
            autoHeal,
            queueTimeout,
            idleTimeout,
            project,
            build,
            name,
            tag);
  }

  @Test
  public void seleniumSmokeTest() {
    TestCase test = new TestCase();
    test.demoTest(driver);
  }

  @AfterMethod
  public void endTest(ITestResult result) {
    if (driver != null) {
      String status = result.isSuccess() ? "pass" : "fail";
      driver.executeScript("pCloudy_setResult", status);
      driver.quit();
    }
  }
}
