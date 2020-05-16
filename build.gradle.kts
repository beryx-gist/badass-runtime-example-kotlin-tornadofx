import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openjfx.gradle.JavaFXModule
import org.openjfx.gradle.JavaFXOptions
import org.openjfx.gradle.JavaFXPlatform


plugins {
    kotlin("jvm") version "1.3.72"
    application
    id("org.openjfx.javafxplugin") version "0.0.8"

    // The Shadow Plugin is currently not compatible with Gradle 6.4
    // see: https://github.com/johnrengelman/shadow/issues/572
    // id("com.github.johnrengelman.shadow") version "5.2.0"
    id("org.beryx.runtime") version "1.8.4"
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
    configuration = "compileOnly"
}

val javaFXOptions = the<JavaFXOptions>()

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.72")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.72")
    implementation("no.tornado:tornadofx:1.7.20") {
        exclude("org.jetbrains.kotlin")
    }
    JavaFXPlatform.values().forEach {platform ->
        val cfg = configurations.create("javafx_" + platform.classifier)
        JavaFXModule.getJavaFXModules(javaFXOptions.modules).forEach { m ->
            project.getDependencies().add(cfg.name,
                    String.format("org.openjfx:%s:%s:%s", m.getArtifactName(), javaFXOptions.version, platform.classifier));
        }
    }
}

runtime {
    imageZip.set(project.file("${project.buildDir}/image-zip/hello-image.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    modules.set(listOf("java.desktop", "jdk.unsupported", "java.scripting", "java.logging", "java.xml"))

    targetPlatform("linux", System.getenv("JDK_LINUX_HOME"))
    targetPlatform("mac", System.getenv("JDK_MAC_HOME"))
    targetPlatform("win", System.getenv("JDK_WIN_HOME"))
}

tasks.withType(CreateStartScripts::class).forEach {script ->
    script.doFirst {
        script.classpath =  files("lib/*")
    }
}

tasks["runtime"].doLast {
    JavaFXPlatform.values().forEach { platform ->
        val cfg = configurations["javafx_" + platform.classifier]
        cfg.resolvedConfiguration.files.forEach { f ->
            copy {
                from(f)
                into("build/image/hello-${platform.classifier}/lib")
            }
        }
    }
}
