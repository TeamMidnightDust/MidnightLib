plugins {
    id("dev.kikugie.stonecutter")
    id("fabric-loom") version "1.15-SNAPSHOT" apply false // For obfuscated releases (<= 1.21.11)
    id("net.fabricmc.fabric-loom") version "1.15-SNAPSHOT" apply false // For unobfuscated releases (>= 26.1)
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    id("me.modmuss50.mod-publish-plugin") version "0.8.4" apply false
}
stonecutter active "1.21.11-fabric" /* [SC] DO NOT EDIT */

// See https://stonecutter.kikugie.dev/wiki/config/params
stonecutter parameters {
    swaps["mod_version"] = "\"" + property("mod.version") + "\";"
    swaps["minecraft"] = "\"" + node.metadata.version + "\";"
    constants["release"] = property("mod.id") != "template"
    dependencies["fapi"] = node.project.property("deps.fabric_version") as String
}