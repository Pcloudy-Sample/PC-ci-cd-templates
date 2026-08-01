# Maven Test Automation Setup

## Prerequisites

1. **Java Version**

   * Java **17** must be installed and available in your system `PATH`.

2. **Maven**

   * Apache Maven must be installed.
   * Verify installation:

     ```bash
     mvn -v
     ```

---

## Steps to Run Tests

### 1. Clean the project

Removes previously generated build files:

```bash
mvn clean
```

### 2. Run tests

Execute Maven tests by passing required parameters:

```bash
mvn test \
  -DuserName=<Enter-user-name> \
  -DaccessKey=<Enter-api-key> \
  -DpCloudy_DeviceFullName=<Enter-device-full-name> \
  -DpCloudy_ApplicationName=<Enter-application-name> \
  -DbaseUrl=<Enter-base-url>
```

---

## Notes

* Replace values inside `< >` with actual credentials and configuration values.
* Sensitive values like `userName` and `accessKey` should be kept secure.
* Ensure the device name and application name exactly match the values configured in pCloudy.

---

## Example

```bash
mvn test \
  -DuserName=testuser \
  -DaccessKey=xxxxxxxxxxxx \
  -DpCloudy_DeviceFullName="Samsung Galaxy S23 Android 14" \
  -DpCloudy_ApplicationName="MyApp.apk" \
  -DbaseUrl=https://example.com
```
