import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import java.util.Base64

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.gradle.ktlint)
}

// ---------------------------------------------------------------------------
// Source-code embedding: reads every component .kt file from :components,
// Base64-encodes it, and generates ComponentSources.kt so the website can
// show raw source in a "Code" tab without any network requests.
// ---------------------------------------------------------------------------

abstract class GenerateComponentSourcesTask : DefaultTask() {
    @get:InputDirectory
    abstract val componentsUiDir: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun generate() {
        val dirs =
            componentsUiDir
                .get()
                .asFile
                .listFiles()
                ?.filter { it.isDirectory }
                ?.sortedBy { it.name }
                ?: emptyList()

        val sb = StringBuilder()
        sb.appendLine("package zed.rainxch.rikkaui.docs.sources")
        sb.appendLine()
        sb.appendLine("import kotlin.io.encoding.Base64")
        sb.appendLine("import kotlin.io.encoding.ExperimentalEncodingApi")
        sb.appendLine()
        sb.appendLine("data class SourceFile(val name: String, val content: String)")
        sb.appendLine()
        sb.appendLine("@OptIn(ExperimentalEncodingApi::class)")
        sb.appendLine("object ComponentSources {")
        sb.appendLine("    private fun d(b64: String): String =")
        sb.appendLine("        Base64.decode(b64).decodeToString()")
        sb.appendLine()
        sb.appendLine("    val sources: Map<String, List<SourceFile>> = mapOf(")

        dirs.forEach { dir ->
            val ktFiles =
                dir
                    .listFiles()
                    ?.filter { it.extension == "kt" }
                    ?.sortedBy { it.name }
                    ?: emptyList()
            if (ktFiles.isNotEmpty()) {
                sb.appendLine("        \"${dir.name}\" to listOf(")
                ktFiles.forEach { file ->
                    val encoded =
                        Base64
                            .getEncoder()
                            .encodeToString(file.readBytes())
                    sb.appendLine("            SourceFile(\"${file.name}\", d(\"$encoded\")),")
                }
                sb.appendLine("        ),")
            }
        }

        sb.appendLine("    )")
        sb.appendLine("}")

        val outFile =
            outputDir.get().asFile.resolve(
                "zed/rainxch/rikkaui/docs/sources/ComponentSources.kt",
            )
        outFile.parentFile.mkdirs()
        outFile.writeText(sb.toString())
    }
}

val generateComponentSources by tasks.registering(GenerateComponentSourcesTask::class) {
    componentsUiDir.set(
        layout.projectDirectory.dir(
            "../../components/src/commonMain/kotlin/zed/rainxch/rikkaui/components/ui",
        ),
    )
    outputDir.set(
        layout.buildDirectory.dir("generated/componentSources/kotlin"),
    )
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain {
            kotlin.srcDir(
                generateComponentSources.flatMap { it.outputDir },
            )

            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.ui)
                implementation(libs.compose.components.resources)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(projects.components)
                implementation(projects.foundation)
            }
        }
    }
}
