import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.gradle.ktlint)
}

// ---------------------------------------------------------------------------
// API Reference generation: reads KDocs from component sources and writes
// RIKKAUI_REFERENCE.md for AI agents and developers without IDE access.
// ---------------------------------------------------------------------------

val generateApiReference by tasks.registering(GenerateApiReferenceTask::class) {
    componentsUiDir.set(
        layout.projectDirectory.dir(
            "../components/src/commonMain/kotlin/zed/rainxch/rikkaui/components/ui",
        ),
    )
    foundationDir.set(
        layout.projectDirectory.dir(
            "../foundation/src/commonMain/kotlin/zed/rainxch/rikkaui/foundation",
        ),
    )
    registrySourceFile.set(
        layout.projectDirectory.file(
            "../feature/docs/src/commonMain/kotlin/zed/rainxch/rikkaui/docs/catalog/ComponentRegistry.kt",
        ),
    )
    outputFile.set(
        layout.projectDirectory.file("../RIKKAUI_REFERENCE.md"),
    )
}

// ---------------------------------------------------------------------------
// Registry JSON generation: reads component .kt files, outputs static JSON
// to /r/ so the website and future CLI share one source of truth.
// ---------------------------------------------------------------------------

val generateRegistryJson by tasks.registering(GenerateRegistryJsonTask::class) {
    componentsUiDir.set(
        layout.projectDirectory.dir(
            "../components/src/commonMain/kotlin/zed/rainxch/rikkaui/components/ui",
        ),
    )
    registrySourceFile.set(
        layout.projectDirectory.file(
            "../feature/docs/src/commonMain/kotlin/zed/rainxch/rikkaui/docs/catalog/ComponentRegistry.kt",
        ),
    )
    outputDir.set(
        layout.projectDirectory.dir("src/webMain/resources/r"),
    )
    foundationVersion.set(
        providers.gradleProperty("VERSION_NAME"),
    )
}

tasks.matching { it.name.contains("wasmJsBrowser") && it.name.contains("Distribution") }.configureEach {
    dependsOn(generateRegistryJson)
}

tasks.matching { it.name == "wasmJsProcessResources" }.configureEach {
    dependsOn(generateRegistryJson)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.navigation.compose)
            implementation(projects.feature.creator)
            implementation(projects.feature.docs)
            implementation(projects.components)
            implementation(projects.foundation)
        }
    }
}
