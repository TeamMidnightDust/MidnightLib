pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.kikugie.dev/snapshots")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9"
}

stonecutter {
    kotlinController = true
    shared {
        fun mc(loader: String, vararg versions: String) {
            for (version in versions) version("$version-$loader", version).buildscript(if (stonecutter.eval(version, ">=26.1-pre-3")) "build-unobfuscated.gradle.kts" else "build-obfuscated.gradle.kts")
        }
        mc("fabric","1.20.1", "1.21.1", "1.21.5", "1.21.8", "1.21.10", "1.21.11", "26.1-pre-3")
        mc("forge","1.20.1")
        mc("neoforge", "1.21.1", "1.21.5", "1.21.8", "1.21.10", "1.21.11")
    }
    create(rootProject)
}

rootProject.name = "MidnightLib"