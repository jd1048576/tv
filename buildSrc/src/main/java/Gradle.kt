import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

val ci = System.getenv("CI") == "true" || System.getenv("GITHUB_ACTIONS") == "true"

fun Project.stringProperty(propertyName: String) = property(propertyName) as String

fun RepositoryHandler.register() {
    maven("https://maven.google.com") {
        content {
            includeGroupByRegex("androidx.*")
            includeGroupByRegex("com.android.*")
            includeGroup("com.crashlytics.sdk.android")
            includeGroup("com.google.android.gms")
            includeGroup("com.google.android.material")
            includeGroup("com.google.firebase")
            includeGroup("com.google.gms")
            includeGroup("io.fabric.sdk.android")
        }
    }

    maven("https://jcenter.bintray.com") {
        content {
            includeGroupByRegex("com.google.*")
            includeGroupByRegex("com.squareup.*")
            includeGroupByRegex("javax.*")
            includeGroupByRegex("org.jetbrains.*")

            includeGroup("com.beust")
            includeGroup("com.ibm.icu")
            includeGroup("com.pinterest.ktlint")
            includeGroup("commons-codec")
            includeGroup("commons-io")
            includeGroup("io.coil-kt")
            includeGroup("io.gitlab.arturbosch.detekt")
            includeGroup("junit")
            includeGroup("net.java")
            includeGroup("net.ltgt.gradle.incap")
            includeGroup("org.abego.treelayout")
            includeGroup("org.antlr")
            includeGroup("org.assertj")
            includeGroup("org.checkerframework")
            includeGroup("org.codehaus.mojo")
            includeGroup("org.ec4j.core")
            includeGroup("org.glassfish")
            includeGroup("org.hamcrest")
            includeGroup("org.junit")
            includeGroup("org.sonatype.oss")
            includeGroup("org.xerial")
            includeGroup("org.yaml")
        }
    }

    maven("https://maven.fabric.io/public") {
        content {
            includeGroup("io.fabric.tools")
        }
    }
}
