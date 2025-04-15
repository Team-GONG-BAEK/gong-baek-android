import java.util.Properties
import org.gradle.api.Project
import org.gradle.api.GradleException

fun Project.getLocalProperty(key: String): String {
    val props = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    return props.getProperty(key)
        ?: throw GradleException("Property $key not found in local.properties")
}