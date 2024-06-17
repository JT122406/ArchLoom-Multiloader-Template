pluginManagement.repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://maven.architectury.dev/")
    maven("https://maven.minecraftforge.net/")
    maven("https://maven.neoforged.net/releases/")
    gradlePluginPortal()
}

plugins {
    id("com.gradle.develocity") version("3.17.5")
}

develocity.buildScan {
    termsOfUseUrl = "https://gradle.com/terms-of-service"
    termsOfUseAgree = "yes"
}

include("common", "fabric", "forge", "neoforge")

rootProject.name = "ExampleMod"
