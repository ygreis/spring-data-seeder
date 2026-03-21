plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "2.3.0"
}

group = "io.github.ygreis"
version = "0.1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("springDataSeederPlugin") {
            id = "io.github.ygreis.spring-data-seeder"
            implementationClass = "io.github.ygreis.gradle.SpringDataSeederGradlePlugin"
        }
    }
}
