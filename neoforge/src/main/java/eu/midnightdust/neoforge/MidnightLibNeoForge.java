package eu.midnightdust.neoforge;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import eu.midnightdust.core.MidnightLib;
import eu.midnightdust.core.screen.MidnightConfigOverviewScreen;
import eu.midnightdust.lib.config.AutoCommand;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.server.command.ServerCommandSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import static net.neoforged.fml.IExtensionPoint.DisplayTest.IGNORESERVERONLY;

@Mod("midnightlib")
public class MidnightLibNeoForge {
    public MidnightLibNeoForge() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IGNORESERVERONLY, (remote, server) -> true));
        if (FMLEnvironment.dist == Dist.CLIENT) MidnightLib.onInitializeClient(); else MidnightLib.onInitializeServer();
    }
    @Mod.EventBusSubscriber(modid = "midnightlib", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class MidnightLibBusEvents {
        @SubscribeEvent
        public static void onPostInit(FMLClientSetupEvent event) {
            ModList.get().forEachModContainer((modid, modContainer) -> {
                if (MidnightConfig.configClass.containsKey(modid)) {
                    modContainer.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                            new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> MidnightConfig.getScreen(parent, modid)));
                }
            });
        }
    }
    @Mod.EventBusSubscriber(modid = "midnightlib", value = Dist.CLIENT)
    public static class MidnightLibClientEvents {
        @SubscribeEvent
        public static void afterInitScreen(ScreenEvent.Init.Post event) {
            MidnightConfigOverviewScreen.addButtonToOptionsScreen(event.getScreen(), event.getScreen().getMinecraft());
        }
    }

    @Mod.EventBusSubscriber(modid = "midnightlib", value = Dist.DEDICATED_SERVER)
    public static class MidnightLibServerEvents {
        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent event) {
            for (LiteralArgumentBuilder<ServerCommandSource> command : AutoCommand.commands){
                event.getDispatcher().register(command);
            }
        }
    }
}
