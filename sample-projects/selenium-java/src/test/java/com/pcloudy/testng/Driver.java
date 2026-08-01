package com.pcloudy.testng;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;

public class Driver {

  public RemoteWebDriver createDriver(
      String os,
      String browserName,
      String userName,
      String accessKey,
      String baseUrl,
      String local,
      String autoHeal,
      String queueTimeout,
      String idleTimeout,
      String project,
      String build,
      String name,
      String tag)
      throws IOException {

    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("browserName", browserName);

    HashMap<String, Object> pcloudyOptions = new HashMap<String, Object>();
    pcloudyOptions.put("userName", userName);
    pcloudyOptions.put("accessKey", accessKey);
    pcloudyOptions.put("os", os);
    pcloudyOptions.put("local", stringToBoolean(local));
    pcloudyOptions.put("pCloudy_EnableVideo", true);
    pcloudyOptions.put("autoHeal", stringToBoolean(autoHeal));

    if (!"None".equals(queueTimeout)) {
      pcloudyOptions.put("queueTimeout", stringToInteger(queueTimeout));
    }
    if (!"None".equals(idleTimeout)) {
      pcloudyOptions.put("idleTimeout", stringToInteger(idleTimeout));
    }
    if (!"None".equals(project)) {
      pcloudyOptions.put("project", project);
    }
    if (!"None".equals(build)) {
      pcloudyOptions.put("build", build);
    }
    if (!"None".equals(name)) {
      pcloudyOptions.put("name", name);
    }
    if (!"None".equals(tag)) {
      pcloudyOptions.put("tag", tag);
    }

    capabilities.setCapability("pcloudy:options", pcloudyOptions);

    ClientConfig clientConfig =
        ClientConfig.defaultConfig()
            .readTimeout(Duration.ofMinutes(15))
            .connectionTimeout(Duration.ofMinutes(15));

    return (RemoteWebDriver)
        RemoteWebDriver.builder()
            .address(new URL(baseUrl + "/seleniumcloud/wd/hub"))
            .oneOf(capabilities)
            .config(clientConfig)
            .build();
  }

  public static boolean stringToBoolean(String variable) {
    if (variable == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }

    String lower = variable.trim().toLowerCase();
    if (!lower.equals("true") && !lower.equals("false")) {
      throw new IllegalArgumentException("Invalid boolean value: must be exactly 'true' or 'false'");
    }

    return Boolean.parseBoolean(lower);
  }

  public static int stringToInteger(String variable) {
    if (variable == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }

    String trimmed = variable.trim();
    try {
      return Integer.parseInt(trimmed);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid integer value: '" + variable + "'");
    }
  }
}
