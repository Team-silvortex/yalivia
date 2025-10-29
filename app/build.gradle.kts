plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.plugin.serialization")
}

android {
  namespace = "com.yalivia.app"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.yalivia.app"
    minSdk = 26
    targetSdk = 35
    versionCode = 1
    versionName = "0.1.0"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.14"
  }
}

dependencies {
  implementation(project(":core"))

  // Compose 基础
  val composeBom = platform("androidx.compose:compose-bom:2024.09.02")
  implementation(composeBom)
  androidTestImplementation(composeBom)
  implementation("androidx.activity:activity-compose:1.9.2")
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.material3:material3:1.3.0")
  implementation("androidx.compose.ui:ui-tooling-preview")
  debugImplementation("androidx.compose.ui:ui-tooling")

  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}