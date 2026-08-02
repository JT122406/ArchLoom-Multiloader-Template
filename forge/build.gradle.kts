plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    forge()
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

configurations {
    val common = register("common")
    register("shadowCommon")
    compileClasspath.get().extendsFrom(common.get())
    runtimeClasspath.get().extendsFrom(common.get())
    named("developmentForge") { extendsFrom(common.get()) }
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)

    forge {
        convertAccessWideners(tasks.shadowJar, "examplemod.accesswidener")
        mixinConfigs.set(listOf("examplemod.mixins.json", "examplemod-common.mixins.json"))
    }

    // Forge Datagen Gradle config.  Remove if not using Forge datagen
    runs.create("datagen") {
        data()
        programArguments.addAll(
            "--all", "--mod", "examplemod",
            "--output", project(":common").file("src/main/generated/resources").absolutePath,
            "--existing", project(":common").file("src/main/resources").absolutePath
        )
    }
}

dependencies {
    forge("net.minecraftforge:forge:$minecraftVersion-${providers.gradleProperty("forge_version").get()}")

    "common"(project(":common")) { isTransitive = false }
    "shadowCommon"(project(":common", "transformProductionForge"))
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/mods.toml") {
            expand(mapOf("version" to project.version))
        }
    }

    jar.get().archiveClassifier.set("raw")

    shadowJar {
        dependsOn(jar)
        from(zipTree(jar.get().archiveFile))
        exclude("architectury.common.json", ".cache/**", "com/example/examplemod/forge/datagen/**")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set(null)
    }
}
