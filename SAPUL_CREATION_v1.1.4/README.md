# SAPUL CREATION v1.1.4
**From Imagination to Cinema.**

This is a reconstructed starter project based on the project handoff in this conversation. It is not a byte-for-byte recovery of an earlier repository or working directory.

## Contents
- `app/`: Kotlin, Jetpack Compose Android application and Media3 dependencies
- `backend/`: Node.js/Express API foundation
- `.github/workflows/`: Android unit-test and APK build workflow
- `docs/`: project status and security notes

## Requirements
JDK 17, Android SDK 35, Gradle 8.9, Node.js 20+.

## Android
From the project root run `gradle testDebugUnitTest` and `gradle assembleDebug`.
APK: `app/build/outputs/apk/debug/app-debug.apk`.

## Backend
Run `cd backend && npm install && cp .env.example .env && npm start`.
Keep provider API keys on the server only. Never place secrets in the APK.

## Verification status
Builds and tests have NOT been run while creating this archive. Use GitHub Actions or a local Android environment to verify.
