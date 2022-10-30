package eu.midnightdust.lib.util.fabric;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import eu.midnightdust.lib.util.PlatformFunctions;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.ServerCommandSource;

import java.nio.file.Path;

public class PlatformFunctionsImpl {
    /**
     * This is our actual method to {@link PlatformFunctions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
    public static boolean isClientEnv() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
    public static boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
    public static void registerCommand(LiteralArgumentBuilder<ServerCommandSource> command) {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, registrationEnvironment) -> dispatcher.register(command));
    }
}
