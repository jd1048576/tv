import com.diffplug.gradle.spotless.SpotlessPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath(Plugins.androidGradle)
        classpath(Plugins.kotlinGradle)
    }
}

plugins {
    id("com.diffplug.gradle.spotless") version ("3.23.0")
    id("com.github.ben-manes.versions") version ("0.21.0")
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx/")
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
