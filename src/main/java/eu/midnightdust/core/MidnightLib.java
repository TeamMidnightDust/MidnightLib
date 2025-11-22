package eu.midnightdust.core;

import eu.midnightdust.core.config.MidnightLibConfig;
import eu.midnightdust.lib.config.MidnightConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.UIManager;
import net.minecraft.Util;
import java.util.ArrayList;

import java.util.List;

//? if fabric {
import net.fabricmc.api.ClientModInitializer;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import java.util.HashMap;
import java.util.Map;

public class MidnightLib implements ClientModInitializer {
//?} else if neoforge {
/*import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import eu.midnightdust.lib.config.AutoCommand;
import eu.midnightdust.lib.util.PlatformFunctions;
import net.minecraft.commands.CommandSourceStack;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.ConcurrentModificationException;

@Mod("midnightlib")
public class MidnightLib {
*///?} else if forge {
/*import java.util.ConcurrentModificationException;
import eu.midnightdust.lib.config.AutoCommand;
import eu.midnightdust.lib.util.PlatformFunctions;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod("midnightlib")
public class MidnightLib {
*///?}
    public static List<String> hiddenMods = new ArrayList<>();
    public static final String MOD_ID = "midnightlib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitializeClient() {
        try {
            if (Util.getPlatform() != Util.OS.OSX) {
                System.setProperty("java.awt.headless", "false");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (Exception | Error e) { LOGGER.error("Error setting system look and feel", e); }
        MidnightLibConfig.init(MOD_ID, MidnightLibConfig.class);
    }

    //? if fabric {
    public static class ModMenuInit implements ModMenuApi {
        @Override
        public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return parent -> MidnightLibConfig.getScreen(parent, MOD_ID);
        }

        @Override
        public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
            HashMap<String, ConfigScreenFactory<?>> map = new HashMap<>();
            MidnightConfig.configInstances.forEach((modid, cClass) -> {
                if (!MidnightLib.hiddenMods.contains(modid))
                    map.put(modid, parent -> MidnightConfig.getScreen(parent, modid));
            });
            return map;
        }
    }
    //?}

    /*? if neoforge {*/
        /*public static List<LiteralArgumentBuilder<CommandSourceStack>> commands = new ArrayList<>();

        public MidnightLib() {
            if (PlatformFunctions.isClientEnv()) this.onInitializeClient();
        }

        //? if >= 1.21.6 {
        @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
        //?} else {
        /^@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        ^///?}
        public static class MidnightLibBusEvents {
            @SubscribeEvent
            public static void onPostInit(FMLClientSetupEvent event) {
                ModList.get().forEachModContainer((modid, modContainer) -> {
                    if (MidnightConfig.configInstances.containsKey(modid) && !MidnightLib.hiddenMods.contains(modid)) {
                        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (minecraftClient, screen) -> MidnightConfig.getScreen(screen, modid));
                    }
                });
                new AutoCommand().onInitializeServer();
            }
        }

        @EventBusSubscriber(modid = MOD_ID)
        public static class MidnightLibEvents {
            @SubscribeEvent
            public static void registerCommands(RegisterCommandsEvent event) {
                try {
                    commands.forEach(command -> event.getDispatcher().register(command));
                }
                catch (ConcurrentModificationException ignored) {}
            }
        }
    *///?}

    //? if forge {
    /*public MidnightLib() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (remote, server) -> true));
        if (PlatformFunctions.isClientEnv()) this.onInitializeClient();
    }

    public static List<LiteralArgumentBuilder<CommandSourceStack>> commands = new ArrayList<>();

        @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public static class MidnightLibBusEvents {
            @SubscribeEvent
            public static void onPostInit(FMLClientSetupEvent event) {
                ModList.get().forEachModContainer((modid, modContainer) -> {
                    if (MidnightConfig.configInstances.containsKey(modid) && !MidnightLib.hiddenMods.contains(modid)) {
                        modContainer.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraftClient, screen) -> MidnightConfig.getScreen(screen, modid)));
                    }
                });
                new AutoCommand().onInitializeServer();
            }
        }

        @Mod.EventBusSubscriber(modid = MOD_ID)
        public static class MidnightLibEvents {
            @SubscribeEvent
            public static void registerCommands(RegisterCommandsEvent event) {
                try {
                    commands.forEach(command -> event.getDispatcher().register(command));
                }
                catch (ConcurrentModificationException ignored) {}
            }
        }
    *///?}
}
