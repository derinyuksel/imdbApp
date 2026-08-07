# Implementation Plan - Fix Gradle Build Issues (Revised)

The project has multiple Gradle configuration issues. The recent removal of the Kotlin Android plugin has introduced a new error ("Android BaseExtension not found" for Hilt), and the build script uses non-standard DSL that is likely causing configuration failures.

## Proposed Changes

### [Component: Gradle Configuration]

#### [MODIFY] [app/build.gradle.kts](file:///C:/Users/belki/AndroidStudioProjects/imdbApp/app/build.gradle.kts)
- Restore `alias(libs.plugins.kotlin.android)`.
- Reorder plugins: put `android.application` and `kotlin.android` first.
- Fix `compileSdk` DSL: Change to `compileSdk = 35` (or a supported stable version).
- Fix `optimization { enable = false }`: Change to `isMinifyEnabled = false`.
- Fix typo in dependencies: `libs.androidx.hilt.navigation.compose` (Already fixed by user, will verify).

#### [MODIFY] [build.gradle.kts](file:///C:/Users/belki/AndroidStudioProjects/imdbApp/build.gradle.kts)
- Restore `alias(libs.plugins.kotlin.android) apply false`.

## Verification Plan

### Automated Tests
- Run `./gradlew help` to verify configuration.
- Run `./gradlew :app:assembleDebug` to verify build.

### Manual Verification
- Sync project in IDE.
