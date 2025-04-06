plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  id("com.google.dagger.hilt.android")
  id("dagger.hilt.android.plugin")
  id("com.google.devtools.ksp") version "2.1.20-1.0.31"
}

android {
  namespace = "com.vireal.fakestore"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.vireal.fakestore"
    minSdk = 29
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    packaging {
      resources {
        pickFirsts.add("META-INF/gradle/incremental.annotation.processors")
      }
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    compose = true
  }
}


dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.bundles.compose)
  implementation(libs.hilt.navigation.compose)

  implementation(libs.squareup.retrofit2.retrofit)
  implementation(libs.squareup.retrofit2.gson)
  implementation(libs.room.runtime)
  implementation(libs.logging.interceptor)

  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)

  // Room
  implementation(libs.room.runtime)
  implementation(libs.androidx.room.ktx)
  ksp(libs.room.compiler)
  // If this project only uses Java source, use the Java annotationProcessor
  // No additional plugins are necessary

  // optional - Kotlin Extensions and Coroutines support for Room
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

}