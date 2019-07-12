import org.gradle.api.Project
import java.io.File
import java.util.Properties

val ci = System.getenv("CI") == "true"

fun Project.localProperty(propertyName: String): String {
    return if (ci) {
        System.getenv(propertyName)
    } else {
        val localProperties = Properties()
        val file: File = rootProject.file("local.properties")
        if (file.exists()) {
            file.inputStream().use { localProperties.load(it) }
        }
        localProperties.getProperty(propertyName)
    }
}
