plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(libs.clikt)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.testJunit)
}

tasks.shadowJar {
    archiveBaseName.set("rikkaui")
    archiveClassifier.set("")
    archiveVersion.set("")
    manifest {
        attributes("Main-Class" to "zed.rainxch.rikkaui.cli.MainKt")
    }
}
