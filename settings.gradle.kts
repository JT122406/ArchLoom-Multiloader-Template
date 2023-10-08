pluginManagement.repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://maven.architectury.dev/")
    maven("https://maven.minecraftforge.net/")
    gradlePluginPortal()
}

plugins {
    `gradle-enterprise`
}

gradleEnterprise.buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

include("common", "fabric", "forge")

rootProject.name = "ExampleMod"
