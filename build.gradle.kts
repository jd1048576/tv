buildscript {
    repositories.register()
    dependencies {
        classpath(ANDROID_GRADLE)
        classpath(KOTLIN_GRADLE)
        classpath(GOOGLE_SERVICES_GRADLE)
        classpath(FABRIC_GRADLE)
    }
}

plugins {
    id(DEPENDENCY_UPDATES) version (DEPENDENCY_UPDATES_VERSION)
    id(DETEKT) version (DETEKT_VERSION)
}

dependencies {
    detektPlugins(DETEKT_FORMATTING)
}

allprojects {
    repositories.register()
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
}

val detektAll by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
    description = "Runs detekt over whole code base."
    parallel = true
    buildUponDefaultConfig = true
    autoCorrect = false
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    config.setFrom(files("$rootDir/.detekt/config.yml"))
    baseline.set(file("$rootDir/.detekt/baseline.xml"))
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

val detektFormat by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
    description = "Reformats whole code base."
    parallel = true
    disableDefaultRuleSets = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    config.setFrom(files("$rootDir/.detekt/format.yml"))
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}
