val serverDir: File = projectDir.resolve("run")
val pluginDir: File = serverDir.resolve("plugins")

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }

    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }

    maven {
        url = uri("https://libraries.minecraft.net")
    }

    maven {
        url = uri("https://jitpack.io")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api("com.github.YouHaveTrouble:Entiddy:v2.0.1")
    api("org.reflections:reflections:0.10.2")
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
}

group = "club.aurorapvp.paperextras"
version = "1.0.0"
description = "\"This should be a plugin\" features from Purpur"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

tasks {

    compileJava {
        options.encoding = "UTF-8"
    }

    javadoc {
        options.encoding = "UTF-8"
    }

    clean {
        doLast {
            serverDir.deleteRecursively()
        }
    }

    processResources {
        filesMatching("paper-plugin.yml") {
            expand(
                mapOf(
                    "name" to project.name,
                    "version" to project.version,
                    "description" to project.description!!.replace('"'.toString(), "\\\"")
                )
            )
        }
    }

    shadowJar {
        archiveFileName.set("PaperExtras-${version}.jar")
        relocate("org.reflections", "club.aurorapvp.paperextras.reflections")
        relocate("me.youhavetrouble.entiddy", "club.aurorapvp.paperextras.entiddy")
    }
}

