import org.gradle.api.Project

val ci = System.getenv("CI") == "true"

fun Project.gradleProperty(propertyName: String): String {
    return if (ci) {
        System.getenv(propertyName)
    } else {
        property(propertyName) as String
    }
}
