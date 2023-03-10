buildscript {
    repositories {
        mavenCentral()
        maven("https://www.jetbrains.com/intellij-repository/releases")
    }
}

plugins {
    id("org.jetbrains.intellij") version "1.13.2"
    id("jacoco")
    id("com.github.kt3k.coveralls") version "2.12.2"
    id("com.diffplug.spotless") version "6.16.0"
    id("idea")
    id("java")
    id("com.github.ben-manes.versions") version "0.46.0"
}

intellij {
    //Bundled plugin dependencies
    plugins.set(listOf("yaml", "com.intellij.java", "org.jetbrains.plugins.yaml"))
    pluginName.set("intellij-swagger")
    updateSinceUntilBuild.set(false)
    version.set("2021.1")
}

group = "org.zalando.intellij"
version = if (project.hasProperty("version")) project.version else "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
    maven("https://www.jetbrains.com/intellij-repository/releases")
}

dependencies {
    implementation("commons-io:commons-io:2.11.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.mockito:mockito-core:5.2.0")
}
tasks {
    jacocoTestReport {
        reports {
            xml.required.set(true) // coveralls plugin depends on xml format report
            html.required.set(true)
        }
    }
    publishPlugin {
        channels.set(listOf("beta"))
        token.set(System.getenv("JETBRAINS_HUB_TOKEN"))
    }
}


spotless {
    java {
        googleJavaFormat("1.6")
    }
}
