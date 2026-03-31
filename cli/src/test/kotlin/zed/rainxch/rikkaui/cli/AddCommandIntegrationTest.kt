package zed.rainxch.rikkaui.cli

import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import zed.rainxch.rikkaui.cli.commands.AddCommand
import zed.rainxch.rikkaui.cli.model.RikkaConfig
import zed.rainxch.rikkaui.cli.registry.ConfigManager
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Integration tests for the `add` command.
 *
 * These hit the live registry (rikkaui.dev) to test real
 * component installation end-to-end. Each test uses an
 * isolated temp directory as both CWD and project root.
 */
class AddCommandIntegrationTest {

    private lateinit var tempDir: File
    private lateinit var originalUserDir: String

    @BeforeTest
    fun setup() {
        tempDir = kotlin.io.path.createTempDirectory("rikkaui-add-test").toFile()
        originalUserDir = System.getProperty("user.dir")
        System.setProperty("user.dir", tempDir.absolutePath)
    }

    @AfterTest
    fun teardown() {
        System.setProperty("user.dir", originalUserDir)
        tempDir.deleteRecursively()
    }

    private fun runAdd(vararg args: String) {
        RikkaCli()
            .subcommands(AddCommand())
            .main(listOf("add") + args.toList())
    }

    // ── Basic install ───────────────────────────────────

    @Test
    fun `add single component creates files`() {
        runAdd("card", "--path", "src/ui", "--package", "com.test.ui")

        val cardFile = tempDir.resolve("src/ui/card/Card.kt")
        assertTrue(cardFile.exists(), "Card.kt should be created")
        assertTrue(
            cardFile.readText().startsWith("package com.test.ui.card"),
            "Package should be rewritten",
        )
    }

    @Test
    fun `add component with dependencies pulls transitive deps`() {
        runAdd("button", "--path", "src/ui", "--package", "com.test")

        // Button depends on icon, spinner, text
        assertTrue(tempDir.resolve("src/ui/button/Button.kt").exists())
        assertTrue(tempDir.resolve("src/ui/icon/Icon.kt").exists())
        assertTrue(tempDir.resolve("src/ui/spinner/Spinner.kt").exists())
        assertTrue(tempDir.resolve("src/ui/text/Text.kt").exists())
    }

    @Test
    fun `add multiple components shares deps`() {
        runAdd(
            "button", "checkbox", "badge",
            "--path", "src/ui", "--package", "com.test",
        )

        // All three exist
        assertTrue(tempDir.resolve("src/ui/button/Button.kt").exists())
        assertTrue(tempDir.resolve("src/ui/checkbox/Checkbox.kt").exists())
        assertTrue(tempDir.resolve("src/ui/badge/Badge.kt").exists())

        // Shared dep (text) exists only once
        val textFiles = tempDir.walkTopDown().filter { it.name == "Text.kt" }.toList()
        assertEquals(1, textFiles.size, "Text.kt should only exist once")
    }

    // ── Overwrite behavior ──────────────────────────────

    @Test
    fun `add skips existing files without overwrite flag`() {
        val cardDir = tempDir.resolve("src/ui/card")
        cardDir.mkdirs()
        val cardFile = cardDir.resolve("Card.kt")
        cardFile.writeText("// my custom Card")

        runAdd("card", "--path", "src/ui", "--package", "com.test")

        assertEquals(
            "// my custom Card",
            cardFile.readText(),
            "Existing file should NOT be overwritten",
        )
    }

    @Test
    fun `add overwrites existing files with overwrite flag`() {
        val cardDir = tempDir.resolve("src/ui/card")
        cardDir.mkdirs()
        val cardFile = cardDir.resolve("Card.kt")
        cardFile.writeText("// my custom Card")

        runAdd("card", "--path", "src/ui", "--package", "com.test", "--overwrite")

        assertTrue(
            cardFile.readText().startsWith("package com.test.card"),
            "File should be overwritten with registry content",
        )
    }

    // ── Dry run ─────────────────────────────────────────

    @Test
    fun `dry run creates no files`() {
        runAdd(
            "button", "card", "dialog",
            "--path", "src/ui", "--package", "com.test",
            "--dry-run",
        )

        val ktFiles = tempDir.walkTopDown().filter { it.extension == "kt" }.toList()
        assertTrue(ktFiles.isEmpty(), "Dry run should write zero files")
    }

    // ── Config-based path resolution ────────────────────

    @Test
    fun `add uses config when no flags provided`() {
        ConfigManager.save(
            RikkaConfig(
                packageName = "com.configured.ui",
                componentsDir = "src/commonMain/kotlin/com/configured/ui",
            ),
        )

        runAdd("separator")

        val file = tempDir.resolve(
            "src/commonMain/kotlin/com/configured/ui/separator/Separator.kt",
        )
        assertTrue(file.exists(), "Should install to config-specified path")
        assertTrue(
            file.readText().contains("package com.configured.ui.separator"),
            "Package should match config",
        )
    }

    @Test
    fun `add from subdirectory resolves relative to project root`() {
        // Save config at project root
        ConfigManager.save(
            RikkaConfig(
                packageName = "com.kmp.ui",
                componentsDir = "src/commonMain/kotlin/com/kmp/ui",
            ),
        )

        // Move CWD to a nested subdirectory
        val nested = tempDir.resolve("app/feature/deep")
        nested.mkdirs()
        System.setProperty("user.dir", nested.absolutePath)

        runAdd("card")

        // File should be at project root, NOT in nested dir
        val correct = tempDir.resolve(
            "src/commonMain/kotlin/com/kmp/ui/card/Card.kt",
        )
        assertTrue(correct.exists(), "File should be relative to project root")

        val wrong = nested.resolve(
            "src/commonMain/kotlin/com/kmp/ui/card/Card.kt",
        )
        assertFalse(wrong.exists(), "File should NOT be relative to CWD")
    }

    // ── Package rewriting in real files ──────────────────

    @Test
    fun `foundation imports preserved in installed files`() {
        runAdd("button", "--path", "src/ui", "--package", "com.test")

        val content = tempDir.resolve("src/ui/button/Button.kt").readText()
        assertTrue(
            content.contains("import zed.rainxch.rikkaui.foundation."),
            "Foundation imports must remain unchanged",
        )
        assertFalse(
            content.contains("import zed.rainxch.rikkaui.components.ui."),
            "Original component imports must be rewritten",
        )
    }

    @Test
    fun `cross-component imports rewritten correctly`() {
        runAdd("button", "--path", "src/ui", "--package", "my.app")

        val content = tempDir.resolve("src/ui/button/Button.kt").readText()
        assertTrue(
            content.contains("import my.app.spinner."),
            "Cross-component imports should use target package",
        )
    }

    // ── Deep transitive dependencies ────────────────────

    @Test
    fun `alert-dialog pulls button which pulls icon spinner text`() {
        runAdd("alert-dialog", "--path", "src/ui", "--package", "com.test")

        // Direct: alert-dialog → button, text
        assertTrue(tempDir.resolve("src/ui/alertdialog/AlertDialog.kt").exists())
        assertTrue(tempDir.resolve("src/ui/button/Button.kt").exists())
        assertTrue(tempDir.resolve("src/ui/text/Text.kt").exists())
        // Transitive: button → icon, spinner
        assertTrue(tempDir.resolve("src/ui/icon/Icon.kt").exists())
        assertTrue(tempDir.resolve("src/ui/spinner/Spinner.kt").exists())
    }

    // ── Kebab-case component names ──────────────────────

    @Test
    fun `kebab-case component names resolve correctly`() {
        runAdd(
            "scroll-area", "hover-card", "toggle-group",
            "--path", "src/ui", "--package", "com.test",
        )

        assertTrue(tempDir.resolve("src/ui/scrollarea/ScrollArea.kt").exists())
        assertTrue(tempDir.resolve("src/ui/hovercard/HoverCard.kt").exists())
        assertTrue(tempDir.resolve("src/ui/togglegroup/ToggleGroup.kt").exists())
    }

    // ── Flag overrides config ───────────────────────────

    @Test
    fun `flags override config values`() {
        ConfigManager.save(
            RikkaConfig(
                packageName = "com.configured",
                componentsDir = "src/configured/path",
            ),
        )

        runAdd(
            "separator",
            "--path", "src/override",
            "--package", "com.override",
        )

        val file = tempDir.resolve("src/override/separator/Separator.kt")
        assertTrue(file.exists(), "Should use flag path, not config path")
        assertTrue(
            file.readText().contains("package com.override.separator"),
            "Should use flag package, not config package",
        )
    }

    // ── Zero-dependency components ──────────────────────

    @Test
    fun `component with no deps installs only itself`() {
        runAdd("separator", "--path", "src/ui", "--package", "com.test")

        val allKt = tempDir.walkTopDown().filter { it.extension == "kt" }.toList()
        assertEquals(1, allKt.size, "Separator has no deps, should be 1 file")
        assertEquals("Separator.kt", allKt.first().name)
    }
}
