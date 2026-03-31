package zed.rainxch.rikkaui.cli.commands

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import zed.rainxch.rikkaui.cli.model.RegistryComponent
import zed.rainxch.rikkaui.cli.model.RikkaConfig
import zed.rainxch.rikkaui.cli.registry.ConfigManager
import zed.rainxch.rikkaui.cli.registry.PackageRewriter
import zed.rainxch.rikkaui.cli.registry.RegistryClient
import java.io.File

class AddCommand : CliktCommand(name = "add") {

    override fun help(context: Context) = "Add components to your project"

    private val components by argument(help = "Component names (e.g. button dialog card)")
        .multiple(required = false)

    private val pathFlag by option("--path", help = "Target directory for component files")

    private val packageFlag by option("--package", "-p", help = "Target package name")

    private val registryFlag by option("--registry", "-r", help = "Registry URL")

    private val overwrite by option("--overwrite", "-o", help = "Overwrite existing files")
        .flag()

    private val dryRun by option("--dry-run", help = "Preview changes without writing files")
        .flag()

    private val all by option("--all", "-a", help = "Add all available components")
        .flag()

    override fun run() {
        if (!all && components.isEmpty()) {
            echo("Error: Provide component names or use --all.", err = true)
            throw Abort()
        }

        val resolved = resolveTargets()
        val componentsDir = resolved.first
        val packageName = resolved.second
        val registryUrl = resolved.third

        val client = RegistryClient(registryUrl)

        val names = if (all) {
            echo("Fetching component index...")
            val index = try {
                client.fetchIndex()
            } catch (e: Exception) {
                echo("Error: Failed to fetch registry index: ${e.message}", err = true)
                throw Abort()
            }
            index.items.map { it.name }
        } else {
            components.map { it.lowercase() }
        }

        echo("Resolving dependencies...")
        val tree: List<RegistryComponent>
        try {
            tree = client.resolveTree(names)
        } catch (e: Exception) {
            echo("Error: Failed to fetch components: ${e.message}", err = true)
            throw Abort()
        }

        // Resolve paths relative to the project root (where rikka.json lives)
        // rather than CWD, so `rikkaui add` works from any subdirectory.
        val projectDir = ConfigManager.projectRoot() ?: File(System.getProperty("user.dir"))
        val baseDir = projectDir.resolve(componentsDir)

        echo("  Target: ${baseDir.absolutePath}")
        echo("")

        var filesWritten = 0
        var filesSkipped = 0
        val addedComponents = mutableListOf<String>()
        val implicitDeps = mutableListOf<String>()
        val writtenFiles = mutableListOf<String>()

        tree.forEach { component ->
            var componentWritten = false
            component.files.forEach fileLoop@{ file ->
                val relativePath = file.path.removePrefix("ui/")
                val targetFile = baseDir.resolve(relativePath)

                if (targetFile.exists() && !overwrite) {
                    filesSkipped++
                    return@fileLoop
                }

                val rewritten = PackageRewriter.rewrite(file.content, packageName)

                if (dryRun) {
                    echo("  [dry-run] Would write: ${targetFile.relativeTo(projectDir)}")
                } else {
                    try {
                        targetFile.parentFile.mkdirs()
                        targetFile.writeText(rewritten)
                    } catch (e: Exception) {
                        echo(
                            "Error: Cannot write to ${targetFile.path}: ${e.message}",
                            err = true,
                        )
                        throw Abort()
                    }
                    filesWritten++
                    writtenFiles.add(targetFile.relativeTo(projectDir).path)
                }
                componentWritten = true
            }

            if (componentWritten || dryRun) {
                if (component.name in names) {
                    addedComponents.add(component.name)
                } else {
                    implicitDeps.add(component.name)
                }
            }
        }

        echo("")
        if (dryRun) {
            echo("  Dry run complete. No files were written.")
        } else if (filesWritten > 0) {
            addedComponents.forEach { name ->
                echo("  + Added $name")
            }
            implicitDeps.forEach { name ->
                echo("  + Added $name (dependency)")
            }
            echo("")
            echo("  Files written:")
            writtenFiles.forEach { path ->
                echo("    $path")
            }
            if (filesSkipped > 0) {
                echo("")
                echo("  ~ Skipped $filesSkipped existing files (use --overwrite to replace)")
            }
        } else if (filesSkipped > 0) {
            echo("  All components already exist. ($filesSkipped files skipped)")
            echo("  Use --overwrite to replace existing files.")
        } else {
            echo("  No files to write.")
        }

        val gradleDeps = tree.flatMap { it.gradleDependencies }.distinct()
        if (gradleDeps.isNotEmpty()) {
            echo("")
            echo("  Ensure these dependencies are in your build.gradle.kts:")
            gradleDeps.forEach { dep ->
                echo("  implementation(\"$dep\")")
            }
        }

        echo("")
    }

    /**
     * Resolves where to put components and what package to use.
     *
     * Priority:
     * 1. --path + --package flags → use directly
     * 2. rikka.json config → use saved values
     * 3. Interactive prompt → ask, then offer to save
     *
     * Returns Triple(componentsDir, packageName, registryUrl)
     */
    private fun resolveTargets(): Triple<String, String, String> {
        val defaultRegistry = "https://rikkaui.dev/r"

        // 1. Explicit flags — use as-is, no config needed
        if (pathFlag != null && packageFlag != null) {
            return Triple(pathFlag!!, packageFlag!!, registryFlag ?: defaultRegistry)
        }

        // 2. Existing config
        val config = ConfigManager.load()
        if (config != null) {
            // Allow flag overrides on top of config
            return Triple(
                pathFlag ?: config.componentsDir,
                packageFlag ?: config.packageName,
                registryFlag ?: config.registry,
            )
        }

        // 3. Interactive — no config, no (or partial) flags
        echo("")
        echo("  No rikka.json found. Let's set up.")
        echo("")

        val packageName = packageFlag ?: run {
            val detected = detectPackageName()
            if (detected != null) {
                echo("  Detected package: $detected")
                echo("  Package name [$detected]: ", trailingNewline = false)
                val input = readlnOrNull()?.trim()
                if (input.isNullOrBlank()) detected else input
            } else {
                echo("  Package name (e.g. com.myapp.ui): ", trailingNewline = false)
                val input = readlnOrNull()?.trim()
                if (input.isNullOrBlank()) {
                    echo("Error: Package name is required.", err = true)
                    throw Abort()
                }
                input
            }
        }

        val componentsDir = pathFlag ?: run {
            val defaultPath = suggestComponentsDir(packageName)
            echo("  Components directory [$defaultPath]: ", trailingNewline = false)
            val input = readlnOrNull()?.trim()
            if (input.isNullOrBlank()) defaultPath else input
        }

        echo("")
        echo("  Package:    $packageName")
        echo("  Components: $componentsDir")
        echo("")

        // Offer to save as default
        echo("  Save as default? (Y/n): ", trailingNewline = false)
        val save = readlnOrNull()?.trim()?.lowercase()
        if (save != "n") {
            val newConfig = RikkaConfig(
                packageName = packageName,
                componentsDir = componentsDir,
            )
            ConfigManager.save(newConfig)
            echo("  Saved to rikka.json")
        }

        echo("")

        return Triple(componentsDir, packageName, registryFlag ?: defaultRegistry)
    }

    private fun detectPackageName(): String? {
        val projectDir = File(System.getProperty("user.dir"))

        // Try parsing android namespace from build.gradle.kts
        val buildFile = projectDir.resolve("build.gradle.kts")
        if (buildFile.exists()) {
            val content = buildFile.readText()
            val namespaceMatch = Regex("""namespace\s*=\s*"([^"]+)"""").find(content)
            if (namespaceMatch != null) {
                return namespaceMatch.groupValues[1]
            }
        }

        // Try app/build.gradle.kts for multi-module projects
        val appBuildFile = projectDir.resolve("app/build.gradle.kts")
        if (appBuildFile.exists()) {
            val content = appBuildFile.readText()
            val namespaceMatch = Regex("""namespace\s*=\s*"([^"]+)"""").find(content)
            if (namespaceMatch != null) {
                return namespaceMatch.groupValues[1]
            }
        }

        return null
    }

    private fun suggestComponentsDir(packageName: String): String {
        val projectDir = File(System.getProperty("user.dir"))
        val packagePath = packageName.replace('.', '/')

        // Detect KMP vs Android
        return if (projectDir.resolve("src/commonMain").exists()) {
            "src/commonMain/kotlin/$packagePath"
        } else if (projectDir.resolve("src/main").exists()) {
            "src/main/kotlin/$packagePath"
        } else {
            "src/commonMain/kotlin/$packagePath"
        }
    }
}
