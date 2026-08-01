---
name: Bug report
about: Report a problem with CI templates (GitHub/Azure/GitLab/Jenkins)
labels: bug
---

## Template Details

**Template file (required)**
e.g. `github-actions/open-source-tests.yml`, `github-actions/qpilot-tests.yml`, `azure-devops/open-source-tests.yml`, `azure-devops/qpilot-tests.yml`, `gitlab-ci/open-source-tests.yml`, `gitlab-ci/qpilot-tests.yml`, `jenkins/Jenkinsfile-open-source`, `jenkins/Jenkinsfile-qpilot`

**CI/CD platform and version**
e.g. GitHub Actions, Jenkins 2.440, GitLab 16.x, Azure DevOps

**Pipeline type**
- [ ] GitHub Actions
- [ ] Azure DevOps
- [ ] GitLab CI
- [ ] Jenkins

**Template flow (required)**
- [ ] Open-source tests
- [ ] QPilot tests

## Inputs Used

**Host input value (for QPilot flow)**
Provide exactly what you passed (if applicable):
- GitHub: `cloud_host`
- Azure: `cloudHost`
- GitLab: `CLOUD_HOST`
- Jenkins: `CLOUD_HOST`

**Collection inputs (for QPilot flow)**
- Collection ID:
- Collection Config ID:

**Expected resolved values (for QPilot flow)**
- Expected backend host:
- Expected origin header:

**Execution inputs (for Open-source flow)**
- Platform (android/ios):
- App upload enabled (true/false):
- App upload source (app_directory/storage):
- Existing app name (if upload disabled):
- App path or storage URL (if used):
- iOS trust app (true/false):
- Device manufacturer(s):

## Request/Response

**Request printed by pipeline** (redact token)

For QPilot flow, include:
```
POST https://<resolved-backend-host>/api/v2/collection/run
Headers: content-type=application/json, origin=<origin>, token=***
Body: {"collectionId":"...","collectionConfigId":"..."}
```

For Open-source flow, include the failing endpoint and payload (e.g. `/api/access`, `/api/upload_file`, `/api/resign/*`, or test execution command output).

**HTTP status code**
e.g. 200, 400, 401, 500

**What happened**
A clear description of what went wrong.

**API response received** (redact all credentials)
```
Paste the curl response here
```

**Expected behaviour**
What you expected the pipeline to do.

## Authentication Context

**Credentials/token source used**
- [ ] GitHub `PCLOUDY_TOKEN`
- [ ] Azure variable group `pCloudyToken`
- [ ] GitLab CI variable `PCLOUDY_TOKEN`
- [ ] Jenkins credential `pcloudy-token`
- [ ] Username/API key pair (`PCLOUDY_USERNAME` + `PCLOUDY_API_KEY` or platform equivalent)

If relevant, include whether token is project-level, org-level, or inherited.

**Steps to reproduce**
1. 
2. 
3. 

**Recent change before failure**
e.g. template update, new host, secret rotation, CI runner upgrade

**pCloudy plan** (optional)
e.g. Starter / Business / Enterprise / Lab-in-a-Box

**Additional context**
Any other details (job URL, run ID, branch, commit SHA, screenshot, logs).
