package eu.midnightdust.core;

import eu.midnightdust.core.config.MidnightLibConfig;
import eu.midnightdust.lib.config.AutoCommand;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MidnightLib {
    public static List<String> hiddenMods = new ArrayList<>();
    public static final Logger LOGGER = LoggerFactory.getLogger("midnightlib");

    @Environment(EnvType.CLIENT)
    public static void onInitializeClient() {
        System.setProperty("java.awt.headless", "false");
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { LOGGER.error("Error setting system look and feel", e); }
        MidnightLibConfig.init("midnightlib", MidnightLibConfig.class);
    }
    @Environment(EnvType.SERVER)
    public static void onInitializeServer() {
        MidnightConfig.configClass.forEach((modid, config) -> {
            for (Field field : config.getFields()) {
                if (field.isAnnotationPresent(MidnightConfig.Entry.class) && !field.isAnnotationPresent(MidnightConfig.Client.class) && !field.isAnnotationPresent(MidnightConfig.Hidden.class))
                    new AutoCommand(field, modid).register();
            }
        });
    }
}
