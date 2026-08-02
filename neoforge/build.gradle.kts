plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

configurations {
    val common = register("common")
    register("shadowCommon")
    compileClasspath.get().extendsFrom(common.get())
    runtimeClasspath.get().extendsFrom(common.get())
    named("developmentNeoForge") { extendsFrom(common.get()) }
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)

    // NeoForge Datagen Gradle config.  Remove if not using NeoForge datagen
    runs.create("datagen") {
        data()
        programArguments.addAll(
            "--all", "--mod", "examplemod",
            "--output", project(":common").file("src/main/generated/resources").absolutePath,
            "--existing", project(":common").file("src/main/resources").absolutePath
        )
    }

    neoForge.convertAccessWideners(tasks.shadowJar, "examplemod.accesswidener")
}

dependencies {
    neoForge("net.neoforged:neoforge:${providers.gradleProperty("neoforge_version").get()}")

    "common"(project(":common")) { isTransitive = false }
    "shadowCommon"(project(":common", "transformProductionNeoForge"))
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/neoforge.mods.toml") {
            expand(mapOf("version" to project.version))
        }
    }

    jar.get().archiveClassifier.set("raw")

    shadowJar {
        dependsOn(jar)
        from(zipTree(jar.get().archiveFile))
        exclude("architectury.common.json", "com/example/examplemod/neoforge/datagen/**", ".cache/**")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set(null)
    }
}