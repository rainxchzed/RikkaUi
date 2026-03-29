import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class GenerateRegistryJsonTask : DefaultTask() {
    @get:InputDirectory
    abstract val componentsUiDir: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:Input
    abstract val foundationVersion: Property<String>

    // Maps registry ID (e.g. "dropdown-menu") to filesystem dir name (e.g. "dropdown")
    private val idToDirOverrides = mapOf(
        "dropdown-menu" to "dropdown",
        "scroll-area" to "scrollarea",
        "hover-card" to "hovercard",
        "toggle-group" to "togglegroup",
        "alert-dialog" to "alertdialog",
        "context-menu" to "contextmenu",
        "navigation-bar" to "navigationbar",
        "top-app-bar" to "topappbar",
    )

    // Reverse: dir name → registry ID
    private val dirToId by lazy {
        idToDirOverrides.entries.associate { (k, v) -> v to k }
    }

    private fun dirNameToId(dirName: String): String =
        dirToId[dirName] ?: dirName

    private fun escapeJson(s: String): String =
        s.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")

    @TaskAction
    fun generate() {
        val version = foundationVersion.get()
        val rDir = outputDir.get().asFile
        rDir.mkdirs()

        val dirs = componentsUiDir.get().asFile.listFiles()
            ?.filter { it.isDirectory }
            ?.sortedBy { it.name }
            ?: emptyList()

        val indexEntries = mutableListOf<String>()

        dirs.forEach { dir ->
            val ktFiles = dir.listFiles()
                ?.filter { it.extension == "kt" }
                ?.sortedBy { it.name }
                ?: emptyList()
            if (ktFiles.isEmpty()) return@forEach

            val componentId = dirNameToId(dir.name)
            val title = componentId.split("-")
                .joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }

            val filesJson = ktFiles.joinToString(",\n") { file ->
                val content = file.readText()
                """    {
      "name": "${file.name}",
      "path": "ui/${dir.name}/${file.name}",
      "type": "registry:component",
      "content": "${escapeJson(content)}"
    }"""
            }

            val componentJson = """{
  "name": "$componentId",
  "type": "registry:component",
  "title": "$title",
  "version": "$version",
  "registryDependencies": [],
  "gradleDependencies": [
    "dev.rikkaui:foundation:$version"
  ],
  "files": [
$filesJson
  ]
}
"""
            rDir.resolve("$componentId.json").writeText(componentJson)

            // Index entry (no source content)
            val filesMetaJson = ktFiles.joinToString(",\n") { file ->
                """      {
        "name": "${file.name}",
        "path": "ui/${dir.name}/${file.name}",
        "type": "registry:component"
      }"""
            }
            indexEntries.add("""  {
    "name": "$componentId",
    "type": "registry:component",
    "title": "$title",
    "files": [
$filesMetaJson
    ]
  }""")
        }

        // Handle standalone files (e.g. PopupAnimation.kt)
        val standaloneFiles = componentsUiDir.get().asFile.listFiles()
            ?.filter { it.isFile && it.extension == "kt" }
            ?.sortedBy { it.name }
            ?: emptyList()

        standaloneFiles.forEach { file ->
            val id = file.nameWithoutExtension
                .replace(Regex("([a-z])([A-Z])"), "$1-$2")
                .lowercase()
            val title = file.nameWithoutExtension
            val content = file.readText()

            val componentJson = """{
  "name": "$id",
  "type": "registry:utility",
  "title": "$title",
  "version": "$version",
  "registryDependencies": [],
  "gradleDependencies": [
    "dev.rikkaui:foundation:$version"
  ],
  "files": [
    {
      "name": "${file.name}",
      "path": "ui/${file.name}",
      "type": "registry:utility",
      "content": "${escapeJson(content)}"
    }
  ]
}
"""
            rDir.resolve("$id.json").writeText(componentJson)
        }

        // Write index.json
        val indexJson = """{
  "name": "rikkaui",
  "homepage": "https://rikkaui.dev",
  "version": "$version",
  "items": [
${indexEntries.joinToString(",\n")}
  ]
}
"""
        rDir.resolve("index.json").writeText(indexJson)
    }
}
