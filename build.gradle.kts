import com.diffplug.gradle.spotless.SpotlessPlugin
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath(Config.Plugins.androidGradle)
        classpath(Config.Plugins.kotlinGradle)
    }
}

plugins {
    id("com.diffplug.gradle.spotless") version ("3.23.1")
    id("com.github.ben-manes.versions") version ("0.21.0")
    id("io.gitlab.arturbosch.detekt") version ("1.0.0-RC16")
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx/")
    }
    gradle.projectsEvaluated {
        tasks.withType<JavaCompile> {
            sourceCompatibility = "1.8"
            targetCompatibility = "1.8"
            options.compilerArgs.add("-Xlint")
        }
        tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    apply<SpotlessPlugin>()
    spotless {
        kotlin {
            target("**/*.kt")
            ktlint()
        }
        kotlinGradle {
            target("**/*.gradle.kts")
            ktlint()
        }
    }
}

tasks.register("detektAll", Detekt::class) {
    description = "Runs over whole code base without the starting overhead for each module."
    parallel = true
    buildUponDefaultConfig = true
    setSource(files(projectDir))
    config = files(project.rootDir.resolve(".detekt/config.yml"))
    ignoreFailures = false
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/build/**")
    reports {
        xml.enabled = false
        html.enabled = true
    }
}
