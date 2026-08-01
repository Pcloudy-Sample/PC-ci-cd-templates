# iOS Appium Java Sample

This sample is an iOS variant of the Android Appium Java project for pCloudy.

## What is configured

- Platform: iOS
- Automation: XCUITest
- Bundle ID: com.pcloudy.TestmunkDemo
- App directory: app/

## Upload your app

Place your iOS app artifact in:

- app/

Then pass the uploaded app name (or use your CI upload flow) via:

- pCloudy_ApplicationName

## Test flow

The sample test performs:

1. Enter email in the first text field
2. Enter password in the first secure text field
3. Tap the first button (login)

## Run example

mvn test \
  -DuserName=<PCLOUDY_USERNAME> \
  -DaccessKey=<PCLOUDY_API_KEY> \
  -DbaseUrl=https://device.pcloudy.com/appiumcloud/wd/hub \
  -DpCloudy_ApplicationName=<uploaded-app-name>
