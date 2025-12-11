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
    id("dev.kikugie.stonecutter") version "0.7"
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true
    shared {
        fun mc(loader: String, vararg versions: String) {
            for (version in versions) vers("$version-$loader", version)
        }
        //i would recommend to use neoforge for mc > 1.20.1, i haven't tested template for forge on versions higher than that
        mc("fabric","1.20.1", "1.21.1", "1.21.5", "1.21.8", "1.21.10", "1.21.11")
        mc("forge","1.20.1")
        //WARNING: neoforge uses mods.toml instead of neoforge.mods.toml for versions 1.20.4 (?) and earlier
        mc("neoforge", "1.21.1", "1.21.5", "1.21.8", "1.21.10", "1.21.11")
    }
    create(rootProject)
}

rootProject.name = "MidnightLib"