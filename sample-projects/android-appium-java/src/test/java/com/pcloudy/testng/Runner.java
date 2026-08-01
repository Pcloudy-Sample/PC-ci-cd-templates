package com.pcloudy.testng;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.*;
import org.testng.ITestResult;
import org.testng.ITestContext;
import io.appium.java_client.AppiumDriver;
import com.pcloudy.test.TestCase;

public class Runner {

	AppiumDriver driver;
	String folder_name;
	DateFormat df;

	@BeforeTest
	public void setUpSuite() throws Exception {

	}

	@Parameters({ "pCloudyUsername", "pCloudyApiKey", "appiumHub", "pCloudyDeviceManufacturer", "pCloudyDeviceFullName", "pCloudyApplicationName" })
	@BeforeMethod
	public void prepareTest(
			@Optional("") String pCloudyUsername,
			@Optional("") String pCloudyApiKey,
			@Optional("") String appiumHub,
			@Optional("") String pCloudyDeviceManufacturer,
			@Optional("") String pCloudyDeviceFullName,
			@Optional("") String pCloudyApplicationName,
			ITestContext testContext)
			throws IOException, InterruptedException {
		String resolvedUserName = !pCloudyUsername.isEmpty() ? pCloudyUsername : System.getProperty("userName", "");
		String resolvedApiKey = !pCloudyApiKey.isEmpty() ? pCloudyApiKey : System.getProperty("accessKey", "");
		String resolvedHub = !appiumHub.isEmpty() ? appiumHub : System.getProperty("baseUrl", "");

		String resolvedManufacturer = "";
		String manufacturerArray = System.getProperty("pCloudyDeviceManufacturers", "").trim();
		if (!manufacturerArray.isEmpty()) {
			List<String> manufacturers = Arrays.stream(manufacturerArray.split(","))
					.map(String::trim)
					.filter(s -> !s.isEmpty())
					.collect(Collectors.toList());
			if (!manufacturers.isEmpty()) {
				List<org.testng.xml.XmlTest> xmlTests = testContext.getSuite().getXmlSuite().getTests();
				int index = xmlTests.indexOf(testContext.getCurrentXmlTest());
				if (index < 0) {
					index = 0;
				}
				if (index >= manufacturers.size()) {
					index = manufacturers.size() - 1;
				}
				resolvedManufacturer = manufacturers.get(index);
			}
		}

		if (resolvedManufacturer.isEmpty()) {
			resolvedManufacturer = !pCloudyDeviceManufacturer.isEmpty()
					? pCloudyDeviceManufacturer
					: System.getProperty("pCloudy_DeviceManufacturer", "");
		}

		String resolvedDeviceFullName = !pCloudyDeviceFullName.isEmpty()
				? pCloudyDeviceFullName
				: System.getProperty("pCloudy_DeviceFullName", "");
		String resolvedAppName = !pCloudyApplicationName.isEmpty()
				? pCloudyApplicationName
				: System.getProperty("pCloudy_ApplicationName", "");

		Driver driverObj = new Driver();
		driver = driverObj.createDriver(
				resolvedUserName,
				resolvedApiKey,
				resolvedHub,
				resolvedManufacturer,
				resolvedDeviceFullName,
				resolvedAppName);
	}

	@Test(invocationCount = 1)
	public void Test() throws Exception {

		com.pcloudy.test.TestCase test = new TestCase();
		test.demoTest(driver);

	}

	@AfterMethod
	public void endTest(ITestResult result) throws IOException, InterruptedException {
		if (driver != null) {
			driver.quit();
			System.out.println("driver quit done");
		}
	}
}
