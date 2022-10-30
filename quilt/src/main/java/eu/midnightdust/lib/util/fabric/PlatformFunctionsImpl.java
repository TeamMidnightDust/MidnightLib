package eu.midnightdust.lib.util.fabric;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import eu.midnightdust.lib.util.PlatformFunctions;
import net.fabricmc.api.EnvType;
import net.minecraft.server.command.ServerCommandSource;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.impl.QuiltLoaderImpl;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

import java.nio.file.Path;

public class PlatformFunctionsImpl {
    /**
     * This is our actual method to {@link PlatformFunctions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return QuiltLoader.getConfigDir();
    }
    public static boolean isClientEnv() {
        return QuiltLoaderImpl.INSTANCE.getEnvironmentType() == EnvType.CLIENT;
    }
    public static boolean isModLoaded(String modid) {
        return QuiltLoader.isModLoaded(modid);
    }
    public static void registerCommand(LiteralArgumentBuilder<ServerCommandSource> command) {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, registrationEnvironment) -> dispatcher.register(command));
    }
}
