package eu.midnightdust.neoforge;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import eu.midnightdust.core.MidnightLib;
import eu.midnightdust.lib.config.AutoCommand;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.server.command.ServerCommandSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod("midnightlib")
public class MidnightLibNeoForge {
    public MidnightLibNeoForge() {
        if (FMLEnvironment.dist == Dist.CLIENT) MidnightLib.onInitializeClient();
        else MidnightLib.onInitializeServer();
    }

    @EventBusSubscriber(modid = "midnightlib", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class MidnightLibBusEvents {
        @SubscribeEvent
        public static void onPostInit(FMLClientSetupEvent event) {
            ModList.get().forEachModContainer((modid, modContainer) -> {
                if (MidnightConfig.configClass.containsKey(modid)) {
                    modContainer.registerExtensionPoint(IConfigScreenFactory.class, (minecraftClient, screen) -> MidnightConfig.getScreen(screen, modid));
                }
            });
        }
    }

    @EventBusSubscriber(modid = "midnightlib", value = Dist.DEDICATED_SERVER)
    public static class MidnightLibServerEvents {
        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent event) {
            for (LiteralArgumentBuilder<ServerCommandSource> command : AutoCommand.commands) {
                event.getDispatcher().register(command);
            }
        }
    }
}
