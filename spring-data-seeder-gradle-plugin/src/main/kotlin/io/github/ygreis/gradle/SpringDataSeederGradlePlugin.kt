package io.github.ygreis.gradle

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer

class SpringDataSeederGradlePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.pluginManager.withPlugin("java") {
            registerSeedRunTask(project)
        }
    }

    private fun registerSeedRunTask(project: Project) {
        if (project.tasks.findByName("seedRun") != null) {
            return
        }

        val sourceSets = project.extensions.getByType(SourceSetContainer::class.java)
        val mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)

        project.tasks.register("seedRun", JavaExec::class.java) { task ->
            task.group = "application"
            task.description = "Runs Spring Data Seeder."
            task.classpath = mainSourceSet.runtimeClasspath
            task.mainClass.set(resolveMainClass(project))
            task.systemProperty("spring.main.web-application-type", "none")
            task.systemProperty("spring.data.seeder.enabled", "true")

            project.findProperty("seeder")
                ?.toString()
                ?.takeIf { it.isNotBlank() }
                ?.let { task.systemProperty("spring.data.seeder.target", it) }
        }
    }

    private fun resolveMainClass(project: Project): String {
        return listOf("springBootMainClass", "mainClass")
            .asSequence()
            .mapNotNull { propertyName ->
                project.findProperty(propertyName)
                    ?.toString()
                    ?.takeIf { it.isNotBlank() }
            }
            .firstOrNull()
            ?: throw GradleException(
                "Main class not found. Configure 'springBootMainClass' ou 'mainClass'."
            )
    }
}
