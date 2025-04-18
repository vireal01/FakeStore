import java.text.SimpleDateFormat

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

  // Navigation
  implementation(libs.androidx.navigation.compose)

  // Network
  implementation(libs.squareup.retrofit2.retrofit)
  implementation(libs.squareup.retrofit2.gson)
  implementation(libs.logging.interceptor)

  // Hilt
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
  implementation(libs.hilt.navigation.compose)

  // Room
  implementation(libs.androidx.room.ktx)
  implementation(libs.room.runtime)
  ksp(libs.room.compiler)

  // Testing
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

}

@CacheableTask
abstract class GenerateBuildInfoTask : DefaultTask() {
  @get:Input
  var commitHash: String = "none"

  @get:Input
  var buildDateTime: String = "none"

  @get:OutputFile
  val outputFile: File = File(project.projectDir, "build/outputs/apk/build-info.txt")

  @TaskAction
  fun performTask() {
    val buildInfo = "Build date: $buildDateTime\nCommit hash: $commitHash"
    outputFile.parentFile.mkdirs()
    outputFile.writeText(buildInfo)
    println("Build info generated:\n $buildInfo\n into ${outputFile.absolutePath}")
  }
}

tasks.register<GenerateBuildInfoTask>("generateBuildInfo") {
  commitHash = getLastCommitHash()

  buildDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ").format(System.currentTimeMillis())


  doFirst {
    println("Create build info creation...")
  }
  doLast {
    println("Created build info successfully!")
    if (outputFile.exists()) {
      val content = outputFile.readText()
      println(content)
    } else {
      println("File build-info.txt not found.")
    }
  }
}

afterEvaluate {
  tasks.named("assembleDebug") {
    dependsOn("generateBuildInfo")
  }
}

fun getLastCommitHash(): String {
  return try {
    val process = ProcessBuilder("git", "rev-parse", "--short", "HEAD")
      .redirectErrorStream(true)
      .start()

    val output = process.inputStream.bufferedReader().use { it.readText().trim() }
    val exitCode = process.waitFor()

    if (exitCode == 0 && output.isNotEmpty()) {
      output
    } else {
      "unknown-commit"
    }
  } catch (e: Exception) {
    println("Failed to get commit hash: ${e.message}")
    "unknown-commit"
  }
}
