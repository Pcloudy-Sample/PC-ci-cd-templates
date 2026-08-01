package com.pcloudy.testng;

import java.net.URL;
import org.openqa.selenium.remote.HttpCommandExecutor;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;

public class Driver {
	
	AppiumDriver driver;

	public AppiumDriver createDriver(String userName, String accessKey, String appiumHub,
			String pCloudyDeviceManufacturer, String pCloudyDeviceFullName, String pCloudyApplicationName)
			throws IOException, InterruptedException {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("appium:newCommandTimeout", 600);
		capabilities.setCapability("appium:launchTimeout", 90000);
		capabilities.setCapability("appium:platformName", "iOS");
		capabilities.setCapability("appium:automationName", "XCUITest");
		capabilities.setCapability("appium:bundleId", "com.pcloudy.TestmunkDemo");
		HashMap<String, Object> pcloudyOptions = new HashMap<String, Object>();
		pcloudyOptions.put("pCloudy_Username", userName);
		pcloudyOptions.put("pCloudy_ApiKey", accessKey);
		pcloudyOptions.put("pCloudy_WildNet", false);
		pcloudyOptions.put("pCloudy_EnableVideo", false);
		pcloudyOptions.put("pCloudy_EnablePerformanceData", false);
		pcloudyOptions.put("pCloudy_EnableDeviceLogs", false);
		if (pCloudyDeviceFullName != null && !pCloudyDeviceFullName.trim().isEmpty()) {
			pcloudyOptions.put("pCloudy_DeviceFullName", pCloudyDeviceFullName);
		} else if (pCloudyDeviceManufacturer != null && !pCloudyDeviceManufacturer.trim().isEmpty()) {
			pcloudyOptions.put("pCloudy_DeviceManufacturer", pCloudyDeviceManufacturer);
		}
		pcloudyOptions.put("pCloudy_ApplicationName", pCloudyApplicationName);
		pcloudyOptions.put("appiumVersion", "2.0.0");
		capabilities.setCapability("pcloudy:options", pcloudyOptions);
		IOSDriver driver = new IOSDriver(new URL(appiumHub),
				capabilities);

		return driver;
	}

}