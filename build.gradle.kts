buildscript {
    repositories {
        mavenCentral()
        maven("https://www.jetbrains.com/intellij-repository/releases")
    }
}

plugins {
    id("org.jetbrains.intellij") version "1.1.6"
    id("jacoco")
    id("com.github.kt3k.coveralls") version "2.7.1"
    id("com.diffplug.spotless") version "5.12.4"
    id("idea")
    id("java")
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
    implementation("commons-io:commons-io:2.8.0")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.3")
    implementation("com.fasterxml.jackson.core:jackson-core:2.12.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.0-M1")
    testImplementation("org.mockito:mockito-core:3.10.0")
}
tasks {
    jacocoTestReport {
        reports {
            xml.required.set(true) // coveralls plugin depends on xml format report
            html.required.set(true)
        }
    }
    publishPlugin {
        channels.set(listOf("stable"))
        token.set(System.getenv("JETBRAINS_HUB_TOKEN"))
    }
}


spotless {
    java {
        googleJavaFormat("1.6")
    }
}
