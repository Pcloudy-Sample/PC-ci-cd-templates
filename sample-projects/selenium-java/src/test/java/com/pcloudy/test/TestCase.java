package com.pcloudy.test;

import com.pcloudy.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestCase {

  public void demoTest(RemoteWebDriver driver) {
    TestUtils utils = new TestUtils();

    driver.manage().window().maximize();
    driver.get("https://www.selenium.dev/selenium/web/web-form.html");

    WebElement textBox = utils.waitForVisibility(By.name("my-text"), driver);
    textBox.sendKeys("Hello Selenium from pCloudy");

    WebElement submitButton = utils.waitForVisibility(By.cssSelector("button"), driver);
    submitButton.click();

    WebElement message = utils.waitForVisibility(By.id("message"), driver);
    System.out.println("Message: " + message.getText());
  }
}
