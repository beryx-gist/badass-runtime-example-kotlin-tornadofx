import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version "1.3.50"
    application
    id("org.openjfx.javafxplugin") version "0.0.8"
    id("com.github.johnrengelman.shadow") version "5.1.0"
    id("org.beryx.runtime") version "1.6.0"
}

val compileKotlin: KotlinCompile by tasks
val compileJava: JavaCompile by tasks
compileJava.destinationDir = compileKotlin.destinationDir

compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

application {
    mainClassName = "org.beryx.runtime.test.kotlin.HelloWorldKt"
}

repositories {
    mavenCentral()
}

javafx {
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.10")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.10")
    implementation("no.tornado:tornadofx:1.7.17") {
        exclude("org.jetbrains.kotlin")
    }
}

runtime {
    imageZip.set(project.file("${project.buildDir}/image-zip/hello-image.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    modules.set(listOf("java.desktop", "jdk.unsupported", "java.scripting", "java.logging", "java.xml"))
}
