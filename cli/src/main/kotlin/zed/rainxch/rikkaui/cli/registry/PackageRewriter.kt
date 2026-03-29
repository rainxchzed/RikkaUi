package zed.rainxch.rikkaui.cli.registry

private const val SOURCE_BASE_PACKAGE = "zed.rainxch.rikkaui.components.ui"

object PackageRewriter {

    /**
     * Rewrites the package declaration and internal imports in component source code
     * to match the developer's target package.
     *
     * Example:
     *   source: "package zed.rainxch.rikkaui.components.ui.button"
     *   targetBase: "com.myapp.ui"
     *   result: "package com.myapp.ui.button"
     *
     * Also rewrites cross-component imports:
     *   "import zed.rainxch.rikkaui.components.ui.spinner.Spinner"
     *   → "import com.myapp.ui.spinner.Spinner"
     */
    fun rewrite(source: String, targetBasePackage: String): String {
        if (targetBasePackage.isBlank()) return source

        return source
            .lineSequence()
            .joinToString("\n") { line ->
                when {
                    line.startsWith("package $SOURCE_BASE_PACKAGE") ->
                        line.replace(SOURCE_BASE_PACKAGE, targetBasePackage)

                    line.startsWith("import $SOURCE_BASE_PACKAGE") ->
                        line.replace(SOURCE_BASE_PACKAGE, targetBasePackage)

                    // Foundation imports stay as-is — it's a real Gradle dependency
                    else -> line
                }
            }
    }
}
