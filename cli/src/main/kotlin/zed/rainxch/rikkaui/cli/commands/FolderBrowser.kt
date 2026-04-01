package zed.rainxch.rikkaui.cli.commands

import java.io.File

/**
 * Interactive terminal folder browser.
 *
 * Shows subdirectories of the current location and lets the user
 * navigate into them. The user selects the target folder by
 * entering `s` (select).
 *
 * ```
 *   📂 composeApp/src/commonMain/kotlin
 *
 *   1) zed
 *   2) ..
 *
 *   Navigate: number | [s]elect this folder | [q]uit
 *   >
 * ```
 */
object FolderBrowser {

    /**
     * Opens an interactive folder browser starting at [startDir].
     *
     * @param projectRoot  The project root directory (paths are shown relative to this).
     * @param startDir     The directory to start browsing from.
     * @param echo         Function to print output (CliktCommand::echo).
     * @return The selected directory as a path relative to [projectRoot], or `null` if cancelled.
     */
    fun browse(
        projectRoot: File,
        startDir: File = projectRoot,
        echo: (String, Boolean) -> Unit,
    ): String? {
        var current = if (startDir.isDirectory) startDir else projectRoot

        while (true) {
            val relativePath = current.relativeTo(projectRoot).path.ifEmpty { "." }

            echo("", true)
            echo("  \uD83D\uDCC2 $relativePath", true)
            echo("", true)

            val subdirs = current.listFiles()
                ?.filter { it.isDirectory && !it.name.startsWith(".") && it.name != "build" }
                ?.sortedBy { it.name }
                ?: emptyList()

            if (subdirs.isEmpty()) {
                echo("  (empty)", true)
            } else {
                subdirs.forEachIndexed { index, dir ->
                    echo("  ${index + 1}) ${dir.name}/", true)
                }
            }

            // Show parent option if not at project root
            val canGoUp = current.absolutePath != projectRoot.absolutePath
            if (canGoUp) {
                echo("  0) ../", true)
            }

            echo("", true)
            echo("  [s]elect this folder | [q]uit | number to enter", true)
            echo("  > ", false)

            val input = readlnOrNull()?.trim()?.lowercase() ?: return null

            when {
                input == "s" || input == "select" -> {
                    return current.relativeTo(projectRoot).path.ifEmpty { "." }
                }

                input == "q" || input == "quit" -> {
                    return null
                }

                input == "0" && canGoUp -> {
                    current = current.parentFile ?: current
                }

                input.toIntOrNull() != null -> {
                    val idx = input.toInt() - 1
                    if (idx in subdirs.indices) {
                        current = subdirs[idx]
                    } else {
                        echo("  Invalid choice.", true)
                    }
                }

                else -> {
                    echo("  Invalid input. Enter a number, 's' to select, or 'q' to quit.", true)
                }
            }
        }
    }
}
