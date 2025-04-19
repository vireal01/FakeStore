import org.gradle.api.Plugin
import org.gradle.api.Project
import java.text.SimpleDateFormat

class BuildInfoPlugin: Plugin<Project> {
  private val taskName = "generateBuildInfo"
  private val taskDescription = "Prints build information and saves it to a file"
  private val taskGroup = "Build Info"

  override fun apply(target: Project) {
    // Register the task
    target.tasks.register(taskName, GenerateBuildInfoTask::class.java) {
      group = taskGroup
      description = taskDescription

      // Set the task properties
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

    // Make assembleDebug depend on the generateBuildInfo task
    target.afterEvaluate {
      target.tasks.named("assembleDebug").configure {
        dependsOn(taskName)
      }
    }
  }

  private fun getLastCommitHash(): String {
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
}