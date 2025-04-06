// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.compose) apply false
  id("com.google.devtools.ksp") version "2.1.20-1.0.31" apply false
//  alias(libs.plugins.room) apply false
  id("com.google.dagger.hilt.android") version "2.52" apply false
}

buildscript {
  dependencies {
    classpath("com.google.dagger:hilt-android-gradle-plugin:2.52")// Используйте последнюю стабильную версию
  }
}