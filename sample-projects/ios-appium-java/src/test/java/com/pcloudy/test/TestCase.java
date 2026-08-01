package com.pcloudy.test;

import io.appium.java_client.AppiumDriver;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.pcloudy.utils.TestUtils;

public class TestCase {
	
	public void demoTest(AppiumDriver driver) throws Exception {
		TestUtils utils = new TestUtils();

		// Enter email
		WebElement emailField = utils.waitForVisibility(
				By.xpath("//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeTextField[1]"),
				driver);
		emailField.sendKeys("test@testname.com");

		// Enter password
		WebElement passwordField = utils.waitForVisibility(
				By.xpath("//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeSecureTextField[1]"),
				driver);
		passwordField.sendKeys("testmunk");

		// Click on login button
		WebElement loginButton = utils.waitForVisibility(
				By.xpath("//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[1]"),
				driver);
		loginButton.click();
	}

}
