buildscript {
    repositories {
        mavenCentral()
        maven("https://www.jetbrains.com/intellij-repository/releases")
    }
}

plugins {
    id("org.jetbrains.intellij") version "1.13.2"
    id("jacoco")
    id("com.diffplug.spotless") version "6.19.0"
    id("idea")
    id("java")
    id("com.github.ben-manes.versions") version "0.46.0"
}

intellij {
    //Bundled plugin dependencies
    plugins.set(listOf("yaml", "com.intellij.java", "org.jetbrains.plugins.yaml"))
    pluginName.set("intellij-swagger")
    version.set("2022.1") // Recommended to use the lowest supported version to compile against
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
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.15.1"))
    implementation("commons-io:commons-io:2.12.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.guava:guava:32.0.0-jre")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testImplementation("org.mockito:mockito-core:5.3.1")
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
        channels.set(listOf(project.property("jetbrainsReleaseChannel").toString()))
        token.set(System.getenv("JETBRAINS_HUB_TOKEN"))
    }
    patchPluginXml {
        untilBuild.set("") // null will add until-build to the plugin.xml
        version.set(project.findProperty("version")?.toString() ?: "DEV")
        changeNotes.set(project.findProperty("changeNotes")?.toString() ?: "none")
    }
}

spotless {
    java {
        googleJavaFormat("1.16.0")
    }
}
