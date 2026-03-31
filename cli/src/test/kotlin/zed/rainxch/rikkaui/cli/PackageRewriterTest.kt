package zed.rainxch.rikkaui.cli

import zed.rainxch.rikkaui.cli.registry.PackageRewriter
import kotlin.test.Test
import kotlin.test.assertEquals

class PackageRewriterTest {

    // ── Basic rewriting ─────────────────────────────────

    @Test
    fun `rewrites package declaration`() {
        val source = "package zed.rainxch.rikkaui.components.ui.button"
        val result = PackageRewriter.rewrite(source, "com.myapp.ui")
        assertEquals("package com.myapp.ui.button", result)
    }

    @Test
    fun `rewrites cross-component imports`() {
        val source = "import zed.rainxch.rikkaui.components.ui.spinner.Spinner"
        val result = PackageRewriter.rewrite(source, "com.myapp.ui")
        assertEquals("import com.myapp.ui.spinner.Spinner", result)
    }

    @Test
    fun `preserves foundation imports`() {
        val source = "import zed.rainxch.rikkaui.foundation.RikkaTheme"
        val result = PackageRewriter.rewrite(source, "com.myapp.ui")
        assertEquals("import zed.rainxch.rikkaui.foundation.RikkaTheme", result)
    }

    @Test
    fun `preserves non-rikka imports`() {
        val source = "import androidx.compose.runtime.Composable"
        val result = PackageRewriter.rewrite(source, "com.myapp.ui")
        assertEquals("import androidx.compose.runtime.Composable", result)
    }

    @Test
    fun `preserves regular code lines`() {
        val source = "val x = 42 // zed.rainxch.rikkaui.components.ui"
        val result = PackageRewriter.rewrite(source, "com.myapp.ui")
        assertEquals(source, result)
    }

    // ── Multi-line source ───────────────────────────────

    @Test
    fun `rewrites full file with mixed content`() {
        val source = """
            package zed.rainxch.rikkaui.components.ui.button

            import zed.rainxch.rikkaui.components.ui.icon.Icon
            import zed.rainxch.rikkaui.components.ui.text.Text
            import zed.rainxch.rikkaui.foundation.RikkaTheme
            import androidx.compose.runtime.Composable

            @Composable
            fun Button() { }
        """.trimIndent()

        val result = PackageRewriter.rewrite(source, "dev.app.components")

        val expected = """
            package dev.app.components.button

            import dev.app.components.icon.Icon
            import dev.app.components.text.Text
            import zed.rainxch.rikkaui.foundation.RikkaTheme
            import androidx.compose.runtime.Composable

            @Composable
            fun Button() { }
        """.trimIndent()

        assertEquals(expected, result)
    }

    // ── Edge cases ──────────────────────────────────────

    @Test
    fun `blank target package returns source unchanged`() {
        val source = "package zed.rainxch.rikkaui.components.ui.button"
        val result = PackageRewriter.rewrite(source, "")
        assertEquals(source, result)
    }

    @Test
    fun `whitespace-only target package returns source unchanged`() {
        val source = "package zed.rainxch.rikkaui.components.ui.button"
        val result = PackageRewriter.rewrite(source, "   ")
        assertEquals(source, result)
    }

    @Test
    fun `single-segment target package works`() {
        val source = "package zed.rainxch.rikkaui.components.ui.button"
        val result = PackageRewriter.rewrite(source, "ui")
        assertEquals("package ui.button", result)
    }

    @Test
    fun `deeply nested target package works`() {
        val source = "package zed.rainxch.rikkaui.components.ui.button"
        val result = PackageRewriter.rewrite(source, "com.company.product.feature.design.ui")
        assertEquals("package com.company.product.feature.design.ui.button", result)
    }

    @Test
    fun `empty source returns empty`() {
        val result = PackageRewriter.rewrite("", "com.myapp.ui")
        assertEquals("", result)
    }

    @Test
    fun `source with no matching patterns returns unchanged`() {
        val source = """
            import kotlin.math.max
            fun hello() = "world"
        """.trimIndent()
        val result = PackageRewriter.rewrite(source, "com.myapp.ui")
        assertEquals(source, result)
    }

    @Test
    fun `multiple cross-component imports all rewritten`() {
        val source = """
            import zed.rainxch.rikkaui.components.ui.icon.Icon
            import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
            import zed.rainxch.rikkaui.components.ui.spinner.Spinner
            import zed.rainxch.rikkaui.components.ui.spinner.SpinnerSize
            import zed.rainxch.rikkaui.components.ui.text.Text
        """.trimIndent()

        val result = PackageRewriter.rewrite(source, "com.app")

        val expected = """
            import com.app.icon.Icon
            import com.app.icon.RikkaIcons
            import com.app.spinner.Spinner
            import com.app.spinner.SpinnerSize
            import com.app.text.Text
        """.trimIndent()

        assertEquals(expected, result)
    }
}
