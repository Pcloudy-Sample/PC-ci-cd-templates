package com.pcloudy.test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.AndroidKey;
import com.pcloudy.utils.TestUtils;

public class TestCase {
	
	public void demoTest(AppiumDriver driver) throws Exception {

		TestUtils utils = new TestUtils();
		
		  //Click on Accept button
		WebElement accept = utils.waitForVisibility(By.xpath("//android.widget.Button[@resource-id='com.pcloudy.appiumdemo:id/accept']"), driver);
        accept.click();
        
        //Click on Flight button
		WebElement flight = utils.waitForVisibility(By.xpath("//android.widget.Button[@resource-id='com.pcloudy.appiumdemo:id/flightButton']"), driver);
        flight.click();
        
        //Select from location
		WebElement fromLocation = utils.waitForVisibility(By.xpath("//android.widget.Spinner[@resource-id='com.pcloudy.appiumdemo:id/spinnerfrom']"), driver);
        fromLocation.click();
		WebElement fromOption = utils.waitForVisibility(By.xpath("//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='Bangalore, India (BLR)']"), driver);
	    fromOption.click();
		
	    //Select to location
		WebElement toLocation = utils.waitForVisibility(By.xpath("//android.widget.Spinner[@resource-id='com.pcloudy.appiumdemo:id/spinnerto']"), driver);
		toLocation.click();
        WebElement toOption = utils.waitForVisibility(By.xpath("//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='Pune, India (PNQ)']"), driver);
        toOption.click();
               
        //Select one way trip
		WebElement oneWayTrip = utils.waitForVisibility(By.xpath("//android.widget.RadioButton[@resource-id='com.pcloudy.appiumdemo:id/singleTrip']"), driver);
		oneWayTrip.click();
        
        //Select departure time
		WebElement departDate = utils.waitForVisibility(By.xpath("//android.widget.TextView[@resource-id='com.pcloudy.appiumdemo:id/txtdepart']"), driver);
		departDate.click();
        WebElement okButton = utils.waitForVisibility(By.xpath("//android.widget.Button[@resource-id='android:id/button1' and @text='OK']"), driver);
		okButton.click();
        
        //Click on search flights button
		WebElement searchFlights = utils.waitForVisibility(By.xpath("//android.widget.Button[@resource-id='com.pcloudy.appiumdemo:id/searchFlights']"), driver);
        searchFlights.click();
	}

}
