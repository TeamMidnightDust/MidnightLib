package eu.midnightdust.core;

import eu.midnightdust.core.config.MidnightLibConfig;
import eu.midnightdust.lib.config.AutoCommand;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MidnightLib {
    public static List<String> hiddenMods = new ArrayList<>();

    @Environment(EnvType.CLIENT)
    public static void onInitializeClient() {
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
