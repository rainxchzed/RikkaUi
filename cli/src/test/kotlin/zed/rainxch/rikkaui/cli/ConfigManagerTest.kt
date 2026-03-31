package zed.rainxch.rikkaui.cli

import zed.rainxch.rikkaui.cli.model.RikkaConfig
import zed.rainxch.rikkaui.cli.registry.ConfigManager
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ConfigManagerTest {

    private lateinit var tempDir: File
    private lateinit var originalUserDir: String

    @BeforeTest
    fun setup() {
        tempDir = kotlin.io.path.createTempDirectory("rikkaui-config-test").toFile()
        originalUserDir = System.getProperty("user.dir")
        System.setProperty("user.dir", tempDir.absolutePath)
    }

    @AfterTest
    fun teardown() {
        System.setProperty("user.dir", originalUserDir)
        tempDir.deleteRecursively()
    }

    // ── Save & Load ─────────────────────────────────────

    @Test
    fun `save creates rikka json`() {
        val config = RikkaConfig(
            packageName = "com.myapp.ui",
            componentsDir = "src/commonMain/kotlin/com/myapp/ui",
        )
        ConfigManager.save(config)

        val file = tempDir.resolve("rikka.json")
        assertTrue(file.exists(), "rikka.json should be created")
    }

    @Test
    fun `load returns saved config`() {
        val config = RikkaConfig(
            packageName = "com.myapp.ui",
            componentsDir = "src/commonMain/kotlin/com/myapp/ui",
            sourceSet = "commonMain",
        )
        ConfigManager.save(config)

        val loaded = ConfigManager.load()
        assertNotNull(loaded)
        assertEquals("com.myapp.ui", loaded.packageName)
        assertEquals("src/commonMain/kotlin/com/myapp/ui", loaded.componentsDir)
        assertEquals("commonMain", loaded.sourceSet)
    }

    @Test
    fun `load returns null when no config exists`() {
        assertNull(ConfigManager.load())
    }

    @Test
    fun `exists returns false when no config`() {
        assertTrue(!ConfigManager.exists())
    }

    @Test
    fun `exists returns true after save`() {
        ConfigManager.save(RikkaConfig(packageName = "test", componentsDir = "src"))
        assertTrue(ConfigManager.exists())
    }

    // ── Config walk-up ──────────────────────────────────

    @Test
    fun `findConfigFile walks up directory tree`() {
        // Save config in tempDir (project root)
        ConfigManager.save(
            RikkaConfig(packageName = "test", componentsDir = "src"),
        )

        // Set CWD to a nested subdirectory
        val nested = tempDir.resolve("app/src/main/kotlin")
        nested.mkdirs()
        System.setProperty("user.dir", nested.absolutePath)

        val found = ConfigManager.findConfigFile()
        assertNotNull(found, "Should find rikka.json by walking up")
        assertEquals(tempDir.resolve("rikka.json").absolutePath, found.absolutePath)
    }

    @Test
    fun `findConfigFile returns null when nothing found`() {
        // CWD has no rikka.json anywhere up to root
        assertNull(ConfigManager.findConfigFile())
    }

    // ── projectRoot ─────────────────────────────────────

    @Test
    fun `projectRoot returns config parent directory`() {
        ConfigManager.save(
            RikkaConfig(packageName = "test", componentsDir = "src"),
        )

        val nested = tempDir.resolve("deep/nested/dir")
        nested.mkdirs()
        System.setProperty("user.dir", nested.absolutePath)

        val root = ConfigManager.projectRoot()
        assertNotNull(root)
        assertEquals(tempDir.absolutePath, root.absolutePath)
    }

    @Test
    fun `projectRoot returns null when no config`() {
        assertNull(ConfigManager.projectRoot())
    }

    // ── Default values ──────────────────────────────────

    @Test
    fun `default config has expected defaults`() {
        val config = RikkaConfig()
        assertEquals("dev.rikkaui:foundation:0.1.0", config.foundation)
        assertEquals("https://rikkaui.dev/r", config.registry)
        assertEquals("commonMain", config.sourceSet)
        assertEquals("", config.packageName)
        assertEquals("", config.componentsDir)
    }

    @Test
    fun `config ignores unknown JSON keys`() {
        val json = """
            {
                "packageName": "com.test",
                "componentsDir": "src",
                "futureField": "should be ignored"
            }
        """.trimIndent()

        tempDir.resolve("rikka.json").writeText(json)

        val loaded = ConfigManager.load()
        assertNotNull(loaded)
        assertEquals("com.test", loaded.packageName)
    }

    // ── Overwrite ───────────────────────────────────────

    @Test
    fun `save overwrites existing config`() {
        ConfigManager.save(RikkaConfig(packageName = "old", componentsDir = "old/path"))
        ConfigManager.save(RikkaConfig(packageName = "new", componentsDir = "new/path"))

        val loaded = ConfigManager.load()
        assertNotNull(loaded)
        assertEquals("new", loaded.packageName)
        assertEquals("new/path", loaded.componentsDir)
    }
}
