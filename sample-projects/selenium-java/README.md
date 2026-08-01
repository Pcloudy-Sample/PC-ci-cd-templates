# Selenium Java Sample for pCloudy

This sample is based on the Selenium flow used in the Jenkins reference project and is ready to run on pCloudy browser cloud.

## Prerequisites

- Java 8+ installed
- Maven installed
- Valid pCloudy username and access key

## Run

From this directory, run:

```bash
mvn clean test \
  -DuserName="<your-pcloudy-username>" \
  -DaccessKey="<your-pcloudy-access-key>" \
  -DbaseUrl="https://device.pcloudy.com" \
  -Dlocal1="false" \
  -DautoHeal1="false" \
  -DqueueTimeout1="None" \
  -DidleTimeout1="None" \
  -Dproject1="None" \
  -Dbuild1="None" \
  -Dname1="None" \
  -Dtag1="None" \
  -Dlocal2="false" \
  -DautoHeal2="false" \
  -DqueueTimeout2="None" \
  -DidleTimeout2="None" \
  -Dproject2="None" \
  -Dbuild2="None" \
  -Dname2="None" \
  -Dtag2="None"
```

Session mapping in testng:

- Session 1: Windows + Chrome
- Session 2: Mac + Safari

## What the test does

- Opens Selenium sample page
- Enters text and submits form
- Validates returned message
- Marks status back to pCloudy using `pCloudy_setResult`
