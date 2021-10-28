package eu.midnightdust.core;

import eu.midnightdust.lib.config.AutoCommand;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.DedicatedServerModInitializer;

import java.lang.reflect.Field;

public class MidnightLibServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        MidnightConfig.configClass.forEach((modid, config) -> {
            for (Field field : config.getFields()) {
                if (field.isAnnotationPresent(MidnightConfig.Entry.class))
                    new AutoCommand(field, modid).register();
            }
        });
    }
}
