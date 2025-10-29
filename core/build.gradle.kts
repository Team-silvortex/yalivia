plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.plugin.serialization")
}

android {
  namespace = "com.yalivia.core"
  compileSdk = 35
  defaultConfig { minSdk = 26 }
  buildFeatures { buildConfig = false }
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}