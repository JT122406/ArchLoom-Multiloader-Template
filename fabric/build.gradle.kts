plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

configurations {
    val common = register("common")
    register("shadowCommon")
    compileClasspath.get().extendsFrom(common.get())
    runtimeClasspath.get().extendsFrom(common.get())
    named("developmentFabric") { extendsFrom(common.get()) }
}

loom.accessWidenerPath.set(project(":common").loom.accessWidenerPath)

// Fabric Datagen Gradle config.  Remove if not using Fabric datagen
fabricApi.configureDataGeneration()

dependencies {
    implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("fabric_loader_version").get()}")
    api("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_api_version").get()}+$minecraftVersion")

    "common"(project(":common")) { isTransitive = false }
    "shadowCommon"(project(":common", "transformProductionFabric"))
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version))
        }
    }

    jar.get().archiveClassifier.set("raw")

    shadowJar {
        dependsOn(jar)
        from(zipTree(jar.get().archiveFile))
        exclude("architectury.common.json", "com/example/examplemod/fabric/datagen/**")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set(null)
    }
}
