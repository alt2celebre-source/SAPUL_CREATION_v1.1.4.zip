# Security notes
- Never place AI provider API keys in Kotlin source, resources, BuildConfig, or the APK.
- Keep credentials in backend environment variables or a secrets manager.
- Do not commit `.env`; it is ignored by `.gitignore`.
- Add authentication, rate limiting, upload validation, and provider timeout/retry policies before public deployment.
