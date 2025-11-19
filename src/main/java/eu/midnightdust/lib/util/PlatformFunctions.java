package eu.midnightdust.lib.util;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.nio.file.Path;

import net.minecraft.commands.CommandSourceStack;

//? if fabric {
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
//?}
//? if neoforge {
/*import eu.midnightdust.core.MidnightLib;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
*///?}
//? if forge {
/*import eu.midnightdust.core.MidnightLib;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
*///?}

public class PlatformFunctions {
    //? if fabric {
    public static String getPlatformName() {
        return "fabric";
    }
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
    public static boolean isClientEnv() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
    public static boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
    public static void registerCommand(LiteralArgumentBuilder<CommandSourceStack> command) {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, registrationEnvironment) -> dispatcher.register(command));
    }
    //?} else if neoforge {
    /*public static String getPlatformName() {
        return "neoforge";
    }
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
    public static boolean isClientEnv() {
        //? if >= 1.21.9 {
        return FMLEnvironment.getDist().isClient();
        //?} else {
         /^return FMLEnvironment.dist.isClient();
        ^///?}
    }
    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }
    public static void registerCommand(LiteralArgumentBuilder<CommandSourceStack> command) {
        MidnightLib.commands.add(command);
    }
    *///?} else if forge {
    /*public static String getPlatformName() {
        return "forge";
    }
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
    public static boolean isClientEnv() {
        return FMLEnvironment.dist.isClient();
    }
    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }
    public static void registerCommand(LiteralArgumentBuilder<CommandSourceStack> command) {
        MidnightLib.commands.add(command);
    }
    *///?}
}
