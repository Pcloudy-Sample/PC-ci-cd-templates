# QPilot Test Collection Run Guide

This guide explains how to trigger a QPilot collection run using the CI templates in this repository.

## Scope

This document covers only QPilot collection run templates.

For open-source Appium/Selenium/Playwright execution, use:

- `docs/open-source-ci-templates-guide.md`

## Template Locations

- GitHub Actions: `github-actions/qpilot-tests.yml`
- GitLab CI: `gitlab-ci/qpilot-tests.yml`
- Jenkins: `jenkins/Jenkinsfile-qpilot`
- Azure DevOps: `azure-devops/qpilot-tests.yml`

## Required Inputs

All platform templates require:

- Host input:
  - GitHub: `cloud_host`
  - GitLab: `CLOUD_HOST`
  - Jenkins: `CLOUD_HOST`
  - Azure: `cloudHost`
- Collection ID:
  - GitHub: `collection_id`
  - GitLab: `COLLECTION_ID`
  - Jenkins: `COLLECTION_ID`
  - Azure: `collectionId`
- Collection Config ID:
  - GitHub: `collection_config_id`
  - GitLab: `COLLECTION_CONFIG_ID`
  - Jenkins: `COLLECTION_CONFIG_ID`
  - Azure: `collectionConfigId`

## Required Token

- GitHub secret: `PCLOUDY_TOKEN`
- GitLab CI variable: `PCLOUDY_TOKEN`
- Jenkins credential id: `pcloudy-token`
- Azure variable group value: `pCloudyToken`


## API Called by Templates

All templates trigger:

- Method: `POST`
- URL: `https://<resolved-api-host>/api/v2/collection/run`
- Headers:
  - `content-type: application/json`
  - `origin: https://qpilot.device.pcloudy.com`
  - `token: <PCLOUDY_TOKEN>`
- Body:

```json
{
  "collectionId": "<COLLECTION_ID>",
  "collectionConfigId": "<COLLECTION_CONFIG_ID>"
}
```

## Quick Start by Platform

### GitHub Actions

1. Copy `github-actions/qpilot-tests.yml` to `.github/workflows/qpilot-tests.yml`.
2. Add repository secret `PCLOUDY_TOKEN`.
3. Run workflow manually and provide:
  - `cloud_host`
   - `collection_id`
   - `collection_config_id`

### GitLab CI

1. Use `gitlab-ci/qpilot-tests.yml` in your pipeline config.
2. Set masked CI variable `PCLOUDY_TOKEN`.
3. Set values for:
  - `CLOUD_HOST`
   - `COLLECTION_ID`
   - `COLLECTION_CONFIG_ID`
4. Run the manual job.

### Jenkins

1. Use `jenkins/Jenkinsfile-qpilot`.
2. Add Secret Text credential with id `pcloudy-token`.
3. Provide pipeline parameters:
  - `CLOUD_HOST`
   - `COLLECTION_ID`
   - `COLLECTION_CONFIG_ID`
4. Build job.

### Azure DevOps

1. Use `azure-devops/qpilot-tests.yml`.
2. Create variable group `pCloudy` with `pCloudyToken`.
3. Run pipeline with parameters:
  - `cloudHost`
   - `collectionId`
   - `collectionConfigId`

## Troubleshooting

### Unknown Host

If pipeline fails with unknown host, use one of the allowed host values listed above.

### Missing Token

If authentication fails, verify token variable/secret name exactly matches each platform template.

### Non-2xx HTTP Response

If API returns non-success status:

1. Verify collection id and config id are valid.
2. Verify token belongs to correct pCloudy account.
3. Verify selected host corresponds to your account region.
