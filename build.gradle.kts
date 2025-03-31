buildscript {
    repositories {
        mavenCentral()
        maven("https://www.jetbrains.com/intellij-repository/releases")
    }
}

plugins {
    id("org.jetbrains.intellij") version "1.17.4"
    id("jacoco")
    id("com.diffplug.spotless") version "7.0.2"
    id("idea")
    id("java")
}

intellij {
    //Bundled plugin dependencies
    // "com.intellij.modules.json" to be added when minimum required version is at least 2024.3
    plugins.set(listOf("com.intellij.java", "org.jetbrains.plugins.yaml"))
    pluginName.set("intellij-swagger")
    version.set("2022.3") // Recommended to use the lowest supported version to compile against
}

group = "org.zalando.intellij"
version = if (project.version != Project.DEFAULT_VERSION) project.version else "SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven("https://www.jetbrains.com/intellij-repository/releases")
}

dependencies {
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.18.3"))
    implementation("commons-io:commons-io:2.18.0")
    implementation("com.google.code.gson:gson:2.12.1")
    implementation("com.google.guava:guava:33.4.6-jre")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.12.1")
    testImplementation("org.mockito:mockito-core:5.16.1")
}

tasks {
    jacocoTestReport {
        dependsOn(test) // tests are required to run before generating the report
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }
    test {
        finalizedBy(jacocoTestReport) // report is always generated after tests run
        reports {
            junitXml.required.set(false)
            html.required.set(true)
        }
    }
    publishPlugin {
        channels.set(listOf(project.findProperty("jetbrainsReleaseChannel")?.toString()))
        token.set(System.getenv("JETBRAINS_HUB_TOKEN"))
    }
    patchPluginXml {
        untilBuild.set("") // null will add until-build to the plugin.xml
        version.set(project.version.toString())
        changeNotes.set(project.findProperty("changeNotes")?.toString())
    }
}

spotless {
    java {
        googleJavaFormat()
    }
}
