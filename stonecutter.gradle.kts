plugins {
    id("dev.kikugie.stonecutter")
    id("me.modmuss50.mod-publish-plugin") version "2.2.0" apply false
}
stonecutter active "26.3-fabric" /* [SC] DO NOT EDIT */

// See https://stonecutter.kikugie.dev/wiki/config/params
stonecutter parameters {
    val (version, loader) = current.project.split('-', limit = 2)

    // Makes version- and loader-specific properties apply from `stoncutter.properties.toml`
    properties {
        tags(version, loader)
    }

    // Adds constants to Stonecutter comments (i.e. for `//? if fabric {...`)
    constants {
        match(loader, "fabric", "neoforge")
    }

    swaps["mod_version"] = "\"${properties.get<String>("mod.version")}\";"
    swaps["minecraft"] = "\"${node.metadata.version}\";"
    constants["release"] = properties.get<String>("mod.id") != "template"
    dependencies["fapi"] = properties.getOrNull<String>("deps.fabric_api") ?: "0"

    replacements {
        string {
            direction = eval(current.version, ">=1.21.11-rc2")
            replace("ResourceLocation", "Identifier")
        }
        string {
            direction = eval(current.version, ">=1.21.11-rc2")
            replace("net.minecraft.Util", "net.minecraft.util.Util")
        }
        string {
            direction = eval(current.version, ">=26.1")
            replace("render(", "extractRenderState(")
        }
        string {
            direction = eval(current.version, ">=26.1")
            replace("GuiGraphics", "GuiGraphicsExtractor")
        }
        string {
            direction = eval(current.version, ">=26.1")
            replace("renderListSeparators", "extractListSeparators")
        }
        string {
            direction = eval(current.version, ">=26.1")
            replace("renderContent", "extractContent")
        }
        string {
            direction = eval(current.version, ">=26.1")
            replace("drawCenteredString", "centeredText")
        }
        string(current.parsed >= "26.2-pre.3") {
            replace("minecraft.setScreen(", "minecraft.gui.setScreen(")
        }
    }
}
