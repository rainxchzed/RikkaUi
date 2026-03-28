import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec

plugins {
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.gradle.ktlint)
}

subprojects {
    afterEvaluate {
        tasks.configureEach {
            when (name) {
                "preBuild",
                "wasmJsBrowserDevelopmentExecutableDistribution",
                -> {
                    val ktlintFormat = tasks.findByName("ktlintFormat")
                    if (ktlintFormat != null) dependsOn(ktlintFormat)
                }
            }
        }
    }
}
