package eu.midnightdust.lib.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.command.ServerCommandSource;

import java.nio.file.Path;
import java.util.function.Supplier;

public class PlatformVariables {
    @ExpectPlatform
    public static Path getConfigDirectory() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
    @ExpectPlatform
    public static boolean isClientEnv() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
    @ExpectPlatform
    public static boolean isModLoaded(String modid) {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
    @ExpectPlatform
    public static void registerCommand(LiteralArgumentBuilder<ServerCommandSource> command) {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
}
