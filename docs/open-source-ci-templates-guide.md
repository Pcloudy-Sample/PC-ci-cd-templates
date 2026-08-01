# Open Source CI Templates Guide

This guide explains how to use the open-source CI templates for GitHub Actions,
GitLab CI, and Jenkins for pCloudy Device Cloud execution, and how to adapt them
for Browser Cloud runs.

## What These Templates Are For

The current templates are device-first:

- Appium on Android/iOS real devices
- Optional app upload (repo path or storage URL)
- Optional iOS resign flow for non-trusted IPA
- Test execution via Maven/TestNG

It can also be adapted for browser workflows (Selenium/Playwright) by changing the
run command and framework setup as described later in this document.

QPilot collection run documentation is maintained separately in `docs/qpilot-test-collection-run-guide.md`.

## File Location

- Template: `github-actions/open-source-tests.yml`
- GitLab template: `gitlab-ci/open-source-tests.yml`
- Jenkins template: `jenkins/Jenkinsfile-open-source`

All three templates now follow the same execution model:

- Resolve app once (upload or existing)
- Optional iOS resign (for non-trusted IPA)
- Run a single CI test job/stage
- Use TestNG parallel in suite for scaling

## Quick Start

1. Pick your CI template from this directory:
   - `github-actions/open-source-tests.yml`
   - `gitlab-ci/open-source-tests.yml`
   - `jenkins/Jenkinsfile-open-source`
2. For GitHub only, copy `github-actions/open-source-tests.yml` into your repo as:
   `.github/workflows/open-source-tests.yml`
3. Add required secrets and variables/parameters in your CI tool.
4. Trigger your pipeline according to your CI platform.

## Required GitHub Configuration

### Secrets

Set in `Settings -> Secrets and variables -> Actions -> Secrets` (or environment secrets if jobs use `environment`).

- `PCLOUDY_USERNAME`
- `PCLOUDY_API_KEY`

### Variables

Set in `Settings -> Secrets and variables -> Actions -> Variables`.

Device flow:

- `PCLOUDY_APP_NAME` (required when upload is disabled)
- `PCLOUDY_APP_PATH` (required only when upload is enabled with `app_directory` source)

Optional:

- `PCLOUDY_APP_STORAGE_URL` (as secret), if using `app_upload_source=storage` and not passing `app_storage_url` input

## Workflow Inputs

The template supports these manual run inputs:

- `cloud_host` (default `device.pcloudy.com`)
- `enable_app_upload` (`true`/`false`)
- `app_upload_source` (`app_directory`/`storage`)
- `app_storage_url`
- `existing_app_name`
- `platform` (`android`/`ios`)
- `ios_trust_app` (`true`/`false`)
- `resign_poll_interval_sec`
- `resign_timeout_min`
- `device_manufacturer` (single fallback)
- `device_manufacturers` (comma-separated array mapped to TestNG tests by order)

Equivalent controls are available in GitLab and Jenkins templates using CI variables/parameters with the same intent.

## Device Parallel Model (Current)

Parallelism is driven by TestNG suite parallel mode (not GitHub matrix).

How manufacturer mapping works:

- `pCloudyDeviceManufacturers` is passed as CSV from workflow.
- Runner maps manufacturers to TestNG `<test>` blocks by order.
- If there are more tests than manufacturers, the last manufacturer is reused.
- If array is empty, fallback `pCloudyDeviceManufacturer` is used.

Example:

- `device_manufacturers=GOOGLE,SAMSUNG,XIAOMI`
- Test 1 -> GOOGLE
- Test 2 -> SAMSUNG
- Test 3 -> XIAOMI

## Common Device Run Scenarios

### A) Use Existing Uploaded App (No Upload)

- `enable_app_upload=false`
- Set `PCLOUDY_APP_NAME` variable or pass `existing_app_name` input

### B) Upload App From Repo Path

- `enable_app_upload=true`
- `app_upload_source=app_directory`
- Set `PCLOUDY_APP_PATH` variable (example: `app/pCloudyAppiumDemo.apk`)

### C) Upload App From Storage URL

- `enable_app_upload=true`
- `app_upload_source=storage`
- pass `app_storage_url` input (or set `PCLOUDY_APP_STORAGE_URL` secret)

## Browser Adaptation Guide

Use this when you want browser tests instead of mobile device tests.

### What to Change

1. Keep auth/secrets setup as-is.
2. Skip app-specific path:
   - Set `enable_app_upload=false`
   - You can keep `existing_app_name` empty for pure web runs if your framework does not require app artifacts.
3. Update framework setup in `run-tests`:
   - Selenium Java: keep Java setup and run Selenium suite command.
   - Playwright Python: use Python setup and install Playwright dependencies.
   - Playwright Node: use Node setup and install packages.
4. Update your test command and capability wiring:
   - Use browser host/hub endpoint (not appium hub) as required by your framework and pCloudy browser integration.
5. Update artifact paths to your framework outputs.

### Selenium Sample Project (Current)

Use the sample project at:

- `sample-projects/selenium-java`

Current sample behavior:

- Runs 2 parallel TestNG sessions
- Session 1: `Windows` + `chrome`
- Session 2: `Mac` + `safari`
- `osVersion`, `browserVersion`, and `seleniumVersion` are not required
- `local`, `autoHeal`, `queueTimeout`, and `idleTimeout` are optional in code and not required in `testng.xml`

Required runtime properties for this sample:

- `userName`
- `accessKey`
- `baseUrl`
- `project1`, `build1`, `name1`, `tag1`
- `project2`, `build2`, `name2`, `tag2`

Tip: If you do not want to set metadata values, pass `None`.

### Example Run Command Swap

In `run-tests` step, replace Maven/Appium command with framework-specific command.

Selenium Java example (aligned with `sample-projects/selenium-java`):

```bash
mvn test \
   -DuserName="$PCLOUDY_USERNAME" \
   -DaccessKey="$PCLOUDY_API_KEY" \
   -DbaseUrl="https://device.pcloudy.com" \
   -Dproject1="Project1" \
   -Dbuild1="build1" \
   -Dname1="name1" \
   -Dtag1="tag1" \
   -Dproject2="project2" \
   -Dbuild2="build2" \
   -Dname2="name2" \
   -Dtag2="tag2"
```

Playwright Python example:

```bash
pip install -r requirements.txt
python -m playwright install chromium
pytest tests/ -v
```

Note: For Playwright remote execution, ensure your test code uses the pCloudy-supported browser endpoint and capability model. Local Playwright install alone runs on CI machine browser, not automatically on pCloudy Browser Cloud.

## GitLab Open Source Template (Updated)

File: `gitlab-ci/open-source-tests.yml`

The GitLab template now mirrors the same model used in GitHub:

- Job 1 `resolve-app`:
   - Gets token from `/api/access`
   - Resolves app name from either:
      - upload flow (`ENABLE_APP_UPLOAD=true`), or
      - existing app (`EXISTING_APP_NAME` / `PCLOUDY_APP_NAME`)
   - Supports source selection:
      - `APP_UPLOAD_SOURCE=app_directory` using `PCLOUDY_APP_PATH`
      - `APP_UPLOAD_SOURCE=storage` using `APP_STORAGE_URL` / `PCLOUDY_APP_STORAGE_URL`
   - Runs optional iOS resign when required
- Job 2 `run-tests`:
   - Single Maven run
   - Passes both:
      - `-DpCloudyDeviceManufacturer`
      - `-DpCloudyDeviceManufacturers`
   - TestNG controls parallelism (no CI-level matrix/parallel fan-out)

Key GitLab variables:

- `ENABLE_APP_UPLOAD`
- `APP_UPLOAD_SOURCE`
- `PLATFORM`
- `IOS_TRUST_APP`
- `PCLOUDY_APP_PATH`
- `PCLOUDY_APP_NAME`
- `PCLOUDY_APP_STORAGE_URL`
- `DEVICE_MANUFACTURER`
- `DEVICE_MANUFACTURERS`

## Jenkins Open Source Template (Updated)

File: `jenkins/Jenkinsfile-open-source`

The Jenkins template now uses the same behavior as GitHub/GitLab:

- Stage 1 `Resolve App`:
   - Resolves app from upload or existing app name
   - Supports app source mode (`app_directory`/`storage`)
   - Supports optional iOS resign flow
- Stage 2 `Run Tests`:
   - One Maven execution
   - Passes both single manufacturer and manufacturer CSV to TestNG
   - Removes Jenkins pipeline-level hardcoded parallel device branches

Important Jenkins parameters:

- `CLOUD_HOST`
- `PLATFORM`
- `ENABLE_APP_UPLOAD`
- `APP_UPLOAD_SOURCE`
- `APP_PATH`
- `APP_STORAGE_URL`
- `EXISTING_APP_NAME`
- `IOS_TRUST_APP`
- `RESIGN_POLL_INTERVAL_SEC`
- `RESIGN_TIMEOUT_MIN`
- `DEVICE_MANUFACTURER`
- `DEVICE_MANUFACTURERS`

## Cross-Platform Behavior Summary

Across GitHub Actions, GitLab CI, and Jenkins templates:

- App selection and upload logic are aligned
- iOS resign handling is aligned
- CI-level parallel fan-out is removed for device tests
- Test parallelism is delegated to TestNG suite config
- Manufacturer array (`...Manufacturers`) is the scaling input for parallel TestNG test instances

## Troubleshooting

### Missing Secret Values in Logs

Symptoms:

- username empty
- API key missing
- token fetch fails from `/api/access`

Checklist:

1. Confirm secrets exist in the same scope used by job (`repository` or selected `environment`).
2. If using environment secrets, ensure jobs define `environment` and environment allows branch execution.
3. Re-run from latest workflow revision (old runs use old YAML).

### App Name Empty Error

If upload is disabled:

- set `existing_app_name` input, or
- set `PCLOUDY_APP_NAME` variable.

### App Path Not Found

If upload source is `app_directory`:

- set `PCLOUDY_APP_PATH` to a file present in repository checkout.

## Best Practices

- Keep branch triggers minimal and intentional.
- Use `workflow_dispatch` for controlled test runs.
- Keep one source of truth for existing app name (prefer variable, override via input only when needed).
- For scaling TestNG instances, keep manufacturer array length aligned with test count.
