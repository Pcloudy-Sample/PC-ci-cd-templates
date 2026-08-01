package com.pcloudy.utils;

import java.time.Duration;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.io.FileHandler;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumDriver;

public class TestUtils {

	public WebElement waitForVisibility(By locator, AppiumDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForVisibilityextra(By locator, AppiumDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void captureScreenShots(AppiumDriver driver) throws IOException {

		String folder_name;
		DateFormat df;

		folder_name = "screenshot";
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File f = scrShot.getScreenshotAs(OutputType.FILE);
		df = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		// create dir with given folder name
		new File(folder_name).mkdir();
		// Setting file name
		String file_name = df.format(new Date()) + ".png";
		// copy screenshot file into screenshot folder.
		FileHandler.copy(f, new File(folder_name + "/" + file_name));

	}

}
