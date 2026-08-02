import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    id("architectury-plugin") version "3.5-SNAPSHOT"
    id("dev.architectury.loom-no-remap") version "1.17-SNAPSHOT" apply false
    id("com.gradleup.shadow") version "9.6.1" apply false
    java
    `maven-publish`
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()
architectury.minecraft = minecraftVersion

allprojects {
    version = providers.gradleProperty("mod_version").get()
    group = providers.gradleProperty("maven_group").get()
}

subprojects {
    pluginManager.apply("dev.architectury.loom-no-remap")
    pluginManager.apply("architectury-plugin")
    pluginManager.apply("maven-publish")

    base.archivesName.set(providers.gradleProperty("archives_base_name").get() + "-${project.name}")

    val loom = project.extensions.getByName<LoomGradleExtensionAPI>("loom")
    loom.silentMojangMappingsLicense()

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.neoforged.net/releases/")
    }

    dependencies {
        "minecraft"("com.mojang:minecraft:$minecraftVersion")
    }

    java {
        withSourcesJar()

        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    tasks.withType<JavaCompile>().configureEach {
        options.release.set(25)
    }

    publishing {
        publications.create<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            from(components["java"])
        }

        repositories {
            mavenLocal()
            maven {
                val releasesRepoUrl = "https://example.com/releases"
                val snapshotsRepoUrl = "https://example.com/snapshots"
                url = uri(if (project.version.toString().endsWith("SNAPSHOT") || project.version.toString().startsWith("0")) snapshotsRepoUrl else releasesRepoUrl)
                name = "ExampleRepo"
                credentials {
                    username = providers.gradleProperty("repoLogin").orNull
                    password = providers.gradleProperty("repoPassword").orNull
                }
            }
        }
    }
}
