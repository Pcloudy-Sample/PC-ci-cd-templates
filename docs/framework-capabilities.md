# Framework Capabilities Reference — Appium, Selenium, Playwright

These are the driver-level capability objects your test project needs in order to open a
session against pCloudy's device cloud. The CI/CD templates in this repo handle
authentication, app upload, device booking/release, and reporting — they hand your test
run the connection details (username, API key, device name, hub URL, app URL) as
environment variables / `-D` system properties. The capability blocks below are what your
test code uses to actually consume those values and open the remote session.

Copy the block for whichever framework you use, swap in your own values (or wire them to
the env vars your CI template already exports), and run.

---

## Appium — Android

```java
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability("appium:newCommandTimeout", 600);
capabilities.setCapability("appium:launchTimeout", 90000);
capabilities.setCapability("appium:platformName", "Android");
capabilities.setCapability("appium:automationName", "uiautomator2");
capabilities.setCapability("appium:appPackage", "com.pcloudy.appiumdemo");
capabilities.setCapability("appium:appActivity", "com.ba.mobile.LaunchActivity");
capabilities.setCapability("appium:pCloudy_ApplicationName", "pCloudyAppiumDemo-1784009749.apk");

HashMap<String, Object> pcloudyOptions = new HashMap<String, Object>();
pcloudyOptions.put("pCloudy_Username", "xxxxxx");
pcloudyOptions.put("pCloudy_ApiKey", "xxxxxx");
pcloudyOptions.put("pCloudy_DeviceManufacturer", "GOOGLE");
pcloudyOptions.put("pCloudy_WildNet", false);
pcloudyOptions.put("pCloudy_EnableVideo", false);
pcloudyOptions.put("pCloudy_EnablePerformanceData", false);
pcloudyOptions.put("pCloudy_EnableDeviceLogs", false);
pcloudyOptions.put("appiumVersion", "2.0.0");
capabilities.setCapability("pcloudy:options", pcloudyOptions);

AndroidDriver driver = new AndroidDriver(
    new URL("https://device.pcloudy.com/appiumcloud/wd/hub"), capabilities);
```

## Appium — iOS

```java
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability("appium:trust", false);
capabilities.setCapability("appium:newCommandTimeout", 600);
capabilities.setCapability("appium:launchTimeout", 90000);
capabilities.setCapability("appium:platformName", "iOS");
capabilities.setCapability("appium:acceptAlerts", true);
capabilities.setCapability("appium:bundleId", "com.pcloudy.TestmunkDemo");
capabilities.setCapability("appium:pCloudy_ApplicationName", "TestmunkDemo_Resigned1780753137.ipa");

HashMap<String, Object> pcloudyOptions = new HashMap<String, Object>();
pcloudyOptions.put("pCloudy_Username", "xxxxxx");
pcloudyOptions.put("pCloudy_ApiKey", "xxxxxx");
pcloudyOptions.put("pCloudy_DeviceManufacturer", "APPLE");
pcloudyOptions.put("pCloudy_WildNet", false);
pcloudyOptions.put("pCloudy_EnableVideo", false);
pcloudyOptions.put("pCloudy_EnablePerformanceData", false);
pcloudyOptions.put("pCloudy_EnableDeviceLogs", false);
pcloudyOptions.put("appiumVersion", "2.0.0");
capabilities.setCapability("pcloudy:options", pcloudyOptions);

IOSDriver driver = new IOSDriver(
    new URL("https://device.pcloudy.com/appiumcloud/wd/hub"), capabilities);
```

iOS trust behavior:

- `appium:trust=false`: non-trusted IPA flow. App must be resigned before use.
- `appium:trust=true`: trusted/enterprise app flow. Resign is not required.

Resign API sequence for iOS (only when trust is false):

1. Upload app and capture uploaded filename from response, for example:
   `{"result":{"token":"...","code":200,"file":"TestmunkDemo-1784034862.ipa"}}`
2. Initiate resign with `/api/resign/initiate` using `token` and `filename`.
3. Poll `/api/resign/progress` until `resign_status` is `100`.
4. Use `resign_filename` as `appium:pCloudy_ApplicationName`.

Example initiate request:

```bash
curl --location 'https://device.pcloudy.com/api/resign/initiate' \
--header 'Content-Type: application/json' \
--data '{
  "token": "xxxxxx",
  "filename": "TestmunkDemo-1784034862.ipa"
}'
```

Example progress request:

```bash
curl --location 'https://device.pcloudy.com/api/resign/progress' \
--header 'Content-Type: application/json' \
--data '{
  "token": "xxxxxx",
  "resign_token": "d0775426-aa37-4340-ac45-e1b619f29644",
  "filename": "TestmunkDemo-1784034862.ipa"
}'
```

## Selenium

```java
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability("browserName", "chrome");

HashMap<String, Object> pcloudyOptions = new HashMap<String, Object>();
pcloudyOptions.put("userName", "xxxxxx");
pcloudyOptions.put("accessKey", "xxxxxx");
pcloudyOptions.put("os", "Windows");
pcloudyOptions.put("osVersion", "11");
pcloudyOptions.put("browserVersion", "146");
pcloudyOptions.put("seleniumVersion", "4.26.0");
pcloudyOptions.put("local", false);
pcloudyOptions.put("pCloudy_EnableVideo", false);
capabilities.setCapability("pcloudy:options", pcloudyOptions);

WebDriver driver = new RemoteWebDriver(
    new URL("https://browser.device.pcloudy.com/seleniumcloud/wd/hub"), capabilities);
```

## Playwright

Playwright doesn't take a Java-style `DesiredCapabilities`/`HashMap` object — it connects
over CDP with a plain JSON capabilities object. Same keys and values as the Selenium
block above, just expressed as JSON:

```json
{
  "browserName": "chrome",
  "pcloudy:options": {
    "userName": "xxxxxx",
    "accessKey": "xxxxxx",
    "os": "Windows",
    "osVersion": "11",
    "browserVersion": "146",
    "local": false,
    "pCloudy_EnableVideo": false
  }
}
```

Update `userName`, `accessKey`, `os`, `osVersion`, `browserVersion`, and the video/logging
flags to match your target environment and pCloudy plan.

---

## Notes

- Fields under `pcloudy:options` (or the top-level `pCloudy_*` keys for Appium) are the
  ones you'll typically template against your CI secrets/variables — `pCloudy_Username` /
  `userName` → `PCLOUDY_USERNAME`, `pCloudy_ApiKey` / `accessKey` → `PCLOUDY_API_KEY`, and
  so on.
- These blocks live in your **test project**, not in the pipeline YAML — the CI templates
  only get your test command running with the right environment variables in scope; your
  driver setup code is what turns those into a real capabilities object.
