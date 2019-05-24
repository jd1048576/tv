import org.gradle.api.Project
import java.io.File
import java.util.Properties

fun Project.localProperty(property: String): String {
    val localProperties = Properties()
    val file: File = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { localProperties.load(it) }
    }
    return localProperties.getProperty(property)
}
