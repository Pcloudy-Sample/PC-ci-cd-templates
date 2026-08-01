# Contributing to pCloudy CI/CD Templates

Thank you for helping improve these templates. Contributions that are most useful to the community:

- Additional device configurations (new device names, iOS variants)
- Framework variants (Espresso, XCUITest, Cypress, k6)
- New platform templates (Bitbucket Pipelines, CircleCI, TeamCity)
- Improved examples for host mapping and secure secret usage
- Bug fixes in API calls, host/origin resolution, and request payload handling

## How to contribute

1. Fork the repository
2. Sync and checkout `development`, then create your branch from it:
	`git checkout development && git pull && git checkout -b feature/your-template-name`
3. Make your changes — follow the conventions in existing templates (comments, secret references, consistent host mapping)
4. Test your template against a real pCloudy account before submitting
5. Open a pull request targeting the `development` branch, with a short description of what the template does and which pCloudy plan tier it targets

## Template conventions

- Store all credentials as secrets — never hardcode values
- Keep Open Source and QPilot template behavior aligned across platforms (GitHub, Azure, GitLab, Jenkins)
- In QPilot templates, validate allowed host inputs and map host -> backend host + origin consistently
- In QPilot templates, log request metadata safely (never print raw token values)
- Comment every non-obvious API call
- For Open Source templates, keep upload/resolve paths and optional iOS resign flow documented in-template

## Reporting issues

Use the issue template in `.github/ISSUE_TEMPLATE/bug_report.md`. Include CI/CD platform, template file, selected flow (Open Source or QPilot), host/input values, and API response (with credentials redacted).
