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
            for (version in versions) {
                val buildscript = when {
                    sc.eval(version, ">= 26.1") && loader == "fabric" -> "build-unobfuscated-fabric.gradle.kts"
                    sc.eval(version, ">= 26.1") && loader == "neoforge" -> "build-unobfuscated-neoforge.gradle.kts"
                    else -> "build-obfuscated.gradle.kts"
                }
                version("$version-$loader", version).buildscript(buildscript)
            }
        }
        mc("fabric","1.20.1", "1.21.1", "1.21.5", "1.21.8", "1.21.10", "1.21.11", "26.1", "26.2")
        //mc("fabric", "26.1-rc-1")
        //mc("forge","1.20.1")
        mc("neoforge", "1.21.1", "1.21.5", "1.21.8", "1.21.10", "1.21.11", "26.1", "26.2")
    }
    create(rootProject)
}

rootProject.name = "MidnightLib"
