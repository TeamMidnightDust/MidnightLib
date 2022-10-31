package eu.midnightdust.core;

import eu.midnightdust.lib.config.AutoCommand;
import eu.midnightdust.lib.config.MidnightConfig;

import java.lang.reflect.Field;

public class MidnightLibServer {

    public static void onInitializeServer() {
        MidnightConfig.configClass.forEach((modid, config) -> {
            for (Field field : config.getFields()) {
                if (field.isAnnotationPresent(MidnightConfig.Entry.class) && !field.isAnnotationPresent(MidnightConfig.Client.class) && !field.isAnnotationPresent(MidnightConfig.Hidden.class))
                    new AutoCommand(field, modid).register();
            }
        });
    }
}
