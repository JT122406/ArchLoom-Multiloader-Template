architectury {
    common("forge", "fabric", "neoforge")
    platformSetupLoomIde()
}

val minecraftVersion = providers.gradleProperty("minecraft_version").get()

loom.accessWidenerPath.set(file("src/main/resources/examplemod.accesswidener"))

sourceSets.main.get().resources.srcDir("src/main/generated/resources")

dependencies {
    implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("fabric_loader_version").get()}")
}
