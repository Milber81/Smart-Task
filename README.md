# Task description

Write an app Smart Task that supports work on daily tasks.

# Solution

App is using clean code approach built on the top of MVVM. App is targeting API 3 (Android 14).
For storing data app is using DataStorePreferences.

Project was written in Kotlin.

For documentation i was using Dokka.
Documentation can be build with executing gradlew task 'dokkaHtml'.

# Setup

App is using next frameworks:

• Retrofit
• Preferences Data Store

# Func️ional test

App was test with next devices:
- Samsung S23 A14
- Samsung S21 FE A14
- Emulator A15

# Known build problem ⚠️⚠️⚠️

An exception occurred applying plugin request [id: 'com.android.application']
> Failed to apply plugin 'com.android.internal.application'.
> Android Gradle plugin requires Java 11 to run. You are currently using Java 1.8. You can try some of the following options:

- changing the IDE settings.
- changing the JAVA_HOME environment variable.
- changing `org.gradle.java.home` in `gradle.properties`.

✅ AndroidStudio -> File -> Project Structure -> SDK Location -> Gradle settings -> select at least Java 11
