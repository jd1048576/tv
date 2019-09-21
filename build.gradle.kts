buildscript {
    repositories {
        google()
        jcenter()
        maven("https://maven.fabric.io/public")
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath(ANDROID_GRADLE)
        classpath(KOTLIN_GRADLE)
        classpath(GOOGLE_SERVICES_GRADLE)
        classpath(FABRIC_GRADLE)
    }
}

plugins {
    id("com.diffplug.gradle.spotless") version ("3.24.2")
    id("com.github.ben-manes.versions") version ("0.25.0")
    id("io.gitlab.arturbosch.detekt") version ("1.0.1")
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    gradle.projectsEvaluated {
        tasks.withType<JavaCompile> {
            sourceCompatibility = "1.8"
            targetCompatibility = "1.8"
            options.compilerArgs.add("-Xlint:all")
        }
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs += "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
                freeCompilerArgs += "-Xuse-experimental=kotlinx.coroutines.FlowPreview"
            }
        }
    }
    apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
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

tasks.register("detektAll", io.gitlab.arturbosch.detekt.Detekt::class) {
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
