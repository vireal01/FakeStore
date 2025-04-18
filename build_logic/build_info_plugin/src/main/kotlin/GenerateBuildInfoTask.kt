import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

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