package zed.rainxch.rikkaui.cli.registry

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import zed.rainxch.rikkaui.cli.model.RikkaConfig
import java.io.File

private val prettyJson = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}

object ConfigManager {
    private const val CONFIG_FILE = "rikka.json"

    fun findConfigFile(): File? {
        var dir = File(System.getProperty("user.dir"))
        while (dir.parent != null) {
            val file = dir.resolve(CONFIG_FILE)
            if (file.exists()) return file
            dir = dir.parentFile
        }
        return null
    }

    fun load(): RikkaConfig? {
        val file = findConfigFile() ?: return null
        return prettyJson.decodeFromString<RikkaConfig>(file.readText())
    }

    /**
     * Returns the directory containing `rikka.json`, or null if
     * no config file exists. Used to resolve relative paths
     * (like `componentsDir`) against the project root rather
     * than the current working directory.
     */
    fun projectRoot(): File? = findConfigFile()?.parentFile

    fun save(config: RikkaConfig, dir: File = File(System.getProperty("user.dir"))) {
        val file = dir.resolve(CONFIG_FILE)
        file.writeText(prettyJson.encodeToString(config))
    }

    fun exists(): Boolean = findConfigFile() != null
}
