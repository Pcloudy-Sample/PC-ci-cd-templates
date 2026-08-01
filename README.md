# pCloudy CI/CD Integration Templates

> **Production-ready pipeline templates for running open-source tests and QPilot collection runs on pCloudy's real device cloud.**

<p align="center">
	<a href="https://www.pcloudy.com"><img alt="pCloudy" src="https://img.shields.io/badge/Made%20by-pCloudy-1a73e8"></a>
	<img alt="Platforms" src="https://img.shields.io/badge/platforms-GitHub%20Actions%20%7C%20Jenkins%20%7C%20GitLab%20CI%20%7C%20Azure%20DevOps-blue">
	<img alt="Templates" src="https://img.shields.io/badge/templates-8%20ready--to--use-teal">
	<img alt="License" src="https://img.shields.io/badge/license-MIT-green">
</p>

---

## Getting Started

[pCloudy](https://www.pcloudy.com) is a mobile app testing platform that gives you single-click access to real Android and iOS devices directly from your browser. Use these real devices to perform manual and automated testing for your app, and integrate the platform with your CI pipeline for continuous testing across multiple real devices with every build.

pCloudy is fully integrated with Appium, letting you run your existing Appium test scripts against real devices on the pCloudy cloud with minimal changes.

This project is a plain Java + Maven Appium sample (no test framework) showing how to run Java Appium tests on the pCloudy Real Device Cloud.

## What's in this repo

Two categories of templates, across four CI/CD platforms:

| Template type | What it does |
|---|---|
| **Open Source Execution** | Runs your existing Appium / Selenium / Playwright tests on pCloudy real devices in parallel |
| **QPilot Collection Run** | Triggers an existing QPilot collection run using `collectionId` and `collectionConfigId` |

---

## Template index

| Platform | Open Source | QPilot |
|---|---|---|
| GitHub Actions | [`github-actions/open-source-tests.yml`](github-actions/open-source-tests.yml) | [`github-actions/qpilot-tests.yml`](github-actions/qpilot-tests.yml) |
| Jenkins | [`jenkins/Jenkinsfile-open-source`](jenkins/Jenkinsfile-open-source) | [`jenkins/Jenkinsfile-qpilot`](jenkins/Jenkinsfile-qpilot) |
| GitLab CI | [`gitlab-ci/open-source-tests.yml`](gitlab-ci/open-source-tests.yml) | [`gitlab-ci/qpilot-tests.yml`](gitlab-ci/qpilot-tests.yml) |
| Azure DevOps | [`azure-devops/open-source-tests.yml`](azure-devops/open-source-tests.yml) | [`azure-devops/qpilot-tests.yml`](azure-devops/qpilot-tests.yml) |

---

## Quick start

### Prerequisites

You need credentials from your [pCloudy dashboard](https://device.pcloudy.com):

| Variable | Where to find it |
|---|---|
| `PCLOUDY_USERNAME` | Your pCloudy account email *(Open Source templates)* |
| `PCLOUDY_API_KEY` | Settings → API Key *(Open Source templates)* |
| `PCLOUDY_TOKEN` / `pCloudyToken` / `pcloudy-token` | API token secret/variable in CI *(QPilot templates)* |

Store these as secrets in your CI/CD platform — never in plain text in the YAML file.

---

### Open Source Execution — 3 steps

**1. Copy the template for your platform into your repo**

```bash
# GitHub Actions example
cp github-actions/open-source-tests.yml .github/workflows/pcloudy-tests.yml
```

**2. Add secrets to your CI/CD platform**

- `PCLOUDY_USERNAME`
- `PCLOUDY_API_KEY`

**3. Set runtime inputs/variables for app resolution and platform**

```yaml
# Platform
platform: android  # or ios

# App source mode
enable_app_upload: "true"         # or "false" to use existing app name
app_upload_source: app_directory   # or storage

# If upload disabled
existing_app_name: "my-app.apk"

# Manufacturer fallback values used by tests
device_manufacturer: GOOGLE
device_manufacturers: GOOGLE,SAMSUNG
```

Templates include example command blocks for Maven, WebdriverIO, and Pytest/Playwright. Enable the block your project uses.

---

### QPilot Collection Run — 2 additional steps

**4. Add your QPilot token secret/variable**

- GitHub: `PCLOUDY_TOKEN`
- Azure DevOps (variable group): `pCloudyToken`
- GitLab: `PCLOUDY_TOKEN`
- Jenkins credential id: `pcloudy-token`

**5. Run with host + collection inputs**

```bash
# Example values
host=device.pcloudy.com
collectionId=<your-collection-id>
collectionConfigId=<your-collection-config-id>
```

QPilot templates resolve host to backend/origin and call:

`POST https://<resolved-backend-host>/api/v2/collection/run`

**Trigger a manual run first** to verify collection run succeeds, then add scheduling as needed in your CI platform.

---

## How the pipelines work

### Open Source Execution

```
Resolve app (upload/existing) → Optional iOS resign → Run tests → Publish artifacts/results
```

Open Source templates focus on app artifact resolution and passing runtime values into your existing test runner.

### QPilot Collection Run

```
Resolve host/origin → Trigger collection run (/api/v2/collection/run) → Validate response
```

---

## Framework support

Open source templates support all three common enterprise frameworks. Uncomment the relevant block:

| Framework | Runtime | Command pattern |
|---|---|---|
| Appium + TestNG | Java 17 | `mvn test -Dplatform=android -DdeviceName=...` |
| WebdriverIO | Node.js 20 | `npx wdio run wdio.conf.js --device "..."` |
| Playwright | Node.js 20 / Python 3.11 | `pytest tests/ -v` or `npx playwright test` |
| Selenium + JUnit | Java 17 | `mvn test -Dsuite=regression` |

See [`docs/framework-capabilities.md`](docs/framework-capabilities.md) for the exact
pCloudy capability blocks (Appium Android/iOS, Selenium, Playwright JSON) to drop into
your test project's driver setup.

---

## Regulated enterprise configuration

For BFSI, healthcare, and government teams with data residency requirements:

**On-premise device cloud (Lab-in-a-Box)**

Replace the API base URL with your internal pCloudy endpoint:

```yaml
PCLOUDY_API_BASE: "https://pcloudy.internal.yourcompany.com/api"
PCLOUDY_HUB:      "https://pcloudy.internal.yourcompany.com/appiumcloud/wd/hub"
```

**Local LLM for QPilot**

When QPilot is configured with a local LLM, point the QPilot base URL to your internal instance:

```yaml
QPILOT_API_BASE: "https://qpilot.internal.yourcompany.com/v1"
```

No other template changes are required. Both configurations maintain PCI-DSS, HIPAA, RBI, and SOC 2 compliance postures.

---

## Platform-specific notes

### GitHub Actions
- Secrets: Settings → Secrets and variables → Actions
- QPilot run template expects `PCLOUDY_TOKEN`
- QPilot host input is validated and mapped to backend/origin

### Jenkins
- Credentials: Manage Jenkins → Credentials → Secret Text
- QPilot run template expects credential id `pcloudy-token`
- Required plugins: Pipeline + Credentials Binding + Pipeline Utility Steps (for JSON helpers if used)

### GitLab CI
- Variables: Settings → CI/CD → Variables (mark API keys as **Masked**)
- QPilot run template expects `PCLOUDY_TOKEN`
- Manual run variables: `CLOUD_HOST`, `COLLECTION_ID`, `COLLECTION_CONFIG_ID`

### Azure DevOps
- Create a Variable Group named `pCloudy` in Pipelines → Library
- Link the Variable Group to your pipeline
- QPilot run template expects variable `pCloudyToken`
- Set `trigger: none` for manual invocation

---

## Folder structure

```
pcloudy-ci-templates/
├── README.md
├── LICENSE
├── CONTRIBUTING.md
├── github-actions/
│   ├── open-source-tests.yml
│   └── qpilot-tests.yml
├── jenkins/
│   ├── Jenkinsfile-open-source
│   └── Jenkinsfile-qpilot
├── gitlab-ci/
│   ├── open-source-tests.yml
│   └── qpilot-tests.yml
├── azure-devops/
│   ├── open-source-tests.yml
│   └── qpilot-tests.yml
```

---

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md). Pull requests are welcome — especially host-mapping improvements, framework variants, and CI compatibility fixes.

---

## Resources

- [pCloudy documentation](https://www.pcloudy.com/docs)
- [QPilot documentation](https://www.pcloudy.com/docs/test-creation-agent/)
- [pCloudy API reference](https://www.pcloudy.com/docs/api-reference)
- [Lab-in-a-Box (on-premise)](https://www.pcloudy.com/docs/on-premise-cloud)
- [CI/CD Integration Guide](pCloudy_CICD_Integration_Guide.docx) — full setup walkthrough

---

## Learn More

- [pCloudy Website](https://www.pcloudy.com)


## License

MIT — see [LICENSE](LICENSE).

---

*Built by [pCloudy](https://www.pcloudy.com) — quality infrastructure for enterprise mobile testing.*

