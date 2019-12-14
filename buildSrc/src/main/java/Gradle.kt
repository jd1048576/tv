import org.gradle.api.Project

val ci = System.getenv("CI") == "true" || System.getenv("GITHUB_ACTIONS") == "true"

fun Project.stringProperty(propertyName: String) = property(propertyName) as String
