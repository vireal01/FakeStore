plugins {
  `kotlin-dsl`
}

gradlePlugin {
  plugins {
    create("buildInfoPlugin") {
      id = "buildInfoPlugin"
      implementationClass = "BuildInfoPlugin"
    }
  }
}